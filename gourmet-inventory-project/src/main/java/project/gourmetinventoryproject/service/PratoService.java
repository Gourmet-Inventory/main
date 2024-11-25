package project.gourmetinventoryproject.service;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import project.gourmetinventoryproject.domain.Empresa;
import project.gourmetinventoryproject.domain.Ingrediente;
import project.gourmetinventoryproject.domain.Prato;
import project.gourmetinventoryproject.domain.Receita;
import project.gourmetinventoryproject.dto.ingrediente.IngredienteConsultaDto;
import project.gourmetinventoryproject.dto.prato.PratoConsultaDto;
import project.gourmetinventoryproject.dto.prato.PratoCriacaoDto;
import project.gourmetinventoryproject.dto.receita.ReceitaConsultaDto;
import project.gourmetinventoryproject.repository.PratoRepository;
import project.gourmetinventoryproject.exception.IdNotFoundException;
import project.gourmetinventoryproject.repository.ReceitaRepository;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class PratoService {

    @Autowired
    private PratoRepository pratoRepository;

    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private IngredienteService ingredienteService;

    @Autowired
    private EstoqueIngredienteService estoqueIngredienteService;

    @Autowired
    private S3Service s3Service;

    @Autowired
    private ReceitaRepository receitaRepository;

    //Get all pratos sem formatação
    public List<Prato> getAllPratos(Long idEmpresa) {
        Empresa empresa = empresaService.getEmpresasById(idEmpresa);
        return pratoRepository.findAllByEmpresa(empresa);
    }

    //Get all pratos formatado e com imagem
    public List<PratoConsultaDto> getAllPratosImagem(Long idEmpresa) {
        Empresa empresa = empresaService.getEmpresasById(idEmpresa);
        List<Prato> pratos = pratoRepository.findAllByEmpresa(empresa);

        return pratos.stream()
                .map(this::processarPrato)
                .collect(Collectors.toList());
    }

    //Pegar prato por id
    public PratoConsultaDto getPratoById(Long id) {
        Prato prato = pratoRepository.findById(id).orElseThrow(IdNotFoundException::new);
        return processarPrato(prato);
    }

    //Criar novo prato
    public PratoConsultaDto createPrato(PratoCriacaoDto prato, Long idEmpresa, MultipartFile foto) {
        Empresa empresa = empresaService.getEmpresasById(idEmpresa);
        if (idEmpresa.equals(null)) {
            System.out.println("Empresa não encontrada");
            throw new IdNotFoundException();
        }
        Prato pratoNovo = Prato.builder()
                .nome(prato.getNome())
                .categoria(prato.getCategoria())
                .descricao(prato.getDescricao())
                .preco(prato.getPreco())
                .alergicosRestricoes(prato.getAlergicosRestricoes())
                .empresa(empresa)
                .isBebida(prato.getIsBebida())
                .build();
        List<Ingrediente> ingredientes = estoqueIngredienteService.createIngrediente(prato.getReceitaPrato());

        Receita receita = Receita.builder()
                .ingredientes(ingredientes)
                .build();

        if (foto != null) {
            receita.setIdPrato(uploadImagePrato(foto, pratoNovo).getIdPrato());
            return mapperToDto(pratoNovo, receita);
        } else {
            Prato prato1 = pratoRepository.save(pratoNovo);
            receita.setIdPrato(prato1.getIdPrato());
            return mapperToDto(prato1, receitaRepository.save(receita));
        }
    }

    public PratoConsultaDto updatePrato(Long id, PratoCriacaoDto prato) {
        if (pratoRepository.existsById(id)) {
            Optional<Prato> existingPratoOptional = pratoRepository.findById(id);
            if (existingPratoOptional.isPresent()) {
                Prato existingPrato = existingPratoOptional.get();
                existingPrato.setNome(prato.getNome());
                existingPrato.setDescricao(prato.getDescricao());
                existingPrato.setPreco(prato.getPreco());
                existingPrato.setCategoria(prato.getCategoria());
                existingPrato.setIsBebida(prato.getIsBebida());
                existingPrato.setAlergicosRestricoes(prato.getAlergicosRestricoes());

                Receita existingReceita = receitaRepository.findByIdPrato(existingPrato.getIdPrato()).orElse(null);
                existingReceita.setIngredientes(estoqueIngredienteService.createIngrediente(prato.getReceitaPrato()));

                return mapperToDto(pratoRepository.save(existingPrato), receitaRepository.save(existingReceita));
            } else {
                throw new IdNotFoundException();
            }
        }
        throw new IdNotFoundException();

    }

    public void deletePrato(Long idPrato) {
        if (pratoRepository.existsById(idPrato)) {
            Prato prato = pratoRepository.findById(idPrato).orElseThrow(IdNotFoundException::new);
            if (!receitaRepository.findByIdPrato(idPrato).isEmpty()) {
                receitaRepository.delete(receitaRepository.findByIdPrato(idPrato).orElseThrow(IdNotFoundException::new));
            }
            pratoRepository.delete(prato);
        } else {
            throw new IdNotFoundException();
        }
    }

    private String gerarUrlAssinada(Prato prato) {
        String fotoUrl = prato.getFoto();
        if (fotoUrl != null) {
            String key = fotoUrl.substring(fotoUrl.lastIndexOf("/") + 1);
            String urlAssinada = s3Service.generatePresignedUrl(key);
            prato.setURLAssinada(urlAssinada);
            pratoRepository.save(prato);
            return urlAssinada;
        }
        return null;
    }

    private PratoConsultaDto processarPrato(Prato prato) {
        Receita receita = receitaRepository.findByIdPrato(prato.getIdPrato()).orElse(null);
        return mapperToDto(prato, receita);
    }

    public Prato uploadImagePrato(@RequestParam("file") MultipartFile file, @PathVariable Prato prato) {
        try {
            return s3Service.uploadFile(file, prato);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        }

    }

    public ResponseEntity<String> updateImagemPrato(@PathVariable Long idPrato, @RequestBody MultipartFile file) throws IOException {
        Prato prato = pratoRepository.findById(idPrato).orElse(null);
        try {
            s3Service.updateFile(prato.getFoto(), file, prato);
            return new ResponseEntity<>("Imagem alterada com sucesso", HttpStatus.OK);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Erro ao enviar imagem: " + e.getMessage());
        }
    }

    public PratoConsultaDto mapperToDto(Prato prato, Receita receita) {
        ReceitaConsultaDto receitaConsultaDto = new ReceitaConsultaDto();
        return PratoConsultaDto.builder()
                .idPrato(prato.getIdPrato())
                .nome(prato.getNome())
                .descricao(prato.getDescricao())
                .preco(prato.getPreco())
                .alergicosRestricoes(prato.getAlergicosRestricoes())
                .categoria(prato.getCategoria())
                .receitaPrato(ingredienteService.mapReceitaToDto(receita))
                .foto(prato.getFoto())
                .URLAssinada(gerarUrlAssinada(prato))
                .build();
    }

    public void generateExcelReport(List<Long> servedDishesIds, int numberOfIngredients, String filePath) throws IOException {
        // Criar um novo workbook do Excel
        Workbook workbook = new XSSFWorkbook();

        // Criar uma nova planilha
        Sheet sheet = workbook.createSheet("Relatório de Uso de Ingredientes");

        // Cabeçalho
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < numberOfIngredients; i++) {
            Cell cell = headerRow.createCell(i + 1);
            cell.setCellValue("Ingrediente " + (i + 1));
        }

//        // Preencher os dados do relatório
//        int[][] ingredientUsageReport = generateIngredientUsageReport(servedDishesIds, numberOfIngredients);
//        for (int i = 0; i < servedDishesIds.size(); i++) {
//            Row row = sheet.createRow(i + 1);
//            row.createCell(0).setCellValue("Prato " + (i + 1));
//            for (int j = 0; j < numberOfIngredients; j++) {
//                row.createCell(j + 1).setCellValue(ingredientUsageReport[i][j]);
//            }
//        }

        // Escrever os dados no arquivo
        try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
            workbook.write(fileOut);
        }

        // Fechar o workbook
        workbook.close();
    }


