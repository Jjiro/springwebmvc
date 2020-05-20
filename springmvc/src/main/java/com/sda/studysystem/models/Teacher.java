package com.sda.studysystem.models;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

/**
 * Teacher model
 */

@Entity
@Data
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private LocalDate joinDate;
    private boolean isActive;
    @OneToOne
    private School school;
    @OneToMany
    private List<SpecializedField> specializedFields;
}
