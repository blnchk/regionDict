package org.blinchik.regionsdirectory;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.blinchik.regionsdirectory.mapper.RegionMapper;
import org.blinchik.regionsdirectory.model.Region;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import java.io.IOException;
import java.io.InputStream;

@SpringBootApplication
@EnableCaching
public class RegionsDirectoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(RegionsDirectoryApplication.class, args);
    }

    @Bean()
    @Scope("singleton")
    public RegionMapper regionMapper() throws IOException {
        String resource = "mybatis-config.xml";
        try (InputStream inputStream = Resources.getResourceAsStream(resource);
             SqlSession session = new SqlSessionFactoryBuilder().build(inputStream).openSession()) {
            return session.getMapper(RegionMapper.class);
        }
    }

}