//    //PARA TESTES
//    public void setEmpresaService(EmpresaService empresaService) {
//        this.empresaService = empresaService;
//    }
//
//    public void setIngredienteService(IngredienteService ingredienteService) {
//        this.ingredienteService = ingredienteService;
//    }
//
//    public void setModelMapper(ModelMapper modelMapper) {
//        this.modelMapper = modelMapper;
//    }

    //    }
//        workbook.close();
//        // Fechar o workbook
//
//        }
//            workbook.write(fileOut);
//        try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
//        // Escrever os dados no arquivo
//
////        }
////            }
////                row.createCell(j + 1).setCellValue(ingredientUsageReport[i][j]);
////            for (int j = 0; j < numberOfIngredients; j++) {
////            row.createCell(0).setCellValue("Prato " + (i + 1));
////            Row row = sheet.createRow(i + 1);
////        for (int i = 0; i < servedDishesIds.size(); i++) {
////        int[][] ingredientUsageReport = generateIngredientUsageReport(servedDishesIds, numberOfIngredients);
////        // Preencher os dados do relatório
//
//        }
//            cell.setCellValue("Ingrediente " + (i + 1));
//            Cell cell = headerRow.createCell(i + 1);
//        for (int i = 0; i < numberOfIngredients; i++) {
//        Row headerRow = sheet.createRow(0);
//        // Cabeçalho
//
//        Sheet sheet = workbook.createSheet("Relatório de Uso de Ingredientes");
//        // Criar uma nova planilha
//
//        Workbook workbook = new XSSFWorkbook();
//        // Criar um novo workbook do Excel
//    public void generateExcelReport(List<Long> servedDishesIds, int numberOfIngredients, String filePath) throws IOException {
//    }
//        */
//            Prato 3 |       3       |       0       |       8       |       7       |
//            Prato 2 |       0       |       4       |       2       |       0       |
//            Prato 1 |       1       |       3       |       0       |       1       |
//            ----------------------------------------------------------------------
//                    | Ingrediente 1 | Ingrediente 2 | Ingrediente 3 | Ingrediente 4 |
//        /*Resultado esperado
//
//        return ingredientUsageReport;
//
//        }
//            }
//                ingredientUsageReport[i][ingredientIndex] += quantity;
//                int quantity = recipe.getQuantidade();
//                int ingredientIndex = recipe.getIdIngrediente().intValue() - 1;
//            for (Receita recipe : recipes) {
//
//            List<Receita> recipes = receitaRepository.findByIdPrato(dishId);
//            Long dishId = servedDishesIds.get(i);
//        for (int i = 0; i < servedDishesIds.size(); i++) {
//
//        int[][] ingredientUsageReport = new int[servedDishesIds.size()][numberOfIngredients];
//    public int[][] generateIngredientUsageReport(List<Long> servedDishesIds, int numberOfIngredients) {
//    }
//        return ingredientUsage;
//
//
//        }
//            }
//                ingredientUsage.merge(recipe.getIdIngrediente(), recipe.getQuantidade(), Integer::sum);
//                // Se o ingrediente não estiver presente, ele é adicionado ao mapa com a quantidade da receita atual.
//                // Se o ingrediente já estiver presente no mapa, o valor existente é substituído pela soma do valor existente e da quantidade do ingrediente na receita atual.
//                // Atualiza o mapa ingredientUsage:
//            for (Receita recipe : recipes) {
//            // Itera sobre todas as receitas associadas ao prato atual.
//
//            List<Receita> recipes = receitaRepository.findByIdPrato(dishId);
//            // Supõe-se que receitaRepository.findByIdPrato(dishId) retorne uma lista de objetos do tipo Receita.
//            // Obtém a lista de receitas associadas ao prato atual usando o ID do prato.
//        for (Long dishId : servedDishesIds) {
//        // Itera sobre todos os IDs de pratos servidos fornecidos como entrada para o método.
//
//        Map<Long, Integer> ingredientUsage = new HashMap<>();
//        // Valor: Quantidade do ingrediente usado (Integer)
//        // Chave: ID do ingrediente (Long)
//        // Cria um mapa para armazenar o uso de ingredientes.
//    public Map<Long, Integer> calculateIngredientUsage(List<Long> servedDishesIds) {

}