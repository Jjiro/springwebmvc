package com.sda.studysystem.models;


import lombok.Data;

import javax.persistence.*;

/**
 * County model
 */

@Entity
@Data
public class County {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private boolean isActive;
    @OneToOne
    private Country country;
}
