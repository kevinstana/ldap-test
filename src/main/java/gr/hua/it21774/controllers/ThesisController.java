package gr.hua.it21774.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gr.hua.it21774.dto.DetailedThesisDTO;
import gr.hua.it21774.dto.ThesisDTO;
import gr.hua.it21774.entities.Course;
import gr.hua.it21774.enums.ERole;
import gr.hua.it21774.enums.EThesisStatus;
import gr.hua.it21774.exceptions.GenericException;
import gr.hua.it21774.requests.AssignStudentRequest;
import gr.hua.it21774.requests.CreateThesisRequest;
import gr.hua.it21774.requests.ThesisStatusChangeRequest;
import gr.hua.it21774.requests.UpdateThesisRequest;
import gr.hua.it21774.responses.DetailedThesisResponse;
import gr.hua.it21774.responses.MessageRespone;
import gr.hua.it21774.respository.ThesisRepository;
import gr.hua.it21774.respository.UserRepository;
import gr.hua.it21774.service.CourseService;
import gr.hua.it21774.service.ThesisService;
import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;

@RestController
public class ThesisController {

    private final ThesisService thesisService;
    private final CourseService courseService;
    private final UserRepository userRepository;
    private final ThesisRepository thesisRepository;

    public ThesisController(ThesisService thesisService,
            CourseService courseService, UserRepository userRepository, ThesisRepository thesisRepository) {
        this.thesisService = thesisService;
        this.courseService = courseService;
        this.userRepository = userRepository;
        this.thesisRepository = thesisRepository;
    }

    @PostMapping("/theses")
    public ResponseEntity<?> createThesis(@Valid @RequestBody CreateThesisRequest request) {
        thesisService.handleThesisCreation(request);

        return ResponseEntity.ok().body(new MessageRespone("Thesis Created!"));
    }

    @GetMapping("/theses")
    public ResponseEntity<?> getTheses(@RequestParam(required = false) String page,
            @RequestParam(required = false) String size, @RequestParam(required = false) List<String> statuses,
            @RequestParam(required = false) String query) {

        List<String> validSizeValues = Arrays.asList("5", "10", "15", "20", "ALL");

        Integer intPage = 0;
        try {
            intPage = Integer.parseInt(page);
            if (intPage < 0) {
                intPage = 0;
            }
        } catch (Exception e) {
            intPage = 0;
        }

        if (size == null || !validSizeValues.contains(size)) {
            size = "15";
        }

        if (query == null) {
            query = "";
        }

        if (query != null) {
            query = URLDecoder.decode(query, StandardCharsets.UTF_8);
        }

        List<EThesisStatus> validStatuses = new ArrayList<>();

        if (statuses != null) {
            for (String role : statuses) {
                try {
                    validStatuses.add(EThesisStatus.valueOf(role.toUpperCase()));
                } catch (IllegalArgumentException e) {
                }
            }
        }

        List<EThesisStatus> statusesToQuery = validStatuses.isEmpty() ? null : validStatuses;

        Page<ThesisDTO> theses = thesisService.getPagedTheses(intPage, size, query, statusesToQuery);

        return ResponseEntity.ok().body(theses);
    }

    @GetMapping("/theses/me")
    public ResponseEntity<?> getMyTheses(@RequestParam(required = false) String page,
            @RequestParam(required = false) String size) {

        List<String> validSizeValues = Arrays.asList("5", "10", "15", "20", "ALL");

        Integer intPage = 0;
        try {
            intPage = Integer.parseInt(page);
            if (intPage < 0) {
                intPage = 0;
            }
        } catch (Exception e) {
            intPage = 0;
        }

        if (size == null || !validSizeValues.contains(size)) {
            size = "15";
        }

        Page<ThesisDTO> theses = thesisService.getMyPagedTheses(intPage, size);

        return ResponseEntity.ok().body(theses);
    }

    @GetMapping("/theses/{id}")
    public ResponseEntity<?> getThesis(@PathVariable Long id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean isStudent = authentication.getAuthorities().stream()
                .anyMatch(auth -> "STUDENT".equals(auth.getAuthority()));

        boolean canMakeRequest = false;
        boolean hasMadeRequest = false;
        if (isStudent) {
            Claims accessTokenClaims = (Claims) authentication.getDetails();
            Long studentId = Long.parseLong(accessTokenClaims.getSubject());
            hasMadeRequest = thesisService.hasMadeRequest(studentId, id);

            if (!hasMadeRequest) {
                canMakeRequest = thesisService.canMakeRequest(studentId, id);
            }
        }

        DetailedThesisDTO thesis = thesisService.getThesis(id);
        List<Course> recommendedCourses = courseService.getThesisCourses(id);

        DetailedThesisResponse data = new DetailedThesisResponse(thesis, recommendedCourses, canMakeRequest,
                hasMadeRequest);

        if (thesis == null) {
            throw new GenericException(HttpStatus.NOT_FOUND, "Thesis not found");
        }

        return ResponseEntity.ok().body(data);
    }

