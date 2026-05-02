package edu.isgb.school.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "t_address")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Adresse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PK_ADDRESS")
    private Integer idAddress;

    @Column(name = "cl_street")
    private String street;

    @Column(name = "cl_city")
    private String city;

    @Column(name = "cl_postal_Code")
    private String postalCode;
}
