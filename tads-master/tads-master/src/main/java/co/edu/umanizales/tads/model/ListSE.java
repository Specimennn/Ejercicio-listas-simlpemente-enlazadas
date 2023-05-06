package co.edu.umanizales.tads.model;

import co.edu.umanizales.tads.exception.DLListException;
import co.edu.umanizales.tads.exception.ListSEException;
import lombok.Data;

import java.util.List;

@Data
public class ListSE {
    private Node head;
    private int size;

    public void add(Kid kid) throws ListSEException {

        //reviso si hay niños para saber si ciclar o no por la fila
        if (head != null) {

            //defino un ayudante para ciclar en la fila
            Node temp = head;


            //tengo que revisar fuera del while una vez por si la cabeza es igual al que estoy intentnaod agregar
            //tiro una excepcion si me encuentro con un niño con la misma identiffcacion o con la misma ciudad y nombre
            if (temp.getData().getIdentification().equals(kid.getIdentification())) {
                throw new ListSEException("ya existe un niño con dicha identificacion");
            } else if (temp.getData().getName().equals(kid.getName()) && temp.getData().getLocation().equals(kid.getLocation())){
                throw new ListSEException("ya existe un niño con el mismo nombre y ubicación");
            }

            //ciclo por la fila
            while (temp.getNext() != null) {

                //tiro una excepcion si me encuentro con un niño con la misma identiffcacion o con la misma ciudad y nombre
                if (temp.getData().getIdentification().equals(kid.getIdentification())) {
                    throw new ListSEException("ya existe un niño con dicha identificacion");
                } else if (temp.getData().getName().equals(kid.getName()) && temp.getData().getLocation().equals(kid.getLocation())){
                    throw new ListSEException("ya existe un niño con el mismo nombre y ubicación");
                }

                temp = temp.getNext();
            }
            // Parado en el último, agrego en el niño
            Node newNode = new Node(kid);
            temp.setNext(newNode);

        } else {

            //si no habian niños, lo meto de primero
            head = new Node(kid);
        }
        size++;
    }

    public void addToStart(Kid kid) {
        if (head != null) {
            Node newNode = new Node(kid);
            newNode.setNext(head);
            head = newNode;
        } else {
            head = new Node(kid);
        }
        size++;
    }

    public void invert() {
        if (this.head != null) {
            ListSE listCp = new ListSE();
            Node temp = this.head;
            while (temp != null) {
                listCp.addToStart(temp.getData());
                temp = temp.getNext();
            }
            this.head = listCp.getHead();
        }
    }

    public void orderBoysToStart() throws ListSEException{
        if (this.head != null) {

            ListSE listCp = new ListSE();
            Node temp = this.head;
            while (temp != null) {
                if (temp.getData().getGender() == 'M' || temp.getData().getGender()== 'm') {
                    listCp.addToStart(temp.getData());
                } else {
                    try {
                        listCp.add(temp.getData());
                    }catch (ListSEException e){
                    }
                }

                temp = temp.getNext();
            }
            this.head = listCp.getHead();
        }
    }

    public void changeExtremes() {
        if (this.head != null && this.head.getNext() != null) {
            Node temp = this.head;
            while (temp.getNext() != null) {
                temp = temp.getNext();
            }
            //temp está en el último
            Kid copy = this.head.getData();
            this.head.setData(temp.getData());
            temp.setData(copy);
        }

    }

    public int kidsPerCity(String code) {

        //defino una variable donde voy a contar los niños llamada count
        int count = 0;

        //reviso si la lista de niños no está vacía
        if (this.head != null) {

            //si no, defino a temp y empiezo a ciclar por la lista
            Node temp = this.head;
            while (temp != null) {

                //reviso que el codigo sea de 8 digitos, es decir de ciudad
                if (code.length() == 8) {

                    //si sí, reviso si este codigo es igual al del niño siendo iterado
                    if (temp.getData().getLocation().getCode().equals(code)) {

                        //si sí, agrego uno a la cuenta
                        count++;
                    }
                }

                //defino a temp como el siguiente y continuo el ciclo
                temp = temp.getNext();
            }
        }

        //retorno la cantidad de niños en esa ciudad
        return count;
    }

