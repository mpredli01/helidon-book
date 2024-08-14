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

import org.eclipse.microprofile.metrics.Histogram;
import org.eclipse.microprofile.metrics.MetricRegistry;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.Metadata;
import org.eclipse.microprofile.metrics.MetadataBuilder;
import org.eclipse.microprofile.metrics.annotation.Metric;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.ws.rs.ConstrainedTo;
import jakarta.ws.rs.RuntimeType;
import jakarta.ws.rs.ext.Provider;

@ConstrainedTo(RuntimeType.SERVER)
@Provider
public class TemperatureHistogram {

    static final int[] RECENT_NEW_YORK_TEMPS = { 46, 45, 50, 46, 45, 27, 30, 48, 55, 54, 45, 41, 45, 43, 46 };
    static final String NYC_TEMPERATURE_HISTOGRAM = "nycHistogram";

    @Inject
    private MetricRegistry registry;

    @Inject
    @Metric(name = "temperatures", description = "A histogram metrics example.")
    private Histogram histogram;

    @PostConstruct
    public void init() {
        Metadata metadata = new MetadataBuilder()
                .withName(NYC_TEMPERATURE_HISTOGRAM)
                .withDescription("A histogram of recent New York City temperatures")
                .withUnit(MetricUnits.NONE)
                .build();
        MetadataBuilder builder = new MetadataBuilder();
        histogram = registry.histogram(metadata);
        for(int temp : RECENT_NEW_YORK_TEMPS) {
            histogram.update(temp);
            }

        }
    }
