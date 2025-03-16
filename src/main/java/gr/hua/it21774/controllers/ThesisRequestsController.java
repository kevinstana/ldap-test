package gr.hua.it21774.controllers;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import gr.hua.it21774.dto.ThesisRequestsDTO;
import gr.hua.it21774.requests.GetThesisRequestsReq;
import gr.hua.it21774.requests.ThesisRequestReq;
import gr.hua.it21774.responses.MessageRespone;
import gr.hua.it21774.responses.SignedUrlResponse;
import gr.hua.it21774.service.MinioService;
import gr.hua.it21774.service.ThesisRequestsService;

@RestController
public class ThesisRequestsController {

    private final MinioService minioService;
    private final ThesisRequestsService thesisRequestsService;

    public ThesisRequestsController(MinioService minioService, ThesisRequestsService thesisRequestsService) {
        this.thesisRequestsService = thesisRequestsService;
        this.minioService = minioService;
    }

    @PutMapping("/theses/{id}/requests")
    public ResponseEntity<?> createThesisRequest(@PathVariable Long id, @ModelAttribute ThesisRequestReq request)
            throws Exception {

        thesisRequestsService.saveThesisRequest(id, request.getDescription(), request.getPdf());

        return ResponseEntity.ok().body(new MessageRespone("Request made!"));
    }

    @PostMapping("/theses/{id}/requests")
    public ResponseEntity<?> getThesisRequests(@PathVariable Long id, @RequestBody GetThesisRequestsReq req)
            throws Exception {

        Integer pageNumber = 0;
        try {
            pageNumber = Integer.parseInt(req.getPageNumber());
            if (pageNumber < 0) {
                pageNumber = 0;
            }
        } catch (Exception e) {
            pageNumber = 0;
        }

        Integer pageSize = 5;
        try {
            pageSize = Integer.parseInt(req.getPageSize());
            if (pageSize < 0) {
                pageSize = 5;
            }
        } catch (Exception e) {
            pageSize = 5;
        }

        Page<ThesisRequestsDTO> requests = thesisRequestsService.getThesisRequests(pageNumber, pageSize, id);

        return ResponseEntity.ok().body(requests);
    }

    @GetMapping("/theses/{id}/requests/{pdf}")
    public ResponseEntity<?> getRequestFile(@PathVariable Long id, @PathVariable String pdf)
            throws Exception {
        String url = minioService.getSignedUrl("theses-requests", "thesis-" + id, pdf);

        return ResponseEntity.ok()
                .body(new SignedUrlResponse(url));
    }

}
