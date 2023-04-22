package co.edu.umanizales.tads.model;

import ch.qos.logback.core.joran.spi.ElementSelector;
import lombok.Data;

@Data
public class ListSE {
    private Node head;
    private int size;

    public void add(Kid kid) {
        if (head != null) {
            Node temp = head;
            while (temp.getNext() != null) {
                temp = temp.getNext();
            }
            /// Parado en el último
            Node newNode = new Node(kid);
            temp.setNext(newNode);
        } else {
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

    public void orderBoysToStart() {
        if (this.head != null) {
            ListSE listCp = new ListSE();
            Node temp = this.head;
            while (temp != null) {
                if (temp.getData().getGender() == 'M') {
                    listCp.addToStart(temp.getData());
                } else {
                    listCp.add(temp.getData());
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

                    //si el codigo de ubicacion es menor a 3 o mayor a 8, retorno un 2
                } else if (kid.getLocation().getCode().length() < 3 || kid.getLocation().getCode().length() > 8) {
                    validAdd = 2;
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

    public void removeKidsByAge(byte age) {
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

    public void addKidAtPlace(int position, Kid kid){
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

}

