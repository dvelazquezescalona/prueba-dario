package cu.fcc.pigeon.service.mapper;

import cu.fcc.pigeon.domain.Municipio;
import cu.fcc.pigeon.service.dto.MunicipioDTO;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = { ProvinciaMapper.class })
public interface MunicipioMapper extends EntityMapper<MunicipioDTO, Municipio> {
    //ProvinciaMapper PROVINCIA_MAPPER = Mappers.getMapper(ProvinciaMapper.class);

    @Override
    @Mapping(source = "provinciaId", target = "provincia")
    @Mapping(target = "removeSociedad", ignore = true)
    @Mapping(target = "sociedades", ignore = true)
    Municipio toEntity(MunicipioDTO dto);

    @Override
    @Mapping(source = "provincia.id", target = "provinciaId")
    MunicipioDTO toDto(Municipio entity);

    @Override
    List<Municipio> toEntity(List<MunicipioDTO> DtoList);

    @Override
    List<MunicipioDTO> toDto(List<Municipio> entityList);

    default Municipio municipioFromId(Long id) {
        if (id == null) {
            return null;
        }
        Municipio municipio = new Municipio();
        municipio.setId(id);
        return municipio;
    }
}
