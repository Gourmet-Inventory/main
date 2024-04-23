package project.gourmetinventoryproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.gourmetinventoryproject.domain.Receita;

@Repository
public interface ReceitaRepository extends JpaRepository<Receita, Long> {
}

