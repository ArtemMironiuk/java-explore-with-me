package ru.practicum.model.dto;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ViewStats {
    private String app;
    private String uri;
    private Long hits;
}
