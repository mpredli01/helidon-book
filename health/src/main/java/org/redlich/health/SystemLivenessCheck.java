package org.redlich.health;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;

import io.helidon.health.HealthCheckType;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.health.Liveness;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;

@Liveness
@ApplicationScoped
public class SystemLivenessCheck implements HealthCheck {

    @Override
    public HealthCheckResponse call() {
        MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
        long memoryUsed = memoryBean.getHeapMemoryUsage().getUsed();
        long memoryMax = memoryBean.getHeapMemoryUsage().getMax();

        return HealthCheckResponse
                .named(name())
                .withData("resource", SystemResource.class.getSimpleName())
                .withData("type", type().toString())
                .withData("memory bean", memoryBean.toString())
                .withData("memory used", memoryUsed)
                .withData("memory max", memoryMax)
                .status(memoryUsed < memoryMax * 0.9)
                .build();
        }

    public String name() {
        return "System Liveness Check";
        }

    public HealthCheckType type() {
        return HealthCheckType.LIVENESS;
        }
    }
    