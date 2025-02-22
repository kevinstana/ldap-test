package gr.hua.it21774.service;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import gr.hua.it21774.entities.Thesis;
import gr.hua.it21774.enums.ERole;
import gr.hua.it21774.enums.EThesisStatus;
import gr.hua.it21774.exceptions.GenericException;
import gr.hua.it21774.requests.CreateThesisRequest;
import gr.hua.it21774.respository.CourseThesisRepository;
import gr.hua.it21774.respository.ThesisRepository;
import gr.hua.it21774.respository.UserRepository;
import gr.hua.it21774.userdetails.AppUserDetails;
import jakarta.transaction.Transactional;

@Service
public class ThesisService {

    private final ThesisRepository thesisRepository;
    private final UserRepository userRepository;
    private final CourseThesisRepository courseThesisRepository;


    public ThesisService(ThesisRepository thesisRepository, UserRepository userRepository, CourseThesisRepository courseThesisRepository) {
        this.thesisRepository = thesisRepository;
        this.userRepository = userRepository;
        this.courseThesisRepository = courseThesisRepository;
    }

    @Transactional
    public void handleThesisCreation(CreateThesisRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long createdBy = ((AppUserDetails) authentication.getPrincipal()).getId();

        Long secondReviewerId = Long.valueOf(request.getSecondReviewerId());
        Long thirdReviewerId = Long.valueOf(request.getThirdReviewerId());

        Set<Long> reviewerIds = new HashSet<Long>(
                Arrays.asList(secondReviewerId, thirdReviewerId));
        if (reviewerIds.contains(createdBy)) {
            throw new GenericException(HttpStatus.BAD_REQUEST, "You can't assing yourself as an extra reviewer.");
        }

        if (thesisRepository.existsByTitle(request.getTitle())) {
            throw new GenericException(HttpStatus.CONFLICT, "A thesis with this title already exists.");
        }
        validateReviewerPair(secondReviewerId, thirdReviewerId);

        if (request.getDescription().isBlank()) {
            request.setDescription("Pending description");
        }

        Long statusId = thesisRepository.findIdByStatus(EThesisStatus.AVAILABLE).get();

        Instant now = Instant.now();
        Thesis thesis = new Thesis(request.getTitle(), request.getDescription(), createdBy, now, now, null, createdBy,
                createdBy, null, secondReviewerId, thirdReviewerId, statusId, null, null, null, null);

        Thesis createdThesis = thesisRepository.save(thesis);
        courseThesisRepository.saveCoursesForThesis(request.getRecommendedCourses(), createdThesis.getId());
    }

    private void validateReviewerPair(Long secondReviewerId, Long thirdReviewerId) {

        if (secondReviewerId.equals(thirdReviewerId)) {
            throw new GenericException(HttpStatus.BAD_REQUEST, "The reviewers can't have the same id.");
        }

        Map<Long, String> map = Collections.unmodifiableMap(new HashMap<>() {
            {
                put(secondReviewerId, "Second reviewer is not a professor");
                put(thirdReviewerId, "Third reviewer is not a professor");
            }
        });

        map.forEach((id, errorMessage) -> {
            if (!userRepository.hasRole(id, ERole.PROFESSOR)) {
                throw new GenericException(HttpStatus.BAD_REQUEST, errorMessage);
            }
        });

        return;
    }
}
