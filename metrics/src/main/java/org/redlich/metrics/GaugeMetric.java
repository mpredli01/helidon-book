package org.redlich.metrics;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicLong;

import org.eclipse.microprofile.metrics.annotation.Gauge;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.event.Observes;

@ApplicationScoped
public class GaugeMetric {

    private AtomicLong startTime = new AtomicLong(0);

    public void onStartUp(@Observes @Initialized(ApplicationScoped.class)Object init) {
        startTime = new AtomicLong(System.currentTimeMillis());
        }

    @Gauge(unit = "TimeSeconds")
    public long appUpTimeSeconds() {
        return Duration.ofMillis(System.currentTimeMillis() - startTime.get()).getSeconds();
        }
    }
