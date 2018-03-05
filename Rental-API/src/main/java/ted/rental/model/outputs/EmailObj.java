package ted.rental.model.outputs;

public class EmailObj {
    private Email email;

    private class Email {
        private Boolean available;

        public Email(Boolean available) {
            this.available = available;
        }

        public Boolean getAvailable() {
            return available;
        }

        public void setAvailable(Boolean available) {
            this.available = available;
        }
    }

    public EmailObj(Boolean available) {
        email = new Email(available);
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }
}
