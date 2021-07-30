package softuni.exam.models.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "towns")
public class Town extends BaseEntity{
    private String name;
    private Integer population;
    private String guide;
    private Set<Passenger> passengers;
    private Set<Ticket> fromTickets;
    private Set<Ticket> toTickets;

    public Town() {
    }

    @Column(unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Column
    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }
    @Column(columnDefinition = "TEXT")
    public String getGuide() {
        return guide;
    }

    public void setGuide(String guide) {
        this.guide = guide;
    }

    @OneToMany(mappedBy = "town")
    public Set<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(Set<Passenger> passengers) {
        this.passengers = passengers;
    }

    @OneToMany(mappedBy = "fromTown")
    public Set<Ticket> getFromTickets() {
        return fromTickets;
    }

    public void setFromTickets(Set<Ticket> fromTickets) {
        this.fromTickets = fromTickets;
    }

    @OneToMany(mappedBy = "toTown")
    public Set<Ticket> getToTickets() {
        return toTickets;
    }

    public void setToTickets(Set<Ticket> toTickets) {
        this.toTickets = toTickets;
    }
}
