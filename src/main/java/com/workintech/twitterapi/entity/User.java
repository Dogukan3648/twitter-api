package com.workintech.twitterapi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "email")
    @NotBlank
    @Size(max = 255)
    @Email
    private String email;
    @NotBlank
    @Size(max = 40)
    @Column(name = "first_name")
    private String firstName;
    @NotBlank
    @Size(max = 40)
    @Column(name = "last_name")
    private String lastName;
    @NotBlank
    @Size(max = 255)
    @Column(name = "password")
    private String password;
}
