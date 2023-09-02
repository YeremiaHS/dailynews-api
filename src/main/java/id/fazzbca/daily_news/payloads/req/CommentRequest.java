package id.fazzbca.daily_news.payloads.req;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CommentRequest {
    @NotEmpty(message = "input news id")
    private String newsId;

    @NotEmpty(message = "input username")
    private String commentBy;

    @NotEmpty(message = "input your comment")
    private String comment;
}
