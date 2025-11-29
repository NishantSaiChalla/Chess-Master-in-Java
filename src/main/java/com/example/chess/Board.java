package com.example.chess;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private final Piece[][] grid = new Piece[8][8];

    public Board() {
        setupInitial();
    }

    public Piece get(int r, int c) { return inBounds(r,c) ? grid[r][c] : null; }
    public void set(int r, int c, Piece p) { if (inBounds(r,c)) grid[r][c] = p; }
    public static boolean inBounds(int r, int c) { return r>=0 && r<8 && c>=0 && c<8; }

    public Board copy() {
        Board b = new Board(false);
        for (int r=0;r<8;r++) for (int c=0;c<8;c++) b.grid[r][c] = grid[r][c];
        return b;
    }

    private Board(boolean setup) {}

    private void setupInitial() {
        // Pawns
        for (int c=0;c<8;c++) {
            grid[6][c] = new Piece(Piece.Type.PAWN, Piece.Color.WHITE);
            grid[1][c] = new Piece(Piece.Type.PAWN, Piece.Color.BLACK);
        }
        // Rooks
        grid[7][0] = new Piece(Piece.Type.ROOK, Piece.Color.WHITE);
        grid[7][7] = new Piece(Piece.Type.ROOK, Piece.Color.WHITE);
        grid[0][0] = new Piece(Piece.Type.ROOK, Piece.Color.BLACK);
        grid[0][7] = new Piece(Piece.Type.ROOK, Piece.Color.BLACK);
        // Knights
        grid[7][1] = new Piece(Piece.Type.KNIGHT, Piece.Color.WHITE);
        grid[7][6] = new Piece(Piece.Type.KNIGHT, Piece.Color.WHITE);
        grid[0][1] = new Piece(Piece.Type.KNIGHT, Piece.Color.BLACK);
        grid[0][6] = new Piece(Piece.Type.KNIGHT, Piece.Color.BLACK);
        // Bishops
        grid[7][2] = new Piece(Piece.Type.BISHOP, Piece.Color.WHITE);
        grid[7][5] = new Piece(Piece.Type.BISHOP, Piece.Color.WHITE);
        grid[0][2] = new Piece(Piece.Type.BISHOP, Piece.Color.BLACK);
        grid[0][5] = new Piece(Piece.Type.BISHOP, Piece.Color.BLACK);
        // Queens
        grid[7][3] = new Piece(Piece.Type.QUEEN, Piece.Color.WHITE);
        grid[0][3] = new Piece(Piece.Type.QUEEN, Piece.Color.BLACK);
        // Kings
        grid[7][4] = new Piece(Piece.Type.KING, Piece.Color.WHITE);
        grid[0][4] = new Piece(Piece.Type.KING, Piece.Color.BLACK);
    }

    public List<Move> pseudoMoves(int r, int c) {
        Piece p = get(r,c);
        List<Move> moves = new ArrayList<>();
        if (p == null) return moves;
        switch (p.getType()) {
            case PAWN -> pawnMoves(r,c,p,moves);
            case KNIGHT -> knightMoves(r,c,p,moves);
            case BISHOP -> slideMoves(r,c,p,moves, new int[][]{{1,1},{1,-1},{-1,1},{-1,-1}});
            case ROOK -> slideMoves(r,c,p,moves, new int[][]{{1,0},{-1,0},{0,1},{0,-1}});
            case QUEEN -> slideMoves(r,c,p,moves, new int[][]{{1,0},{-1,0},{0,1},{0,-1},{1,1},{1,-1},{-1,1},{-1,-1}});
            case KING -> kingMoves(r,c,p,moves);
        }
        return moves;
    }

    private void addIfValid(List<Move> moves, int sr, int sc, int tr, int tc, Piece p, boolean captureOnly) {
        if (!inBounds(tr,tc)) return;
        Piece tgt = get(tr,tc);
        if (tgt == null && !captureOnly) {
            moves.add(new Move(sr,sc,tr,tc));
        } else if (tgt != null && tgt.getColor() != p.getColor()) {
            moves.add(new Move(sr,sc,tr,tc));
        }
    }

    private void pawnMoves(int r,int c,Piece p,List<Move> moves) {
        int dir = p.getColor() == Piece.Color.WHITE ? -1 : 1;
        // forward
        if (get(r+dir,c) == null) addIfValid(moves,r,c,r+dir,c,p,false);
        // double
        int startRow = p.getColor() == Piece.Color.WHITE ? 6 : 1;
        if (r == startRow && get(r+dir,c) == null && get(r+2*dir,c) == null) addIfValid(moves,r,c,r+2*dir,c,p,false);
        // captures
        addIfValid(moves,r,c,r+dir,c+1,p,true);
        addIfValid(moves,r,c,r+dir,c-1,p,true);
        // promotion handled in Game on move apply
    }

    private void knightMoves(int r,int c,Piece p,List<Move> moves) {
        int[][] d={{2,1},{2,-1},{-2,1},{-2,-1},{1,2},{1,-2},{-1,2},{-1,-2}};
        for (int[] x:d) addIfValid(moves,r,c,r+x[0],c+x[1],p,false);
    }

    private void slideMoves(int r,int c,Piece p,List<Move> moves,int[][] dirs) {
        for (int[] d:dirs) {
            int tr=r+d[0], tc=c+d[1];
            while (inBounds(tr,tc)) {
                Piece tgt=get(tr,tc);
                if (tgt==null) moves.add(new Move(r,c,tr,tc));
                else {
                    if (tgt.getColor()!=p.getColor()) moves.add(new Move(r,c,tr,tc));
                    break;
                }
                tr+=d[0]; tc+=d[1];
            }
        }
    }

    private void kingMoves(int r,int c,Piece p,List<Move> moves) {
        for (int dr=-1;dr<=1;dr++) for (int dc=-1;dc<=1;dc++) {
            if (dr==0 && dc==0) continue;
            addIfValid(moves,r,c,r+dr,c+dc,p,false);
        }
        // castling omitted for initial version
    }
}
