package project.gourmetinventoryproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project.gourmetinventoryproject.domain.Alerta;
import project.gourmetinventoryproject.domain.Empresa;
import project.gourmetinventoryproject.domain.Ingrediente;
import project.gourmetinventoryproject.domain.ItemListaCompras;

import java.util.List;

@Repository
public interface ItemsComprasRepository extends JpaRepository<ItemListaCompras, Long> {
    @Query("SELECT i FROM ItemListaCompras i WHERE i.estoqueIngrediente.empresa = :empresa")
    List<ItemListaCompras> findAllByEmpresa(@Param("empresa") Empresa empresa);
    
}
