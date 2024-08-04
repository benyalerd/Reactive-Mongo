package com.example.kafka;

public enum EventType {
    DEMO("demo");
    private String value;

    EventType(String value) {
        this.value = value;
    }

    public static EventType fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (EventType e : EventType.values()) {
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
