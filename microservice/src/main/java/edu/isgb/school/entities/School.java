package edu.isgb.school.entities;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "t_school")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class School {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PK_school")
    private Integer idSchool;

    @Column(name = "cl_name_school")
    private String name;

    @Column(name = "cl_phone_school")
    private Integer phone;

    @OneToMany(
            mappedBy      = "school",
            cascade       = CascadeType.ALL,
            orphanRemoval = true,
            fetch         = FetchType.LAZY
    )
    private List<Departement> departments = new ArrayList<>();

    @OneToMany(
            mappedBy      = "school",
            cascade       = CascadeType.ALL,
            orphanRemoval = true,
            fetch         = FetchType.LAZY
    )
    private List<Student> students = new ArrayList<>();

    @OneToMany(
            mappedBy      = "school",
            cascade       = CascadeType.ALL,
            orphanRemoval = true,
            fetch         = FetchType.LAZY
    )
    private List<Instructor> instructors = new ArrayList<>();
    public void addDepartment(Departement department) {
        departments.add(department);
        department.setSchool(this);
    }

    public void addStudent(Student student) {
        students.add(student);
        student.setSchool(this);
    }

    public void addInstructor(Instructor instructor) {
        instructors.add(instructor);
        instructor.setSchool(this);
    }
}
