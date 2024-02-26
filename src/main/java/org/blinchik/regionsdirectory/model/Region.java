package org.blinchik.regionsdirectory.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class Region {
    private Long id;
    private String name;
    private String shortName;
}
