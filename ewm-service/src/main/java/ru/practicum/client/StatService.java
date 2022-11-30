package ru.practicum.client;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StatService {

    private final WebClient webClient;

    public Mono<EndpointHit> getView(LocalDateTime start, LocalDateTime end, List<String> uri, Boolean unique) {
        return webClient
                .get()
                .uri(String.join("/stats?start={start}&end={end}&uris={uris}&unique={unique}"), start, end, uri, unique)
                .header(MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(EndpointHit.class);

    }
}
