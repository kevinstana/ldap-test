package gr.hua.it21774.service;

import java.time.Instant;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import gr.hua.it21774.dto.TaskFilesDTO;
import gr.hua.it21774.dto.TasksDTO;
import gr.hua.it21774.entities.Task;
import gr.hua.it21774.enums.ETaskPriorityStatus;
import gr.hua.it21774.enums.ETaskStatus;
import gr.hua.it21774.exceptions.GenericException;
import gr.hua.it21774.requests.TaskRequest;
import gr.hua.it21774.requests.UpdateTaskRequest;
import gr.hua.it21774.respository.TaskFilesRepository;
import gr.hua.it21774.respository.TaskPriorityRepository;
import gr.hua.it21774.respository.TaskStatusRepository;
import gr.hua.it21774.respository.TasksRepository;
import jakarta.transaction.Transactional;

@Service
public class ThesisTasksService {

    private final MinioService minioService;
    private final TasksRepository tasksRepository;
    private final TaskFilesRepository taskFilesRepository;
    private final TaskStatusRepository taskStatusRepository;
    private final TaskPriorityRepository taskPriorityRepository;

    public ThesisTasksService(MinioService minioService, TasksRepository tasksRepository,
            TaskFilesRepository taskFilesRepository,
            TaskStatusRepository taskStatusRepository, TaskPriorityRepository taskPriorityRepository) {
        this.minioService = minioService;
        this.tasksRepository = tasksRepository;
        this.taskFilesRepository = taskFilesRepository;
        this.taskStatusRepository = taskStatusRepository;
        this.taskPriorityRepository = taskPriorityRepository;
    }

    @Transactional(rollbackOn = Exception.class)
    public void createTask(Long thesisId, TaskRequest request) throws Exception {
        try {

            Long statusId = taskStatusRepository.findIdByStatus(ETaskStatus.IN_PROGRESS).get();
            Long priorityId = taskPriorityRepository.findIdByStatus(ETaskPriorityStatus.valueOf(request.getPriority()))
                    .get();

            Task task = new Task(request.getTitle(), request.getDescription(), thesisId, Instant.now(), priorityId,
                    statusId);
            task = tasksRepository.save(task);

            if (request.getFiles() != null && !request.getFiles().isEmpty()) {

                Map<String, Long> files = new HashMap<>();

                for (MultipartFile file : request.getFiles()) {
                    String fileName = file.getOriginalFilename();
                    Long fileSize = file.getSize();
                    files.put(fileName, fileSize);
                }

                taskFilesRepository.saveTaskFiles(task.getId(), files);

                minioService.uploadMultipleFiles(request.getFiles(), "theses-tasks",
                        "thesis-" + thesisId + "/task-" + task.getId());
            }
        } catch (Exception e) {
            throw new GenericException(HttpStatus.BAD_REQUEST, "There was an error while saving the task");
        }

    }

    public Page<TasksDTO> getTasks(Integer pageNumber, Integer pageSize, Long thesisId) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Page<TasksDTO> tasks = tasksRepository.getTasks(pageable, thesisId);

        tasks.forEach(task -> {
            List<TaskFilesDTO> files = taskFilesRepository.getTaskFiles(task.getId());
            task.setFiles(files);
        });

        return tasks;
    }

    @Transactional(rollbackOn = Exception.class)
    public void updateTask(Long thesisId, Long taskId, UpdateTaskRequest request) throws Exception {
        try {
            Long statusId = taskStatusRepository.findIdByStatus(ETaskStatus.valueOf(request.getStatus())).get();
            Long priorityId = taskPriorityRepository.findIdByStatus(ETaskPriorityStatus.valueOf(request.getPriority()))
                    .get();

            tasksRepository.updateTask(taskId, thesisId, request.getTitle(), request.getDescription(), priorityId,
                    statusId);

            List<TaskFilesDTO> existingFiles = taskFilesRepository.getTaskFiles(taskId);

            Set<String> existingFileNames = existingFiles.stream()
                    .map(TaskFilesDTO::getFileName)
                    .collect(Collectors.toSet());

            Set<String> updatedFileNames = (request.getFiles() != null) ? new HashSet<>(request.getFiles())
                    : new HashSet<>();

            Set<String> removedFiles = new HashSet<>(existingFileNames);
            removedFiles.removeAll(updatedFileNames);

            if (!removedFiles.isEmpty()) {
                taskFilesRepository.deleteTaskFilesByNames(taskId, removedFiles);
                minioService.deleteFiles("theses-tasks", "/thesis-" + thesisId + "/task-" + taskId, removedFiles);
            }

            if (request.getNewFiles() != null && !request.getNewFiles().isEmpty()) {
                Map<String, Long> files = new HashMap<>();

                for (MultipartFile file : request.getNewFiles()) {
                    String fileName = file.getOriginalFilename();
                    Long fileSize = file.getSize();
                    files.put(fileName, fileSize);
                }

                taskFilesRepository.saveTaskFiles(taskId, files);

                minioService.uploadMultipleFiles(request.getNewFiles(), "theses-tasks",
                        "thesis-" + thesisId + "/task-" + taskId);
            }

        } catch (Exception e) {
            throw new GenericException(HttpStatus.BAD_REQUEST, "Something went wrong");
        }
    }

    @Transactional(rollbackOn = Exception.class)
    public void deleteTask(Long taskId, Long thesisId) throws Exception {
        try {
            taskFilesRepository.deleteTaskFiles(taskId);
            tasksRepository.deleteTask(taskId, thesisId);
            minioService.deleteAllFilesInFolder("theses-tasks", "thesis-" + thesisId + "/task-" + taskId);
        } catch (Exception e) {
            throw new GenericException(HttpStatus.BAD_REQUEST, "Couldn't delete task");
        }
    }
}
