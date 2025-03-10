package gr.hua.it21774.service;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import gr.hua.it21774.config.AuthEntryPointJwt;
import gr.hua.it21774.dto.DetailedThesisDTO;
import gr.hua.it21774.dto.ThesisDTO;
import gr.hua.it21774.dto.ThesisRequestsDTO;
import gr.hua.it21774.entities.Thesis;
import gr.hua.it21774.entities.ThesisRequest;
import gr.hua.it21774.enums.ERole;
import gr.hua.it21774.enums.EThesisRequestStatus;
import gr.hua.it21774.enums.EThesisStatus;
import gr.hua.it21774.exceptions.GenericException;
import gr.hua.it21774.requests.AssignStudentRequest;
import gr.hua.it21774.requests.CreateThesisRequest;
import gr.hua.it21774.requests.UpdateThesisRequest;
import gr.hua.it21774.respository.CourseThesisRepository;
import gr.hua.it21774.respository.ThesisRepository;
import gr.hua.it21774.respository.ThesisRequestRepository;
import gr.hua.it21774.respository.ThesisRequestStatusRepository;
import gr.hua.it21774.respository.UserRepository;
import gr.hua.it21774.userdetails.AppUserDetails;
import io.jsonwebtoken.Claims;
import jakarta.transaction.Transactional;

@Service
public class ThesisService {

    private final ThesisRepository thesisRepository;
    private final ThesisRequestRepository thesisRequestRepository;
    private final ThesisRequestStatusRepository thesisRequestStatusRepository;
    private final UserRepository userRepository;
    private final CourseThesisRepository courseThesisRepository;
    private final MinioService minioService;

