package es.upm.miw.lost_found_spring.infrastructure.api.http_errors;

public enum Role {
    ADMIN, OPERATOR, AUTHENTICATED;

    public static final String PREFIX = "ROLE_";

    public static Role of(String withPrefix) {
        return Role.valueOf(withPrefix.replace(Role.PREFIX, ""));
    }

    public String withPrefix() {
        return PREFIX + this.toString();
    }

}
