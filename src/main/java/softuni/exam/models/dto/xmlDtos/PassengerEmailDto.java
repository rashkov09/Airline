package softuni.exam.models.dto.xmlDtos;

import javax.validation.constraints.Email;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name= "passenger")
@XmlAccessorType(XmlAccessType.FIELD)
public class PassengerEmailDto {

    @XmlElement(name = "email")
    @Email
    private String email;

    public PassengerEmailDto() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
