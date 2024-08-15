package org.redlich.metrics;

import java.io.IOException;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.ws.rs.ConstrainedTo;
import jakarta.ws.rs.RuntimeType;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;
import org.eclipse.microprofile.metrics.Counter;
import org.eclipse.microprofile.metrics.Metadata;
import org.eclipse.microprofile.metrics.MetricRegistry;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.Tag;

/**
 * REST service filter to update a family of counters based on the HTTP status of each response.
 * <p>
 *     The filter uses one {@link org.eclipse.microprofile.metrics.Counter} for each HTTP status family (1xx, 2xx, etc.).
 *     All counters share the same name--{@value STATUS_COUNTER_NAME}--and each has the tag {@value STATUS_TAG_NAME} with
 *     value {@code 1xx}, {@code 2xx}, etc.
 * </p>
 */
@ConstrainedTo(RuntimeType.SERVER)
@Provider
public class HttpStatusMetricFilter implements ContainerResponseFilter {

    static final String STATUS_COUNTER_NAME = "httpStatus";

    static final String STATUS_COUNTER_DESCRIPTION = "Counts the number of HTTP responses in each status category (1xx, 2xx, etc.)";

    static final String STATUS_TAG_NAME = "range";

    @Inject
    private MetricRegistry metricRegistry;

    private final Counter[] responseCounters = new Counter[6];

    @PostConstruct
    private void init() {
        Metadata metadata = Metadata.builder()
                .withName(STATUS_COUNTER_NAME)
                .withDescription(STATUS_COUNTER_DESCRIPTION)
                .withUnit(MetricUnits.NONE)
                .build();
        // declare the counters and keep references to them
        for (int i = 1; i < responseCounters.length; i++) {
            responseCounters[i] = metricRegistry.counter(metadata, new Tag(STATUS_TAG_NAME, i + "xx"));
            }
        }

    @Override
    public void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext containerResponseContext)
            throws IOException {
        updateCountForStatus(containerResponseContext.getStatus());
        }

    private void updateCountForStatus(int statusCode) {
        int range = statusCode / 100;
        if (range > 0 && range < responseCounters.length) {
            responseCounters[range].inc();
            }
        }
    }
