package com.realtime.leader.board.web;

import com.realtime.leader.board.entity.Leaderboard;
import com.realtime.leader.board.service.LeaderboardService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LeaderboardController {
    private LeaderboardService leaderboardService;
    private SimpMessagingTemplate messagingTemplate;

    public LeaderboardController(LeaderboardService leaderboardService, SimpMessagingTemplate messagingTemplate) {
        this.leaderboardService = leaderboardService;
        this.messagingTemplate = messagingTemplate;
    }

    @GetMapping("/test")
    public String sayHello() {
        return "Hello! The server is running.";
    }

    @PostMapping("/add-participant")
    public void addParticipant(@RequestBody Leaderboard.Participant participant) {
        leaderboardService.addParticipant(participant);

        Leaderboard leaderboard = Leaderboard.builder()
                .participants(leaderboardService.getParticipants())
                .build();
        messagingTemplate.convertAndSend("/topic/leaderboard", leaderboard);
    }

    @MessageMapping("/leaderboard/update")
    @SendTo("/topic/leaderboard")
    public Leaderboard sendLeaderboardUpdate() {
        return Leaderboard.builder()
                .participants(leaderboardService.getParticipants())
                .build();
    }
}
