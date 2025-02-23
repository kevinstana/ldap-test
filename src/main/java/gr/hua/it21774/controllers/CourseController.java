package gr.hua.it21774.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gr.hua.it21774.dto.BasicCourseDTO;
import gr.hua.it21774.entities.Course;
import gr.hua.it21774.requests.CourseQueryRequest;
import gr.hua.it21774.service.CourseService;

@RestController
@RequestMapping("")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping("/courses")
    public ResponseEntity<?> searchCourses(@RequestParam(required = false) String name,
            @RequestBody CourseQueryRequest request) {

        List<BasicCourseDTO> courses = courseService.searchCourses(name, request.getSelectedCourses());

        return ResponseEntity.ok().body(courses);
    }

    @GetMapping("/courses/{thesisId}")
    public ResponseEntity<?> getThesisCourses(@PathVariable Long thesisId) {

        List<Course> courses = courseService.getThesisCourses(thesisId);

        return ResponseEntity.ok().body(courses);
    }

}
