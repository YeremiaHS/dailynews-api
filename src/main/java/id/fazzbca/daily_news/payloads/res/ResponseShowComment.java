package id.fazzbca.daily_news.payloads.res;

import java.time.LocalDateTime;

public interface ResponseShowComment {
    String getTitle();
    
    String getComment();

    String getUsername();

    LocalDateTime getCreated_at();
}
