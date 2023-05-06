package co.edu.umanizales.tads.controller;

import co.edu.umanizales.tads.controller.dto.*;
import co.edu.umanizales.tads.exception.ListSEException;
import co.edu.umanizales.tads.model.Kid;
import co.edu.umanizales.tads.model.AgeRange;
import co.edu.umanizales.tads.controller.dto.AgeRangeReportDTO;
import co.edu.umanizales.tads.model.Location;
import co.edu.umanizales.tads.service.AgeRangeService;
import co.edu.umanizales.tads.service.ListSEService;
import co.edu.umanizales.tads.service.LocationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.MethodArgumentNotValidException;



import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(path = "/listse")
public class ListSEController {

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
    private ListSEService listSEService;
    @Autowired
    private LocationService locationService;

    @Autowired
    private AgeRangeService ageRangeService;

    @GetMapping
    public ResponseEntity<ResponseDTO> getKids(){
        return new ResponseEntity<>(new ResponseDTO(
                200,listSEService.getKids().getHead(),null), HttpStatus.OK);
    }

    @GetMapping("/invertkids")
    public ResponseEntity<ResponseDTO> invert(){
        listSEService.invert();
        return new ResponseEntity<>(new ResponseDTO(
                200,"SE ha invertido la lista",
                null), HttpStatus.OK);

    }

    @GetMapping(path = "/change_extremes")
    public ResponseEntity<ResponseDTO> changeExtremes() {
        listSEService.getKids().changeExtremes();
        return new ResponseEntity<>(new ResponseDTO(
                200,"SE han intercambiado los extremos",
                null), HttpStatus.OK);
    }

