package pl.janeck.carsapi.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import javax.sql.DataSource;

/**
 * ... comment class...
 *
 * @author JKN janeck@protonmail.com
 * @since 27 December 2020 @ 16:08
 */

@Configuration
public class DbConfiguration {


    private DataSource dataSource;
    //In dataSource we need to set all properties to connect to our data base.
    //in this case, dataSource get all data from application.properties file , We can set dataSource trough a method getDataSource with
    //@Bean annotation (in case we're using more than one data base)


    @Autowired
    public DbConfiguration(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    // JdbcTemplate allows us to make operations on our database, we need to provide in constructor a datasource.
    @Bean
    public JdbcTemplate getJdbcTemplate() {
        return new JdbcTemplate(dataSource);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initial(){
        String sql = "CREATE TABLE IF NOT EXISTS cars(id int, mark varchar(255), model varchar(255), year int, color varchar(255), PRIMARY KEY (id))";
        getJdbcTemplate().update(sql);
    }

}
