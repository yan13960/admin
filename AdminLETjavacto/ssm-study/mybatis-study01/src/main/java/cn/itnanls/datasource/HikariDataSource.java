package cn.itnanls.datasource;

import com.zaxxer.hikari.HikariConfig;
import org.apache.ibatis.datasource.DataSourceFactory;

import javax.sql.DataSource;
import java.util.Properties;

/**
 *
 */
public class HikariDataSource  implements DataSourceFactory {

    private Properties properties ;
    @Override
    public void setProperties(Properties properties) {
       this.properties=properties;
    }

    @Override
    public DataSource getDataSource() {
        HikariConfig hikariConfig=new HikariConfig(properties);
        return new com.zaxxer.hikari.HikariDataSource(hikariConfig);
    }
}
