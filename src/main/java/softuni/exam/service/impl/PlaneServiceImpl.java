package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.xmlDtos.PlaneRootSeedDto;
import softuni.exam.models.entity.Plane;
import softuni.exam.repository.PlaneRepository;
import softuni.exam.service.PlaneService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Service
public class PlaneServiceImpl implements PlaneService {

    private final static String PLANES_FILE_PATH = "src/main/resources/files/xml/planes.xml";
    private final PlaneRepository planeRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;

    public PlaneServiceImpl(PlaneRepository planeRepository, ModelMapper modelMapper, XmlParser xmlParser, ValidationUtil validationUtil) {
        this.planeRepository = planeRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return planeRepository.count()> 0;
    }

    @Override
    public String readPlanesFileContent() throws IOException {

           return Files.readString(Path.of(PLANES_FILE_PATH));

    }

    @Override
    public String importPlanes() throws JAXBException, FileNotFoundException {
        PlaneRootSeedDto planeRootSeedDto = xmlParser.fromFile(PLANES_FILE_PATH,PlaneRootSeedDto.class);
        StringBuilder builder = new StringBuilder();
        List<Plane> planeList = new ArrayList<>();
        planeRootSeedDto
                .getPlanes()
                .forEach(planeSeedDto -> {
                    boolean valid = validationUtil.isValid(planeSeedDto);
                    builder.append(valid ?
                            String.format("Successfully imported Plane %s",planeSeedDto.getRegisterNumber()) :
                    "Invalid plane");
                    builder.append(System.lineSeparator());
                    if (valid){
//                        planeList.add(modelMapper.map(planeSeedDto,Plane.class));
                        planeRepository.save(modelMapper.map(planeSeedDto, Plane.class));
                    }
                });
        return builder.toString();
    }
}
