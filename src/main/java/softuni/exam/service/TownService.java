package softuni.exam.service;


import org.springframework.stereotype.Service;
import softuni.exam.models.entity.Town;


public interface TownService {

    boolean areImported();

    String readTownsFileContent() ;
	
	String importTowns() ;

    Town findTownByName(String name);
}
