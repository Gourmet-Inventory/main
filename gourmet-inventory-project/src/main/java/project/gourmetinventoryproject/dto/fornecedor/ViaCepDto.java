package project.gourmetinventoryproject.dto.fornecedor;

public class ViaCepDto {
    public record Endereco(String cep, String logradouro, String complemento, String bairro, String uf, String ibge, String gia, String ddd, String siafi){
    }
}
