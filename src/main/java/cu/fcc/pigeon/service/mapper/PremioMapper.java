package cu.fcc.pigeon.service.mapper;

import cu.fcc.pigeon.domain.Premio;
import cu.fcc.pigeon.service.dto.PremioDTO;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { PalomaMapper.class, VueloMapper.class })
public interface PremioMapper extends EntityMapper<PremioDTO, Premio> {
    @Override
    @Mapping(source = "palomaId", target = "paloma")
    @Mapping(source = "vueloId", target = "vuelo")
    Premio toEntity(PremioDTO dto);

    @Override
    @Mapping(source = "paloma.id", target = "palomaId")
    @Mapping(source = "vuelo.id", target = "vueloId")
    PremioDTO toDto(Premio entity);

    @Override
    List<Premio> toEntity(List<PremioDTO> DtoList);

    @Override
    List<PremioDTO> toDto(List<Premio> entityList);

    default Premio premioFromId(Long id) {
        if (id == null) {
            return null;
        }
        Premio premio = new Premio();
        premio.setId(id);
        return premio;
    }
}
