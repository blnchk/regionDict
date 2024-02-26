package org.blinchik.regionsdirectory;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.blinchik.regionsdirectory.mapper.RegionMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class RegionsDirectoryApplicationTests {

    @Autowired
    private RegionMapper regionMapper;

    @Test
    public void testRegionMapperInitialization() {
        assertNotNull(regionMapper);
    }

}
