package org.blinchik.regionsdirectory.service;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
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

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Cacheable("regions")
    public List<Region> getAllRegions() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            return sqlSession.getMapper(RegionMapper.class).getAllRegions();
        }
    }

    public List<Region> getAllRegionsWithLimitAndSort(int page, int limit, String sortBy, String order) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            int offset = page != 0 ? (page-1) * limit : 0;
            return sqlSession.getMapper(RegionMapper.class).getAllRegionsWithLimitAndSort(offset, limit, sortBy, order);
        }
    }

    public Region getRegionById(Long id) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            return sqlSession.getMapper(RegionMapper.class).getRegionById(id);
        }
    }

    @CachePut(value = "regions", key = "#region.id")
    public Region addRegion(Region region) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            sqlSession.getMapper(RegionMapper.class).insertRegion(region);
        }
        return getRegionById(region.getId()) ;
    }

    @CachePut(value = "regions", key = "#region.id")
    public boolean updateRegion(Region region) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            RegionMapper mapper = sqlSession.getMapper(RegionMapper.class);
            if (mapper.getRegionById(region.getId()) == null) {
                return false;
            }
            mapper.updateRegion(region);
        }
        return true;
    }

    @CacheEvict(value = "regions", key = "#id")
    public boolean deleteRegion(Long id) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            RegionMapper mapper = sqlSession.getMapper(RegionMapper.class);
            if (mapper.getRegionById(id) == null) {
                return false;
            }
            mapper.deleteRegionById(id);
        }
        return true;
    }
}
