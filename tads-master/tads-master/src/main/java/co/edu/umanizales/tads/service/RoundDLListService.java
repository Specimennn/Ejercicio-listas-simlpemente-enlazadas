package co.edu.umanizales.tads.service;

import co.edu.umanizales.tads.exception.DLListException;
import co.edu.umanizales.tads.model.RoundDLList;
import co.edu.umanizales.tads.model.Kid;
import co.edu.umanizales.tads.model.ListSE;
import co.edu.umanizales.tads.model.Node;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data

public class RoundDLListService {

    private RoundDLList pets;

    public RoundDLListService() {
        pets = new RoundDLList();

    }
}
