package org.redlich.health;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.health.Readiness;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;

@Readiness
@ApplicationScoped
public class SystemReadinessCheck implements HealthCheck {

    private static final String READINESS_CHECK = SystemResource.class.getSimpleName()  + " Readiness Check";

    @Override
    public HealthCheckResponse call() {
        if (!System.getProperty("java.vm.name").equals("OpenJDK 64-Bit Server VM")) {
            return HealthCheckResponse
                    .down(READINESS_CHECK);
            }
        return HealthCheckResponse
                .up(READINESS_CHECK);
        }
    }
    