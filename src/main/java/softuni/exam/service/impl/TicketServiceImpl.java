package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.xmlDtos.TicketRootSeedDto;
import softuni.exam.models.entity.Ticket;
import softuni.exam.repository.TicketRepository;
import softuni.exam.service.PassengerService;
import softuni.exam.service.PlaneService;
import softuni.exam.service.TicketService;
import softuni.exam.service.TownService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {
    private  final static String TICKETS_FILE_PATH = "src/main/resources/files/xml/tickets.xml";
    private final TicketRepository ticketRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;
    private final PassengerService passengerService;
    private final PlaneService  planeService;
    private final TownService townService;


    public TicketServiceImpl(TicketRepository ticketRepository, ModelMapper modelMapper, XmlParser xmlParser, ValidationUtil validationUtil, PassengerService passengerService, PlaneService planeService, TownService townService) {
        this.ticketRepository = ticketRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
        this.passengerService = passengerService;
        this.planeService = planeService;
        this.townService = townService;
    }

    @Override
    public boolean areImported() {
        return ticketRepository.count() > 0;
    }

    @Override
    public String readTicketsFileContent() throws IOException {
        return Files.readString(Path.of(TICKETS_FILE_PATH));
    }

    @Override
    public String importTickets() throws JAXBException, FileNotFoundException {
        StringBuilder builder = new StringBuilder();
        TicketRootSeedDto ticketRootSeedDto = xmlParser.fromFile(TICKETS_FILE_PATH,TicketRootSeedDto.class);
        List<Ticket> tickets = new ArrayList<>();
        ticketRootSeedDto
                .getTickets()
                .forEach(ticketSeedDto -> {
                    boolean valid = validationUtil.isValid(ticketSeedDto);
                    builder.append(valid ?
                            String.format("Successfully imported Ticket %s - %s",ticketSeedDto.getFromTownName().getName(),ticketSeedDto.getToTownName().getName()):
                            "Invalid ticket");
                    builder.append(System.lineSeparator());
                    if (valid){
                        Ticket ticket = modelMapper.map(ticketSeedDto,Ticket.class);
                        ticket.setPassenger(passengerService.findPassengerByEmail(ticketSeedDto.getPassenger().getEmail()));
                        ticket.setPlane(planeService.findPlaneByRegisterNumber(ticketSeedDto.getPlane().getRegisterNumber()));
                        ticket.setFromTown(townService.findTownByName(ticketSeedDto.getFromTownName().getName()));
                        ticket.setToTown(townService.findTownByName(ticketSeedDto.getToTownName().getName()));

//                        tickets.add(ticket);
                        ticketRepository.save(ticket);
                    }
                });
        return builder.toString();
    }
}
