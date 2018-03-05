package ted.rental.model.outputs;

public class PhoneObj {
    private Phone phone;

    private class Phone {
        private Boolean available;

        public Phone(Boolean available) {
            this.available = available;
        }

        public Boolean getAvailable() {
            return available;
        }

        public void setAvailable(Boolean available) {
            this.available = available;
        }
    }

    public PhoneObj(Boolean available){
        phone = new Phone(available);
    }

    public Phone getPhone() {
        return phone;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }
}
