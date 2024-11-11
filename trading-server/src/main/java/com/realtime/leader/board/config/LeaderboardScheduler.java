package com.realtime.leader.board.config;

import com.realtime.leader.board.entity.Leaderboard;
import com.realtime.leader.board.service.LeaderboardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LeaderboardScheduler {
    private final SimpMessageSendingOperations messagingTemplate;
    private final LeaderboardService leaderboardService;

    public LeaderboardScheduler(SimpMessageSendingOperations messagingTemplate, LeaderboardService leaderboardService) {
        this.messagingTemplate = messagingTemplate;
        this.leaderboardService = leaderboardService;
    }

    @Scheduled(fixedRate = 2000)
    public void sendMockLeaderboardPeriodically() {
        log.info("Starting scheduled leaderboard update.");

        leaderboardService.updateScores();
        log.info("Scores updated successfully.");

        Leaderboard leaderboard = Leaderboard.builder()
                .participants(leaderboardService.getParticipants())
                .build();
        log.debug("Updated leaderboard: {}", leaderboard.getParticipants());

        messagingTemplate.convertAndSend("/topic/leaderboard", leaderboard);
        log.info("Leaderboard sent to WebSocket topic '/topic/leaderboard'.");
    }
}
