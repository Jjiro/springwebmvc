package com.sda.studysystem.models;

import lombok.Data;

import javax.persistence.*;

/**
 * Specialized Field model
 */

@Entity
@Data
public class SpecializedField {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @OneToOne
    private Category category;
    private boolean isActive;
}
