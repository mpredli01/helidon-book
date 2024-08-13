package org.redlich.health;

import java.lang.management.ManagementFactory;
import com.sun.management.OperatingSystemMXBean;
import io.helidon.health.HealthCheckType;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.health.Startup;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;

@Startup
@ApplicationScoped
public class SystemStartupCheck implements HealthCheck {

    @Override
    public HealthCheckResponse call() {
        OperatingSystemMXBean bean = (com.sun.management.OperatingSystemMXBean)ManagementFactory.getOperatingSystemMXBean();
        double cpuUsed = bean.getCpuLoad();
        String cpuUsage = String.valueOf(cpuUsed);
        return HealthCheckResponse
                .named(name())
                .withData("resource", SystemResource.class.getSimpleName())
                .withData("type", type().toString())
                .withData("bean", bean.toString())
                .withData("cpu usage", cpuUsage)
                .status(cpuUsed < 0.95)
                .build();
        }

    public String name() {
        return "System Startup Check";
        }

    public HealthCheckType type() {
        return HealthCheckType.STARTUP;
        }
    }
