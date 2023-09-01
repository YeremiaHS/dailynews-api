package id.fazzbca.daily_news.payloads.req;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class NewsRequest {
    @NotEmpty(message = "input writter")
    private String writtenBy;

    @NotEmpty(message = "input title")
    private String title;

    @NotEmpty(message = "write down your news")
    private String content;

    @NotEmpty(message = "write down your news")
    private String category;
}
