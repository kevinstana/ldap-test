package gr.hua.it21774.dto;

public class BasicCourseDTO {

    private Long id;

    private String name;

    public BasicCourseDTO() {
    }

    public BasicCourseDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
