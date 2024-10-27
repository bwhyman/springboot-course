package org.example.springmvcexamples.example07.timer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

//@Component
@Slf4j
public class MyTimer {
    @Scheduled(cron = "0 0 8 10 * ?")
    public void paySalary() {
        log.debug("Your salary has been paid!");
    }
}
