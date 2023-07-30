package com.pixelthump.seshservice.config;
import com.pixelthump.seshservice.repository.model.Sesh;
import com.pixelthump.seshservice.repository.model.SeshType;
import com.pixelthump.seshservice.rest.model.HttpSeshDTO;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    ModelMapper getModelMapper() {

        ModelMapper modelMapper = new ModelMapper();
        getMappings(modelMapper);
        return modelMapper;
    }

    private void getMappings(ModelMapper modelMapper) {

        TypeMap<Sesh, HttpSeshDTO> propertyMap = modelMapper.createTypeMap(Sesh.class, HttpSeshDTO.class);
        Converter<SeshType, String> seshTypeStringConverter = st -> st.getSource().getName();
        propertyMap.addMappings(mapper -> mapper.using(seshTypeStringConverter).map(Sesh::getSeshType, HttpSeshDTO::setSeshType));
    }
}
