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
import gr.hua.it21774.exceptions.GenericException;
import gr.hua.it21774.requests.CreateThesisRequest;
import gr.hua.it21774.respository.ThesisRepository;
import gr.hua.it21774.respository.UserRepository;
import gr.hua.it21774.userdetails.AppUserDetails;

@Service
public class ThesisService {

    private final ThesisRepository thesisRepository;
    private final UserRepository userRepository;

    public ThesisService(ThesisRepository thesisRepository, UserRepository userRepository) {
        this.thesisRepository = thesisRepository;
        this.userRepository = userRepository;
    }

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
        Thesis thesis = new Thesis(request.getTitle(), request.getDescription(), createdBy, Instant.now(), null, null,
                createdBy, null, secondReviewerId, thirdReviewerId, 1L, null, null, null,
                null, null);

        thesisRepository.save(thesis);
    }

    private void validateReviewerPair(Long secondReviewerId, Long thirdReviewerId) {
        boolean blankValues = (secondReviewerId == null && thirdReviewerId == null);
        if (blankValues) {
            return;
        }

        Map<Long, String> map = Collections.unmodifiableMap(new HashMap<>() {
            {
                put(secondReviewerId, "Third reviewer is not a professor");
                put(thirdReviewerId, "Second reviewer is not a professor");
            }
        });

        if (secondReviewerId == null || thirdReviewerId == null) {
            map.forEach((id, errorMessage) -> {
                if (id == null
                        && !userRepository.hasRole(id == secondReviewerId ? thirdReviewerId : id, ERole.PROFESSOR)) {
                    throw new GenericException(HttpStatus.BAD_REQUEST, errorMessage);
                }
            });
        } else if (secondReviewerId.equals(thirdReviewerId)) {
            throw new GenericException(HttpStatus.BAD_REQUEST, "The reviewers can't have the same id.");
        } else {
            map.forEach((id, errorMessage) -> {
                if (!userRepository.hasRole(id, ERole.PROFESSOR)) {
                    throw new GenericException(HttpStatus.BAD_REQUEST,
                            map.get(id == secondReviewerId ? thirdReviewerId : secondReviewerId));
                }
            });
        }

        return;
    }
}
