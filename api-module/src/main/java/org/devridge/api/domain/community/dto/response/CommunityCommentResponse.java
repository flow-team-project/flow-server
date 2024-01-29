package org.devridge.api.domain.community.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;


@Builder
@Getter
public class CommunityCommentResponse {

    private String nickName;

    private String content;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String profileImageUrl;

    private String introduction;

    private Long likeCount;

    private Long dislikeCount;


}
