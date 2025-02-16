package gr.hua.it21774.requests;

import java.util.List;

public class CourseQueryRequest {

    private List<String> selectedCourses;

    public CourseQueryRequest() {
    }

    public CourseQueryRequest(List<String> selectedCourses) {
        this.selectedCourses = selectedCourses;
    }

    public List<String> getSelectedCourses() {
        return selectedCourses;
    }

    public void setSelectedCourses(List<String> selectedCourses) {
        this.selectedCourses = selectedCourses;
    }
}
