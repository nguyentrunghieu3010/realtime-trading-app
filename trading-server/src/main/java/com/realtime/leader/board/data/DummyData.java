package com.realtime.leader.board.data;

import com.realtime.leader.board.entity.Leaderboard;

import java.util.ArrayList;
import java.util.List;

public class DummyData {
    public static List<Leaderboard.Participant> createParticipants() {
        List<Leaderboard.Participant> participants = new ArrayList<>();
        participants.add(new Leaderboard.Participant("Alice", 250));
        participants.add(new Leaderboard.Participant("Bob", 300));
        participants.add(new Leaderboard.Participant("Charlie", 150));
        participants.add(new Leaderboard.Participant("Diana", 350));
        return participants;
    }
}
