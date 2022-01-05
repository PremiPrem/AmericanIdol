package com.americanidol.dependencymanager.model;

public class Property {
    private String property;
    private String value;
    private String action;
    private  String uuid;

    public Property(){

    }

    public Property( String property, String action, String value, String uuid) {

        this.property = property;
        this.action = action;
        this.value = value;
        this.uuid = uuid;
    }


    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
