package co.edu.umanizales.tads.model;

import lombok.Data;

@Data
public class ListSE {
    private Node head;

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
    }

    public void addToStart(Kid kid) {
        if (head != null) {
            Node newNode = new Node(kid);
            newNode.setNext(head);
            head = newNode;
        } else {
            head = new Node(kid);
        }
    }

    public int listLength() {
        int size = 0;
        Node temp = head;
        while (temp != null) {
            size++;
            temp = temp.getNext();
        }
        return size;
    }


    public void addKidInPlace(int place, Kid kid) {

        if (listLength() >= place) {

            if (place == 0) {
                addToStart(kid);
            } else {
                Node temp = head;
                for (int i = 0; i < place - 1; i++) {
                    temp = temp.getNext();
                }
                Node newNode = new Node(kid);
                newNode.setNext(temp.getNext());
                temp.setNext(newNode);

            }
        } else {
          add(kid);
        }
    }

    public void deleteById(String identification) {
        Node temp = head;
        Node previousNode = null;


        while (temp != null && temp.getData().getIdentification()!=(identification)) {
            previousNode = temp;
            temp= temp.getNext();

        }
          if (temp != null){
                if (previousNode== null){
                    head=temp.getNext();
                }
                else {
                    previousNode.setNext(temp.getNext());
                }
          }


        }
    }



