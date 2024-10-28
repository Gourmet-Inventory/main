package project.gourmetinventoryproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.gourmetinventoryproject.domain.Comanda;

@Repository
public interface ComandaRepository extends JpaRepository<Comanda, Long> {
}
