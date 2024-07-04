package project.gourmetinventoryproject.service;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.gourmetinventoryproject.domain.Fornecedor;
import project.gourmetinventoryproject.dto.fornecedor.FornecedorCriacaoDto;
import project.gourmetinventoryproject.exception.IdNotFoundException;
import project.gourmetinventoryproject.repository.FornecedorRepository;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.status;

@Service
public class FornecedorService {

    @Autowired
    public FornecedorRepository fornecedorRepository;

    @Autowired
    private ModelMapper modelMapper;

    public void postFornecedor(FornecedorCriacaoDto fornecedorCriacaoDto) throws Exception {

        //Consumindo API externa ViaCep
        URL url = new URL("https://viacep.com.br/ws/"+ fornecedorCriacaoDto.getCep() +"/json/");
        URLConnection connection = url.openConnection();
        InputStream is = connection.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

        String cep = "";
        StringBuilder jsonCep = new StringBuilder();

        while ((cep = br.readLine()) != null){
            jsonCep.append(cep);
        }

        System.out.println(jsonCep.toString());

        FornecedorCriacaoDto userAux = new Gson().fromJson(jsonCep.toString(), FornecedorCriacaoDto.class);

        fornecedorCriacaoDto.setCep(userAux.getCep());
        fornecedorCriacaoDto.setLogradouro(userAux.getLogradouro());
        fornecedorCriacaoDto.setComplemento(userAux.getComplemento());
        fornecedorCriacaoDto.setBairro(userAux.getBairro());
        fornecedorCriacaoDto.setLocalidade(userAux.getLocalidade());
        fornecedorCriacaoDto.setUf(userAux.getUf());
        //

        Fornecedor novoFornecedor = modelMapper.map(fornecedorCriacaoDto, Fornecedor.class);
        fornecedorRepository.save(novoFornecedor);
    }

    public List<Fornecedor> getFornecedores(){
        List<Fornecedor> fornecedores = new ArrayList<>();
        fornecedores = fornecedorRepository.findAll();
        return fornecedores;
    }


    public void deleteFornecedor(Long id){
        if (fornecedorRepository.existsById(id)) {
            fornecedorRepository.deleteById(id);
        } else {
            throw new IdNotFoundException();
        }
    }

    public void patchFornecedor(Long id, FornecedorCriacaoDto fornecedorCriacaoDto){
        if (fornecedorRepository.existsById(id)) {
            Optional<Fornecedor> optionalFornecedor = fornecedorRepository.findById(id);

            if (optionalFornecedor.isPresent()) {
                Fornecedor fornecedor = optionalFornecedor.get();
                modelMapper.map(fornecedorCriacaoDto, fornecedor);
                fornecedorRepository.save(fornecedor);

            }
        } else {
            throw new IdNotFoundException();
        }
    }


}
