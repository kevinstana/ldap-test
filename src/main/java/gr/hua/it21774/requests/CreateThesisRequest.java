package gr.hua.it21774.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class CreateThesisRequest {

    @NotBlank(message = "Thesis title required")
    @Size(max = 256, message = "Title cannot be longer than 256 characters.")
    private String title;

    private String description;

    @Pattern(regexp = "^[1-9]\\d{0,18}$", message = "Id must be a positive integer with 19 digits at most")
    private String secondReviewerId;

    @Pattern(regexp = "^[1-9]\\d{0,18}$", message = "Id must be a positive integer with 19 digits at most")
    private String thirdReviewerId;

    public CreateThesisRequest(String title, String description, String secondReviewerId, String thirdReviewerId) {
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

    public String getSecondReviewerId() {
        return secondReviewerId;
    }

    public String getThirdReviewerId() {
        return thirdReviewerId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSecondReviewerId(String secondReviewerId) {
        this.secondReviewerId = secondReviewerId;
    }

    public void setThirdReviewerId(String thirdReviewerId) {
        this.thirdReviewerId = thirdReviewerId;
    }
}
