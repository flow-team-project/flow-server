package org.devridge.api.domain.coffeechat.controller;

import lombok.RequiredArgsConstructor;

import org.devridge.api.domain.coffeechat.dto.request.CreateChatMessageRequest;
import org.devridge.api.domain.coffeechat.dto.response.GetAllChatMessage;
import org.devridge.api.domain.coffeechat.service.CoffeeChatService;

import org.devridge.api.domain.member.entity.Member;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
public class ChatStompController {

    private final CoffeeChatService coffeeChatService;
    private final SimpMessagingTemplate template;

    /**
     * 채팅 메세지 보내기
     * @param roomId
     * @param request
     */
    @MessageMapping("/{roomId}")     // request: /api/pub/{roomId}
    @SendTo("/api/sub/{roomId}")  // subscribe 채팅방으로 메세지 전송
    public void createChatMessage(
        @DestinationVariable Long roomId,
        @Payload CreateChatMessageRequest request,
        @AuthenticationPrincipal Member member
    ) {
        GetAllChatMessage message = coffeeChatService.createChatMessage(request, roomId, member.getId());
        template.convertAndSend("/api/sub/" + roomId, message);
    }
}
