package ru.practicum.client;

import org.springframework.http.*;
import org.springframework.lang.Nullable;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

public class BaseClient {

    protected final RestTemplate rest;

    public BaseClient(RestTemplate rest) {
        this.rest = rest;
    }

    protected ResponseEntity<ViewStat[]> get(String path, @Nullable Map<String, Object> parameters) {
        return getMakeAndSendRequest(HttpMethod.GET, path, parameters);
    }

    protected <T> ResponseEntity<Object> post(String path, T body) {
        return postMakeAndSendRequest(HttpMethod.POST, path, body);
    }

    private <T> ResponseEntity<ViewStat[]> getMakeAndSendRequest(HttpMethod method,
                                                                 String path,
                                                                 @Nullable Map<String, Object> parameters) {
        HttpEntity<T> requestEntity = new HttpEntity<>(defaultHeaders());

        ResponseEntity<ViewStat[]> statsServerResponse;
        try {
            if (parameters != null) {
                statsServerResponse = rest.exchange(path, method, requestEntity, ViewStat[].class, parameters);
            } else {
                statsServerResponse = rest.exchange(path, method, requestEntity, ViewStat[].class);
            }
        } catch (HttpStatusCodeException e) {
            return ResponseEntity.status(e.getStatusCode()).build();
        }
        return getPrepareGatewayResponse(statsServerResponse);
    }

    private <T> ResponseEntity<Object> postMakeAndSendRequest(HttpMethod method,
                                                              String path,
                                                              @Nullable T body) {
        HttpEntity<T> requestEntity = new HttpEntity<>(body, defaultHeaders());

        ResponseEntity<Object> statsServerResponse = rest.exchange(path, method, requestEntity, Object.class);

        return postPrepareGatewayResponse(statsServerResponse);
    }

    private HttpHeaders defaultHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        return headers;
    }

    private static ResponseEntity<ViewStat[]> getPrepareGatewayResponse(ResponseEntity<ViewStat[]> response) {
        if (response.getStatusCode().is2xxSuccessful()) {
            return response;
        }

        ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.status(response.getStatusCode());

        if (response.hasBody()) {
            return responseBuilder.body(response.getBody());
        }

        return responseBuilder.build();
    }

    private static ResponseEntity<Object> postPrepareGatewayResponse(ResponseEntity<Object> response) {
        if (response.getStatusCode().is2xxSuccessful()) {
            return response;
        }

        ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.status(response.getStatusCode());

        if (response.hasBody()) {
            return responseBuilder.body(response.getBody());
        }

        return responseBuilder.build();
    }
}
