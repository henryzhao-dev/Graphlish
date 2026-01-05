package com.qimiao.graphlishproject.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.xml.transform.Result;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UnsplashResponse {

    public List<Result> results;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Urls{
        public String regular;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Result{
        public Urls urls;
    }

}
