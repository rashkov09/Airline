package softuni.exam.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import softuni.exam.models.entity.Ticket;

@Service
public interface TicketRepository extends JpaRepository<Ticket,Long> {

}
