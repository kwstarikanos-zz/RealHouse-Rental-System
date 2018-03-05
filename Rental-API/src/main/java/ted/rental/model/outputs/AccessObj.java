package ted.rental.model.outputs;

public class AccessObj {
    private Access access;

    private class Access {
        private String token;
        public Access(String token) {
            this.token = token;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }

    public AccessObj(String accessToken) {
        this.access = new Access(accessToken);
    }

    public Access getAccess() {
        return access;
    }

    public void setAccess(Access access) {
        this.access = access;
    }
}
