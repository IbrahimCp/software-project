package kz.ibrahim.SoftwareProject.models;


import jakarta.persistence.Entity;

public class Handle {
    private String handle;

    public Handle(String handle) {
        this.handle = handle;
    }

    public Handle() {
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }
}
