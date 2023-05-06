package co.edu.umanizales.tads.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
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

}
