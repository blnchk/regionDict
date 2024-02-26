package org.blinchik.regionsdirectory.mapper;

import org.apache.ibatis.annotations.Param;
import org.blinchik.regionsdirectory.model.Region;

import java.util.List;

public interface RegionMapper {
        Region getRegionById(Long id);
        List<Region> getAllRegions();
        List<Region> getAllRegionsWithLimitAndSort(@Param("offset") int offset, @Param("limit") int limit, @Param("sortBy") String sortBy, @Param("order") String order);
        void insertRegion(Region region);
        void updateRegion(Region region);
        void deleteRegionById(Long id);
}