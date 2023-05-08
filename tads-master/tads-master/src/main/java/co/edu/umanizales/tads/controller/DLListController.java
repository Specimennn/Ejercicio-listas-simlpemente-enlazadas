package co.edu.umanizales.tads.controller;

import co.edu.umanizales.tads.exception.DLListException;
import co.edu.umanizales.tads.model.AgeRange;
import co.edu.umanizales.tads.model.Pet;
import co.edu.umanizales.tads.service.DLListService;
import co.edu.umanizales.tads.service.AgeRangeService;
import co.edu.umanizales.tads.controller.dto.*;
import co.edu.umanizales.tads.model.Location;
import co.edu.umanizales.tads.service.LocationService;
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
@RequestMapping(path="/dllist")

public class DLListController {

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
    private DLListService DLListService;

    @Autowired
    private AgeRangeService ageRangeService;

    @Autowired
    private LocationService locationService;

    @GetMapping
    public ResponseEntity<ResponseDTO> getPets() {

        List<Pet> pets = new ArrayList<>();

        try {
            pets = DLListService.getPets().print();

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
        DLListService.getPets().add(
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

    @GetMapping(path = "/addtostart")
    public ResponseEntity<ResponseDTO> addToStart(@RequestBody @Valid PetDTO petDTO) {

        Location location = locationService.getLocationByCode(petDTO.getCode());

        if (location == null) {
            return new ResponseEntity<>(new ResponseDTO(
                    404,"la ubicación no existe",
                    null), HttpStatus.OK);
        }

        try {
            DLListService.getPets().addToStart(new Pet(petDTO.getIdentification(),
                    petDTO.getName(),petDTO.getAge(), petDTO.getGender().charAt(0), petDTO.getSpecies(),
                    location));
        }
        catch (DLListException e){
            return new ResponseEntity<>(new ResponseDTO(
                    409,e.getMessage(),
                    null), HttpStatus.OK);
        }

        return new ResponseEntity<>(new ResponseDTO(
                200, "Se ha adicionado a la mascota con éxito", null), HttpStatus.OK);
    }

    @GetMapping(path = "/deletebyage/{age}")
    public ResponseEntity<ResponseDTO> deleteByAge(@PathVariable @Valid byte age) {
        DLListService.getPets().removePetsByAge(age);
        return new ResponseEntity<>(new ResponseDTO(
                200, "La mascota fue eliminada", null), HttpStatus.OK);
    }

    @GetMapping(path = "/deletebyid/{id}")
    public ResponseEntity<ResponseDTO> deleteById(@PathVariable String id) {
        try {
            DLListService.getPets().deleteById(id);
        } catch (DLListException e){
            return new ResponseEntity<>(new ResponseDTO(
                    409,e.getMessage(),
                    null), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDTO(
                200, "La mascota fue eliminada", null), HttpStatus.OK);
    }

    @GetMapping(path = "/deletebyplace/{place}")
    public ResponseEntity<ResponseDTO> deleteByPlace(@PathVariable int place) {
        try {
            DLListService.getPets().deleteByPlace(place);
        } catch (DLListException e){
            return new ResponseEntity<>(new ResponseDTO(
                    409,e.getMessage(),
                    null), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDTO(
                200, "La mascota fue eliminada", null), HttpStatus.OK);
    }

    @GetMapping(path = "/invert")
    public ResponseEntity<ResponseDTO> invert() {
        try {
            DLListService.getPets().invert();

        } catch (DLListException e){
            return new ResponseEntity<>(new ResponseDTO(409, e.getMessage(), null), HttpStatus.OK);

        }
        return new ResponseEntity<>(new ResponseDTO(
                200, "La lista fue invertida", null), HttpStatus.OK);
    }

    @GetMapping(path = "/ordermalestostart")
    public ResponseEntity<ResponseDTO> odermalestostart() {
        try {
            DLListService.getPets().orderMalesToStart();
        }  catch (DLListException e) {
        return new ResponseEntity<>(new ResponseDTO(409, e.getMessage(), null), HttpStatus.OK);
    }

        return new ResponseEntity<>(new ResponseDTO(
                200, "La lista fue actualizada", null), HttpStatus.OK);
    }

    @GetMapping(path = "/interleavebygender")
    public ResponseEntity<ResponseDTO> interleavebygender() {
        try {
            DLListService.getPets().interleaveByGender();
        } catch (DLListException e) {
            return new ResponseEntity<>(new ResponseDTO(409, e.getMessage(), null), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDTO(
                200, "La lista fue actualizada", null), HttpStatus.OK);
    }

    @GetMapping(path = "/averageage")
    public ResponseEntity<ResponseDTO> averageAge() {

        float averageAge = 0f;
        try {
            averageAge = DLListService.getPets().averageAge();
        } catch (DLListException e) {
            return new ResponseEntity<>(new ResponseDTO(
                    409,e.getMessage(),
                    null), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDTO(
                200, "La edad promedio de mascotas es: " + averageAge, null), HttpStatus.OK);
    }

    @GetMapping(path = "/addpetatplace/{position}")
    public ResponseEntity<ResponseDTO> addPetAtPlace(@RequestBody @Valid PetDTO petDTO, @PathVariable int position) {

        Location location = locationService.getLocationByCode(petDTO.getCode());

        if (location == null) {
            return new ResponseEntity<>(new ResponseDTO(
                    404,"la ubicación no existe",
                    null), HttpStatus.OK);
        }

        try {
            DLListService.getPets().addPetAtPlace(new Pet(petDTO.getIdentification(),
                    petDTO.getName(),petDTO.getAge(), petDTO.getGender().charAt(0), petDTO.getSpecies(),
                    location),position);
        } catch (DLListException e){
            return new ResponseEntity<>(new ResponseDTO(
                    409,e.getMessage(),
                    null), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDTO(
                200, "La mascota fue añadida en la posición solicitada", null), HttpStatus.OK);
    }

    @GetMapping(path = "/movepet/{initialplace}/{places}")
    public ResponseEntity<ResponseDTO> movePet(@PathVariable("initialplace") int initialplace, @PathVariable ("places")int places) {

        if (initialplace > DLListService.getPets().getSize() || places >= initialplace || places < 1 || DLListService.getPets().getSize() == 0 || initialplace == 1){
            return new ResponseEntity<>(new ResponseDTO(
                    400, "Los lugares ingresados no son válidos", null), HttpStatus.BAD_REQUEST);
        }
        else {

            //copio el niño a ser movido
            Pet movedPet = DLListService.getPets().getPet(initialplace);

            //borro el niño a ser movido
            try {
                DLListService.getPets().deleteByPlace(initialplace);
            }catch (DLListException e){

            }

            //calculo el lugar final del niño
            int finalplace = initialplace - places;

            //agrego el niño en el lugar final
            try {
                DLListService.getPets().addPetAtPlace(movedPet, finalplace);
            } catch (DLListException e) {
                return new ResponseEntity<>(new ResponseDTO(
                        409, e.getMessage(), null), HttpStatus.OK);
            }
            return new ResponseEntity<>(new ResponseDTO(
                    200, "Mascota movida", null), HttpStatus.OK);
        }
    }

    @GetMapping(path = "/setpetback/{initialplace}/{places}")
    public ResponseEntity<ResponseDTO> backPosition(@PathVariable int initialplace, @PathVariable int places) {

        if (initialplace > DLListService.getPets().getSize() || places + initialplace > DLListService.getPets().getSize() || places < 1 || DLListService.getPets().getSize() == 0 || initialplace == DLListService.getPets().getSize()){
            return new ResponseEntity<>(new ResponseDTO(
                    400, "Los lugares ingresados no son válidos", null), HttpStatus.BAD_REQUEST);
        }
        else {

            //copio el niño a ser movido
            Pet movedPet = DLListService.getPets().getPet(initialplace);

            //borro el niño a ser movido
            try {
                DLListService.getPets().deleteByPlace(initialplace);
            }catch (DLListException e){

            }

            //calculo el lugar final de la mascota
            int finalplace = initialplace + places;

            //agrego la mascota en el lugar final
            try {
                DLListService.getPets().addPetAtPlace(movedPet, finalplace);
            } catch (DLListException e){
                return new ResponseEntity<>(new ResponseDTO(
                        409, e.getMessage(), null), HttpStatus.OK);
            }

            return new ResponseEntity<>(new ResponseDTO(
                    200, "Mascota movida", null), HttpStatus.OK);
        }
    }

    @GetMapping(path = "/agerangereport")
    public ResponseEntity<ResponseDTO> ageRangeReport(){

        ArrayList<AgeRangeReportDTO> AgeRangeReportDTOList = new ArrayList<>();

        for (AgeRange ageRange: ageRangeService.getAgeRanges()){
            AgeRangeReportDTOList.add(new AgeRangeReportDTO(DLListService.getPets().petsByAgeRange(ageRange.getFirstBracket(),ageRange.getSecondBracket()),new AgeRange(ageRange.getFirstBracket(),ageRange.getSecondBracket())));
        }

        return new ResponseEntity<>(

                new ResponseDTO(200, AgeRangeReportDTOList, null),
                HttpStatus.OK);
    }

    @GetMapping(path = "/infolocation")
    public ResponseEntity<ResponseDTO> getAmountOfPetsByLocationCode(){
        List<KidsByLocationDTO> petsByLocationDTOList = new ArrayList<>();
        for(Location loc: locationService.getLocations()){
                int count = DLListService.getPets().getAmountOfPetsByLocation(loc.getCode());
            if(count>0){
                petsByLocationDTOList.add(new KidsByLocationDTO(loc,count));
            }
        }
        return new ResponseEntity<>(new ResponseDTO(
                200,petsByLocationDTOList,
                null), HttpStatus.OK);
    }

    @GetMapping(path = "/infodepto")
    public ResponseEntity<ResponseDTO> getCountPetsByDeptoCode(){
        List<KidsByLocationDTO> petsByLocationDTOList1= new ArrayList<>();
        for(Location loc: locationService.getLocations()){
            int count = DLListService.getPets().getAmountOfPetsByLocation(loc.getCode());
            if(count>0){
                petsByLocationDTOList1.add(new KidsByLocationDTO(loc,count));
            }
        }
        return new ResponseEntity<>(new ResponseDTO(
                200,petsByLocationDTOList1,
                null), HttpStatus.OK);
    }

    @GetMapping(path = "/sendend/{first}")
    public ResponseEntity<ResponseDTO> petToFinishByLetter(@PathVariable char first) {
        try {
            DLListService.getPets().sendToBottom(Character.toUpperCase(first));
        } catch (DLListException e){
            return new ResponseEntity<>(new ResponseDTO(
                    409,e.getMessage(),
                    null), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDTO(
                200,"niños reorganizados",
                null), HttpStatus.OK);
    }

}