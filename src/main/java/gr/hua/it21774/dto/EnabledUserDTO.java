package gr.hua.it21774.dto;

public class EnabledUserDTO {
    private Long id;
    private boolean isEnabled;

    public EnabledUserDTO() {}

    public EnabledUserDTO(Long id, boolean isEnabled) {
        this.id = id;
        this.isEnabled = isEnabled;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }
}
