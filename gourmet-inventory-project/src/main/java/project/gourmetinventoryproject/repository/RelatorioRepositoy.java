package project.gourmetinventoryproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.gourmetinventoryproject.domain.Relatorio;

@Repository
public interface RelatorioRepositoy extends JpaRepository<Relatorio, Long> {

}
