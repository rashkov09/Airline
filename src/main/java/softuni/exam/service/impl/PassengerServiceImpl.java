package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.jsonDtos.PassengerSeedDto;
import softuni.exam.models.entity.Passenger;
import softuni.exam.repository.PassengerRepository;
import softuni.exam.service.PassengerService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class PassengerServiceImpl implements PassengerService {
    private final static String PASSENGERS_FILE_PATH = "src/main/resources/files/json/passengers.json";
    private final PassengerRepository passengerRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;

    public PassengerServiceImpl(PassengerRepository passengerRepository, ModelMapper modelMapper, Gson gson, ValidationUtil validationUtil) {
        this.passengerRepository = passengerRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return passengerRepository.count()> 0;
    }

    @Override
    public String readPassengersFileContent() throws IOException {
        return Files.readString(Path.of(PASSENGERS_FILE_PATH));
    }

    @Override
    public String importPassengers() throws IOException {
        StringBuilder builder = new StringBuilder();
        List<Passenger> passengers  = new ArrayList<>();
        Arrays
                .stream(gson.fromJson(readPassengersFileContent(), PassengerSeedDto[].class))
                .forEach(passengerSeedDto -> {
                    boolean valid = validationUtil.isValid(passengerSeedDto);
                    builder.append(valid ?
                            String.format("Successfully imported %s - %s",passengerSeedDto.getLastName(),passengerSeedDto.getEmail()) :
                    "Invalid passenger");
                    builder.append(System.lineSeparator());
                    if (valid){
//                        passengers.add(modelMapper.map(passengerSeedDto,Passenger.class));
                        passengerRepository.save(modelMapper.map(passengerSeedDto, Passenger.class));
                    }
                });
        return builder.toString();
    }

    @Override
    public String getPassengersOrderByTicketsCountDescendingThenByEmail() {
        return null;
    }
}
