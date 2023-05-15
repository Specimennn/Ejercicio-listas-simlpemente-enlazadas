package co.edu.umanizales.tads.model;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Random;


import java.util.ArrayList;
import java.util.List;

import co.edu.umanizales.tads.exception.DLListException;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import static java.lang.Character.toLowerCase;
import static java.lang.Character.toUpperCase;

@Data
@Getter
@Setter


public class RoundDLList {

    private DLNode head;

    private int size;

    private List<Pet> pets = new ArrayList<>();

    public void validAdd(Pet pet) throws DLListException{

        if (head == null) {
        } else {

            DLNode current = head;

            //reviso si ya existe una mascota con la id de la mascota siendo agregada
            if (current.getData().getIdentification().equals(pet.getIdentification())) {
                throw new DLListException("Ya existe una mascota con la identificación " + pet.getIdentification());
            }

            //reviso si ya existe una mascota con el mismo nombre y ubicacion
            if (current.getData().getName().equals(pet.getName()) && current.getData().getLocation().equals(pet.getLocation())) {
                throw new DLListException("Ya existe una mascota con el mismo nombre y ubicacion");
            }

            //recorro la lista hasta que llegue al final, es decir a la cabeza
            while (current.getNext() != head) {

                //reviso si ya existe una mascota con la id de la mascota siendo agregada
                if (current.getData().getIdentification().equals(pet.getIdentification())) {
                    throw new DLListException("Ya existe una mascota con la identificación " + pet.getIdentification());
                }

                //reviso si ya existe una mascota con el mismo nombre y ubicacion
                if (current.getData().getName().equals(pet.getName()) && current.getData().getLocation().equals(pet.getLocation())) {
                    throw new DLListException("Ya existe una mascota con el mismo nombre y ubicacion");
                }

                current = current.getNext();

            }

            //en el final, o uno antes de la cabeza reviso si el último tiene los mismos datos
            //reviso si ya existe una mascota con la id de la mascota siendo agregada
            if (current.getData().getIdentification().equals(pet.getIdentification())) {
                throw new DLListException("Ya existe una mascota con la identificación " + pet.getIdentification());
            }

            //reviso si ya existe una mascota con el mismo nombre y ubicacion
            if (current.getData().getName().equals(pet.getName()) && current.getData().getLocation().equals(pet.getLocation())) {
                throw new DLListException("Ya existe una mascota con el mismo nombre y ubicacion");
            }

        }
    }

    public void add(Pet pet) throws DLListException {

        //reviso si puedo meter a la mascota
        try {
            validAdd(pet);
        } catch (DLListException e){
            throw new DLListException(e.getMessage());
        }

        //creo un nuevo nodo que es el que voy a meter
        DLNode newNode = new DLNode(pet);

        //si la cabeza es nula, la nueva cabeza es la mascota ingresada
        if (head == null) {

            head = newNode;
            newNode.setNext(head);
            newNode.setPrev(head);

        } else {

            //meto la mascota
            DLNode lastNode = head.getPrev();

            newNode.setNext(head);
            newNode.setPrev(lastNode);

            lastNode.setNext(newNode);
            head.setPrev(newNode);

        }

        //aumento el tamaño de la lista
        size++;
    }

    public List <Pet> print() throws DLListException{

        //vacío la lista normal que ya tengo
        pets.clear();

        //recorro la lista agregando las mascotas de cada nodo a la lista normal
        if (head != null){
            DLNode temp = head;

            do {
                pets.add(temp.getData());
                temp = temp.getNext();
            }
            while (temp != head);
        } else {
            throw new DLListException("la lista está vacía");
        }

        //retorno dicha lista normal
        return pets;
    }

    public void addToStart(@Valid Pet pet)throws DLListException{

        DLNode newNode = new DLNode(pet);

        if (head != null) {
            try {
                validAdd(pet);
            } catch (DLListException e) {
                throw new DLListException(e.getMessage());
            }
        }

        //si la cabeza está llena, pongo de previo a la cabeza al nuevo, y pongo a la cabeza de siguiente al nuevo
        if(head != null)
        {
            newNode.setPrev(head);
            head.setNext(newNode);
            //defino la cabeza como el nuevo
            head = newNode;
            size ++;
        } else {
            add(pet);
        }

    }

    public void addPetAtPlace(@Valid Pet pet, int position) throws DLListException {

        //reviso que no haya ninguna otra mascota con el mismo id o mismo nombre y ubicacion utilizando el metodo validadd
        try {
            validAdd(pet);
        } catch (DLListException e) {
            throw new DLListException(e.getMessage());
        }

        //defino un nuevo nodo que es el que voy a meter
        DLNode newNode = new DLNode(pet);

        //aqui lo estoy metiendo en la cabeza
        if (position == 1) {

            addToStart(pet);
            return;

        } else if (position > size) {

            add(pet);

        } else if (position > 1 && position <= size) {

            //aqui lo estoy metiendo hacia adelante
            DLNode temp = head;
            int count = 1;

            //ciclo hasta llegar a un puesto antes de donde lo voy a meter
            while (count < position - 1) {

                temp = temp.getNext();
                count++;
            }

            if (temp.getNext() != head) {

                newNode.setNext(temp.getNext());
                newNode.setPrev(temp);
                temp.getNext().setPrev(newNode);
                temp.setNext(newNode);
                size++;

            } else {
                add(pet);
            }

        } else if (position < 1) {

            //aqui estoy metiendo la mascota hacia atras
            DLNode temp = head;
            int count = 1;
            while (count > position) {

                temp = temp.getPrev();
                count--;
            }

            //meto la mascota
            DLNode lastNode = temp.getPrev();

            newNode.setNext(temp);
            newNode.setPrev(lastNode);

            lastNode.setNext(newNode);
            temp.setPrev(newNode);

            size++;

        }

    }

    public void washRandomPet()throws DLListException{

        if (head == null){
            throw new DLListException("la lista está vacía");
        }


        //defino el lugar random que solo llegue hasta el tamañpo
        Random random = new Random();
        int place = random.nextInt(size) + 1;


        //ciclo hasta llegar hasta el lugar
        DLNode temp = head;
        int steps = 0;

        while (temp != null){

            //reviso si el puesto es igual al puesto buscado
            if (steps == place){

                //baño al perro
                temp.getData().setClean(true);
                return;
            }
            steps++;
            temp = temp.getNext();
        }

    }

}
