package br.com.starwars.api.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SWAPISearchDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonProperty("count")
    private int count;

    @JsonProperty("results")
    private List<SWAPIPlanetaSearchDTO> results;
}
