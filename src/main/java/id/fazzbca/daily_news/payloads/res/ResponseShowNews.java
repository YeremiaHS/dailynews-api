package id.fazzbca.daily_news.payloads.res;

import java.time.LocalDateTime;

public interface ResponseShowNews {
    String getTitle();

    String getContent();
    
    String getUsername();
    
    String getCategory();
    
    LocalDateTime getCreated_at();
}
