package softuni.exam.service;



import org.springframework.stereotype.Service;
import softuni.exam.models.entity.Passenger;

import java.io.IOException;


public interface PassengerService {

    boolean areImported();

    String readPassengersFileContent() throws IOException;
	
	String importPassengers() throws IOException;

	String getPassengersOrderByTicketsCountDescendingThenByEmail();
}
