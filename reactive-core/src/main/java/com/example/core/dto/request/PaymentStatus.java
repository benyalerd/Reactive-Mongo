package com.example.core.dto.request;

public enum PaymentStatus {

    AWAITING_PAYMENT("AWAITING_PAYMENT"),
    COMPLETED("COMPLETED"),
    CANCELED("CANCELED");

    private String value;

    PaymentStatus(String value) {
        this.value = value;
    }

    public static PaymentStatus fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (PaymentStatus e : PaymentStatus.values()) {
            if (e.value().equals(value)) {
                return e;
            }
        }
        throw new IllegalArgumentException("Invalid value " + value);
    }

    public String value() {
        return value;
    }
}
