package co.edu.umanizales.tads.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ListSE {
    private Node head;

    public int size() {

        //defino un int que es donde voy a contar y defino un ayudante que es la cabeza
        int count = 0;
        Node temp = head;
        //mientras el ayudante tenga a alguien agarrado, vaya ciclando por la lista y sumandole una unidad a la cuneta cada vez
        while (temp != null) {
            count++;
            temp = temp.getNext();
        }
        //cuando termine este ciclo retorno la cantidad
        return count;
    }


    public void add(Kid kid) {
        if (head != null) {
            //si la cabzea no está vacía
            //hago que el ayudante agarre la cabeza
            Node temp = head;
            //ciclo por cada miembro de la fila, haciendo que el ayudante vaya aggarando cada uno de los niños hsata llegar al ultimo
            while (temp.getNext() != null) {
                temp = temp.getNext();
            }
            // Aquí estoy metiendo el nodo nuevo después del último nodo de la lista
            Node newNode = new Node(kid);
            temp.setNext(newNode);
        } else {
            //si la cabeza esta vacia, metoel niño en la cabeza
            head = new Node(kid);
        }
    }

    public void addToStart(Kid kid) {
        //reviso si hay alguien en la cabeza
        if (head != null) {
            //sino, le agrego la cabeza al niño que estoy metiendo, para que este quede de primero y la cabeza de segundo
            Node newNode = new Node(kid);
            newNode.setNext(head);
            head = newNode;

        } else {
            // si no hay nadie meto el niño en la cabeza
            head = new Node(kid);
        }

    }

    public void addKidAtPlace(int position, Kid kid){
        Node temp = head;
        Node previous = null;
        int currentPosition = 0;

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

    public void moveKid(int initialPlace, int finalPlace) {

        //defino el temp y el prev para el niño qeu esta en el lugar inicial
        Node prevInitial = null;
        Node initial = head;
        //ciclo por la fila hasta que ambos lleguen a ese niño
        for (int i = 1; i < initialPlace; i++) {
            prevInitial = initial;
            initial = initial.getNext();
        }

        //ahora defino el temp y el prev para el niño qeu esta en el lugar final
        Node prevFinal = null;
        Node finalNode = head;
        //ciclo por la fila hasta llegar al niño que esta en el lugar final
        for (int i = 1; i < finalPlace; i++) {
            prevFinal = finalNode;
            finalNode = finalNode.getNext();
        }
        //en esta parte voy a sacar al niño del puesto inicial
        //aqui reviso si el lugar inicial es la cabeza
        if (prevInitial == null) {
            //si lo es, declaro la cabeza como la persona siguiente a ella para sacar a la cabeza de ahi
            head = initial.getNext();
        } else {
            //si no lo es, hago que prev, o el niño de atrás a quien está en el lugar inicial,  agarre a la persona 1 puesto después del lugar inciail, para sacar a la persona inicial de la fila
            prevInitial.setNext(initial.getNext());
        }

        //en esta parte meto al niño al puesto final
        if (prevInitial==null){
            //hago que el niño que está en inicial agarre los niños que final tiene por delante
            initial.setNext(finalNode.getNext());
            //hago que final agarre a inciial
            finalNode.setNext(initial);
        } else {
            //hago que el que está antes de inicial agarre a los niños que tiene inicial por delante
            prevInitial.setNext(initial.getNext());
            //hago que inicial agarre a los niños que tiene final por delante
            initial.setNext(finalNode.getNext());
            //hago que final agarre a los niños que tiene inicial por delante
            finalNode.setNext(initial);
        }



    }

    public void deleteKid(int place){

        if (place == 1) {
            head = head.getNext();
        }
        else {

            Node temp = head;
            Node previous = null;

            for (int i = 1; i < place; i++){

                previous = temp;
                temp = temp.getNext();

            }

            temp = temp.getNext();
            previous.setNext(temp);

        }

    }

}

