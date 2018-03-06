package com.example.danielcampbell.a800868709_midterm;

/**
 * Created by danielcampbell on 10/16/17.
 */

import java.io.Serializable;


/**
 * Created by danielcampbell on 10/23/17.
 * Daniel Campbell
 * Group21InClass06.zip
 */


public class Source implements Serializable {

    String id;
    String name;

    public Source(){

    }

    public Source(String id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
        return "Source{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
