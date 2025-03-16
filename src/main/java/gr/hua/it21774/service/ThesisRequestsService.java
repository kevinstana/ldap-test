package gr.hua.it21774.service;

import java.time.Instant;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import gr.hua.it21774.dto.ThesisRequestsDTO;
import gr.hua.it21774.entities.ThesisRequest;
import gr.hua.it21774.enums.EThesisRequestStatus;
import gr.hua.it21774.exceptions.GenericException;
import gr.hua.it21774.respository.ThesisRequestRepository;
import gr.hua.it21774.respository.ThesisRequestStatusRepository;
import io.jsonwebtoken.Claims;
import jakarta.transaction.Transactional;

@Service
public class ThesisRequestsService {

    private final MinioService minioService;
    private final ThesisRequestRepository thesisRequestRepository;
    private final ThesisRequestStatusRepository thesisRequestStatusRepository;

    public ThesisRequestsService(ThesisRequestStatusRepository thesisRequestStatusRepository,
            ThesisRequestRepository thesisRequestRepository, MinioService minioService) {
        this.thesisRequestStatusRepository = thesisRequestStatusRepository;
        this.thesisRequestRepository = thesisRequestRepository;
        this.minioService = minioService;
    }

    @Transactional(rollbackOn = Exception.class)
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

        try {
            thesisRequestRepository.save(thesisRequest);

            minioService.uploadRequestFile(pdf, "theses-requests", folderName, pdfName);
        } catch (Exception e) {
            throw new GenericException(HttpStatus.BAD_REQUEST, "Something went wrong while uploading the request file");
        }
    }

    public Page<ThesisRequestsDTO> getThesisRequests(Integer pageNumber, Integer pageSize, Long thesisId) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        return thesisRequestRepository.getThesisRequests(pageable, thesisId);
    }

}
