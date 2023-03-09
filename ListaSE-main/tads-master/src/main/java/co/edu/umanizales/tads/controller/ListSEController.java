package co.edu.umanizales.tads.controller;

import co.edu.umanizales.tads.controller.dto.ResponseDTO;
import co.edu.umanizales.tads.model.ListSE;
import co.edu.umanizales.tads.service.ListSEService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/listse")
public class ListSEController {
    @Autowired
    private ListSEService listSEService;


    private ListSE listSE;
    @GetMapping(path = "/getkids")
    public ResponseEntity<ResponseDTO> getKids(){
        return new ResponseEntity<>(new ResponseDTO(
                200,listSEService.getKids(),null), HttpStatus.OK);
    }

    @GetMapping(path="/addkidinplace/{place}")
    public ResponseEntity<ResponseDTO> addKidInPlace(@RequestBody KidDTO kidDTO, @PathVariable int place){
        return new ResponseEntity<>(new ResponseDTO(
                200,listSE.AddKidInPlace(place, (new kid (kidDTO.getName, KidDTO.getIdentification, KidDTO.getAge)),null), HttpStatus.OK);
    }



    }





