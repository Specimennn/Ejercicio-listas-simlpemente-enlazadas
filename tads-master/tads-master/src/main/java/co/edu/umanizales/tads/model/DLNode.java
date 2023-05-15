package co.edu.umanizales.tads.model;

import lombok.Data;

@Data
public class DLNode {

    private Pet data;

    private DLNode next;

    private DLNode prev;

    public DLNode(Pet data) {
        this.data = data;
    }
}
