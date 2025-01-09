package gr.hua.it21774.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class CreateThesisRequest {

    @NotBlank(message = "Thesis title required")
    private String title;

    private String description;

    @Min(value = 1, message = "Id must be bigger than 0")
    private Long secondReviewerId;

    @Min(value = 1, message = "Id must be bigger than 0")
    private Long thirdReviewerId;

    public CreateThesisRequest(String title, String description, Long secondReviewerId, Long thirdReviewerId) {
        this.title = title;
        this.description = description;
        this.secondReviewerId = secondReviewerId;
        this.thirdReviewerId = thirdReviewerId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Long getSecondReviewerId() {
        return secondReviewerId;
    }

    public Long getThirdReviewerId() {
        return thirdReviewerId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSecondReviewerId(Long secondReviewerId) {
        this.secondReviewerId = secondReviewerId;
    }

    public void setThirdReviewerId(Long thirdReviewerId) {
        this.thirdReviewerId = thirdReviewerId;
    }
}
