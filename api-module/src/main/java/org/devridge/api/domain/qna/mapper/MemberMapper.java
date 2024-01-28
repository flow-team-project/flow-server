package org.devridge.api.domain.qna.mapper;

import org.devridge.api.domain.member.entity.Member;
import org.devridge.api.domain.qna.dto.response.FindWriterInformation;

import javax.persistence.EntityNotFoundException;

public class MemberMapper {

    public static FindWriterInformation toMember(Member member) {
        try {
            return FindWriterInformation.builder()
                .id(member.getId())
                .nickname(member.getNickname())
                .introduction(member.getIntroduction())
                .profileImageUrl(member.getProfileImageUrl())
                .build();
        } catch (EntityNotFoundException e) {
            return FindWriterInformation.builder()
                .id(0L)
                .nickname("탈퇴한 사용자")
                .introduction("탈퇴한 사용자입니다.")
                .profileImageUrl("default_user.png")
                .build();
        }
    }
}