    @GetMapping(path="/removekidsbyage/{age}")
    public ResponseEntity<ResponseDTO> removeKidsByAge(@PathVariable byte age){
        try {
            listSEService.getKids().removeKidsByAge(age);
        } catch (ListSEException e){
            return new ResponseEntity<>(new ResponseDTO(

                    400,e.getMessage(),null), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDTO(

                200,"Niños removidos",null), HttpStatus.OK);

    }

    @GetMapping(path = "/addkid")
    public ResponseEntity<ResponseDTO> addKid(@RequestBody @Valid KidDTO kidDTO){
        Location location = locationService.getLocationByCode(kidDTO.getCode());
       if(location == null){
           return new ResponseEntity<>(new ResponseDTO(
                   404,"La ubicación no existe",
                   null), HttpStatus.OK);
       }

       try {
           listSEService.getKids().add(
                   new Kid(kidDTO.getIdentification(),
                           kidDTO.getName(), kidDTO.getAge(),
                           kidDTO.getGender().charAt(0), location));
       } catch (ListSEException e){
           return new ResponseEntity<>(new ResponseDTO(
                   400,e.getMessage(),
                   null), HttpStatus.OK);
       }
        return new ResponseEntity<>(new ResponseDTO(
                200, "Se ha adicionado el petacón",
                null), HttpStatus.OK);
    }

    @GetMapping(path="/addkidatplace/{place}")
    public ResponseEntity<ResponseDTO> addKidAtPlace(@PathVariable int place, @RequestBody @Valid KidDTO kidDTO){



        Location location = locationService.getLocationByCode(kidDTO.getCode());

        int validAdd = listSEService.getKids().validAdd(new Kid(kidDTO.getIdentification(),
                kidDTO.getName(), kidDTO.getAge(),
                kidDTO.getGender().charAt(0), location));

        if ( validAdd== 1){
            return new ResponseEntity<>(new ResponseDTO(
                    400,"ya hay un niño con ese ID",null), HttpStatus.OK);
        } else if (validAdd == 2){
            return new ResponseEntity<>(new ResponseDTO(
                    400,"el código de ubicación debe tener entre 3 y 8 dígitos",null), HttpStatus.OK);
        }else if (validAdd == 3){
            return new ResponseEntity<>(new ResponseDTO(
                    400,"ya existe alguien con ese nombre y ubicación",null), HttpStatus.OK);
        }

        try {
            listSEService.getKids().addKidAtPlace(place, new Kid(kidDTO.getIdentification(),
                    kidDTO.getName(), kidDTO.getAge(),
                    kidDTO.getGender().charAt(0), location));
        } catch (ListSEException e) {

        }

        return new ResponseEntity<>(new ResponseDTO(
                200,"Niño agregado",null), HttpStatus.OK);

    }

    @GetMapping(path = "/orderboystostart")
    public ResponseEntity<ResponseDTO> orderboystostart(){


        try {
            listSEService.getKids().orderBoysToStart();
        } catch (ListSEException e){

        }
        return new ResponseEntity<>(new ResponseDTO(
                200,"fila ordenada",null),HttpStatus.OK);
    }

    @GetMapping(path="/getsize")
    public ResponseEntity<ResponseDTO> getSize(){
        return new ResponseEntity<>(new ResponseDTO(
                200, listSEService.getKids().getSize(), null), HttpStatus.OK);
    }

    @GetMapping(path="/movekid/{initialplace}/{steps}")
    public ResponseEntity<ResponseDTO> moveKid(@PathVariable("initialplace") int initialplace, @PathVariable("steps") int steps)    {

        if (initialplace > listSEService.getKids().getSize() || steps > initialplace || steps < 1 || listSEService.getKids().getSize() == 0 || initialplace == 1){
            return new ResponseEntity<>(new ResponseDTO(
                    400, "Los lugares ingresados no son válidos", null), HttpStatus.BAD_REQUEST);
        }
        else {

            //copio el niño a ser movido
            Kid movedKid = listSEService.getKids().getKid(initialplace);

            //borro el niño a ser movido
            listSEService.getKids().deleteKid(initialplace);

            //calculo el lugar final del niño
            int finalplace = initialplace - steps;

            //agrego el niño en el lugar final
            try {
                listSEService.getKids().addKidAtPlace(finalplace, movedKid);
            } catch (ListSEException e){

            }

            return new ResponseEntity<>(new ResponseDTO(
                    200, "Niño movido", null), HttpStatus.OK);
        }
    }

    @GetMapping(path="/setkidback/{initialplace}/{steps}")
    public ResponseEntity<ResponseDTO> setKidBack(@PathVariable("initialplace") int initialplace, @PathVariable("steps") int steps)    {

        if (initialplace > listSEService.getKids().getSize() || (steps + initialplace) > listSEService.getKids().getSize() || steps < 1 || listSEService.getKids().getSize() == 0 || initialplace == listSEService.getKids().getSize()){
            return new ResponseEntity<>(new ResponseDTO(
                    400, "Los lugares ingresados no son válidos", null), HttpStatus.BAD_REQUEST);
        }
        else {

            //copio el niño
            Kid movedKid = listSEService.getKids().getKid(initialplace);

            //borro el niño a ser movido
            listSEService.getKids().deleteKid(initialplace);

            //calculo el lugar final del niño
            int finalplace = initialplace + steps;

            //agrego el niño en el lugar final
            try {
                listSEService.getKids().addKidAtPlace(finalplace, movedKid);
            } catch (ListSEException e){

            }

            return new ResponseEntity<>(new ResponseDTO(
                    200, "Niño movido", null), HttpStatus.OK);
        }
    }

    @GetMapping(path="/deletekid/{place}")
    public ResponseEntity<ResponseDTO> moveKid(@PathVariable("place") int place){

        if (place == 0 || place > listSEService.getKids().getSize()){
            return new ResponseEntity<>(new ResponseDTO(
                    400, "No existe ese lugar", null), HttpStatus.BAD_REQUEST);
        }
        else {
            listSEService.getKids().deleteKid(place);

            return new ResponseEntity<>(new ResponseDTO(
                    200, "Niño borrado", null), HttpStatus.OK);
        }
    }

    @GetMapping(path = "/kidspercity")
    public ResponseEntity<ResponseDTO> getKidsPerCity(){

        //defino una nueva lista de tipo KidsByLocationDTO llamada KidsByLocationDOTList
        List<KidsByLocationDTO> kidsByLocationDTOList = new ArrayList<>();

        //hago un ciclo for que itere por cada ubicacion existente
        for(Location loc: locationService.getLocations()){

            //defino una variable int llamada count y hago uqe sea igual a lo que retorne el metodo kidsPerCity dado en el parametro la ubicacion por la que se esta iterando en el for
            int count = listSEService.getKids().kidsPerCity(loc.getCode());

            //si dicho metodo retorno unn valor superior, es decir, si la ubicacion tiene niños en ella, la agrego a la lista de KidsByLocationDTOList
            if(count>0){
                kidsByLocationDTOList.add(new KidsByLocationDTO(loc,count));
            }
        }

        //al finalizar este for retorno la lista construida
        return new ResponseEntity<>(new ResponseDTO(
                200,kidsByLocationDTOList,
                null), HttpStatus.OK);
    }

    @GetMapping(path = "/kidsperdepartment")
    public ResponseEntity<ResponseDTO> getKidsPerDepartment(){

        //defino una nueva lista de tipo KidsByLocationDTO llamada KidsByLocationDOTList
        List<KidsByLocationDTO> kidsByLocationDTOList = new ArrayList<>();

        //hago un ciclo for que itere por cada ubicacion existente
        for(Location loc: locationService.getLocations()){

            //defino una variable int llamada count y hago uqe sea igual a lo que retorne el metodo kidsPerCity dado en el parametro la ubicacion por la que se esta iterando en el for
            int count = listSEService.getKids().kidsPerDepartment(loc.getCode());

            //si dicho metodo retorno unn valor superior, es decir, si la ubicacion tiene niños en ella, la agrego a la lista de KidsByLocationDTOList
            if(count>0){
                kidsByLocationDTOList.add(new KidsByLocationDTO(loc,count));
            }
        }

        //al finalizar este for retorno la lista construida
        return new ResponseEntity<>(new ResponseDTO(
                200,kidsByLocationDTOList,
                null), HttpStatus.OK);
    }

    @GetMapping(path = "/report/{age}")
    public ResponseEntity<ResponseDTO> report(@PathVariable int age){
        //defino una nueva lista de tipo kidsbylocationDTO
        ArrayList<KidsByCityDTO> KidsByCityDTOList = new ArrayList<>();

        for(Location loc: locationService.getLocations()) {
            if (loc.getCode().length() == 8) {

                //defino un string llamado cityname para el primer argumento
                String cityName = loc.getName();

                //defino una lista de KidsPerGenderDTO
                ArrayList<KidsPerGenderDTO> KidsPerGenderDTOList = new ArrayList<>();

                //cuento cuantos niños hay y los agrego a la lista
                KidsPerGenderDTOList.add(new KidsPerGenderDTO('m',((listSEService.getKids().kidsPerCityPerGenderAboveAge(loc.getCode(),'m',age)))));

                //cuento cuantas niñas hay
                KidsPerGenderDTOList.add(new KidsPerGenderDTO('f',((listSEService.getKids().kidsPerCityPerGenderAboveAge(loc.getCode(),'f',age)))));

                //defino una variable int llamada total y hago uqe sea igual a lo que retorne el metodo kidsPerCity dado en el parametro la ubicacion por la que se esta iterando en el for
                int total = listSEService.getKids().kidsPerCity(loc.getCode());


                KidsByCityDTOList.add(new KidsByCityDTO(cityName, KidsPerGenderDTOList, total));
            }
        }
        return new ResponseEntity<>(new ResponseDTO(
                200,KidsByCityDTOList,null),HttpStatus.OK);
    }

    @GetMapping(path = "/sendtobottom/{firstChar}")
    public ResponseEntity<ResponseDTO> sendTobottom(@PathVariable char firstChar) {

        listSEService.getKids().sendToBottom(Character.toUpperCase(firstChar));
        return new ResponseEntity<>(
                new ResponseDTO(200, "Cambio Realizado", null),
                HttpStatus.OK);


    }

    @GetMapping(path = "/averageage")
    public ResponseEntity<ResponseDTO> averageAge() {

        return new ResponseEntity<>(
                new ResponseDTO(200, listSEService.getKids().averageAge(), null),
                HttpStatus.OK);

    }

    @GetMapping(path = "/interleavebygender")
    public ResponseEntity<ResponseDTO> interleaveByGender() {

        try {
            listSEService.getKids().interleaveByGender();
        } catch (ListSEException e){
            return new ResponseEntity<>(new ResponseDTO(400, e.getMessage(), null),
                    HttpStatus.OK);
        }

        return new ResponseEntity<>(
                new ResponseDTO(200, "niños reorganizados", null),
                HttpStatus.OK);

    }

    @GetMapping(path = "/agerangereport")
    public ResponseEntity<ResponseDTO> agerangereport(){

        ArrayList<AgeRangeReportDTO> AgeRangeReportDTOList = new ArrayList<>();

        for (AgeRange ageRange: ageRangeService.getAgeRanges()){
            AgeRangeReportDTOList.add(new AgeRangeReportDTO(listSEService.getKids().kidsByAgeRange(ageRange.getFirstBracket(),ageRange.getSecondBracket()),new AgeRange(ageRange.getFirstBracket(),ageRange.getSecondBracket())));
        }

        return new ResponseEntity<>(

                new ResponseDTO(200, AgeRangeReportDTOList, null),
                HttpStatus.OK);
    }


}