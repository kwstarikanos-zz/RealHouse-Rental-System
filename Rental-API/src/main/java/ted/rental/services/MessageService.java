package ted.rental.services;

import ted.rental.database.DataAccessObject;
import ted.rental.database.entities.MessageEntity;
import ted.rental.database.entities.RenterEntity;
import ted.rental.exceptions.ErrorException;
import ted.rental.model.Message;
import ted.rental.model.outputs.InboxMessage;
import ted.rental.model.outputs.OutboxMessage;

import javax.ws.rs.core.Response;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class MessageService {

    public MessageService() {

    }

    public List<Message> getMessages(String username) {
        return null;
    }


    public Message getMessage(Integer id) {
        DataAccessObject dao = new DataAccessObject();
        dao.setParam("id", id);
        List<MessageEntity> messageEntities = dao.getTuples("message.findById");
        messageEntities.get(0).setRead(true);
        dao.updateTuple(messageEntities.get(0));
        return messageEntities.isEmpty() ? null : messageEntities.get(0).toModel();
    }

    public String retrieveMessageOwner(Integer message_id, String username) {
        DataAccessObject dao = new DataAccessObject();
        dao.setParam("id", message_id);
        List<MessageEntity> messageEntities = dao.getTuples("message.findById");
        if (messageEntities.get(0).getAuthor().getUsername().equals(username)) {
            throw new ErrorException(Response.Status.CONFLICT, "You have to wait for the other person to respond first or start a new conversation!");
        } else {
            return messageEntities.get(0).getRecipient().getUsername();
        }
    }

    public Integer startConversation(String username, Message message) {
        DataAccessObject dao = new DataAccessObject();
        dao.setParam("username", username);
        List<RenterEntity> recipient = dao.getTuples("renter.findByUsername");
        if (recipient.isEmpty()) {
            throw new ErrorException(Response.Status.NOT_FOUND, "The recipient does not exist!");
        }
        dao.setParam("username", message.getAuthor());
        List<RenterEntity> author = dao.getTuples("renter.findByUsername");
        if (author.isEmpty()) {
            throw new ErrorException(Response.Status.NOT_FOUND, "The author does not exist!");
        }
        MessageEntity messageEntity = new MessageEntity(message.getMessage(), message.getSubject(),
                false, false, new Timestamp(System.currentTimeMillis()), author.get(0),
                recipient.get(0), null);
        MessageEntity messageEntity1 = dao.insertTuple(messageEntity);
        return messageEntity1.getId();
    }

    public Integer answerMessage(String username, Message message, Integer message_id) {
        DataAccessObject dao = new DataAccessObject();
        dao.setParam("id", message_id);
        List<MessageEntity> latest_message = dao.getTuples("message.findById");
        if (latest_message.isEmpty()) {
            throw new ErrorException(Response.Status.NOT_FOUND, "The message you are answering does not exist");
        }
        dao.setParam("username", username);
        List<RenterEntity> recipient = dao.getTuples("renter.findByUsername");
        if (recipient.isEmpty()) {
            throw new ErrorException(Response.Status.NOT_FOUND, "The recipient does not exist!");
        }
        dao.setParam("username", message.getAuthor());
        List<RenterEntity> author = dao.getTuples("renter.findByUsername");
        if (author.isEmpty()) {
            throw new ErrorException(Response.Status.NOT_FOUND, "The author does not exist!");
        }

        MessageEntity messageEntity = new MessageEntity(message.getMessage(), message.getSubject(),
                false, false, new Timestamp(System.currentTimeMillis()), author.get(0),
                recipient.get(0), latest_message.isEmpty() ? null : latest_message.get(0));
        MessageEntity messageEntity1 = dao.insertTuple(messageEntity);
        return messageEntity1.getId();
    }


    public List<InboxMessage> getInbox(String username) {
        DataAccessObject dao = new DataAccessObject();
        dao.setParam("recipient", username);
        List<MessageEntity> messageEntities = dao.getTuples("messages.findInbox");
        if (messageEntities.isEmpty()) {
            throw new ErrorException(Response.Status.NOT_FOUND, "You have no messages in your inbox!");
        }
        List<InboxMessage> messages = new ArrayList<>();
        for (MessageEntity messageEntity : messageEntities) {
            messages.add(messageEntity.toInboxMessage());
        }
        return messages;

    }

    public List<OutboxMessage> getOutbox(String username) {
        DataAccessObject dao = new DataAccessObject();
        dao.setParam("author", username);
        List<MessageEntity> messageEntities = dao.getTuples("messages.findOutbox");
        if (messageEntities.isEmpty()) {
            throw new ErrorException(Response.Status.NOT_FOUND, "You have no messages in your outbox!");
        }
        List<OutboxMessage> messages = new ArrayList<>();
        for (MessageEntity messageEntity : messageEntities) {
            messages.add(messageEntity.toOutboxMessage());
        }
        return messages;
    }

    public List<Message> getDeletedMessages(String username) {
        DataAccessObject dao = new DataAccessObject();
        dao.setParam("username", username);
        List<MessageEntity> messageEntities = dao.getTuples("messages.findDeletedMessages");
        if (messageEntities.isEmpty()) {
            throw new ErrorException(Response.Status.NOT_FOUND, "You have no deleted messages!");
        }
        List<Message> messages = new ArrayList<>();
        for (MessageEntity messageEntity : messageEntities) {
            messages.add(messageEntity.toModel());
        }
        return messages;
    }


    public void deleteMessage(Integer id) {
        DataAccessObject dao = new DataAccessObject();
        dao.setParam("id", id);
        List<MessageEntity> messageEntities = dao.getTuples("message.findById");
        messageEntities.get(0).setDeleted(true);
        dao.updateTuple(messageEntities.get(0));
    }


}
