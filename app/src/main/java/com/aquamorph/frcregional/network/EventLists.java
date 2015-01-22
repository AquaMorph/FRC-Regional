package com.aquamorph.frcregional.network;

public class EventLists {
    public long id;
    public String name;
    public String event_code;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEvent_code() {
        return event_code;
    }

    public void setEvent_code(String event_code) {
        this.event_code = event_code;
    }

    public void setId(long id) {
        this.id  = id;
    }
}
