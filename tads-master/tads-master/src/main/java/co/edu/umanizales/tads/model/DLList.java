package co.edu.umanizales.tads.model;

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

public class DLList {
    private DLNode head;
    private DLNode tail;
    private int size;
    private List<Pet> pets = new ArrayList<>();

    //este devuelve exception, sino no hace nada
    public void validAdd (Pet pet) throws DLListException{

        if (head == null) {

        } else {

            DLNode current = head;

            //reviso si ya existe una mascota con la id de la mascota siendo agregada
            if (current.getData().getIdentification().equals(pet.getIdentification()) ){
                throw new DLListException("Ya existe una mascota con la identificación" + pet.getIdentification());
            }

            //reviso si ya existe una mascota con el mismo nombre y ubicacion
            if (current.getData().getName().equals(pet.getName()) && current.getData().getLocation().equals(pet.getLocation())){
                throw new DLListException("Ya existe una mascota con el mismo nombre y ubicacion");
            }

            //recorro la lista hasta que llegue al final
            while (current.getNext() != null) {

                //reviso si ya existe una mascota con la id de la mascota siendo agregada
                if (current.getData().getIdentification().equals(pet.getIdentification()) ){
                    throw new DLListException("Ya existe una mascota con la identificación" + pet.getIdentification());
                }

                //reviso si ya existe una mascota con el mismo nombre y ubicacion
                if (current.getData().getName().equals(pet.getName()) && current.getData().getLocation().equals(pet.getLocation())){
                    throw new DLListException("Ya existe una mascota con el mismo nombre y ubicacion");
                }

                current = current.getNext();

            }
        }
}

    public void add(Pet pet) throws DLListException {

        //si la cabeza es nula, la nueva cabeza es la mascota ingresada
        if (head == null) {
            head = new DLNode(pet);

        } else {

            //sino, defino un nuevo nodo
            DLNode newNode = new DLNode(pet);
            DLNode current = head;

            //reviso si ya existe una mascota con la id de la mascota siendo agregada
            if (current.getData().getIdentification().equals(pet.getIdentification()) ){
                throw new DLListException("Ya existe una mascota con la identificación" + pet.getIdentification());
            }

            //reviso si ya existe una mascota con el mismo nombre y ubicacion
            if (current.getData().getName().equals(pet.getName()) && current.getData().getLocation().equals(pet.getLocation())){
                throw new DLListException("Ya existe una mascota con el mismo nombre y ubicacion");
            }

            //recorro la lista hasta que llegue al final
            while (current.getNext() != null) {

                //reviso si ya existe una mascota con la id de la mascota siendo agregada
                if (current.getData().getIdentification().equals(pet.getIdentification()) ){
                    throw new DLListException("Ya existe una mascota con la identificación" + pet.getIdentification());
                }

                //reviso si ya existe una mascota con el mismo nombre y ubicacion
                if (current.getData().getName().equals(pet.getName()) && current.getData().getLocation().equals(pet.getLocation())){
                    throw new DLListException("Ya existe una mascota con el mismo nombre y ubicacion");
                }

                current = current.getNext();

            }

            //en el final, meto la mascota
            newNode.setPrev(current);
            current.setNext(newNode);

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
            while (temp != null){
                pets.add(temp.getData());
                temp = temp.getNext();
            }
        } else {
            throw new DLListException("la lista está vacía");
        }

        //retorno dicha lista normal
        return pets;
    }

    public void addToStart(Pet pet)throws DLListException{

        DLNode newNode = new DLNode(pet);

        try {
            validAdd(pet);
        } catch (DLListException e){
            throw new DLListException(e.getMessage());
        }

        //si la cabeza está llena, pongo de previo a la cabeza al nuevo, y pongo a la cabeza de siguiente al nuevo
        if(head !=null)
        {
            head.setPrev(newNode);
            newNode.setNext(head);
        }

        //defino la cabeza como el nuevo
        head = newNode;
        size ++;

    }

    public void removePetsByAge (Byte age){
        DLNode temp = head;

        //recorro la lista
        while (temp != null){

            //cuando encuentre a una mascota de la edad
            if (temp.getData().getAge() == age){
                DLNode prev = temp.getPrev();
                DLNode next = temp.getNext();

                //reviso si es la cabeza
                if (prev == null){

                    //si sí, la cabeza será la siguiente, es decir, quito la mascota de la cabeza
                    head = next;
                }else{

                    //sino, hago que el previo tenga como siguiente a el siguiente de temp, es decir, saco a temp de la lista
                    prev.setNext(next);
                }

                //reviso si lo estoy sacando del final
                if (next != null){
                    //si no, defino el prev de el que es siguiente al borrado
                    next.setPrev(prev);
                }

                //defino el tamaño como menor
                size --;

            }
            temp = temp.getNext();
        }
    }

