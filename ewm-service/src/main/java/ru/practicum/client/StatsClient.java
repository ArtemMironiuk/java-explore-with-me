package ru.practicum.client;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class StatsClient extends BaseClient {

    @Value("${app-name}")
    private String appName;

    @Autowired
    public StatsClient(@Value("{stats-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public void save(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String ip = request.getRemoteAddr();
        post("/hit", new EndpointHit(appName, uri, ip));
    }

    public ResponseEntity<Object> getViews(LocalDateTime start, LocalDateTime end, List<String> uri, Boolean unique) {
        return get("/stats?start={start}&end={end}&uris={uris}&unique={unique}", start, end, uri, unique);
    }


}
