/*
 * Copyright (c) 2024 Oracle and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.redlich.webclient;

import io.helidon.common.http.Http;
import io.helidon.media.jackson.JacksonSupport;
import io.helidon.webclient.WebClient;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TestMain {

    // private static WebClient webClient;

    @BeforeAll
    public static WebClient startClient() {
        System.out.println("[APP] Starting the client");

        WebClient webClient = WebClient.builder()
                .baseUri("https://restcountries.com")
                .addHeader(Http.Header.ACCEPT_LANGUAGE, "en-us")
                .addHeader(Http.Header.ACCEPT_ENCODING, "gzip, deflate, br")
                .addReader(JacksonSupport.reader())
                .addWriter(JacksonSupport.writer())
                .build();

        return webClient;

    }
    @Test
    public void testMain() {
        // TODO: finish writing test
        WebClient client = startClient();
        assert(true);
        }
    }
