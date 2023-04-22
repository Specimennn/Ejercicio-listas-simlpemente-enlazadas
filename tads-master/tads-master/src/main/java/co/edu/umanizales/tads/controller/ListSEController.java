package co.edu.umanizales.tads.controller;

import co.edu.umanizales.tads.controller.dto.*;
import co.edu.umanizales.tads.model.Kid;
import co.edu.umanizales.tads.model.Location;
import co.edu.umanizales.tads.service.ListSEService;
import co.edu.umanizales.tads.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/listse")
public class ListSEController {
    @Autowired
    private ListSEService listSEService;
    @Autowired
    private LocationService locationService;

    @GetMapping
    public ResponseEntity<ResponseDTO> getKids(){
        return new ResponseEntity<>(new ResponseDTO(
                200,listSEService.getKids().getHead(),null), HttpStatus.OK);
    }

    @GetMapping("/invert")
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
        listSEService.getKids().removeKidsByAge(age);
        return new ResponseEntity<>(new ResponseDTO(

                200,"Niños removidos",null), HttpStatus.OK);

    }

    @GetMapping(path = "/addkid")
    public ResponseEntity<ResponseDTO> addKid(@RequestBody KidDTO kidDTO){
        Location location = locationService.getLocationByCode(kidDTO.getCodeLocation());
       if(location == null){
           return new ResponseEntity<>(new ResponseDTO(
                   404,"La ubicación no existe",
                   null), HttpStatus.OK);
       }

       int validAdd = listSEService.getKids().validAdd(new Kid(kidDTO.getIdentification(), kidDTO.getName(), kidDTO.getAge(),kidDTO.getGender(),location));

       //Cuando validadd retorna 1 es porque encontro otro niño con el mismo id
       if( validAdd == 1) {
           return new ResponseEntity<>(new ResponseDTO(
                   400,"ya hay un niño con ese id",
                   null), HttpStatus.OK);
       }

       //cuando validadd retorna 2 es porque el codigo de ubincacion es menor a 3 o mayor a 8
        else if ( validAdd == 2) {
            return new ResponseEntity<>(new ResponseDTO(
                    400,"La ubicación no es valida",
                    null), HttpStatus.OK);
        }

        //en el unico otro caso posible, es decir cuando retorna 0 es porque no hay error
       else {
           listSEService.getKids().add(
                   new Kid(kidDTO.getIdentification(),
                           kidDTO.getName(), kidDTO.getAge(),
                           kidDTO.getGender(), location));
           return new ResponseEntity<>(new ResponseDTO(
                   200, "Se ha adicionado el petacón",
                   null), HttpStatus.OK);
       }

    }

    @GetMapping(path="/addkidatplace/{place}")
    public ResponseEntity<ResponseDTO> addKidAtPlace(@PathVariable int place, @RequestBody Kid kid){
        listSEService.getKids().addKidAtPlace(place, kid);
        return new ResponseEntity<>(new ResponseDTO(

                200,"Niño agregado",null), HttpStatus.OK);

    }

    @GetMapping(path = "/orderboystostart")
    public ResponseEntity<ResponseDTO> orderboystostart(){


        listSEService.getKids().orderBoysToStart();
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

        if (initialplace > listSEService.getKids().getSize() || steps > initialplace || steps < 1){
            return new ResponseEntity<>(new ResponseDTO(
                    400, "Los lugares ingresados no son válidos", null), HttpStatus.BAD_REQUEST);
        }
        else {

            Kid movedKid = listSEService.getKids().getKid(initialplace);

            listSEService.getKids().deleteKid(initialplace);

            int finalplace = initialplace - steps;

            listSEService.getKids().addKidAtPlace(finalplace, movedKid);

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

}
