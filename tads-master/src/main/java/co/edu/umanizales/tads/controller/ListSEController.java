package co.edu.umanizales.tads.controller;

import co.edu.umanizales.tads.controller.dto.KidDTO;
import co.edu.umanizales.tads.controller.dto.ResponseDTO;
import co.edu.umanizales.tads.model.Kid;
import co.edu.umanizales.tads.model.ListSE;
import co.edu.umanizales.tads.service.ListSEService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/listse")
public class ListSEController {
    @Autowired
    private ListSEService listSEService;

    @GetMapping
    public ResponseEntity<ResponseDTO> getKids(){
        return new ResponseEntity<>(new ResponseDTO(
                200,listSEService.getKids(),null), HttpStatus.OK);

    }

    @GetMapping(path="/removekidsbyage/{age}")
    public ResponseEntity<ResponseDTO> removeKidsByAge(@PathVariable byte age){
        listSEService.getKids().removeKidsByAge(age);
        return new ResponseEntity<>(new ResponseDTO(

                200,"Niños removidos",null), HttpStatus.OK);

    }

    @GetMapping(path="/addkidatplace/{place}")
    public ResponseEntity<ResponseDTO> addKidAtPlace(@PathVariable int place, @RequestBody Kid kid){
        listSEService.getKids().addKidAtPlace(place, kid);
        return new ResponseEntity<>(new ResponseDTO(

                200,"Niño agregado",null), HttpStatus.OK);

    }

    @GetMapping(path="/addkid")
    public ResponseEntity<ResponseDTO> add(@RequestBody Kid kid){
        listSEService.getKids().add(kid);
        return new ResponseEntity<>(new ResponseDTO(
                200,"Niño agregado",null), HttpStatus.OK);

    }

    @GetMapping(path="/movekid/{initialplace}/{finalplace}")
    public ResponseEntity<ResponseDTO> moveKid(@PathVariable("initialplace") int initialplace, @PathVariable("finalplace") int finalplace){

        if (initialplace < 1 || finalplace > listSEService.getKids().size() || initialplace > listSEService.getKids().size() || finalplace < 1 || initialplace == finalplace){
            return new ResponseEntity<>(new ResponseDTO(
                    400, "Los lugares ingresados no son válidos", null), HttpStatus.BAD_REQUEST);
        }
        else {
            listSEService.getKids().moveKid(initialplace, finalplace);

            return new ResponseEntity<>(new ResponseDTO(
                    200, "Niño movido", null), HttpStatus.OK);
        }
    }

    @GetMapping(path="/deletekid/{place}")
    public ResponseEntity<ResponseDTO> moveKid(@PathVariable("place") int place){

        if (place == 0 || place > listSEService.getKids().size()){
            return new ResponseEntity<>(new ResponseDTO(
                    400, "No existe ese lugar", null), HttpStatus.BAD_REQUEST);
        }
        else {
            listSEService.getKids().deleteKid(place);

            return new ResponseEntity<>(new ResponseDTO(
                    200, "Niño borrado", null), HttpStatus.OK);
        }
    }

}
