package starting.precourse.dto;

import lombok.Getter;

@Getter
public class TokenDto {
    private String token;

    public TokenDto(String token) {
        this.token = token;
    }
}
