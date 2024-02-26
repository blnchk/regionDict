package org.blinchik.regionsdirectory;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.blinchik.regionsdirectory.mapper.RegionMapper;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.sql.DataSource;

@SpringBootApplication
@EnableCaching
public class RegionsDirectoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(RegionsDirectoryApplication.class, args);
    }

    @Autowired
    private DataSource dataSource;

    @Bean
    @Scope("singleton")
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        Resource resource = new ClassPathResource("mybatis-config.xml");
        sessionFactory.setConfigLocation(resource);
        return sessionFactory.getObject();
    }
}
