package ted.rental.model.outputs;

public class UsernameObj {
    private Username username;

    private class Username {
        private Boolean available;

        public Username(Boolean available) {
            this.available = available;
        }

        public Boolean getAvailable() {
            return available;
        }

        public void setAvailable(Boolean available) {
            this.available = available;
        }
    }

    public UsernameObj(Boolean available){
        username = new Username(available);
    }

    public Username getUsername() {
        return username;
    }

    public void setUsername(Username username) {
        this.username = username;
    }
}
