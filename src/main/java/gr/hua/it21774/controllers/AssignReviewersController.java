package gr.hua.it21774.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gr.hua.it21774.dto.CommonUserDTO;
import gr.hua.it21774.requests.AssignReviewersRequest;
import gr.hua.it21774.service.UserService;
import io.jsonwebtoken.Claims;

@RestController
@RequestMapping("")
public class AssignReviewersController {

    private final UserService userService;

    public AssignReviewersController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/assign-reviewers")
    public ResponseEntity<?> searchProfessors(@RequestParam(required = false) String query,
            @RequestBody AssignReviewersRequest request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Claims accessTokenClaims = (Claims) authentication.getDetails();
        Long requestingUserId = Long.parseLong(accessTokenClaims.getSubject());

        List<Long> excludedIds = request.getExcludedIds() != null ? new ArrayList<>(request.getExcludedIds())
                : new ArrayList<>();
        excludedIds.add(requestingUserId);

        List<CommonUserDTO> availableProfessors = userService.getProfessors(query, excludedIds);

        return ResponseEntity.ok().body(availableProfessors);
    }
}
