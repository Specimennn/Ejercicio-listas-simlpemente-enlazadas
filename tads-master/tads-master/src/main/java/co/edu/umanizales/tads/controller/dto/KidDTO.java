package co.edu.umanizales.tads.controller.dto;
import jakarta.validation.constraints.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class KidDTO {
    @NotBlank(message = "Ingresó una identificación vacía")
    private String identification;

    @NotBlank(message = "Ingresó un nombre vacío")
    @Size(min = 2, max = 20, message = "El nombre debe tener entre 2 a 20 carácteres")
    private String name;
    @Min(value = 0, message = "La edad no puede ser inferior a 0")
    @Max(value = 13, message = "La edad no puede ser mayor a 13")
    private byte age;

    @Pattern(regexp="[fmFM]", message = "El género debe ser 'm' o 'f'")
    @Size(min = 1, max = 1, message = "El género debe ser solo una letra")
    private String gender;
    @Size(min = 3, max = 8, message = "El código de la locación debe de tener 3 a 8 caracteres ")
    private String code;

}
