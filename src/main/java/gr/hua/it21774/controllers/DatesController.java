package gr.hua.it21774.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import gr.hua.it21774.entities.AssignmentDates;
import gr.hua.it21774.entities.ReviewingDates;
import gr.hua.it21774.requests.DateRequest;
import gr.hua.it21774.responses.MessageRespone;
import gr.hua.it21774.service.DateService;

@RestController
public class DatesController {

    private final DateService dateService;

    public DatesController(DateService dateService) {
        this.dateService = dateService;
    }

    @PostMapping("/assignment-dates")
    public ResponseEntity<?> assignmentDates(@RequestBody DateRequest request)
            throws Exception {

        dateService.saveAssignmentDates(request.getFrom(), request.getTo());

        return ResponseEntity.ok().body(new MessageRespone("Dates saved"));
    }

    @PostMapping("/reviewing-dates")
    public ResponseEntity<?> reviewingDates(@RequestBody DateRequest request)
            throws Exception {

        dateService.saveReviewingDates(request.getFrom(), request.getTo());

        return ResponseEntity.ok().body(new MessageRespone("Dates saved"));
    }

    @GetMapping("/assignment-dates")
    public ResponseEntity<?> getAssignmentDates()
            throws Exception {

        AssignmentDates dates = dateService.getAssignmentDates();

        return ResponseEntity.ok().body(dates);
    }

    @GetMapping("/reviewing-dates")
    public ResponseEntity<?> getReviewingDates()
            throws Exception {

        ReviewingDates dates = dateService.getReviewingDates();

        return ResponseEntity.ok().body(dates);
    }
}