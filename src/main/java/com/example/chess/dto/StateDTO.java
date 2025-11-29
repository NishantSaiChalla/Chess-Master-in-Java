package com.example.chess.dto;

import com.example.chess.Game;
import com.example.chess.Piece;

public class StateDTO {
    public String[][] board;
    public String turn;
    public boolean over;
    public String winner;

    public static StateDTO from(Game g){
        StateDTO s=new StateDTO();
        s.board=new String[8][8];
        for(int r=0;r<8;r++) for(int c=0;c<8;c++){
            Piece p=g.getBoard().get(r,c);
            s.board[r][c]= p==null?"." : p.toString();
        }
        s.turn=g.getTurn().name();
        s.over=g.isOver();
        s.winner=g.winner();
        return s;
    }
}
