package org.blinchik.regionsdirectory;

import org.apache.coyote.BadRequestException;
import org.apache.ibatis.exceptions.PersistenceException;
import org.blinchik.regionsdirectory.controler.RegionController;
import org.blinchik.regionsdirectory.model.Region;
import org.blinchik.regionsdirectory.service.RegionRepositoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class RegionsDirectoryApplicationTests {

    @Mock
    private RegionRepositoryService regionService;

    @InjectMocks
    private RegionController regionController;

    @Test
    public void testGetAllRegions() {
        ResponseEntity<List<Region>> emptyResponseEntity = regionController.getAllRegions();
        assertEquals(HttpStatus.NOT_FOUND, emptyResponseEntity.getStatusCode());
        assertEquals(null, emptyResponseEntity.getBody());

        List<Region> regions = Arrays.asList(
                new Region(1L, "Irkutsk", "Irk", LocalDateTime.now(), null),
                new Region(1L, "Kazan", "KZ",LocalDateTime.now(), null));

        when(regionService.getAllRegions()).thenReturn(regions);

        ResponseEntity<List<Region>> responseOK = regionController.getAllRegions();

        assertEquals(2, responseOK.getBody().size());
        assertEquals("Irk", responseOK.getBody().get(0).getShortName());
        assertEquals(HttpStatus.OK, responseOK.getStatusCode());
    }

    @Test
    public void testGetRegionsPaginated() {
        List<Region> regions = Arrays.asList(
                new Region(1L, "Moscow", "MO", LocalDateTime.now(), null),
                new Region(2L, "Saint-Petersburg", "Spb", LocalDateTime.now(), null));

        when(regionService.getAllRegionsWithLimitAndSort(anyInt(), anyInt(), anyString(), anyString())).thenReturn(regions);

        ResponseEntity<List<Region>> responseEntityAll = regionController.getRegionsPaginated(1, "10", "name", "asc");

        assertEquals(HttpStatus.OK, responseEntityAll.getStatusCode());
        assertEquals(regions, responseEntityAll.getBody());
    }

    @Test
    public void testGetRegionById() {
        Region region = new Region(1L, "Irkutsk", "Irk", LocalDateTime.now(), null);
        when(regionService.getRegionById(1L)).thenReturn(region);

        ResponseEntity<Region> responseEntityOK = regionController.getRegionById(1L);
        ResponseEntity<Region> responseEntityNotFound = regionController.getRegionById(3L);

        assertEquals(HttpStatus.OK, responseEntityOK.getStatusCode());
        assertEquals(region, responseEntityOK.getBody());

        assertEquals(HttpStatus.NOT_FOUND, responseEntityNotFound.getStatusCode());
    }

    @Test
    public void testAddRegion() throws BadRequestException {
        Region region = new Region(1L, "Irkutsk", "Irk", LocalDateTime.now(), null);
        when(regionService.addRegion(any())).thenReturn(region);

        ResponseEntity responseEntity = regionController.addRegion(region);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        when(regionService.addRegion(any())).thenThrow(PersistenceException.class);
        ResponseEntity responseEntityBR = regionController.addRegion(region);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntityBR.getStatusCode());
    }

    @Test
    public void testUpdateRegion() throws BadRequestException {
        Region region = new Region(1L, "Saint-Petersburg", "Spb", LocalDateTime.now(), null);
        when(regionService.updateRegion(eq(region))).thenReturn(true);

        ResponseEntity responseEntityOK = regionController.updateRegion(1L, region);
        assertEquals(HttpStatus.OK, responseEntityOK.getStatusCode());

        when(regionService.updateRegion(region)).thenReturn(false);
        ResponseEntity responseEntityNot = regionController.updateRegion(24L, region);

        assertEquals(HttpStatus.NO_CONTENT, responseEntityNot.getStatusCode());

        when(regionService.updateRegion(any())).thenThrow(PersistenceException.class);
        ResponseEntity responseEntityBR = regionController.addRegion(region);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntityBR.getStatusCode());
    }

    @Test
    public void testDeleteRegion() {
        when(regionService.deleteRegion(1L)).thenReturn(true);

        ResponseEntity responseEntityNoContent = regionController.deleteRegion(1L);
        ResponseEntity responseEntityNotFound = regionController.deleteRegion(23L);

        assertEquals(HttpStatus.NO_CONTENT, responseEntityNoContent.getStatusCode());
        assertEquals(HttpStatus.NOT_FOUND, responseEntityNotFound.getStatusCode());
    }


}
