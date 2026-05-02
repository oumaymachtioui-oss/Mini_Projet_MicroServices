package edu.isgb.school.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "t_student")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PK_Student")
    private Integer idStudent;

    @Column(name = "cl_name_student")
    private String name;

    //stocker dans la BD la date TemporalType.DATE: 2005-06-05
    @Temporal(TemporalType.DATE)
    @Column(name = "cl_birthdate")
    private Date birthDate;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "address_PK_ADDRESS")
    private Adresse address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_PK_school")
    @JsonIgnore
    private School school;
}
