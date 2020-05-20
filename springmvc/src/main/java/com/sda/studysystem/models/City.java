package com.sda.studysystem.models;

import lombok.Data;

import javax.persistence.*;

/**
 * City model
 */

@Entity
@Data
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private boolean isActive;
    @OneToOne
    private County county;
    @OneToOne
    private Country country;
}
