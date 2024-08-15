/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.redlich.metrics;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.ws.rs.ConstrainedTo;
import jakarta.ws.rs.RuntimeType;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;

import org.eclipse.microprofile.metrics.Histogram;
import org.eclipse.microprofile.metrics.MetricRegistry;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.Metadata;
import org.eclipse.microprofile.metrics.MetadataBuilder;
import org.eclipse.microprofile.metrics.annotation.Metric;
import org.eclipse.microprofile.metrics.Tag;

import java.io.IOException;

@ConstrainedTo(RuntimeType.SERVER)
@Provider
public class TemperatureHistogramFilter implements ContainerResponseFilter {

    static final int[] RECENT_TRENTON_TEMPERATURES = { 80,83,84,91,95,96,92,94,93,93,88,82,90,90,96,97,94,83,84,82,87,82,83,80,83,83,85,87,86,87,91 };

    static final String TEMPERATURE_HISTOGRAM_NAME = "temperatureHistogram";

    static final String TEMPERATURE_HISTOGRAM_DESCRIPTION = "A histogram of temperatures in Trenton, New Jersey for the month of July 2024";

    @Inject
    private MetricRegistry metricRegistry;

    @Inject
    @Metric(name = "temperatures", description = TEMPERATURE_HISTOGRAM_DESCRIPTION)
    private Histogram histogram;

    @PostConstruct
    public void init() {
        Metadata metadata = new MetadataBuilder()
                .withName(TEMPERATURE_HISTOGRAM_NAME)
                .withDescription(TEMPERATURE_HISTOGRAM_DESCRIPTION)
                .withUnit(MetricUnits.NONE)
                .build();
        histogram = metricRegistry.histogram(metadata, new Tag("unit", "ºF"));
        }

    @Override
    public void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext containerResponseContext)
            throws IOException {
        updateHistogram();
        }

    private void updateHistogram() {
        for(int temp : RECENT_TRENTON_TEMPERATURES) {
            histogram.update(temp);
            }
        }
    }
