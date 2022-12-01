//package ru.practicum.client.web;
//
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.stereotype.Service;
//import org.springframework.web.reactive.function.client.WebClient;
//import reactor.core.publisher.Mono;
//import ru.practicum.client.EndpointHit;
//import ru.practicum.client.ViewStat;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Service
//@Slf4j
//@AllArgsConstructor
//public class StatService {
//
//    @Resource
//    private final WebClient webClient;
//
//    public Mono<ViewStat> getViews(LocalDateTime start, LocalDateTime end, List<String> uri, Boolean unique) {
//        return webClient
//                .get()
//                .uri(String.join("/stats?start={start}&end={end}&uris={uris}&unique={unique}"), start, end, uri, unique)
//                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                .retrieve()
//                .bodyToMono(ViewStat.class);
//
//    }
//    public Mono<EndpointHit> postHit(HttpServletRequest request) {
//        log.info("client ip: {}", request.getRemoteAddr());
//        log.info("endpoint path: {}", request.getRequestURI());
//        return webClient
//                .post()
//                .uri("/hit")
//                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                .body(new EndpointHit("запрос к эндпоинту GET, /events/id", request.getRequestURI(),
//                        request.getRemoteAddr()), EndpointHit.class)
//                .retrieve()
//                .bodyToMono(EndpointHit.class);
//    }
//}
