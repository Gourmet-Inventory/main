package project.gourmetinventoryproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.gourmetinventoryproject.domain.Empresa;
import project.gourmetinventoryproject.domain.Prato;

import java.util.List;

@Repository
public interface PratoRepository extends JpaRepository<Prato, Long> {
    List<Prato> findByNomeIgnoreCase(String nome);
    List<Prato> findAllByEmpresa(Empresa empresa);

}

