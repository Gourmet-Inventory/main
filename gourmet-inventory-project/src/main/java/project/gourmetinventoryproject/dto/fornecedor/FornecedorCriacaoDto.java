package project.gourmetinventoryproject.dto.fornecedor;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FornecedorCriacaoDto {
    private String nomeFornecedor;
    private String cnpj;

    private String cep;
    private String logradouro;
    private String complemento;
    private String bairro;
    private String localidade;
    private String uf;

    private String numeracaoLogradouro;
    private String telefone;
    private String categoria;
}
