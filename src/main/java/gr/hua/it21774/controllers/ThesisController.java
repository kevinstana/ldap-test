package gr.hua.it21774.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import gr.hua.it21774.requests.CreateThesisRequest;
import gr.hua.it21774.respository.ThesisRepository;
import gr.hua.it21774.service.ThesisService;
import jakarta.validation.Valid;

@RestController
public class ThesisController {

    private final ThesisService thesisService;

    public ThesisController(ThesisRepository thesisRepository, ThesisService thesisService) {
        this.thesisService = thesisService;
    }

    @PostMapping("/theses")
    public ResponseEntity<?> createThesis(@Valid @RequestBody CreateThesisRequest request) {
        thesisService.handleThesisCreation(request);

        return ResponseEntity.ok().body(request);
    }
}