    public void deleteById (String id) throws DLListException{

        //reviso si hay mascotas, si no, tiro un error
        if (head == null){
            throw new DLListException("No hay mascotas en la fila");
        }

        //defino un ayudante y defino un boolean para saber si borré o no a alguna mascota
        DLNode temp = head;
        boolean petWasDeleted = false;

        //recorro la lista
        while (temp != null){

            //si la identificacion es igual a la identificacion buscada
            if (temp.getData().getIdentification().equals(id)){
                DLNode prev = temp.getPrev();
                DLNode next = temp.getNext();

                //establezco que ya borre la mascota, y resto 1 al tamaño de la fila
                petWasDeleted = true;
                size --;

                //reviso si es la cabeza
                if (prev == null){
                    //si sí, la cabeza será la siguiente, es decir, quito la mascota de la cabeza
                    head = next;
                }else{
                    //sino, hago que el previo tenga como siguiente a el siguiente de temp, es decir, saco a temp de la lista
                    prev.setNext(next);
                }
                if (next != null){
                    //si next no es nulo, es decir, si no estoy en el final de la fila, hago que next tenga de previo a prev
                    next.setPrev(prev);
                }
            }
            temp = temp.getNext();
        }

        if (petWasDeleted == false){
            throw new DLListException("no había ninguna mascota con dicha edad");
        }

    }

    public void deleteByPlace (int place) throws DLListException  {

        //reviso que los lugares ingresados sean válidos
        if (head == null) {
            throw new DLListException("la lista está vacía");
        } else if (place < 1) {
            throw new DLListException("el ingresado es menor a 1");
        } else if (place > size){
            throw new DLListException("el lugar ingresado es mayor al tamaño de la lista");
        }

        //defino un ayudante y un contador para saber cuantos pasos llevo
        DLNode temp = head;
        int steps = 1;

        //recorro la lista
        while (temp != null){

            //reviso si el puesto es igual al puesto buscado
            if (steps == place){
                DLNode prev = temp.getPrev();
                DLNode next = temp.getNext();

                //reviso si es la cabeza
                if (prev == null){
                    //si sí, la cabeza será la siguiente, es decir, quito la mascota de la cabeza
                    head = next;
                }else{
                    //sino, hago que el previo tenga como siguiente a el siguiente de temp, es decir, saco a temp de la lista
                    prev.setNext(next);
                }

                //reviso si es el último
                if (next != null){
                    //si next no es nulo, es decir, si no estoy en el final de la fila, hago que next tenga de previo a prev
                    next.setPrev(prev);
                }

                //después de haberlo removido, resto del tamaño de la fila
                size--;
            }
            steps++;
            temp = temp.getNext();
        }
    }

    public Pet getPet(int place){
        DLNode temp = head;
        int steps = 1;

        //recorro la lista
        while (steps < place){

            temp = temp.getNext();
            steps++;
        }

        return temp.getData();
    }

    public void invert() throws DLListException{
        if (this.head != null){

            //copio la lista y defino el ayudante
            DLList copy = new DLList();
            DLNode temp = this.head;

            //ciclo por la fila
            while (temp != null){

                //voy metiendo al principio a cada uno por el que voy ciclando
                copy.addToStart(temp.getData());
                temp = temp.getNext();
            }

            //reemplazo esta lista por la copia invertida que acabo de crear
            this.head = copy.getHead();

        }
    }

    public void orderMalesToStart() throws DLListException {
        if (head != null){

            //creo una lista copia
            DLList copy = new DLList();
            DLNode temp = head;

            if (temp == null) {
                throw new DLListException("La lista está vacía");
            }

            //ciclo por la lista
            while (temp != null){

                //si encuentro un macho, lo meto al principio
                if (toLowerCase(temp.getData().getGender()) == 'm'){
                    copy.addToStart(temp.getData());
                }else{
                    //si encunetro una hembra, la meto al final
                    copy.add(temp.getData());
                }
                temp = temp.getNext();
            }

            //remplazo la lista por la copia
            head = copy.getHead();
        }
    }

    public void interleaveByGender() throws DLListException{

        if (head == null) {
            throw new DLListException("la lista está vacía");
        }

        //creo una lista vacía de machos y una vacía de hembras
        DLList listMale = new DLList();
        DLList listFemale = new DLList();

        //ciclo por la fila
        DLNode temp = this.head;
        while (temp != null){

            //si me encuentro a un macho lo meto a la lista de machos
            if(toLowerCase(temp.getData().getGender())=='m'){
                listMale.add(temp.getData());
            }
            else{

                //si me encuentro a una hembra, la meto a la lista de hembras
                listFemale.add(temp.getData());
            }
            temp = temp.getNext();
        }


        //creo una lista copia y los ayudnates para la lista de hembras y la lista de mujeres
        DLList rearrangedList = new DLList();
        DLNode maleNode = listMale.getHead();
        DLNode femaleNode = listFemale.getHead();

        if  ( maleNode == null) {
            throw new DLListException("No hay machos en la fila");
        }

        if  (femaleNode == null) {
            throw new DLListException("No hay hembras en la fila");
        }

        //recorro ambas listas
        while (maleNode != null || femaleNode != null){

            //reviso si todavia hay machos en la lista de machos
            if (maleNode != null){

                //meto un macho
                rearrangedList.add(maleNode.getData());
                maleNode = maleNode.getNext();
            }

            //reviso si todavia hay hembras en la lista de hembras
            if (femaleNode != null){

                //meto una hembra
                rearrangedList.add(femaleNode.getData());
                femaleNode = femaleNode.getNext();
            }
        }

        //remplazo esta lista por la lista copia que cree
        this.head = rearrangedList.getHead();
    }

