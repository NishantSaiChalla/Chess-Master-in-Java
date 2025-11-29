package com.example.chess;

import static spark.Spark.*;

import com.google.gson.Gson;
import com.example.chess.dto.MoveDTO;
import com.example.chess.dto.ResultDTO;
import com.example.chess.dto.StateDTO;

public class Server {
    public static void main(String[] args) {
        port(getHerokuAssignedPort());
        staticFiles.location("/public");
        Gson gson = new Gson();
        GameService service = new GameService();

        get("/api/state", (req,res) -> {
            res.type("application/json");
            return gson.toJson(service.getState());
        });

        post("/api/move", (req,res) -> {
            MoveDTO m = gson.fromJson(req.body(), MoveDTO.class);
            ResultDTO result = service.applyMove(m);
            res.type("application/json");
            res.status(result.ok ? 200 : 400);
            return gson.toJson(result);
        });

        post("/api/reset", (req,res) -> {
            StateDTO s = service.reset();
            res.type("application/json");
            return gson.toJson(s);
        });
    }

    private static int getHerokuAssignedPort() {
        String port = System.getenv("PORT");
        if (port != null) {
            return Integer.parseInt(port);
        }
        return 4567;
    }

    // DTOs moved to com.example.chess.dto package
}
