package project.gourmetinventoryproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.gourmetinventoryproject.domain.Comanda;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ComandaRepository extends JpaRepository<Comanda, Long> {
    Optional<Comanda> findTopByOrderByIdDesc();

    List<Comanda> findAllByStatusAndData(String status, LocalDate data);

}
