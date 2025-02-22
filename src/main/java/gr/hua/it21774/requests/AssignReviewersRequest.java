package gr.hua.it21774.requests;

import java.util.List;

public class AssignReviewersRequest {
    
        private List<Long> excludedIds ;

    public AssignReviewersRequest() {
    }

    public AssignReviewersRequest(List<Long> excludedIds) {
        this.excludedIds = excludedIds;
    }

    public List<Long> getExcludedIds() {
        return excludedIds;
    }

    public void setExcludedIds(List<Long> excludedIds) {
        this.excludedIds = excludedIds;
    }
}
