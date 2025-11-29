package com.example.chess.dto;

public class ResultDTO {
    public boolean ok;
    public StateDTO state;
    public String message;

    public ResultDTO(boolean ok, StateDTO state, String message){
        this.ok=ok; this.state=state; this.message=message;
    }
}
