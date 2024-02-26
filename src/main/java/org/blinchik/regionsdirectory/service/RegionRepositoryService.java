package org.blinchik.regionsdirectory.service;

import org.blinchik.regionsdirectory.mapper.RegionMapper;
import org.blinchik.regionsdirectory.model.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegionRepositoryService {
    private final RegionMapper regionMapper;

    @Autowired
    public RegionRepositoryService(RegionMapper regionMapper) {
        this.regionMapper = regionMapper;
    }

    @Cacheable("regions")
    public List<Region> getAllRegions() {
        return regionMapper.getAllRegions();
    }

    public List<Region> getAllRegionsWithLimitAndSort(int limit, String sortBy, String order) {
        return regionMapper.getAllRegionsWithLimitAndSort(limit, sortBy, order);
    }

    public Region getRegionById(Long id) {
        return regionMapper.getRegionById(id);
    }

    @CachePut(value = "regions", key = "#region.id")
    public void addRegion(Region region) {
        regionMapper.insertRegion(region);
    }

    @CachePut(value = "regions", key = "#region.id")
    public void updateRegion(Region region) {
        regionMapper.updateRegion(region);
    }

    @CacheEvict(value = "regions", key = "#id")
    public void deleteRegion(Long id) {
        regionMapper.deleteRegionById(id);
    }
}
