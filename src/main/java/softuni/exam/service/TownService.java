package softuni.exam.service;


import org.springframework.stereotype.Service;


public interface TownService {

    boolean areImported();

    String readTownsFileContent() ;
	
	String importTowns() ;
}
