package id.fazzbca.daily_news.payloads.req;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ChangePassRequest {
    @NotEmpty(message = "email cannot be empty")
    private String email;

    @NotEmpty(message = "please input your password")
    @Size(min = 8, max = 12, message = "password must be beetwen 8 to 12 characters")
    private String password;
}
