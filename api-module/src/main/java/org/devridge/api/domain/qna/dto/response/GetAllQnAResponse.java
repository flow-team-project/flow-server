package org.devridge.api.domain.qna.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class GetAllQnAResponse {

    private Long id;
    private String title;
    private Integer likes;
    private Integer views;
    private Integer commentCount;
    private LocalDateTime createdAt;
}