    public int kidsPerDepartment(String code) {

        //defino una variable donde voy a contar los niños llamada count
        int count = 0;

        //reviso si la lista de niños no está vacía
        if (this.head != null) {

            //si no, defino a temp y empiezo a ciclar por la lista
            Node temp = this.head;
            while (temp != null) {

                //reviso que el codigo sea de 8 digitos, es decir de departamento
                if (code.length() == 5) {

                    //si sí, reviso si este codigo es igual al del niño siendo iterado
                    if (temp.getData().getLocation().getCode().substring(0, 5).equals(code)) {

                        //si sí, agrego uno a la cuenta
                        count++;
                    }
                }

                //defino a temp como el siguiente y continuo el ciclo
                temp = temp.getNext();
            }
        }

        //retorno la cantidad de niños en esa ciudad
        return count;
    }

    public int validAdd(Kid kid) {

        //defino una variable de tipo int para que retorne un valor especifico que represente el tiop de error
        int validAdd = 0;

        if (this.head != null) {
            Node temp = this.head;

            //ciclo por la fila
            while (temp != null) {

                //si el niño agregado tiene el msimo id de un niño ya existente, retorno un 1
                if (temp.getData().getIdentification().equals(kid.getIdentification())) {
                    validAdd = 1;


                } else if (kid.getLocation().getCode().length() < 3 || kid.getLocation().getCode().length() > 8) {
                    //si el codigo de ubicacion es menor a 3 o mayor a 8, retorno un 2
                    validAdd = 2;
                }
                else if (temp.getData().getName().equals(kid.getName()) && temp.getData().getLocation().equals(kid.getLocation())){
                 //si hay algun niño con el mismo nombre y ubicación, retorno un 3
                    validAdd = 3;
                }

                temp = temp.getNext();

            }
        }
        return validAdd;
    }

    public int kidsPerCityPerGenderAboveAge(String code, char gender, int age) {

        //defino una variable donde voy a contar los niños llamada count
        int count = 0;

        //reviso si la lista de niños no está vacía
        if (this.head != null) {

            //si no, defino a temp y empiezo a ciclar por la lista
            Node temp = this.head;
            while (temp != null) {

                //reviso que el codigo sea de 8 digitos, es decir de ciudad
                if (code.length() == 8) {

                    //si sí, reviso si este codigo es igual al del niño siendo iterado
                    if (temp.getData().getLocation().getCode().equals(code)) {

                        if (temp.getData().getGender() == gender) {

                            if(temp.getData().getAge() > age) {

                                //si sí, agrego uno a la cuenta
                                count++;
                            }
                        }
                    }
                }

                //defino a temp como el siguiente y continuo el ciclo
                temp = temp.getNext();
            }
        }
        return count;
    }

    public void removeKidsByAge(byte age) throws ListSEException{

        if (head == null){
            throw new ListSEException("la fila está vacía");
        }

        Node temp = head;
        Node previous = null;

        // Recorro la lista para encontrar el niño de edad especificada
        while (temp != null) {
            if (temp.getData().getAge() == age) {
                // Quito el niño de dicha edad
                if (previous == null) {
                    // Aquí estoy quitando el niño de la cabeza
                    head = temp.getNext();
                } else {
                    // Aquí estoy quitando el niño que no sea la cabeza
                    previous.setNext(temp.getNext());
                }
            } else {
                // Sigo con el siguiente niño
                previous = temp;
            }
            temp = temp.getNext();
        }
    }

    public void addKidAtPlace(int position, Kid kid) throws ListSEException{

        //reviso si no metio una posicion invalida
        if (size > position){
            throw new ListSEException("la posición que ingresó es mayor a el tamaño de la fila");
        } else if (position < 1){
            throw new ListSEException("ingresó una posición inferior a 0");
        }

        Node temp = head;
        Node previous = null;
        int currentPosition = 1;

        // Recorrodo toda la lista para encontrar la posicion donde voy a meter al niño
        while (temp != null && currentPosition < position) {
            previous = temp;
            temp = temp.getNext();
            currentPosition++;
        }

        // Creo un nuevo nodo para el niño y lo inserto
        Node newNode = new Node(kid);
        //reviso si tengo algun niño atras
        if (previous == null) {
            // Aquí lo estaría metiendo al principio de la lista
            newNode.setNext(head);
            head = newNode;
        } else {
            // Aquí lo estaría metiendo a otra posición
            newNode.setNext(temp);
            previous.setNext(newNode);
        }
    }

