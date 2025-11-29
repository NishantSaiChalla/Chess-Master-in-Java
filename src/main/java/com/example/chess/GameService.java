package com.example.chess;

import java.util.Objects;
import com.example.chess.dto.MoveDTO;
import com.example.chess.dto.ResultDTO;
import com.example.chess.dto.StateDTO;

/**
 * Thin service layer that encapsulates a single Game instance and
 * provides validated operations for the web/API tier.
 */
public class GameService {
    private final Game game;

    public GameService() {
        this.game = new Game();
    }

    public synchronized Game getGame() { return game; }

    public synchronized StateDTO getState() {
        return StateDTO.from(game);
    }

    public synchronized ResultDTO applyMove(MoveDTO move) {
        Objects.requireNonNull(move, "move");
        // Basic input validation: bounds
            if (!Board.inBounds(move.sr, move.sc) || !Board.inBounds(move.tr, move.tc)) {
                return new ResultDTO(false, getState(), "Square out of bounds.");
        }
        boolean ok = game.makeMove(move.sr, move.sc, move.tr, move.tc);
            return new ResultDTO(ok, getState(), ok ? null : "Move is not legal.");
    }

    public synchronized StateDTO reset() {
        game.reset();
        return getState();
    }
}
