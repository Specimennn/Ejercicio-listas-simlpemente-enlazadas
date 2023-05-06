package co.edu.umanizales.tads.controller.dto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class PetDTO {

    @NotBlank(message = "Ingresó una identificación vacía")
    private String identification;

    @NotBlank(message = "Ingresó un nombre vacío")
    @Size(min = 2, max = 20, message = "El nombre debe tener entre 2 a 20 carácteres")
    private String name;

    @Min(value = 0, message = "La edad no puede ser inferior a 0")
    @Max(value = 40, message = "La edad no puede ser mayor a 40")
    private byte age;


    @Pattern(regexp="[fmFM]", message = "El género debe ser 'm' o 'f'")
    private String gender;

    @NotBlank(message = "La especie no puede estar vacía")
    @Size(min = 2, max = 100, message = "La especie debe tener entre 2 y 100 carácteres")
    private String species;

    @Size(min = 3, max = 8, message = "El código de la ubicación debe de tener 3 a 8 caracteres ")
    private String code;

}
