package gr.hua.it21774.controllers;

import java.util.Arrays;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gr.hua.it21774.dto.DetailedThesisDTO;
import gr.hua.it21774.dto.ThesisDTO;
import gr.hua.it21774.exceptions.GenericException;
import gr.hua.it21774.requests.CreateThesisRequest;
import gr.hua.it21774.responses.MessageRespone;
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

    @GetMapping("/theses/{id}")
    public ResponseEntity<?> getThesis(@PathVariable Long id) {

        DetailedThesisDTO thesis = thesisService.getThesis(id);

        if (thesis == null) {
            throw new GenericException(HttpStatus.NOT_FOUND, "Thesis not found");
        }

        return ResponseEntity.ok().body(thesis);
    }

    @PutMapping("/theses")
    public ResponseEntity<?> updateThesis(@Valid @RequestBody CreateThesisRequest request) {

        return ResponseEntity.ok().body(new MessageRespone("Thesis Updated!"));
    }
}
