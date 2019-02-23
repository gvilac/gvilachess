package com.guillem.gvilachess;

import com.guillem.gvilachess.controller.request.AccountRequest;
import com.guillem.gvilachess.controller.request.CreateGameRequest;
import com.guillem.gvilachess.controller.request.MovementRequest;
import com.guillem.gvilachess.controller.response.Response;
import com.guillem.gvilachess.model.domain.chess.BoardData;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * Created by guillem on 22/02/2019.
 */
public class ClientTest {

    RestTemplate restTemplate;

    HttpHeaders headersBlack;

    HttpHeaders headersWhite;


    @Before
    public void setUp() {
        restTemplate = new RestTemplate();

        // Create two players
        AccountRequest white = new AccountRequest();
        white.setUsername("white");
        white.setPassword("{test1234}");
        ResponseEntity response = restTemplate.postForEntity("http://localhost:8080/api/account/create", white, Response.class);
        Assert.assertTrue(response.getStatusCode().equals(HttpStatus.OK));

        AccountRequest black = new AccountRequest();
        black.setUsername("black");
        black.setPassword("{test1234}");
        response = restTemplate.postForEntity("http://localhost:8080/api/account/create", black, Response.class);
        Assert.assertTrue(response.getStatusCode().equals(HttpStatus.OK));

        // Login Players and save headers
        response = restTemplate.postForEntity("http://localhost:8080/login", white, Response.class);
        Assert.assertTrue(response.getStatusCode().equals(HttpStatus.OK));
        String bearerWhite = response.getHeaders().getFirst("Authorization");

        response = restTemplate.postForEntity("http://localhost:8080/login", black, Response.class);
        Assert.assertTrue(response.getStatusCode().equals(HttpStatus.OK));
        String bearerBlack = response.getHeaders().getFirst("Authorization");

        this.headersWhite = new HttpHeaders();
        headersWhite.set(HttpHeaders.AUTHORIZATION, bearerWhite);
        System.out.println("White: " + bearerWhite);

        this.headersBlack = new HttpHeaders();
        headersBlack.set(HttpHeaders.AUTHORIZATION, bearerBlack);
        System.out.println("Black: " + bearerBlack);

        // Create Game
        CreateGameRequest newGame = new CreateGameRequest();
        newGame.setWhitePlayerUsername("white");
        newGame.setBlackPlayerUsername("black");

        HttpEntity<CreateGameRequest> request = new HttpEntity<>(newGame, headersWhite);

        response = restTemplate.postForEntity("http://localhost:8080/api/chess/createGame", request, Response.class);
        Assert.assertTrue(response.getStatusCode().equals(HttpStatus.OK));

    }

    @Test
    public void clientTestSample() throws JSONException {
       doMovement("E2", "E4", headersWhite);
    }

    @Test
    public void clientTestCheckMateSample() throws JSONException {
        doMovement("E2", "E4", headersWhite);
        doMovement("E7", "E5", headersBlack);

        doMovement("D1", "H5", headersWhite);
        doMovement("B8", "C6", headersBlack);

        doMovement("F1", "C4", headersWhite);
        doMovement("G8", "F6", headersBlack);

        doMovement("H5", "F7", headersWhite);
    }

    private void doMovement(String from, String to, HttpHeaders header) {
        // Move
        MovementRequest movementRequest = new MovementRequest();
        movementRequest.setFromPosition(from);
        movementRequest.setToPosition(to);

        HttpEntity<MovementRequest> requestMovement = new HttpEntity<>(movementRequest, header);

        ResponseEntity response = restTemplate.postForEntity("http://localhost:8080/api/chess/move", requestMovement, Response.class);
        Assert.assertTrue(response.getStatusCode().equals(HttpStatus.OK));
    }

}
