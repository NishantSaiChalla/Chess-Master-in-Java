package com.example.chess;

public class Piece {
    public enum Type { KING, QUEEN, ROOK, BISHOP, KNIGHT, PAWN }
    public enum Color { WHITE, BLACK }

    private final Type type;
    private final Color color;

    public Piece(Type type, Color color) {
        this.type = type;
        this.color = color;
    }

    public Type getType() { return type; }
    public Color getColor() { return color; }

    @Override
    public String toString() {
        String s = switch (type) {
            case KING -> "K";
            case QUEEN -> "Q";
            case ROOK -> "R";
            case BISHOP -> "B";
            case KNIGHT -> "N";
            case PAWN -> "P";
        };
        return color == Color.WHITE ? s : s.toLowerCase();
    }
}
