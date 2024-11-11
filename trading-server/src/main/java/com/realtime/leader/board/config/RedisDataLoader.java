package com.realtime.leader.board.config;

import com.realtime.leader.board.entity.Leaderboard;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

@Configuration
public class RedisDataLoader {
    @Bean
    public CommandLineRunner loadData(RedisTemplate<String, Leaderboard.Participant> redisTemplate) {
        return args -> {
            ValueOperations<String, Leaderboard.Participant> valueOps = redisTemplate.opsForValue();

            // Create some dummy participants with unique IDs
            Leaderboard.Participant participant1 = new Leaderboard.Participant( "Alice", 250);
            Leaderboard.Participant participant2 = new Leaderboard.Participant( "Bob", 120);
            Leaderboard.Participant participant3 = new Leaderboard.Participant( "Carol", 90);

            // Save dummy participants to Redis
            valueOps.set("participant:1", participant1);
            valueOps.set("participant:2", participant2);
            valueOps.set("participant:3", participant3);

            System.out.println("Dummy participants added to Redis.");
        };
    }
}
