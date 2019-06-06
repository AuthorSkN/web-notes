package com.webnotes.data.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="new_schema.note")
public class Note implements DataEntity {

    private static final int DEFAULT_ID = 0;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id_note")
    private Integer id;
    @Column(name="name")
    private String name;
    @Column(name="text")
    private String text;
    @Column(name="create_date")
    private Date createDate;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy="note")
    private Set<Action> actions = new HashSet<>();

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_folder")
    private Group group;

    public Note() {}

    public Note(String name, String text, Date createDate) {
        this.id  = DEFAULT_ID;
        this.name = name;
        this.text = text;
        this.createDate = createDate;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Set<Action> getActions() {
        return actions;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
