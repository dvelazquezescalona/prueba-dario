package cu.fcc.pigeon.service.mapper;

import cu.fcc.pigeon.domain.Colombofilo;
import cu.fcc.pigeon.service.dto.ColombofiloDTO;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { SociedadMapper.class })
public interface ColombofiloMapper extends EntityMapper<ColombofiloDTO, Colombofilo> {
    @Override
    @Mapping(source = "sociedadId", target = "sociedad")
    @Mapping(target = "removePaloma", ignore = true)
    @Mapping(target = "removeColombofiloVuelo", ignore = true)
    @Mapping(target = "palomas", ignore = true)
    @Mapping(target = "colombofiloVuelos", ignore = true)
    Colombofilo toEntity(ColombofiloDTO dto);

    @Override
    @Mapping(source = "sociedad.id", target = "sociedadId")
    ColombofiloDTO toDto(Colombofilo entity);

    @Override
    List<Colombofilo> toEntity(List<ColombofiloDTO> DtoList);

    @Override
    List<ColombofiloDTO> toDto(List<Colombofilo> entityList);

    default Colombofilo colombofiloFromId(Long id) {
        if (id == null) {
            return null;
        }
        Colombofilo colombofilo = new Colombofilo();
        colombofilo.setId(id);
        return colombofilo;
    }
}
