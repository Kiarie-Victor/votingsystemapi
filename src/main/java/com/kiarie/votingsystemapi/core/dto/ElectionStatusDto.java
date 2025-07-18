package com.kiarie.votingsystemapi.core.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ElectionStatusDto {
    private String faculty;
    private Integer yearOfStudy;
    private String title;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean voted;
}
