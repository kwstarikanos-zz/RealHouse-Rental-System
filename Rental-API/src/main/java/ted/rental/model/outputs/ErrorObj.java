package ted.rental.model.outputs;

public class ErrorObj {
    private Error error;

    private class Error {
        private String type;
        private Integer code;
        private Object reason;

        public Error(String type, Integer code, Object reason) {
            this.type = type;
            this.code = code;
            this.reason = reason;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public Object getReason() {
            return reason;
        }

        public void setReason(Object message) {
            this.reason = reason;
        }
    }

    public ErrorObj(String type, Integer code, Object reason){
        error = new Error(type, code, reason);
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }
}
