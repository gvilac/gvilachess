package com.guillem.gvilachess.controller;

import com.guillem.gvilachess.controller.request.CreateGameRequest;
import com.guillem.gvilachess.controller.request.MovementRequest;
import com.guillem.gvilachess.controller.response.Response;
import com.guillem.gvilachess.exceptions.GeneralChessLogicError;
import com.guillem.gvilachess.exceptions.PositionNotFoundException;
import com.guillem.gvilachess.model.domain.chess.Move;
import com.guillem.gvilachess.model.domain.chess.enums.Position;
import com.guillem.gvilachess.services.ChessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping("api/chess")
public class ChessController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChessController.class);

    private final ChessService chessService;
    //Sessió o cookies: HttpServletRequest, ServletContext, HttpSession

    @Autowired
    public ChessController(ChessService chessService) {
        this.chessService = chessService;
    }

    @PostMapping("/createGame")
    public Response createGame(@Valid @RequestBody CreateGameRequest createGameRequest) {
        try {
            chessService.createGame(createGameRequest.getWhitePlayerUsername(), createGameRequest.getBlackPlayerUsername());
        } catch (Exception e) {
            return Response.builder().message(e.getMessage()).build();
        }
        return Response.builder().message("Game created succesfully").build();
    }

    @PostMapping("/move")
    public Response doMove(@Valid @RequestBody MovementRequest moveRequest, Authentication authentication) {
        String username = authentication.getName();
        try {
            Move move = Move.builder()
                    .from(Position.fromString(moveRequest.getFromPosition()))
                    .to(Position.fromString(moveRequest.getToPosition())).build();
            chessService.doMovement(move, username);
        } catch (PositionNotFoundException e) {
            LOGGER.warn("Warning: " + e);
            return Response.builder().message(e.getMessage()).build();
        } catch (GeneralChessLogicError e) {
            LOGGER.warn("Warning: " + e);
            return Response.builder().message(e.getMessage()).build();
        }
        return Response.builder().message("Moved Succesfully").build();
    }
}
