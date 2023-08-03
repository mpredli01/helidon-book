package org.redlich.webclient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Country {

    private Name name;
    private List<String> tld;
    private boolean independent;
    private String status;
    private boolean unMember;
    private List<String> capital;
    private String region;
    private String subregion;
    }
