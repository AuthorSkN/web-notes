package com.webnotes.data.entity;


import javax.persistence.*;

@Entity
@Table(name="action")
public final class Action implements DataEntity{

    private static final int DEFAULT_ID = 0;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_action")
    private Integer id;
    @Column(name="text")
    private String text;
    @Column(name="passed")
    private Boolean passed;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_note")
    private Note note;

    public Action() {}

    public Action(String text, Boolean passed) {
        this.text = text;
        this.passed = passed;
        this.id = DEFAULT_ID;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getPassed() {
        return passed;
    }

    public void setPassed(Boolean passed) {
        this.passed = passed;
    }

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }
}
