package com.webnotes.data.entity;

import javax.persistence.*;

@Entity
@Table(name="log")
public class Log implements DataEntity{

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id_log")
    private Integer id;
    @Column(name="type")
    private String type;
    @Column(name="text")
    private String text;
    @Column(name="operation")
    private String operation;

    @Override
    public Integer getId() {
        return null;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }
}
