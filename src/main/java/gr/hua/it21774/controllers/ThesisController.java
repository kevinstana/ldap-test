package gr.hua.it21774.controllers;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gr.hua.it21774.dto.DetailedThesisDTO;
import gr.hua.it21774.dto.ThesisDTO;
import gr.hua.it21774.entities.Course;
import gr.hua.it21774.exceptions.GenericException;
import gr.hua.it21774.requests.CreateThesisRequest;
import gr.hua.it21774.requests.ThesisRequestReq;
import gr.hua.it21774.requests.UpdateThesisRequest;
import gr.hua.it21774.responses.DetailedThesisResponse;
import gr.hua.it21774.responses.MessageRespone;
import gr.hua.it21774.service.CourseService;
import gr.hua.it21774.service.ThesisService;
import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;

@RestController
public class ThesisController {

    private final ThesisService thesisService;
    private final CourseService courseService;

    public ThesisController(ThesisService thesisService,
            CourseService courseService) {
        this.thesisService = thesisService;
        this.courseService = courseService;
    }

    @PostMapping("/theses")
    public ResponseEntity<?> createThesis(@Valid @RequestBody CreateThesisRequest request) {
        thesisService.handleThesisCreation(request);

        return ResponseEntity.ok().body(new MessageRespone("Thesis Created!"));
    }

    @GetMapping("/theses")
    public ResponseEntity<?> getTheses(@RequestParam(required = false) String page,
            @RequestParam(required = false) String size, @RequestParam(required = false) String query) {

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

        Page<ThesisDTO> theses = thesisService.getPagedTheses(intPage, size, query);

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
                canMakeRequest = thesisService.canMakeRequest(studentId);
            }
        }

        DetailedThesisDTO thesis = thesisService.getThesis(id);
        List<Course> recommendedCourses = courseService.getThesisCourses(id);

        DetailedThesisResponse data = new DetailedThesisResponse(thesis, recommendedCourses, canMakeRequest, hasMadeRequest);

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

    @PostMapping("/theses/{id}/requests")
    public ResponseEntity<?> createThesisRequest(@PathVariable Long id, @ModelAttribute ThesisRequestReq request)
            throws Exception {

        thesisService.saveThesisRequest(id, request.getDescription(), request.getPdf());

        return ResponseEntity.ok().body(new MessageRespone("Request made!"));
    }
}
