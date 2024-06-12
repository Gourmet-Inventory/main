package project.gourmetinventoryproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import project.gourmetinventoryproject.domain.Empresa;
import project.gourmetinventoryproject.domain.Prato;
import project.gourmetinventoryproject.domain.Receita;
import project.gourmetinventoryproject.repository.EmpresaRepository;
import project.gourmetinventoryproject.repository.PratoRepository;
import project.gourmetinventoryproject.repository.ReceitaRepository;
import project.gourmetinventoryproject.exception.ElementAlreadyExistException;
import project.gourmetinventoryproject.exception.IdNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.HashMap;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.status;

@Service
public class PratoService {

    @Autowired

    private PratoRepository pratoRepository;

    @Autowired
    private ReceitaRepository receitaRepository;

    @Autowired
    private EmpresaService empresaService;

    public List<Prato> getAllPratos(Long idEmpresa) {
        Empresa empresa = empresaService.getEmpresasById(idEmpresa);
        return pratoRepository.findAllByEmpresa(empresa);
    }

    public Prato getPratoById(Long id) {
        if (pratoRepository.existsById(id)){
            Optional<Prato> pratoOptional = pratoRepository.findById(id);
            return pratoOptional.orElse(null);
        }
        throw new IdNotFoundException();

    }

    public Prato createPrato(Prato prato, Long idEmpresa) {
        prato.setEmpresa(empresaService.getEmpresasById(idEmpresa));
        if (pratoRepository.findByNomeIgnoreCase(prato.getNome()).isEmpty()){
            return pratoRepository.save(prato);
        }
        throw new ElementAlreadyExistException();


    }

    public Prato updatePrato(Long id, Prato prato) {
        if (pratoRepository.existsById(id)){
            Optional<Prato> existingPratoOptional = pratoRepository.findById(id);
            if (existingPratoOptional.isPresent()) {
                Prato existingPrato = existingPratoOptional.get();
                existingPrato.setNome(prato.getNome());
                existingPrato.setDescricao(prato.getDescricao());
                existingPrato.setPreco(prato.getPreco());
                existingPrato.setCategoria(prato.getCategoria());
                return pratoRepository.save(existingPrato);
            } else {
                throw new IdNotFoundException();
            }
        }
        throw new IdNotFoundException();

    }

    public void deletePrato(Long id) {
        if (pratoRepository.findById(id).orElse(null) == null){
            throw new IdNotFoundException();
        }
        pratoRepository.deleteById(id);
    }

    public Map<Long, Integer> calculateIngredientUsage(List<Long> servedDishesIds) {
        // Cria um mapa para armazenar o uso de ingredientes.
        // Chave: ID do ingrediente (Long)
        // Valor: Quantidade do ingrediente usado (Integer)
        Map<Long, Integer> ingredientUsage = new HashMap<>();

        // Itera sobre todos os IDs de pratos servidos fornecidos como entrada para o método.
        for (Long dishId : servedDishesIds) {
            // Obtém a lista de receitas associadas ao prato atual usando o ID do prato.
            // Supõe-se que receitaRepository.findByIdPrato(dishId) retorne uma lista de objetos do tipo Receita.
            List<Receita> recipes = receitaRepository.findByIdPrato(dishId);

            // Itera sobre todas as receitas associadas ao prato atual.
            for (Receita recipe : recipes) {
                // Atualiza o mapa ingredientUsage:
                // Se o ingrediente já estiver presente no mapa, o valor existente é substituído pela soma do valor existente e da quantidade do ingrediente na receita atual.
                // Se o ingrediente não estiver presente, ele é adicionado ao mapa com a quantidade da receita atual.
                ingredientUsage.merge(recipe.getIdIngrediente(), recipe.getQuantidade(), Integer::sum);
            }
        }

        // Retorna ingredientUsage, que contém o uso de ingredientes calculado para todos os pratos servidos.
        return ingredientUsage;
    }


    public int[][] generateIngredientUsageReport(List<Long> servedDishesIds, int numberOfIngredients) {
        int[][] ingredientUsageReport = new int[servedDishesIds.size()][numberOfIngredients];

        for (int i = 0; i < servedDishesIds.size(); i++) {
            Long dishId = servedDishesIds.get(i);
            List<Receita> recipes = receitaRepository.findByIdPrato(dishId);

            for (Receita recipe : recipes) {
                int ingredientIndex = recipe.getIdIngrediente().intValue() - 1;
                int quantity = recipe.getQuantidade();
                ingredientUsageReport[i][ingredientIndex] += quantity;
            }
        }

        return ingredientUsageReport;

        /*Resultado esperado
                    | Ingrediente 1 | Ingrediente 2 | Ingrediente 3 | Ingrediente 4 |
            ----------------------------------------------------------------------
            Prato 1 |       1       |       3       |       0       |       1       |
            Prato 2 |       0       |       4       |       2       |       0       |
            Prato 3 |       3       |       0       |       8       |       7       |
        */
    }

    public Prato updatePratoFoto(@PathVariable Long codigo, @RequestBody byte[] novaFoto) {
        Prato prato;
        if (pratoRepository.existsById(codigo) == false){
            throw new IdNotFoundException();
        } else {
            prato = pratoRepository.findById(codigo).get();
            prato.setFoto(novaFoto);
            pratoRepository.save(prato);
        }
        return prato;
    }
}