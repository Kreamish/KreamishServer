package com.kreamish.kream.common.runner;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("data & test")
@Component
public class TestDataRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // ToDo : 필요한 데이터들 생성
    }
}
