package com.kanasinfo.kt

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.ServletComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@ServletComponentScan
@EnableJpaAuditing(auditorAwareRef = "jpaAuditorProvider")
@SpringBootApplication(scanBasePackages=["com.kanasinfo"])
class KtSpeedyPlatformDemoApplication

fun main(args: Array<String>) {
    runApplication<KtSpeedyPlatformDemoApplication>(*args)
}
