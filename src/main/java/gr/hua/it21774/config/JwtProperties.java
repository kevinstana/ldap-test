package gr.hua.it21774.config;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DurationUnit;

@ConfigurationProperties(prefix = "app.jwt")
public record JwtProperties(

                String jwtSecret,

                @DurationUnit(ChronoUnit.SECONDS) Duration accessTokenDurationMs,

                @DurationUnit(ChronoUnit.SECONDS) Duration refreshTokenDurationMs) {
}
