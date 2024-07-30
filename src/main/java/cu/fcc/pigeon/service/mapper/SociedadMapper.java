package cu.fcc.pigeon.service.mapper;

import cu.fcc.pigeon.domain.Sociedad;
import cu.fcc.pigeon.service.dto.SociedadDTO;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { MunicipioMapper.class })
public interface SociedadMapper extends EntityMapper<SociedadDTO, Sociedad> {
    @Override
    @Mapping(source = "municipioId", target = "municipio")
    @Mapping(target = "removeColombofilo", ignore = true)
    @Mapping(target = "removeParadero", ignore = true)
    @Mapping(target = "colombofilos", ignore = true)
    @Mapping(target = "paraderos", ignore = true)
    Sociedad toEntity(SociedadDTO dto);

    @Override
    @Mapping(source = "municipio.id", target = "municipioId")
    SociedadDTO toDto(Sociedad entity);

    @Override
    List<Sociedad> toEntity(List<SociedadDTO> DtoList);

    @Override
    List<SociedadDTO> toDto(List<Sociedad> entityList);

    default Sociedad sociedadFromId(Long id) {
        if (id == null) {
            return null;
        }
        Sociedad sociedad = new Sociedad();
        sociedad.setId(id);
        return sociedad;
    }
}
