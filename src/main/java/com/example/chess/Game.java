package com.example.chess;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private Board board = new Board();
    private Piece.Color turn = Piece.Color.WHITE;
    private boolean whiteWon=false, blackWon=false;
    private boolean stalemate=false;

    public Board getBoard() { return board; }
    public Piece.Color getTurn() { return turn; }
    public boolean isOver() { return whiteWon || blackWon || stalemate; }
    public String winner() { return whiteWon?"WHITE": blackWon?"BLACK": null; }

    public void reset(){
        board = new Board();
        turn = Piece.Color.WHITE;
        whiteWon = false;
        blackWon = false;
        stalemate = false;
    }

    public List<Move> legalMoves() {
        List<Move> res = new ArrayList<>();
        for (int r=0;r<8;r++) for (int c=0;c<8;c++) {
            Piece p = board.get(r,c);
            if (p==null || p.getColor()!=turn) continue;
            for (Move m: board.pseudoMoves(r,c)) {
                if (leavesKingSafe(m)) res.add(m);
            }
        }
        return res;
    }

    private boolean leavesKingSafe(Move m) {
        Board copy = board.copy();
        applyMove(copy,m);
        return !inCheck(copy, turn);
    }

    private void applyMove(Board b, Move m) {
        Piece p = b.get(m.sr,m.sc);
        // simple promotion to queen
        if (p!=null && p.getType()==Piece.Type.PAWN && (m.tr==0 || m.tr==7)) {
            p = new Piece(Piece.Type.QUEEN, p.getColor());
        }
        b.set(m.tr,m.tc,p);
        b.set(m.sr,m.sc,null);
    }

    public boolean inCheck(Board b, Piece.Color color) {
        int kr=-1,kc=-1;
        for (int r=0;r<8;r++) for (int c=0;c<8;c++) {
            Piece p=b.get(r,c);
            if (p!=null && p.getType()==Piece.Type.KING && p.getColor()==color) {kr=r;kc=c;}
        }
        if (kr==-1) return true; // king missing -> treated as checkmate
        // generate opponent pseudo moves; if any hits king square -> check
        Piece.Color opp = color==Piece.Color.WHITE?Piece.Color.BLACK:Piece.Color.WHITE;
        for (int r=0;r<8;r++) for (int c=0;c<8;c++) {
            Piece p=b.get(r,c);
            if (p==null || p.getColor()!=opp) continue;
            for (Move m: b.pseudoMoves(r,c)) {
                if (m.tr==kr && m.tc==kc) return true;
            }
        }
        return false;
    }

    public boolean makeMove(int sr,int sc,int tr,int tc) {
        if (isOver()) return false;
        Move chosen = null;
        for (Move m: legalMoves()) if (m.sr==sr && m.sc==sc && m.tr==tr && m.tc==tc) { chosen=m; break; }
        if (chosen==null) return false;
        applyMove(board, chosen);
        // toggle turn
        turn = (turn==Piece.Color.WHITE)?Piece.Color.BLACK:Piece.Color.WHITE;
        // checkmate detection: if opponent has no legal moves and is in check
        List<Move> oppMoves = legalMoves();
        Piece.Color oppTurn = turn;
        if (oppMoves.isEmpty()) {
            if (inCheck(board, oppTurn)) {
                if (oppTurn==Piece.Color.WHITE) blackWon=true; else whiteWon=true;
            } else {
                stalemate = true;
            }
        }
        return true;
    }
}