    @PutMapping("/theses/{id}")
    public ResponseEntity<?> updateThesis(@PathVariable Long id, @Valid @RequestBody UpdateThesisRequest request) {

        thesisService.updateThesis(id, request);

        return ResponseEntity.ok().body(new MessageRespone("Thesis updated!"));
    }

    @PutMapping("/theses/{id}/assign-student")
    public ResponseEntity<?> assignStudent(@PathVariable Long id, @Valid @RequestBody AssignStudentRequest request)
            throws Exception {

        boolean isStudent = userRepository.hasRole(request.getStudentId(), ERole.STUDENT);

        if (!isStudent) {
            throw new GenericException(HttpStatus.BAD_REQUEST, "You can only assign a student");
        }

        if (thesisRepository.hasStudentThesis(request.getStudentId())) {
            throw new GenericException(HttpStatus.BAD_REQUEST, "Student already has a thesis");
        }

        if (request.getType().equals("APPROVE")) {
            thesisService.assignStudent(request);
            return ResponseEntity.ok().body(new MessageRespone("Request approved"));
        }

        if (request.getType().equals("UNDO")) {
            thesisService.undoThesisRequestAction(request);
            return ResponseEntity.ok().body(new MessageRespone("Action undone"));
        }

        thesisService.rejectRequest(request.getRequestId());
        return ResponseEntity.ok().body(new MessageRespone("Request rejected"));
    }

    @GetMapping("/theses/my-assignment")
    public ResponseEntity<?> myAssignment() {

        DetailedThesisDTO myAssignment = thesisService.getMyAssignment();

        if (myAssignment == null) {
            throw new GenericException(HttpStatus.BAD_REQUEST, "No assigned thesis found");
        }

        List<Course> recommendedCourses = courseService.getThesisCourses(myAssignment.getId());

        DetailedThesisResponse data = new DetailedThesisResponse(myAssignment, recommendedCourses, null, null);

        return ResponseEntity.ok().body(data);
    }

    @GetMapping("/theses/search")
    public ResponseEntity<?> searchThesis(@RequestParam(required = false) String query) {
        if (query != null) {
            query = URLDecoder.decode(query, StandardCharsets.UTF_8);
        }

        List<String> data = thesisService.searchTheses(query);

        return ResponseEntity.ok().body(data);
    }

    @DeleteMapping("/theses/{id}")
    public ResponseEntity<?> deleteThesis(@PathVariable Long id) throws Exception {

        thesisService.deleteThesis(id);

        return ResponseEntity.ok().body(new MessageRespone("Thesis deleted"));
    }

    @PostMapping("/theses/{id}/reset")
    public ResponseEntity<?> resetThesis(@PathVariable Long id) throws Exception {

        thesisService.resetThesis(id);

        return ResponseEntity.ok().body(new MessageRespone("Thesis reset"));
    }

    @PutMapping("/theses/{id}/status")
    public ResponseEntity<?> changeThesisStatus(@PathVariable Long id, @RequestBody ThesisStatusChangeRequest request) {

        thesisService.changeThesisStatus(id, EThesisStatus.valueOf(request.getStatus()));

        return ResponseEntity.ok().body(new MessageRespone("Status updated"));
    }

    @GetMapping("/theses/assigned-reviews")
    public ResponseEntity<?> getMyAssignedReviews(@RequestParam(required = false) String page,
            @RequestParam(required = false) String size) {

        List<String> validSizeValues = Arrays.asList("5", "10", "15", "20", "ALL");

        Integer intPage = 0;
        try {
            intPage = Integer.parseInt(page);
            if (intPage < 0) {
                intPage = 0;
            }
        } catch (Exception e) {
            intPage = 0;
        }

        if (size == null || !validSizeValues.contains(size)) {
            size = "15";
        }

        Page<ThesisDTO> theses = thesisService.getMyAssignedReviews(intPage, size);

        return ResponseEntity.ok().body(theses);
    }
}