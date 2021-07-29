package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.jsonDtos.TownSeedDto;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.TownService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Service
public class TownServiceImpl implements TownService {
    private final static String TOWNS_FILE_PATH = "src/main/resources/files/json/towns.json";

    private final TownRepository townRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;

    public TownServiceImpl(TownRepository townRepository, ModelMapper modelMapper, Gson gson, ValidationUtil validationUtil) {
        this.townRepository = townRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return townRepository.count() > 0 ;
    }

    @Override
    public String readTownsFileContent() {
        try {
            return Files.readString(Path.of(TOWNS_FILE_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String importTowns() {
        StringBuilder builder = new StringBuilder();
        Arrays
                .stream(gson.fromJson(readTownsFileContent(), TownSeedDto[].class))
                .forEach(townSeedDto -> {
                    boolean valid = validationUtil.isValid(townSeedDto);
                    builder.append(valid ?
                            String.format("Successfully imported %s - %s",
                                    townSeedDto.getName(),townSeedDto.getPopulation()) :
                                "Invalid town");
                    if (valid) {
                        townRepository.save(modelMapper.map(townSeedDto, Town.class));
                    }
                    builder.append(System.lineSeparator());
                });
        return builder.toString();
    }
}
