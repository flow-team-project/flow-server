package org.devridge.api.domain.community.service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.devridge.api.domain.community.entity.Community;
import org.devridge.api.domain.community.entity.CommunityComment;
import org.devridge.api.domain.community.entity.CommunityCommentLikeDislike;
import org.devridge.api.domain.community.entity.LikeStatus;
import org.devridge.api.domain.community.entity.id.CommunityCommentLikeDislikeId;
import org.devridge.api.domain.community.mapper.CommunityCommentLikeDislikeMapper;
import org.devridge.api.domain.community.repository.CommunityCommentLikeDislikeRepository;
import org.devridge.api.domain.community.repository.CommunityCommentRepository;
import org.devridge.api.domain.community.repository.CommunityRepository;
import org.devridge.api.domain.member.entity.Member;
import org.devridge.api.domain.member.repository.MemberRepository;
import org.devridge.api.util.SecurityContextHolderUtil;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommunityCommentLikeDislikeService {

    private final CommunityCommentLikeDislikeRepository communityCommentLikeDislikeRepository;
    private final CommunityCommentRepository communityCommentRepository;
    private final MemberRepository memberRepository;
    private final CommunityRepository communityRepository;
    private final CommunityCommentLikeDislikeMapper communityCommentLikeDislikeMapper;

    @Transactional
    public void createCommunityCommentLike(Long communityId, Long commentId) {
        Long accessMemberId = SecurityContextHolderUtil.getMemberId();
        Member member = getMemberById(accessMemberId);
        Community community = getCommunityById(communityId);
        CommunityComment comment = getCommentById(commentId);
        CommunityCommentLikeDislikeId communityCommentLikeDislikeId =
            new CommunityCommentLikeDislikeId(member.getId(), comment.getId());

        if (!accessMemberId.equals(community.getMember().getId())) {
            throw new AccessDeniedException("거부된 접근입니다.");
        }

        communityCommentLikeDislikeRepository.findById(communityCommentLikeDislikeId).ifPresentOrElse(
            CommunityCommentLikeDislike -> {
                LikeStatus status = CommunityCommentLikeDislike.getStatus();

                if (status == LikeStatus.G) {
                    changeIsDeletedStatus(CommunityCommentLikeDislike);
                }

                if (status == LikeStatus.B) {
                    if (CommunityCommentLikeDislike.getIsDeleted()) {
                        communityCommentLikeDislikeRepository.restoreById(communityCommentLikeDislikeId);
                    }
                    communityCommentLikeDislikeRepository.updateLikeDislike(communityCommentLikeDislikeId,
                        LikeStatus.G);
                }
            },
            () -> {
                CommunityCommentLikeDislike commentLikeDislike =
                    communityCommentLikeDislikeMapper.toCommunityCommentLikeDislike(member, comment, LikeStatus.G);
                communityCommentLikeDislikeRepository.save(commentLikeDislike);
            }
        );
        updateLikeDislike(communityCommentLikeDislikeId);
    }

    @Transactional
    public void createCommunityCommentDislike(Long communityId, Long commentId) {
        Long accessMemberId = SecurityContextHolderUtil.getMemberId();
        Member member = getMemberById(accessMemberId);
        Community community = getCommunityById(communityId);
        CommunityComment comment = getCommentById(commentId);
        CommunityCommentLikeDislikeId communityCommentLikeDislikeId =
            new CommunityCommentLikeDislikeId(member.getId(), comment.getId());

        if (!accessMemberId.equals(community.getMember().getId())) {
            throw new AccessDeniedException("거부된 접근입니다.");
        }

        communityCommentLikeDislikeRepository.findById(communityCommentLikeDislikeId).ifPresentOrElse(
            CommunityCommentLikeDislike -> {
                LikeStatus status = CommunityCommentLikeDislike.getStatus();

                if (status == LikeStatus.B) {
                    changeIsDeletedStatus(CommunityCommentLikeDislike);
                }

                if (status == LikeStatus.G) {
                    if (CommunityCommentLikeDislike.getIsDeleted()) {
                        communityCommentLikeDislikeRepository.restoreById(communityCommentLikeDislikeId);
                    }
                    communityCommentLikeDislikeRepository.updateLikeDislike(communityCommentLikeDislikeId,
                        LikeStatus.B);
                }
            },
            () -> {
                CommunityCommentLikeDislike commentLikeDislike =
                    communityCommentLikeDislikeMapper.toCommunityCommentLikeDislike(member, comment, LikeStatus.B);
                communityCommentLikeDislikeRepository.save(commentLikeDislike);
            }
        );
        updateLikeDislike(communityCommentLikeDislikeId);
    }

    private void updateLikeDislike(CommunityCommentLikeDislikeId id) {
        Long likes = Long.valueOf(communityCommentLikeDislikeRepository.countCommunityLikeDislikeById(id, LikeStatus.G));
        Long disLikes = Long.valueOf(communityCommentLikeDislikeRepository.countCommunityLikeDislikeById(id, LikeStatus.B));
        communityCommentRepository.updateLikeDislike(likes, disLikes, id.getCommentId());
    }

    private void changeIsDeletedStatus(CommunityCommentLikeDislike communitycommentLikeDislike) {
        if (communitycommentLikeDislike.getIsDeleted()) {
            communityCommentLikeDislikeRepository.restoreById(communitycommentLikeDislike.getId());
        }
        if (!communitycommentLikeDislike.getIsDeleted()) {
            communityCommentLikeDislikeRepository.deleteById(communitycommentLikeDislike.getId());
        }
    }

    private Member getMemberById(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> new EntityNotFoundException());
    }

    private CommunityComment getCommentById(Long commentId) {
        return communityCommentRepository.findById(commentId).orElseThrow(() -> new EntityNotFoundException());
    }

    private Community getCommunityById(Long communityId) {
        return communityRepository.findById(communityId).orElseThrow(() -> new EntityNotFoundException());
    }
}
