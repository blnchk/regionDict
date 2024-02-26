package org.blinchik.regionsdirectory.controler;

import org.blinchik.regionsdirectory.model.Region;
import org.blinchik.regionsdirectory.service.RegionRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/regions")
public class RegionController {
    private final RegionRepositoryService regionService;
    @Autowired
    public RegionController(RegionRepositoryService regionService) {
        this.regionService = regionService;
    }

    @GetMapping
    public ResponseEntity<List<Region>> getAllRegions() {
        List<Region> result = regionService.getAllRegions();
        return result.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(result);
    }

    @GetMapping("page/{page}")
    public ResponseEntity<List<Region>> getRegionsPaginated(@PathVariable int page,
                                                         @RequestParam(required = false, defaultValue = "10") String limit,
                                                         @RequestParam(required = false, defaultValue = "id") String sortBy,
                                                         @RequestParam(required = false, defaultValue = "asc") String order) {
        List<Region> result = regionService.getAllRegionsWithLimitAndSort(page, Integer.parseInt(limit), sortBy, order);
        return result.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(result) ;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Region> getRegionById(@PathVariable Long id) {
        Region region = regionService.getRegionById(id);
        return region != null ? ResponseEntity.ok(region) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity addRegion(@RequestBody Region region) {
        Region result = regionService.addRegion(region);
        return result != null ?
                ResponseEntity.ok().body(String.format("Регион %s был успешно добавлен!", region.getShortName())) :
                ResponseEntity.internalServerError().body("При добавлении региона возникла ошибка.");
    }

    @PutMapping("/{id}")
    public ResponseEntity updateRegion(@PathVariable Long id, @RequestBody Region region) {
        region.setId(id);
        return regionService.updateRegion(region) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteRegion(@PathVariable Long id) {
        return regionService.deleteRegion(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
