package project.gourmetinventoryproject.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);

//        modelMapper.addMappings(new PropertyMap<EstoqueIngredientePratosConsultaDto, EstoqueIngrediente>() {
//            @Override
//            protected void configure() {
//                map().setNome(source.getNome());
//            }
//        });

        return modelMapper;
    }
}
