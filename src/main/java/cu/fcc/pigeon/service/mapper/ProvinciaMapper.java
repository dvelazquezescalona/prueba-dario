package cu.fcc.pigeon.service.mapper;

import cu.fcc.pigeon.domain.Provincia;
import cu.fcc.pigeon.service.dto.ProvinciaDTO;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {})
public interface ProvinciaMapper extends EntityMapper<ProvinciaDTO, Provincia> {
    @Override
    @Mapping(target = "removeMunicipio", ignore = true)
    @Mapping(target = "municipios", ignore = true)
    Provincia toEntity(ProvinciaDTO dto);

    @Override
    ProvinciaDTO toDto(Provincia entity);

    @Override
    List<Provincia> toEntity(List<ProvinciaDTO> DtoList);

    @Override
    List<ProvinciaDTO> toDto(List<Provincia> entityList);

    default Provincia provinciaFromId(Long id) {
        if (id == null) {
            return null;
        }
        Provincia provincia = new Provincia();
        provincia.setId(id);
        return provincia;
    }
}
