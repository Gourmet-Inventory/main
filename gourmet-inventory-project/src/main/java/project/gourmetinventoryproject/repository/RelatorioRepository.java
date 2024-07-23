package project.gourmetinventoryproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.gourmetinventoryproject.domain.Empresa;
import project.gourmetinventoryproject.domain.Relatorio;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RelatorioRepository extends JpaRepository<Relatorio, Long> {
     Relatorio findByDataAndEmpresa(LocalDate data, Empresa empresa);
}
