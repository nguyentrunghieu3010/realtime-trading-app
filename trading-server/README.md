# Real-time Leaderboard: A leaderboard should display the current standings of all participants in real-time.

sudo lsof -i :5432
docker-compose -f src/main/docker/redis.yml up

realtime-leaderboard % ./gradlew clean build
realtime-leaderboard % ./gradlew bootRun

## Getting started

http://localhost:8070/swagger-ui/index.html#/leaderboard-controller/addParticipant

## Add your files

### Register a new user

**Endpoint:**
POST http://localhost:8070/add-participant

{
"name": "Phuc hau",
"score": 220
}