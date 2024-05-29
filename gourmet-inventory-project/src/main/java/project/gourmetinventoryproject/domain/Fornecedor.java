package project.gourmetinventoryproject.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Getter
@Setter
public class Fornecedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFornecedor;
    private String nomeFornecedor;
    private String cnpj;
    private String logradouro;
    private String numeracaoLogradouro;
    private String telefone;
    private String categoria;

}
