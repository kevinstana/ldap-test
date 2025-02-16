package gr.hua.it21774.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gr.hua.it21774.config.AuthEntryPointJwt;
import gr.hua.it21774.dto.BasicCourseDTO;
import gr.hua.it21774.requests.CourseQueryRequest;
import gr.hua.it21774.service.CourseService;

@RestController
@RequestMapping("")
public class CourseController {

    private final CourseService courseService;

        private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);


    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping("/courses")
    public ResponseEntity<?> searchCourses(@RequestParam(required = false) String name, @RequestBody CourseQueryRequest request) {


        logger.error("{}", request.getSelectedCourses());

        List<BasicCourseDTO> lol = courseService.searchCourses(name, request.getSelectedCourses());

        return ResponseEntity.ok().body(lol);
    }
}
