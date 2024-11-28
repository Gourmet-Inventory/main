package project.gourmetinventoryproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.gourmetinventoryproject.domain.Comanda;

import java.util.Optional;

@Repository
public interface ComandaRepository extends JpaRepository<Comanda, Long> {
    Optional<Comanda> findTopByOrderByIdDesc();
}
