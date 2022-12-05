package ru.practicum.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.handler.exception.ValidationException;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Service
public class StatsClient extends BaseClient {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final String API_PREFIX = "";
    @Value("${app-name}")
    private String appName;

    @Autowired
    public StatsClient(@Value("${stats-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public ResponseEntity<Object> save(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String ip = request.getRemoteAddr();
        return post("/hit", new EndpointHit(appName, uri, ip));
    }

    public ResponseEntity<ViewStat[]> getViews(String start, String end, String[] uri, Boolean unique) {
        String startEn = encode(start);
        String endEn = encode(end);
        Map<String, Object> parameters = Map.of(
                "start", startEn,
                "end", endEn,
                "uris", uri,
                "unique", unique
        );
        return get("/stats?start={start}&end={end}&uris={uris}&unique={unique}", parameters);
    }

//    public Long getViews(HttpServletRequest request, Event event) {
//        ResponseEntity<ViewStat[]> response = getViewStats(
//                event.getPublishedOn().format(formatter),
//                event.getEventDate().plusMinutes(1).format(formatter),
//                new String[]{request.getRequestURI()},
//                false);
//        Long views = response.getBody()[0].getHits();
//        return views;
//    }


    private String encode(String path) {
        try {
            path = URLEncoder.encode(path, StandardCharsets.UTF_8.toString());
        } catch (ValidationException e) {
            throw new ValidationException("Error encoding parameter");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return path;
    }

}
