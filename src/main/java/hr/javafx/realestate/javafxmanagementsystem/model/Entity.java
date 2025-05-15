package hr.javafx.realestate.javafxmanagementsystem.model;

import java.io.Serializable;

public abstract class Entity implements Serializable {
    private Long id;

    protected Entity(Long id){
        this.id = id;
    }
    protected Entity(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
