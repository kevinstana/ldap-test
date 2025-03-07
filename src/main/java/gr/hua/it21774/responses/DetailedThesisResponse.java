package gr.hua.it21774.responses;

import java.util.List;
import gr.hua.it21774.dto.DetailedThesisDTO;
import gr.hua.it21774.entities.Course;

public class DetailedThesisResponse {

    private DetailedThesisDTO thesis;
    private List<Course> recommendedCourses;
    private Boolean canMakeRequest;
    private Boolean hasMadeRequest;

    public DetailedThesisResponse() {
    }

    public DetailedThesisResponse(DetailedThesisDTO thesis, List<Course> recommendedCourses, Boolean canMakeRequest,
            Boolean hasMadeRequest) {
        this.thesis = thesis;
        this.recommendedCourses = recommendedCourses;
        this.canMakeRequest = canMakeRequest;
        this.hasMadeRequest = hasMadeRequest;
    }

    public Boolean getCanMakeRequest() {
        return canMakeRequest;
    }

    public void setCanMakeRequest(Boolean canMakeRequest) {
        this.canMakeRequest = canMakeRequest;
    }

    public Boolean getHasMadeRequest() {
        return hasMadeRequest;
    }

    public void setHasMadeRequest(Boolean hasMadeRequest) {
        this.hasMadeRequest = hasMadeRequest;
    }

    public DetailedThesisDTO getThesis() {
        return thesis;
    }

    public void setThesis(DetailedThesisDTO thesis) {
        this.thesis = thesis;
    }

    public List<Course> getRecommendedCourses() {
        return recommendedCourses;
    }

    public void setRecommendedCourses(List<Course> recommendedCourses) {
        this.recommendedCourses = recommendedCourses;
    }
}
