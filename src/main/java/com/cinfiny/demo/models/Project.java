package com.cinfiny.demo.models;

import java.util.ArrayList;
import java.util.List;

public class Project {

    private String orderNumber;

    private String name;

    private String description;

    private List<Area> rooms = new ArrayList<>();

    private Client client;

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Area> getRooms() {
        return rooms;
    }

    public void setRooms(List<Area> rooms) {
        this.rooms = rooms;
    }
}