    private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);

    public ThesisService(ThesisRepository thesisRepository, ThesisRequestRepository thesisRequestRepository,
            ThesisRequestStatusRepository thesisRequestStatusRepository,
            UserRepository userRepository,
            CourseThesisRepository courseThesisRepository, MinioService minioService) {
        this.thesisRepository = thesisRepository;
        this.thesisRequestRepository = thesisRequestRepository;
        this.thesisRequestStatusRepository = thesisRequestStatusRepository;
        this.userRepository = userRepository;
        this.courseThesisRepository = courseThesisRepository;
        this.minioService = minioService;
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

        if (request.getDescription().isBlank()
                || request.getDescription().equals("[{\"type\":\"paragraph\",\"children\":[{\"text\":\"\"}]}]")) {
            request.setDescription("[{\"type\":\"paragraph\",\"children\":[{\"text\":\"Pending Description\"}]}]");
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

    public Page<ThesisDTO> getPagedTheses(Integer pageNumber, String pageSize, String query) {
        Pageable pageable;

        if (pageSize.equals("ALL")) {
            pageable = Pageable.unpaged();
        } else {
            pageable = PageRequest.of(pageNumber, Integer.parseInt(pageSize));
        }

        return thesisRepository.customFindAll(pageable, query);
    }

    public DetailedThesisDTO getThesis(Long id) {

        return thesisRepository.findThesis(id);
    }

    public Page<ThesisDTO> getMyPagedTheses(Integer pageNumber, String pageSize) {
        Pageable pageable;

        if (pageSize.equals("ALL")) {
            pageable = Pageable.unpaged();
        } else {
            pageable = PageRequest.of(pageNumber, Integer.parseInt(pageSize));
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Claims accessTokenClaims = (Claims) authentication.getDetails();
        Long professorId = Long.parseLong(accessTokenClaims.getSubject());

        return thesisRepository.customFindAllByTeacherId(pageable, professorId);
    }

    @Transactional
    public void updateThesis(Long id, UpdateThesisRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long createdBy = ((AppUserDetails) authentication.getPrincipal()).getId();

        Long secondReviewerId = Long.valueOf(request.getSecondReviewerId());
        Long thirdReviewerId = Long.valueOf(request.getThirdReviewerId());

        Set<Long> reviewerIds = new HashSet<Long>(
                Arrays.asList(secondReviewerId, thirdReviewerId));
        if (reviewerIds.contains(createdBy)) {
            throw new GenericException(HttpStatus.BAD_REQUEST, "You can't assing yourself as an extra reviewer.");
        }

        Long tmpId = thesisRepository.findIdByTitle(request.getTitle()).orElse(0L);

        if (tmpId != 0L && tmpId != id) {
            throw new GenericException(HttpStatus.CONFLICT, "A thesis with this title already exists.");
        }
        validateReviewerPair(secondReviewerId, thirdReviewerId);

        if (request.getDescription().isBlank()
                || request.getDescription().equals("[{\"type\":\"paragraph\",\"children\":[{\"text\":\"\"}]}]")) {
            request.setDescription("[{\"type\":\"paragraph\",\"children\":[{\"text\":\"Pending Description\"}]}]");
        }

        thesisRepository.deleteThesisCourseRelationships(id);
        courseThesisRepository.saveCoursesForThesis(request.getRecommendedCourses(), id);

        thesisRepository.updateThesisDetails(id, request.getTitle(), request.getDescription(), secondReviewerId,
                thirdReviewerId, Instant.now());
    }

    public void saveThesisRequest(Long thesisId, String description, MultipartFile pdf) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Claims accessTokenClaims = (Claims) authentication.getDetails();

        Long studentId = Long.parseLong(accessTokenClaims.getSubject());
        String username = accessTokenClaims.get("username", String.class);

        String folderName = "thesis-" + thesisId;
        String pdfName = username + ".pdf";

        Long statusId = thesisRequestStatusRepository.findIdByStatus(EThesisRequestStatus.PENDING)
                .orElseThrow(() -> new GenericException(HttpStatus.BAD_REQUEST, "Thesis Request Status not found"));

        ThesisRequest thesisRequest = new ThesisRequest(0L, studentId, thesisId, description, pdfName, pdf.getSize(),
                statusId, Instant.now());

        thesisRequestRepository.save(thesisRequest);

        try {
            minioService.uploadRequestFile(pdf, folderName, pdfName);
        } catch (Exception e) {
            throw new GenericException(HttpStatus.BAD_REQUEST, "Something went wrong while uploading the request file");
        }
    }

    public boolean canMakeRequest(Long studentId, Long thesisId) {
        Boolean canMakeRequest = (!thesisRepository.hasStudentThesis(studentId)
                && (thesisRepository.getThesisStatus(thesisId) == EThesisStatus.AVAILABLE));

        return canMakeRequest;
    }

    public boolean hasMadeRequest(Long studentId, Long thesisId) {
        return thesisRepository.hasMadeRequest(studentId, thesisId);
    }

    public Page<ThesisRequestsDTO> getThesisRequests(Integer pageNumber, Integer pageSize, Long thesisId) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        return thesisRequestRepository.getThesisRequests(pageable, thesisId);
    }

    @Transactional
    public void assignStudent(AssignStudentRequest request) {

        Long approvedStatusId = 0L;
        Long requestId = request.getRequestId();
        if (request.getRequestId() > 0L) {
            approvedStatusId = thesisRequestStatusRepository.findIdByStatus(EThesisRequestStatus.APPROVED).get();
            thesisRepository.changeRequestStatus(approvedStatusId, requestId);
        }

        thesisRepository.deleteOtherRequestsByStudent(request.getStudentId(), requestId);

        Long rejectedStatusId = thesisRequestStatusRepository.findIdByStatus(EThesisRequestStatus.REJECTED).get();
        thesisRepository.rejectOtherRequests(requestId, request.getThesisId(), rejectedStatusId);

        Long inProgressStatusId = thesisRepository.findIdByStatus(EThesisStatus.IN_PROGRESS).get();
        thesisRepository.updateThesisStatus(request.getThesisId(), request.getStudentId(), inProgressStatusId);
    }

    @Transactional
    public void rejectRequest(Long requestId) {
        Long rejectedStatusId = thesisRequestStatusRepository.findIdByStatus(EThesisRequestStatus.REJECTED).get();
        thesisRequestRepository.rejectRequest(requestId, rejectedStatusId);
    }

    @Transactional
    public void undoThesisRequestAction(AssignStudentRequest request) {

        // try {

        if (thesisRepository.getThesisStatus(request.getThesisId()) != EThesisStatus.AVAILABLE) {
            throw new GenericException(HttpStatus.BAD_REQUEST, "You can only undo a request on an available thesis");
        }

        Long statusId = 0L;
        EThesisRequestStatus status = thesisRequestStatusRepository.findStatusByThesisRequestId(request.getRequestId()).get();
        if (status == EThesisRequestStatus.PENDING) {
            throw new GenericException(HttpStatus.BAD_REQUEST, "You cannot undo a pending request");
        }

        if (status == EThesisRequestStatus.REJECTED) {
            statusId = thesisRequestStatusRepository.findIdByStatus(EThesisRequestStatus.PENDING).get();
            thesisRepository.changeRequestStatus(statusId, request.getRequestId());
            return;
        }

        if (status == EThesisRequestStatus.APPROVED) {
            statusId = thesisRequestStatusRepository.findIdByStatus(EThesisRequestStatus.PENDING).get();
            thesisRepository.changeAllThesisRequestStatus(request.getThesisId(), statusId);
            thesisRepository.changeRequestStatusByStudentId(request.getStudentId(), statusId);
        }

    }
}
