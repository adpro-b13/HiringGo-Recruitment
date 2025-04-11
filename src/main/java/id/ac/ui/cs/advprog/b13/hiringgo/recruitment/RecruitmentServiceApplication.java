package id.ac.ui.cs.advprog.b13.hiringgo.recruitment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.sql.Connection;

@SpringBootApplication
public class RecruitmentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RecruitmentServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner testDbConnection(DataSource dataSource) {
        return args -> {
            try (Connection conn = dataSource.getConnection()) {
                System.out.println("✅ Connected to DB: " + conn.getMetaData().getURL());
            } catch (Exception e) {
                System.out.println("❌ Failed to connect to DB");
                e.printStackTrace();
            }
        };
    }
}
