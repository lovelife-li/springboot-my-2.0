package com.springboot.demo.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.alibaba.druid.spring.boot.autoconfigure.properties.DruidStatProperties;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.handlers.EnumTypeHandler;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.springboot.demo.common.db.DBTypeEnum;
import com.springboot.demo.common.db.DynamicDataSource;
import lombok.Data;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author ldb
 * @date 2019-05-09 11:42
 */
@Data
@Configuration
@EnableConfigurationProperties({DruidStatProperties.class})
@ConfigurationProperties(prefix = "spring.datasource.druid" )
public class DruidConfig {

    private int initialSize;
    private boolean asyncInit;
    private int minIdle;
    private boolean keepAlive;
    private int maxActive;
    private long maxWait;
    private long timeBetweenEvictionRunsMillis;
    private long minEvictableIdleTimeMillis;
    private String validationQuery;
    private boolean testWhileIdle;
    private boolean testOnBorrow;
    private boolean testOnReturn;
    private boolean poolPreparedStatements;
    private int maxPoolPreparedStatementPerConnectionSize;
    private boolean useGlobalDataSourceStat;
    private String filters;
    private String connectionProperties;


    @Bean(name = "db01")
    @ConfigurationProperties(prefix = "spring.datasource.druid.db01" )
    public DataSource db01(){

        DruidDataSource druid = DruidDataSourceBuilder.create().build();
        setOtherProperties(druid);
        return druid;
    }

    private Properties getProperties(String connectionProperties){
        String[] entries = connectionProperties.split(";");
        Properties properties = new Properties();
        for (int i = 0; i < entries.length; i++) {
            String entry = entries[i];
            if (entry.length() > 0) {
                int index = entry.indexOf('=');
                if (index > 0) {
                    String name = entry.substring(0, index);
                    String value = entry.substring(index + 1);
                    properties.setProperty(name, value);
                } else {
                    // no value is empty string which is how java.util.Properties works
                    properties.setProperty(entry, "");
                }
            }
        }
        return properties;
    }

    @Bean(name = "db02")
    @ConfigurationProperties(prefix = "spring.datasource.druid.db02" )
    public DataSource db02() {
        DruidDataSource druid = DruidDataSourceBuilder.create().build();
        setOtherProperties(druid);
        return druid;
    }

    private void setOtherProperties(DruidDataSource druid){
        //初始化时建立物理连接的个数
        druid.setInitialSize(initialSize);
        druid.setAsyncInit(asyncInit);
        //最大连接池数量
        druid.setMaxActive(maxActive);
        //最小连接池数量
        druid.setMinIdle(minIdle);
        druid.setKeepAlive(keepAlive);
        //获取连接时最大等待时间，单位毫秒。
        druid.setMaxWait(maxWait);
        //间隔多久进行一次检测，检测需要关闭的空闲连接
        druid.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        //一个连接在池中最小生存的时间
        druid.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        //用来检测连接是否有效的sql
        druid.setValidationQuery(validationQuery);
        //建议配置为true，不影响性能，并且保证安全性。
        druid.setTestWhileIdle(testWhileIdle);
        //申请连接时执行validationQuery检测连接是否有效
        druid.setTestOnBorrow(testOnBorrow);
        druid.setTestOnReturn(testOnReturn);


        //是否缓存preparedStatement，也就是PSCache，oracle设为true，mysql设为false。分库分表较多推荐设置为false
        druid.setPoolPreparedStatements(poolPreparedStatements);
        druid.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
        druid.setUseGlobalDataSourceStat(useGlobalDataSourceStat);

        // 监控统计拦截的filters
        try {
            druid.setFilters(filters);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        druid.setConnectProperties(getProperties(connectionProperties));
    }
    /**
     * 动态数据源配置
     * @return
     */
    @Bean
    @Primary
    public DataSource multipleDataSource (@Qualifier("db01") DataSource db1,
                                          @Qualifier("db02") DataSource db2 ) {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        Map< Object, Object > targetDataSources = new HashMap<>(16);
        targetDataSources.put(DBTypeEnum.db01.getValue(), db1 );
        targetDataSources.put(DBTypeEnum.db02.getValue(), db2);
        dynamicDataSource.setTargetDataSources(targetDataSources);
        dynamicDataSource.setDefaultTargetDataSource(db1);
        return dynamicDataSource;
    }


    @Bean("sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(multipleDataSource(db01(),db02()));
        //sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mapper/*/*Mapper.xml"));

        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setJdbcTypeForNull(JdbcType.NULL);
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setCacheEnabled(false);
        configuration.setDefaultEnumTypeHandler(EnumTypeHandler.class);
        sqlSessionFactory.setConfiguration(configuration);
        sqlSessionFactory.setTypeAliasesPackage("com.springboot.demo.entity");
        //添加分页功能
        sqlSessionFactory.setPlugins(new Interceptor[]{paginationInterceptor()});
        sqlSessionFactory.setGlobalConfig(globalConfig());
        return sqlSessionFactory.getObject();
    }

    public GlobalConfig globalConfig(){
        GlobalConfig globalConfig = new GlobalConfig();
        GlobalConfig.DbConfig dbConfig = new GlobalConfig.DbConfig();
        dbConfig.setFieldStrategy(FieldStrategy.NOT_EMPTY);
        dbConfig.setTablePrefix("db_");
        dbConfig.setIdType(IdType.AUTO);
        globalConfig.setDbConfig(dbConfig);
        return globalConfig;

    }

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
