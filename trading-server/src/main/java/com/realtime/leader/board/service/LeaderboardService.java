package com.realtime.leader.board.service;

import com.realtime.leader.board.data.DummyData;
import com.realtime.leader.board.entity.Leaderboard;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class LeaderboardService {
    private List<Leaderboard.Participant> participants;

    public LeaderboardService() {
        this.participants = DummyData.createParticipants();
    }

    public List<Leaderboard.Participant> getParticipants() {
        return participants.stream()
                .sorted(Comparator.comparingInt(Leaderboard.Participant::getScore).reversed())
                .collect(Collectors.toList());
    }

    public void updateScores() {
        int maxScore = participants.stream()
                .mapToInt(Leaderboard.Participant::getScore)
                .max()
                .orElse(0);
        int minScore = participants.stream()
                .mapToInt(Leaderboard.Participant::getScore)
                .min()
                .orElse(0);

        int scoreRange = maxScore - minScore; // Calculate score range to use as a basis for new score

        List<Leaderboard.Participant> lowestParticipants = participants.stream()
                .filter(participant -> participant.getScore() == minScore)
                .toList();

        participants.forEach(participant -> {
            int scoreChange = ThreadLocalRandom.current().nextInt(-10, 20);
            int newScore = minScore + (scoreRange / 2); // Place the minimum score in the middle range

            if (participant.getScore() == maxScore) {
                newScore = Math.max(newScore - 1, 0);
            }

            if (lowestParticipants.contains(participant)) {
                newScore += 2;
            }
            newScore = Math.max(newScore + scoreChange, 0);
            participant.setScore(newScore);
        });
        participants.sort((p1, p2) -> Integer.compare(p2.getScore(), p1.getScore()));
    }

    public void addParticipant(Leaderboard.Participant participant) {
        participants.add(new Leaderboard.Participant(participant.getName(), participant.getScore()));
    }
}
