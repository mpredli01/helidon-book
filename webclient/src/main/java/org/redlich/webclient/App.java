package org.redlich.webclient;

import io.helidon.common.reactive.Single;
import io.helidon.webclient.WebClient;
import io.helidon.common.GenericType;
import io.helidon.common.http.Http;
import io.helidon.common.http.MediaType;
import io.helidon.media.jackson.JacksonSupport;

import java.util.List;
import java.util.concurrent.ExecutionException;


/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) throws ExecutionException, InterruptedException {
        WebClient webClient = WebClient.builder()
                .baseUri("https://restcountries.com")
                .addHeader(Http.Header.ACCEPT_LANGUAGE, "en-us")
                .addHeader(Http.Header.ACCEPT_ENCODING, "gzip, deflate, br")
                .addReader(JacksonSupport.reader())
                .addWriter(JacksonSupport.writer())
                .build();

        Single<List<Country>> request = webClient.get()
                .path("v3.1/all")
                .contentType(MediaType.APPLICATION_JSON)
                .request(new GenericType<>() {});

        List<Country> listOfCountries = request.get();

        System.out.println(listOfCountries);
        }
    }
