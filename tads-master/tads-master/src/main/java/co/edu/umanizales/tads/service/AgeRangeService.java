package co.edu.umanizales.tads.service;

import co.edu.umanizales.tads.model.AgeRange;
import lombok.Data;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@Data
public class AgeRangeService {

    private List<AgeRange> ageRanges;

    public AgeRangeService(){
        ageRanges = new ArrayList<>();
        ageRanges.add(new AgeRange(0,4));
        ageRanges.add(new AgeRange(5,8));
        ageRanges.add(new AgeRange(9,12));
        ageRanges.add(new AgeRange(13,100));
    }
}
