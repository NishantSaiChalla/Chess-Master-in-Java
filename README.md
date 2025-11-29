# Chess Master (Java)

A self-contained chess app built with Java and Spark Java. It includes a clear rules engine and a responsive web UI.

## Features
- Legal move enforcement
- Check, checkmate, stalemate detection
- Pawn promotion to queen
- Spark Java server with a responsive HTML board

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
 

## API
- `GET /api/state` — current board, turn, status
- `POST /api/move` — body `{sr,sc,tr,tc}` to apply a move
- `POST /api/reset` — start a new game

## Project Structure
- `src/main/java/com/example/chess/` — chess engine and HTTP server
- `src/main/resources/public/` — static front-end files

## Notes
- Castling and en passant are not implemented
- Static files are served from `src/main/resources/public`

## Development
- Build: `mvn clean package`
- Run: `java -cp "target/classes;target/lib/*" com.example.chess.Server`
- Port: defaults to `4567`; can be overridden with `PORT` env var.
