package co.edu.umanizales.tads.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Data
public class Pet {

    private String identification;

    private String name;

    private byte age;

    private char gender;

    private String species;

    private Location location;

    private Boolean clean;

    public Pet(String identification, String name, byte age, char gender, String species, Location location) {
        this.identification = identification;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.species = species;
        this.location = location;
        this.clean = false;
    }

}
