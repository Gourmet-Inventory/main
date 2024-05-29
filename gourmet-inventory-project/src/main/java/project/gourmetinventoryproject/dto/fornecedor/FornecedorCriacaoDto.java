package project.gourmetinventoryproject.dto.fornecedor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FornecedorCriacaoDto {
    private String nomeFornecedor;
    private String cnpj;
    private String logradouro;
    private String numeracaoLogradouro;
    private String telefone;
    private String categoria;
}
