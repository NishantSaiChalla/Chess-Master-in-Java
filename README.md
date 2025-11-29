# Java Chess (Spark Java)

A minimal, deployable chess game built in Java. It uses a simple rules engine and serves a basic web UI via Spark Java.

## Features
- Legal move enforcement (no moving into check).
- Check, checkmate, stalemate detection.
- Simple pawn promotion to queen.
- Lightweight web server (Spark Java) serving a minimal HTML board.

## Requirements
- Java 17+
- Maven 3.8+

## Run Locally
```powershell
# from the project root
mvn clean package
java -cp "target/classes;target/lib/*" com.example.chess.Server
# Open http://localhost:4567
```

## Free Deployment

### Render (Free Web Service)
1. Push this repo to GitHub.
2. Create a new Web Service on Render linked to the repo.
3. Build Command:
   ```
   mvn clean package
   ```
4. Start Command (Linux container):
   ```
   java -cp target/classes:target/lib/* com.example.chess.Server
   ```
5. Environment: `PORT` is provided by Render; the app binds to it.

### Railway
Use similar build/start commands:
```
mvn clean package
java -cp target/classes:target/lib/* com.example.chess.Server
```

### Fly.io
Use a simple Dockerfile that builds with Maven and runs the server listening on `$PORT`.

## API
- `GET /api/state` — current board, turn, status
- `POST /api/move` — body `{sr,sc,tr,tc}` to apply a move
- `POST /api/reset` — start a new game

## Project Structure
- `src/main/java/com/example/chess/` — chess engine and HTTP server.
- `src/main/resources/public/` — static front-end files.

## Notes
- Castling and en passant are not implemented in this simplified version.
- Static files are served from `src/main/resources/public`.
