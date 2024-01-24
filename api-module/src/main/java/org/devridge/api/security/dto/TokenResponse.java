package org.devridge.api.security.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TokenResponse {

    private String accessToken;

    @Builder
    public TokenResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
