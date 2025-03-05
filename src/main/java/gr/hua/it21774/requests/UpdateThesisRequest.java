package gr.hua.it21774.requests;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UpdateThesisRequest {

    @NotBlank(message = "Thesis title required")
    @Size(max = 256, message = "Title cannot be longer than 256 characters.")
    private String title;

    private String description;

    @Pattern(regexp = "^[1-9]\\d{0,18}$", message = "ID must be a positive integer with 19 digits at most")
    @NotBlank(message = "Second reviewer required")
    private String secondReviewerId;

    @Pattern(regexp = "^[1-9]\\d{0,18}$", message = "ID must be a positive integer with 19 digits at most")
    @NotBlank(message = "Third reviewer required")
    private String thirdReviewerId;

    private List<Long> recommendedCourses;

    public UpdateThesisRequest() {
    }

    public UpdateThesisRequest(String title, String description, String secondReviewerId, String thirdReviewerId,
            List<Long> recommendedCourses) {
        this.title = title;
        this.description = description;
        this.secondReviewerId = secondReviewerId;
        this.thirdReviewerId = thirdReviewerId;
        this.recommendedCourses = recommendedCourses;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSecondReviewerId() {
        return secondReviewerId;
    }

    public void setSecondReviewerId(String secondReviewerId) {
        this.secondReviewerId = secondReviewerId;
    }

    public String getThirdReviewerId() {
        return thirdReviewerId;
    }

    public void setThirdReviewerId(String thirdReviewerId) {
        this.thirdReviewerId = thirdReviewerId;
    }

    public List<Long> getRecommendedCourses() {
        return recommendedCourses;
    }

    public void setRecommendedCourses(List<Long> recommendedCourses) {
        this.recommendedCourses = recommendedCourses;
    }
}
