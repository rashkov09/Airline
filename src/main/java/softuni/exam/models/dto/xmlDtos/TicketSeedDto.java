package softuni.exam.models.dto.xmlDtos;

import softuni.exam.models.entity.Passenger;
import softuni.exam.models.entity.Plane;
import softuni.exam.models.entity.Town;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@XmlRootElement(name = "ticket")
@XmlAccessorType(XmlAccessType.FIELD)
public class TicketSeedDto {

    @XmlElement(name = "serial-number")
    @Size(min = 2)
    private String serialNumber;

    @XmlElement(name = "price")
    @Positive
    private BigDecimal price;

    @XmlElement(name = "take-off")
    private String takeOff;

    @XmlElement(name = "from-town")
    private String fromTownName;

    @XmlElement(name = "to-town")
    private String toTownName;

    @XmlElement(name = "passenger")
    private String passengerEmail;

    @XmlElement(name =  "plane")
    private String planeRegisterNumber;

    public TicketSeedDto() {
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getTakeOff() {
        return takeOff;
    }

    public void setTakeOff(String takeOff) {
        this.takeOff = takeOff;
    }

    public String getFromTownName() {
        return fromTownName;
    }

    public void setFromTownName(String fromTownName) {
        this.fromTownName = fromTownName;
    }

    public String getToTownName() {
        return toTownName;
    }

    public void setToTownName(String toTownName) {
        this.toTownName = toTownName;
    }

    public String getPassengerEmail() {
        return passengerEmail;
    }

    public void setPassengerEmail(String passengerEmail) {
        this.passengerEmail = passengerEmail;
    }

    public String getPlaneRegisterNumber() {
        return planeRegisterNumber;
    }

    public void setPlaneRegisterNumber(String planeRegisterNumber) {
        this.planeRegisterNumber = planeRegisterNumber;
    }
}
