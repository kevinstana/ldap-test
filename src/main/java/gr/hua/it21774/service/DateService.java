package gr.hua.it21774.service;

import java.time.Instant;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import gr.hua.it21774.entities.AssignmentDates;
import gr.hua.it21774.entities.ReviewingDates;
import gr.hua.it21774.exceptions.GenericException;
import gr.hua.it21774.respository.AssignmentDatesRepository;
import gr.hua.it21774.respository.ReviewingDatesRepository;

@Service
public class DateService {

    private final AssignmentDatesRepository assignmentDatesRepository;
    private final ReviewingDatesRepository reviewingDatesRepository;

    public DateService(AssignmentDatesRepository assignmentDatesRepository,
            ReviewingDatesRepository reviewingDatesRepository) {
        this.assignmentDatesRepository = assignmentDatesRepository;
        this.reviewingDatesRepository = reviewingDatesRepository;
    }

    public void saveAssignmentDates(Instant from, Instant to) {
        List<AssignmentDates> dates = assignmentDatesRepository.findAll();
        if (dates.size() == 1) {
            assignmentDatesRepository.updateDate(dates.get(0).getId(), from, to);
            return;
        } else if (dates.size() == 0) {
            AssignmentDates date = new AssignmentDates(from, to);
            assignmentDatesRepository.save(date);
            return;
        }

        throw new GenericException(HttpStatus.BAD_REQUEST, "Something went wrong");
    }

    public void saveReviewingDates(Instant from, Instant to) {
        List<ReviewingDates> dates = reviewingDatesRepository.findAll();
        if (dates.size() == 1) {
            reviewingDatesRepository.updateDate(dates.get(0).getId(), from, to);
            return;
        } else if (dates.size() == 0) {
            ReviewingDates date = new ReviewingDates(from, to);
            reviewingDatesRepository.save(date);
            return;
        }

        throw new GenericException(HttpStatus.BAD_REQUEST, "Something went wrong");
    }

    public AssignmentDates getAssignmentDates() {
        List<AssignmentDates> dates = assignmentDatesRepository.findAll();
        if (dates.size() <= 1) {
            return dates.get(0);
        }

        throw new GenericException(HttpStatus.BAD_REQUEST, "Something went wrong");
    }

    public ReviewingDates getReviewingDates() {
        List<ReviewingDates> dates = reviewingDatesRepository.findAll();
        if (dates.size() <= 1) {
            return dates.get(0);
        }

        throw new GenericException(HttpStatus.BAD_REQUEST, "Something went wrong");
    }
}
