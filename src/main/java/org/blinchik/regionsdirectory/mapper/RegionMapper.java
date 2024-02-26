package org.blinchik.regionsdirectory.mapper;

import org.apache.ibatis.annotations.Param;
import org.blinchik.regionsdirectory.model.Region;

import java.util.List;
import java.util.UUID;

public interface RegionMapper {
        Region getRegionById(UUID id);
        List<Region> getAllRegions();
        List<Region> getAllRegionsWithLimitAndSort(@Param("limit") int limit, @Param("sortBy") String sortBy, @Param("order") String order);
        void insertRegion(Region region);
        void updateRegion(Region region);
        void deleteRegionById(UUID id);
}