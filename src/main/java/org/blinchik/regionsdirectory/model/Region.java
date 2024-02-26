package org.blinchik.regionsdirectory.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class Region {
    private Long id;
    private String name;
    private String shortName;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
}
