package co.edu.umanizales.tads.controller.dto;
import java.util.ArrayList;
import co.edu.umanizales.tads.model.Location;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class KidsByLocationDTO {
    private Location location;
    private int total;


}
