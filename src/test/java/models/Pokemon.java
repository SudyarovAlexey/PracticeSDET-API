package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Pokemon {

    private List<AbilityObject> abilities;

    @JsonIgnore
    private int base_experience;

    @JsonIgnore
    private List<Object> forms;

    @JsonIgnore
    private List<Object> game_indices;

    @JsonIgnore
    private int height;

    @JsonIgnore
    private List<Object> held_items;

    @JsonIgnore
    private int id;

    @JsonIgnore
    private Boolean is_default;

    @JsonIgnore
    private String location_area_encounters;

    @JsonIgnore
    private List<Object> moves;

    @JsonIgnore
    private String name;

    @JsonIgnore
    private int order;

    @JsonIgnore
    private List<Object> past_types;

    @JsonIgnore
    private Object species;

    @JsonIgnore
    private Object sprites;

    @JsonIgnore
    private List<Object> stats;

    @JsonIgnore
    private List<Object> types;

    private int weight;

}
