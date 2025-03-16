package gr.hua.it21774.controllers;

import gr.hua.it21774.dto.TasksDTO;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import gr.hua.it21774.requests.TaskRequest;
import gr.hua.it21774.requests.UpdateTaskRequest;
import gr.hua.it21774.responses.MessageRespone;
import gr.hua.it21774.responses.SignedUrlResponse;
import gr.hua.it21774.service.MinioService;
import gr.hua.it21774.service.ThesisTasksService;

@Controller
public class ThesisTasksController {

    private final MinioService minioService;
    private final ThesisTasksService thesisTasksService;

    public ThesisTasksController(MinioService minioService, ThesisTasksService thesisTasksService) {
        this.minioService = minioService;
        this.thesisTasksService = thesisTasksService;
    }

    @PostMapping("/theses/{id}/tasks")
    public ResponseEntity<?> createTask(@PathVariable Long id, @ModelAttribute TaskRequest request)
            throws Exception {

        thesisTasksService.createTask(id, request);

        return ResponseEntity.ok().body(new MessageRespone("Task created"));
    }

    @GetMapping("/theses/{id}/tasks")
    public ResponseEntity<?> getTasks(@PathVariable Long id, @RequestParam(required = false) String pageNumbr,
            @RequestParam(required = false) String pageSiz) {

        Integer pageNumber = 0;
        try {
            pageNumber = Integer.parseInt(pageNumbr);
            if (pageNumber < 0) {
                pageNumber = 0;
            }
        } catch (Exception e) {
            pageNumber = 0;
        }

        Integer pageSize = 5;
        try {
            pageSize = Integer.parseInt(pageSiz);
            if (pageSize < 0) {
                pageSize = 5;
            }
        } catch (Exception e) {
            pageSize = 5;
        }

        Page<TasksDTO> tasks = thesisTasksService.getTasks(pageNumber, pageSize, id);

        return ResponseEntity.ok().body(tasks);
    }

    @PutMapping("/theses/{id}/tasks/{taskId}")
    public ResponseEntity<?> updateTask(@PathVariable Long id, @PathVariable Long taskId,
            @ModelAttribute UpdateTaskRequest request)
            throws Exception {

        thesisTasksService.updateTask(id, taskId, request);

        return ResponseEntity.ok().body(new MessageRespone("Task updated"));
    }

    @DeleteMapping("/theses/{id}/tasks/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id, @PathVariable Long taskId)
            throws Exception {

        thesisTasksService.deleteTask(taskId, id);

        return ResponseEntity.ok().body(new MessageRespone("Task deleted"));
    }

    @GetMapping("/theses/{id}/tasks/{taskId}/{filename}")
    public ResponseEntity<?> getTaskFile(@PathVariable Long id, @PathVariable Long taskId,
            @PathVariable String filename)
            throws Exception {
        String url = minioService.getSignedUrl("theses-tasks", "thesis-" + id + "/task-" + taskId, filename);

        return ResponseEntity.ok()
                .body(new SignedUrlResponse(url));
    }
}
