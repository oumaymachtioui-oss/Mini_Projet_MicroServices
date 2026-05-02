package edu.isgb.school.test;
import edu.isgb.school.entities.Course;
import edu.isgb.school.entities.Instructor;
import edu.isgb.school.entities.School;
import edu.isgb.school.entities.Student;
import edu.isgb.school.entities.*;
import edu.isgb.school.service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/school")
public class TestSchoolController {

    @Autowired
    private SchoolService schoolService;


    //   a. POST /api/school/schools → Créer une School
    /**
     * Corps JSON attendu (exemple Postman) :
     * {
     *   "school":      { "name": "ISG Bizerte", "phone": 72000000 },
     *   "students":    [ { "name": "Oumayma chtioui" }, { "name": "Manel Hichri" },{ "name": "Kochbati Eya" } ],
     *   "instructors": [ { "name": "Dr. Wafa Neji"},{"name" : "Dr.Hela Eljabar"} ],
     *   "departments": [ { "name": "Informatique" }, { "name": "Marketing" } ]
     * }
     */
    @PostMapping("/addSchool")
    public ResponseEntity<School> createSchool(@RequestBody School school) {
        // Spring remplit automatiquement l'objet School et ses listes internes
        School created = schoolService.createSchool(school, school.getStudents(), school.getInstructors(), school.getDepartments());
        return ResponseEntity.ok(created);
    }

    //b. GET /api/school/schools/{id} → Récupérer une école par son ID
    /**
     * @PathVariable Integer id :
     *   Extrait l'id depuis l'URL. Ex: /api/school/schools/1 → id = 1
     *
     * ResponseEntity.ok() :
     *   Retourne le code HTTP 200 OK avec le corps JSON.
     */
    @GetMapping("/schools/{id}")
    public ResponseEntity<School> getSchoolById(@PathVariable Integer id) {
        return ResponseEntity.ok(schoolService.getSchoolById(id));
    }
    // ══════════════════════════════════════════════════════════════════════
    //   c. POST /api/school/students → Créer un Student
    // ══════════════════════════════════════════════════════════════════════
    /**
     * URL : http://localhost:8080/api/school/addStudent?schoolId=1
     * Corps JSON :
     * {
     *   "name": "Oumayma chtioui",
     *   "birthDate": "2005-06-05",
     *   "address": {
     *     "street": "Rue 13 Mars",
     *     "city": "Bizerte",
     *     "postalCode": "7014"
     *   }
     * }
     */
    @PostMapping("/addStudent")
    public ResponseEntity<Student> createStudent(@RequestBody Student student, @PathVariable Integer schoolId) {
        Student created = schoolService.createStudent(student, student.getAddress(), schoolId);
        return ResponseEntity.ok(created);
    }
    //   d. GET /api/school/students → Lister tous les Students
    // 3. Récupérer tous les étudiants
    @GetMapping("/getAllStudents")
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(schoolService.getAllStudents());
    }

    //   e. POST /api/school/instructors → Créer un Instructor
    /**
     * Corps JSON attendu :
     * {
     *   "instructor": { "name": "Dr.Wafa Neji","Dr.Hela Eljabar" },
     *   "courses":    [ { "name": "Java Avancé" },
     *                   { "name": "Architecture logicielle avec Spring Boot " },
     *                  ]
     * }
     */
    // 4. Créer un instructeur (avec ses cours si présents)
    @PostMapping("/addInstructor")
    public ResponseEntity<Instructor> createInstructor(@RequestBody Instructor instructor) {
        Instructor created = schoolService.createInstructor(instructor, instructor.getCourses());
        return ResponseEntity.ok(created);
    }

    //   f. GET /api/school/instructors → Chercher par nom
    /**
     * @PathVariable String name :
     *   Extrait le nom directement depuis l'URL.
     *   Ex: /api/school/instructors/Hela
     *
     * schoolService.getInstructorsByName(name) :
     *   Appelle le service pour chercher les instructeurs contenant ce nom.
     */
    @GetMapping("/instructors/{name}")
    public ResponseEntity<List<Instructor>> getInstructorsByName(@PathVariable String name) {
        return ResponseEntity.ok(schoolService.getInstructorsByName(name));
    }
    //   g. GET /api/school/instructors/{id} → Instructor par id

    // 5. Récupérer un instructeur par ID
    @GetMapping("/getInstructor/{id}")
    public ResponseEntity<Instructor> getInstructorById(@PathVariable Integer id) {
        return ResponseEntity.ok(schoolService.getInstructorById(id));
    }

    //   h. GET /api/school/courses/{id} → Course par id
    @GetMapping("/courses/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Integer id) {
        return ResponseEntity.ok(schoolService.getCourseById(id));
    }

    //   i. GET /api/school/instructors/{id}/courses → Lister les cours d'un instructeur spécifique
    @GetMapping("/instructors/{id}/courses")
    public ResponseEntity<List<Course>> getCoursesByInstructor(@PathVariable Integer id) {
        return ResponseEntity.ok(schoolService.getCoursesByInstructorId(id));
    }

    //   j. POST /api/school/instructors/{id}/courses → Ajouter un nouveau cours à un instructeur existant
    /*** Corps JSON attendu : { "name": "Programmation Web2" }
     * @RequestBody Course course :
     *   Jackson désérialise directement en objet Course
     *   Plus simple car Course a peu de champs */
    @PostMapping("/addCourseToInstructor/{id}")
    public ResponseEntity<Instructor> addCourseToInstructor(@PathVariable Integer id, @RequestBody Course course) {
        Instructor updated = schoolService.addCourseToInstructor(id, course);
        return ResponseEntity.ok(updated);
    }
}

