package co.edu.umanizales.tads.service;

import co.edu.umanizales.tads.model.Kid;
import co.edu.umanizales.tads.model.ListSE;
import co.edu.umanizales.tads.model.Node;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class ListSEService {
    private ListSE kids;

    public ListSEService() {
        kids = new ListSE();
        kids.add(new Kid("1","Alexis",(byte)1));
        kids.add(new Kid("2","Alejandro",(byte)2));
        kids.add(new Kid("3","Alexander",(byte)2));

       kids.addToStart(new Kid("1","Alexandros",(byte)6));
        kids.addKidInPlace(2,new Kid("2","mao tse zung",(byte)5));
          kids.deleteById("2");

    }


    public Node getKids()
    {
        return kids.getHead();
    }
}
