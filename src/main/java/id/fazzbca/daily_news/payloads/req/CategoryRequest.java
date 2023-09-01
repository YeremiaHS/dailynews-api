package id.fazzbca.daily_news.payloads.req;

//import id.fazzbca.daily_news.models.Admin;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CategoryRequest {
    @NotEmpty(message = "Fill category field")
    private String category;

    @NotEmpty(message = "Fill admin name")
    private String createdBy;

    @NotEmpty(message = "Input Description about the category")
    private String description;
}
