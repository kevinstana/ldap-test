package gr.hua.it21774.controllers;

import java.time.Instant;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import gr.hua.it21774.entities.Thesis;
import gr.hua.it21774.requests.CreateThesisRequest;
import gr.hua.it21774.respository.ThesisRepository;
import gr.hua.it21774.userdetails.AppUserDetails;
import jakarta.validation.Valid;

@RestController
public class ThesisController {

    private final ThesisRepository thesisRepository;

    public ThesisController(ThesisRepository thesisRepository) {
        this.thesisRepository = thesisRepository;
    }

    @PostMapping("/theses")
    public ResponseEntity<?> createThesis(@Valid @RequestBody CreateThesisRequest request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long createdBy = ((AppUserDetails) authentication.getPrincipal()).getId();

        if (request.getDescription().isBlank()) {
            request.setDescription("Pending description");
        }

        Thesis thesis = new Thesis(request.getTitle(), request.getDescription(), createdBy, Instant.now(), null, null,
                createdBy, null, request.getSecondReviewerId(), request.getThirdReviewerId(), 1L, null, null, null,
                null, null);

        thesisRepository.save(thesis);

        return ResponseEntity.ok().body("hello from thesis");
    }
}
