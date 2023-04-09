package models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PokemonResource {

    private int count;

    private String next;

    private String previous;

    List<Result> results;
}
