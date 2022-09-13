package com.garcia.giovane.form.impl.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "user_entity")
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private LocalDate birthDate;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "image")
    private byte[] image;
    private String imageName;
}
