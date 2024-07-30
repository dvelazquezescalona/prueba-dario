package cu.fcc.pigeon.service.mapper;

import cu.fcc.pigeon.domain.Paloma;
import cu.fcc.pigeon.service.dto.PalomaDTO;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { ColorMapper.class, ColombofiloMapper.class })
public interface PalomaMapper extends EntityMapper<PalomaDTO, Paloma> {
    @Override
    @Mapping(source = "colorId", target = "color")
    @Mapping(source = "colombofiloId", target = "colombofilo")
    @Mapping(target = "removePremio", ignore = true)
    @Mapping(target = "premios", ignore = true)
    Paloma toEntity(PalomaDTO dto);

    @Override
    @Mapping(source = "color.id", target = "colorId")
    @Mapping(source = "colombofilo.id", target = "colombofiloId")
    PalomaDTO toDto(Paloma entity);

    @Override
    List<Paloma> toEntity(List<PalomaDTO> DtoList);

    @Override
    List<PalomaDTO> toDto(List<Paloma> entityList);

    default Paloma palomaFromId(Long id) {
        if (id == null) {
            return null;
        }
        Paloma paloma = new Paloma();
        paloma.setId(id);
        return paloma;
    }
}
