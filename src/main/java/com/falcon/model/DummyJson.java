package com.falcon.model;


import javax.persistence.*;

@Entity
@Table(name = "dumpjson")
public class DummyJson {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;


    public DummyJson() {
    }

    public DummyJson(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "DummyJson{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
