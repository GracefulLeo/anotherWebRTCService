package com.gracefull.webRTCService;

import com.gracefull.webRTCService.models.Credentials;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(CredentialsRepository credentialsRepository) {
        return args -> {
            log.info("Preloading " + credentialsRepository.save(new Credentials("3000", "8*m3Xf.vq!", false)));
            log.info("Preloading " + credentialsRepository.save(new Credentials("3001", "e!P9UqFE!C", false)));
            log.info("Preloading " + credentialsRepository.save(new Credentials("3002", "BA!B.4JGE.", false)));
            log.info("Preloading " + credentialsRepository.save(new Credentials("3003", "*y!!yInQX*", false)));
            log.info("Preloading " + credentialsRepository.save(new Credentials("3004", "!qDv$1g!VT", false)));
        };
    }
}