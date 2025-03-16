package gr.hua.it21774.requests;

public class GetTasksRequest {
    private String pageNumber;
    private String pageSize;

    public GetTasksRequest() {
    }

    public GetTasksRequest(String pageNumber, String pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    public String getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(String pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }
}