package edu.isgb.school.service;

import edu.isgb.school.entities.*;
import edu.isgb.school.repository.CourseRepository;
import edu.isgb.school.repository.InstructorRepository;
import edu.isgb.school.repository.SchoolRepository;
import edu.isgb.school.repository.StudentRepository;
import edu.isgb.school.entities.*;
import edu.isgb.school.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Transactional
public class SchoolService {

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private InstructorRepository instructorRepository;

    @Autowired
    private CourseRepository courseRepository;

    // a. Créer une nouvelle School avec ses listes
    public School createSchool(School school, List<Student> students, List<Instructor> instructors, List<Departement> departments) {
        if (students != null) {
            for (Student s : students) {
                school.addStudent(s);
            }
        }
        if (instructors != null) {
            for (Instructor i : instructors) {
                school.addInstructor(i);
            }
        }
        if (departments != null) {
            for (Departement d : departments) {
                school.addDepartment(d);
            }
        }
        return schoolRepository.save(school);
    }

    // b. Retourner une School par id
    public School getSchoolById(Integer id) {
        return schoolRepository.findById(id).orElseThrow(() -> new RuntimeException("School non trouvée"));
    }

    // c. Créer un nouveau Student avec son Address et sa School
    public Student createStudent(Student student, Adresse address, Integer schoolId) {
        student.setAddress(address);
        if (schoolId != null) {
            School school = getSchoolById(schoolId);
            student.setSchool(school); // Lie l'étudiant à l'école
            // On sauvegarde l'étudiant directement pour garantir le retour de l'ID
            return studentRepository.save(student);
        }
        return studentRepository.save(student);
    }

    // d. Lister tous les Students
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // e. Créer un Instructor avec une liste de cours
    public Instructor createInstructor(Instructor instructor, List<Course> courses) {
        if (courses != null && !courses.isEmpty()) {
            List<Course> savedCourses = courseRepository.saveAll(courses);
            instructor.setCourses(savedCourses);
            // Maintien de la relation bidirectionnelle ManyToMany
            for (Course c : savedCourses) {
                c.getInstructors().add(instructor);
            }
        }
        return instructorRepository.save(instructor);
    }

    // f. Lister les Instructors par nom
    public List<Instructor> getInstructorsByName(String name) {
        return instructorRepository.findByNameContainingIgnoreCase(name);
    }

    // g. Retourner un Instructor par id
    public Instructor getInstructorById(Integer id) {
        return instructorRepository.findById(id).orElseThrow(() -> new RuntimeException("Instructor non trouvé"));
    }

    // h. Retourner un Course par id
    public Course getCourseById(Integer id) {
        return courseRepository.findById(id).orElseThrow(() -> new RuntimeException("Course non trouvé"));
    }

    // i. Lister les Courses d'un Instructor par son id
    public List<Course> getCoursesByInstructorId(Integer instructorId) {
        return getInstructorById(instructorId).getCourses();
    }

    // j. Rajouter un nouveau Course à un Instructor existant
    public Instructor addCourseToInstructor(Integer instructorId, Course course) {
        Instructor instructor = getInstructorById(instructorId);
        Course savedCourse = courseRepository.save(course);
        instructor.getCourses().add(savedCourse);
        // Maintien de la relation bidirectionnelle ManyToMany
        savedCourse.getInstructors().add(instructor);
        return instructorRepository.save(instructor);
    }
}
