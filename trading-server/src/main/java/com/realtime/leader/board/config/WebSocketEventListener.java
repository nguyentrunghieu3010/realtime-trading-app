package com.realtime.leader.board.config;

import com.realtime.leader.board.data.DummyData;
import com.realtime.leader.board.entity.Leaderboard;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.List;

@Component
@Slf4j
public class WebSocketEventListener {
    private final SimpMessageSendingOperations messagingTemplate;
    private final List<Leaderboard.Participant> participants = DummyData.createParticipants();

    public WebSocketEventListener(SimpMessageSendingOperations messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String sessionId = headerAccessor.getSessionId();
        log.info("Disconnected session ID: {}", sessionId);

        String userId = headerAccessor.getFirstNativeHeader("userId");
        if (userId != null) {
            log.info("User with ID {} has disconnected", userId);
            participants.removeIf(participant -> participant.getName().equals(userId));
        }

        Leaderboard leaderboard = Leaderboard.builder()
                .participants(participants)
                .build();

        messagingTemplate.convertAndSend("/topic/leaderboard", leaderboard);
    }
}
