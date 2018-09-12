package com.pan.solver.entity;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = Photo.TABLE_NAME, indexes = {
        @Index(name = "id", columnList = "id", unique = true),
        @Index(name = "photoName", columnList = "photoName", unique = true)
})
public class Photo {

    public static final String TABLE_NAME = "Photo";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "photoName", columnDefinition = "VARCHAR(255) DEFAULT ''", nullable = false, unique = true)
    private String photoName;

}