    public void deleteKid(int place){
        //si el lugar a borrar es la cabeza, solo hago que la cabeza sea la segunda persona
        if (place == 1) {
            head = head.getNext();
        }
        else {
            //si no, defino mis 2 ayudantes...
            Node temp = head;
            Node previous = null;
            //ciclo por cada persona en la fila hasta encontrar la persona del lugar
            for (int i = 1; i < place; i++){

                previous = temp;
                temp = temp.getNext();

            }
            // cuando haya llegado a la persona del lugar, hago que mi ayudante de adelante agarre a la persona que tiene adelante, y despues, que el ayudante de atras lo agarre a el, dejando afuera a la persona que estaba entre ellos 2
            temp = temp.getNext();
            previous.setNext(temp);

        }

    }

    public Kid getKid(int place){
            Kid kid = null;

            //reviso la
            if (place == 1) {
                kid = head.getData();
            }
            else {
                //si no, defino mis 2 ayudantes...
                Node temp = head;
                //ciclo por cada persona en la fila hasta encontrar la persona del lugar
                for (int i = 1; i < place; i++){


                    temp = temp.getNext();

                }
                // cuando haya llegado a la persona del lugar

                kid = temp.getData();

            }

            return kid;

        }

    public void addToEnd(Kid kid) {
        Node newNode = new Node(kid);
        if (head == null) {
            head = newNode;
        } else {
            Node currentNode = head;
            while (currentNode.getNext() != null) {
                currentNode = currentNode.getNext();
            }
            currentNode.setNext(newNode);
        }
    }

    public void sendToBottom(char firstChar) {
        ListSE sendBottomList = new ListSE();
        Node temp = this.head;


        while (temp != null) {
            if (temp.getData().getName().charAt(0) != Character.toUpperCase(firstChar)) {
                sendBottomList.addToEnd(temp.getData());
            }
            temp = temp.getNext();
        }


        temp = this.head;
        while (temp != null) {
            if (temp.getData().getName().charAt(0) == Character.toUpperCase(firstChar)) {
                sendBottomList.addToEnd(temp.getData());
            }
            temp = temp.getNext();
        }

        this.head = sendBottomList.getHead();
    }

    public float averageAge(){
        if (head != null){

            //defubi mi ayudante
            Node temp = head;

            //defino variables para contar el total de edad y total de niños
            int totalkids = 0;
            int totalage = 0;


            //Ciclo por todos los niños calculado en total de la edad y total de niños
            while(temp != null) {
                totalkids++;
                totalage += temp.getData().getAge();
                temp = temp.getNext();
            }

            //divido ambas variables
            float average = (float)totalage / (float)totalkids;

            return average;
        } else {
            return 0;
        }
    }

    public void interleaveByGender() throws ListSEException {

        if (head == null){
            throw new ListSEException("la lista está vacía");
        }

        //hago una lista de niños y niñas para irlos guardando ahi
        ListSE tempBoys = new ListSE();
        ListSE tempGirls = new ListSE();

        //ciclo por la lista que ya tengo y meto
        Node temp = head;
        while (temp != null){

            //si el niño es hombre lo meto a la lista de niños
            if(temp.getData().getGender()=='m' || temp.getData().getGender()== 'M'){
                tempBoys.add(temp.getData());
            }

            //si no, lo meto a la lista de niñas
            else{
                tempGirls.add(temp.getData());
            }
            temp = temp.getNext();
        }

        //defino una nueva lista que es donde voy a guardar la nueva lista intercalada
        ListSE rearrangedList = new ListSE();

        //defino un ayudante para cada una de estas listas
        Node mTemp = tempBoys.getHead();
        Node fTemp = tempGirls.getHead();

        if (mTemp == null){
            throw new ListSEException("no hay hombres en la fila");
        }if (fTemp == null){
            throw new ListSEException("no hay mujeres en la fila");
        }

        //mientras ambas listas no esten vacías, voy metiendo un niño y una niña
        while (mTemp != null || fTemp != null){
            if (mTemp != null){
                rearrangedList.add(mTemp.getData());
                mTemp = mTemp.getNext();
            }
            if (fTemp != null){
                rearrangedList.add(fTemp.getData());
                fTemp = fTemp.getNext();
            }
        }
        head = rearrangedList.getHead();
    }

    public int kidsByAgeRange(int initialBracket, int finalBracket){
        //defino variable para contar el total
        int total =0;

        if (head != null) {

            //defubi mi ayudante
            Node temp = head;

            //Ciclo por todos los niños calculado en total de la edad y total de niños
            while (temp != null) {

                if ((temp.getData().getAge() <= finalBracket) && (temp.getData().getAge() >= initialBracket)){
                    total++;
                }

                temp = temp.getNext();
            }
        }
        return total;
    }

}

