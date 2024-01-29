package org.devridge.api.domain.community.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommunityListResponse {

    private String title;

    private Long views;

    private Long likeCount;

    private Long commentCount;

}
