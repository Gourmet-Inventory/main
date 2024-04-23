package project.gourmetinventoryproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.gourmetinventoryproject.domain.Prato;

@Repository
public interface PratoRepository extends JpaRepository<Prato, Long> {
}

