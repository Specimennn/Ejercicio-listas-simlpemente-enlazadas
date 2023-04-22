package co.edu.umanizales.tads.controller.dto;

import co.edu.umanizales.tads.model.Location;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class KidsByCityDTO {

    private String city;

    private List<KidsPerGenderDTO> genders;

    private int total;
}
