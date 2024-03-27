package com.early.stage.demo.global;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DemoApplicationRunner implements ApplicationRunner {

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("[active_profile]: " + activeProfile);
    }
}
