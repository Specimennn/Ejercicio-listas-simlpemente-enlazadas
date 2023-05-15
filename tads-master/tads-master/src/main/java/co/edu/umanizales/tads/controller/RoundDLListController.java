package co.edu.umanizales.tads.controller;

import co.edu.umanizales.tads.exception.DLListException;
import co.edu.umanizales.tads.model.AgeRange;
import co.edu.umanizales.tads.model.Pet;
import co.edu.umanizales.tads.model.RoundDLList;
import co.edu.umanizales.tads.service.RoundDLListService;
import co.edu.umanizales.tads.service.AgeRangeService;
import co.edu.umanizales.tads.controller.dto.*;
import co.edu.umanizales.tads.model.Location;
import co.edu.umanizales.tads.service.LocationService;
import co.edu.umanizales.tads.service.RoundDLListService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path="/rdllist")

public class RoundDLListController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDTO> handleValidationException(MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        List<ErrorDTO> errors = new ArrayList<>();
        for (FieldError fieldError : fieldErrors) {
            errors.add(new ErrorDTO(HttpStatus.BAD_REQUEST.value(), fieldError.getDefaultMessage()));
        }
        return new ResponseEntity<>(new ResponseDTO(HttpStatus.BAD_REQUEST.value(), null, errors), HttpStatus.BAD_REQUEST);
    }

    @Autowired
    private RoundDLListService RoundDLListService;

    @Autowired
    private LocationService locationService;

    @GetMapping
    public ResponseEntity<ResponseDTO> getPets() {

        List<Pet> pets = new ArrayList<>();

        try {
            pets = RoundDLListService.getPets().print();

        } catch (DLListException e) {
            return new ResponseEntity<>(new ResponseDTO(
                    204,e.getMessage(),
                    null), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDTO(
                200, pets, null), HttpStatus.OK);
    }

    @GetMapping(path = "/add")
    public ResponseEntity<ResponseDTO> addPet(@RequestBody @Valid PetDTO petDTO) {
        Location location = locationService.getLocationByCode(petDTO.getCode());

        if (location == null) {
            return new ResponseEntity<>(new ResponseDTO(
                    404,"la ubicación no existe",
                    null), HttpStatus.OK);
        }

        try {
            RoundDLListService.getPets().add(
                    new Pet(petDTO.getIdentification(),
                            petDTO.getName(),petDTO.getAge(), petDTO.getGender().charAt(0), petDTO.getSpecies(), location));}
        catch (DLListException e){
            return new ResponseEntity<>(new ResponseDTO(
                    409,e.getMessage(),
                    null), HttpStatus.OK);
        }

        return new ResponseEntity<>(new ResponseDTO(
                200, "Se ha adicionado a la mascota con éxito", null), HttpStatus.OK);
    }

    @GetMapping(path = "/getsize")
    public ResponseEntity<ResponseDTO> getSize() {

        return new ResponseEntity<>(new ResponseDTO(
                200, RoundDLListService.getPets().getSize(), null), HttpStatus.OK);
    }

    @GetMapping(path = "/addinposition/{position}")
    public ResponseEntity<ResponseDTO> addInPosition(@RequestBody @Valid PetDTO petDTO, @PathVariable int position) {
        Location location = locationService.getLocationByCode(petDTO.getCode());

        int finalPosition = 0;

        if ((RoundDLListService.getPets().getSize() + 1 )>= position){

            finalPosition = position % RoundDLListService.getPets().getSize();

        } else {
            finalPosition = RoundDLListService.getPets().getSize() + 1;
        }

        if (location == null) {
            return new ResponseEntity<>(new ResponseDTO(
                    404,"la ubicación no existe",
                    null), HttpStatus.OK);
        }

        try {
            RoundDLListService.getPets().addPetAtPlace(
                    new Pet(petDTO.getIdentification(),
                            petDTO.getName(),petDTO.getAge(), petDTO.getGender().charAt(0), petDTO.getSpecies(), location), finalPosition);}
        catch (DLListException e){
            return new ResponseEntity<>(new ResponseDTO(
                    409,e.getMessage(),
                    null), HttpStatus.OK);
        }

        return new ResponseEntity<>(new ResponseDTO(
                200, "Se ha adicionado a la mascota con éxito", null), HttpStatus.OK);
    }

    @GetMapping(path = "/addatstart")
    public ResponseEntity<ResponseDTO> addToStart(@RequestBody @Valid PetDTO petDTO) {
        Location location = locationService.getLocationByCode(petDTO.getCode());

        if (location == null) {
            return new ResponseEntity<>(new ResponseDTO(
                    404,"la ubicación no existe",
                    null), HttpStatus.OK);
        }

        try {
            RoundDLListService.getPets().addToStart(
                    new Pet(petDTO.getIdentification(),
                            petDTO.getName(),petDTO.getAge(), petDTO.getGender().charAt(0), petDTO.getSpecies(), location));
        }
        catch (DLListException e){
            return new ResponseEntity<>(new ResponseDTO(
                    409,e.getMessage(),
                    null), HttpStatus.OK);
        }

        return new ResponseEntity<>(new ResponseDTO(
                200, "Se ha adicionado a la mascota con éxito", null), HttpStatus.OK);
    }


    @GetMapping(path = "/washrandompet")
    public ResponseEntity<ResponseDTO> addToStart() {


        try {
            RoundDLListService.getPets().washRandomPet();
        }
        catch (DLListException e){
            return new ResponseEntity<>(new ResponseDTO(
                    409,e.getMessage(),
                    null), HttpStatus.OK);
        }

        return new ResponseEntity<>(new ResponseDTO(
                200, "Se ha lavado a la mascota con éxito", null), HttpStatus.OK);
    }

}