    public float averageAge() throws DLListException{

            //defino dos variables int para contar el total de edad
            DLNode temp = head;
            int ageTotal = 0;

            if (temp == null) {
                throw new DLListException("La lista está vacía");
            }

            while (temp.getNext() != null){

                //voy sumando el total de edad con la mascota que esta sienod iterada
                ageTotal += temp.getData().getAge();
                temp = temp.getNext();
            }
            return (float)ageTotal/size;

    }

    public int getAmountOfPetsByLocation(String code){

        //defino un contador
        int count = 0;

        //ciclo por la lista
            DLNode temp = this.head;
            while(temp != null){

                //si me encuentro a una mascota de la ubicacion, aumento el contador
                if(temp.getData().getLocation().getCode().equals(code)){
                    count++;
                }
                temp = temp.getNext();
            }

            //despues de haber ciclado por toda la lista retorno el contador
        return count;
    }

    public int getAmountOfPetsByCity(String code){
        int count =0;
        if( this.head!=null){
            DLNode temp = this.head;
            while(temp != null){
                if(temp.getData().getLocation().getCode().substring(0,5).equals(code)){
                    count++;
                }
                temp = temp.getNext();
            }
        }
        return count;
    }

    public void addPetAtPlace(@Valid Pet pet, int position) throws DLListException{

        //reviso que no hayan puesto valores incorrectos
        if (size < position){
            throw new DLListException("ingresó una posición mayor al tamaño de la fila");
        } else if (position < 1){
            throw new DLListException("ingresó una posición inferior a 0");
        }

        //reviso que no haya ninguna otra mascota con el mismo id o mismo nombre y ubicacion utilizando el metodo validadd
        try {
            validAdd(pet);
        } catch (DLListException e){
            throw new DLListException(e.getMessage());
        }

        //defino un nuevo nodo que es el que voy a meter
        DLNode newNode = new DLNode(pet);

        //aqui lo estoy metiendo en la cabeza
        if (position == 1){

            addToStart(pet);

        } else {

            DLNode temp = head;
            int cont = 1;
            while (temp != null && cont < position - 1) {

                temp = temp.getNext();
                cont++;
            }

            if (temp != null) {

                newNode.setNext(temp.getNext());
                newNode.setPrev(temp);
                if (temp.getNext() != null) {
                    temp.getNext().setPrev(newNode);
                }
                temp.setNext(newNode);

            } else {
                add(pet);
            }
        }
    }

    public int petsByAgeRange(int first, int last){

        //defino la cuenta, el ayudante y ciclo por la fila
        DLNode temp = head;
        int count = 0;
        while (temp != null){

            //cuando me encuentre con una mascota de la edad, aumento la cuenta
            if (temp.getData().getAge() >= first && temp.getData().getAge() <= last){
                count ++;
            }
            temp = temp.getNext();
        }
        return count;
    }

    public void sendToBottom (char letter) throws DLListException{

        //creo la lista copia
        DLList copy = new DLList();
        DLNode temp = this.head;

        if (temp == null) {
            throw new DLListException("la lista está vacía");
        }

        //ciclo por las mascotas
            while (temp != null){

                //si la mascota tiene la primera letra no igual a la letra siendo buscada
                if (toUpperCase(temp.getData().getName().charAt(0)) != toUpperCase(letter)){
                    copy.addToStart(temp.getData());

                    //la meto al principio
                }else {

                    //si no, la meto al final
                    copy.add(temp.getData());
                }
                temp = temp.getNext();
            }

            //reemplazo la lista por la lista copia
            this.head = copy.getHead();
    }

    /*
    este es el que devuelve un bool
    public boolean validAdd(Pet pet){

        //ciclo por las mascotas
            DLNode temp = this.head;
            while (temp != null){

                //si tienen el mismo nombre y misma ubicacion, retorno un falso
                if (temp.getData().getName().equals(pet.getName()) && temp.getData().getLocation().equals(pet.getLocation())){
                    return false;
                } else if (temp.getData().getIdentification().equals(pet.getIdentification())) {
                    return false;
                }
                temp = temp.getNext();
            }

            //si no, retorno verdadero
        return true;
    }
    */

}
