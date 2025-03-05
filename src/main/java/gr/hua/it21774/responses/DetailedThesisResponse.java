package gr.hua.it21774.responses;

import java.util.List;
import gr.hua.it21774.dto.DetailedThesisDTO;
import gr.hua.it21774.entities.Course;

public class DetailedThesisResponse {

    private DetailedThesisDTO thesis;
    private List<Course> recommendedCourses;

    public DetailedThesisResponse() {
    }

    public DetailedThesisResponse(DetailedThesisDTO thesis, List<Course> recommendedCourses) {
        this.thesis = thesis;
        this.recommendedCourses = recommendedCourses;
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
