package org.example.store.config;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class HikariCPLogger {
    private final HikariDataSource dataSource;

    @PostConstruct
    public void logPoolStats() {
        log.info("HikariCP Pool Stats:");
        log.info("  - Total = {}", dataSource.getHikariPoolMXBean().getTotalConnections());
        log.info("  - Active = {}", dataSource.getHikariPoolMXBean().getActiveConnections());
        log.info("  - Idle = {}", dataSource.getHikariPoolMXBean().getIdleConnections());
    }
}
