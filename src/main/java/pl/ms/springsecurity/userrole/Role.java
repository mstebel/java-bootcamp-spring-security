package pl.ms.springsecurity.userrole;

public enum Role {
        USER("użytkownik"), ADMIN("admin");

        public String getDisplayValue() {
                return displayValue;
        }

        Role(String displayValue) {
                this.displayValue = displayValue;
        }

        private final String displayValue;

}
