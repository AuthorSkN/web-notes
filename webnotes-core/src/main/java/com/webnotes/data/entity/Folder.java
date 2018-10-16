package com.webnotes.data.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="folder")
public final class Folder implements DataEntity{

    private static final int DEFAULT_ID = 0;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id_folder")
    private Integer id;
    @Column(name="name")
    private String name;
    @Column(name="create_date")
    private Date createDate;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy="folder")
    private Set<Note> notes = new HashSet<>();

    public Folder() {}

    public Folder(String name, Date createDate) {
        this.id = DEFAULT_ID;
        this.name = name;
        this.createDate = createDate;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setNotes(Set<Note> notes) {
        this.notes = notes;
    }
}
