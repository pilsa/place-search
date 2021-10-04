package com.pilsa.place.framework.database;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.h2.tools.Server;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.SQLException;


/**
 * The type H2DataBase server config.
 * 로컬에서만 사용하는 H2DB 설정을 정의한다.
 * 업무 페키지 : com.pilsa.place.biz
 * 공통 페키지 : com.pilsa.place.common
 *
 * @author pilsa_home1
 */
@Slf4j
@Configuration
@MapperScan(
        basePackages = {"com.pilsa.place.biz.**.mapper","com.pilsa.place.common.**.mapper"},
        sqlSessionFactoryRef = "placeSqlSessionFactory")
public class H2ServerConfig {

    @Resource
    private Environment environment;


    /**
     * Data source data source.
     *
     * @return the data source
     * @throws SQLException the sql exception
     */
    @Profile("local")
    @Bean(name = "placeDataSource")
    @ConfigurationProperties("spring.datasource.hikari")
    public DataSource dataSource() throws SQLException {

        Server server = Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9095").start();
        if (server.isRunning(true)) {
            log.info("h2-server run success");
        }
        log.info("h2-server url = {}", server.getURL());
        return new HikariDataSource();
    }

    /**
     * 서버환경 DataSource 주입 샘플
     */
    @Profile("!local")
    @Bean(name = "placeDataSource")
    public DataSource serverDataSource(){
        String jndiName = environment.getProperty("spring.place.datasource.jndi-name");
        return new JndiDataSourceLookup().getDataSource(jndiName);
    }

    /**
     * place sql session factory bean sql session factory.
     *
     * @param placeDataSource   the place data source
     * @param applicationContext the application context
     * @return the sql session factory
     * @throws Exception the exception
     */
    @Bean(name = "placeSqlSessionFactory")
    public SqlSessionFactory placeSqlSessionFactoryBean(@Qualifier("placeDataSource") DataSource placeDataSource, ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(placeDataSource);
        sqlSessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:mybatis/mybatis-config.xml"));
        sqlSessionFactoryBean.setTypeAliasesPackage(
                "com.pilsa.place.biz.**.dto"+
                ";com.pilsa.place.common.**.dto"
        );
        sqlSessionFactoryBean.setMapperLocations(ArrayUtils.addAll(
                applicationContext.getResources("classpath:mapper/biz/*.xml"),
                applicationContext.getResources("classpath:mapper/common/*.xml")
        ));
        return sqlSessionFactoryBean.getObject();
    }

    /**
     * place sql session template bean sql session template.
     *
     * @param placeSqlSessionFactory the place sql session factory
     * @return the sql session template
     */
    @Bean(name = "placeSqlSessionTemplate")
    public SqlSessionTemplate placeSqlSessionTemplateBean(@Qualifier("placeSqlSessionFactory") SqlSessionFactory placeSqlSessionFactory) {
        return new SqlSessionTemplate(placeSqlSessionFactory);
    }

}