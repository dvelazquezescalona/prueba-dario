package cu.fcc.pigeon.service.mapper;

import cu.fcc.pigeon.domain.ColombofiloVuelo;
import cu.fcc.pigeon.service.dto.ColombofiloVueloDTO;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { ColombofiloMapper.class, VueloMapper.class })
public interface ColombofiloVueloMapper extends EntityMapper<ColombofiloVueloDTO, ColombofiloVuelo> {
    @Override
    @Mapping(source = "colombofiloId", target = "colombofilo")
    @Mapping(source = "vueloId", target = "vuelo")
    ColombofiloVuelo toEntity(ColombofiloVueloDTO dto);

    @Override
    @Mapping(source = "colombofilo.id", target = "colombofiloId")
    @Mapping(source = "vuelo.id", target = "vueloId")
    ColombofiloVueloDTO toDto(ColombofiloVuelo entity);

    @Override
    List<ColombofiloVuelo> toEntity(List<ColombofiloVueloDTO> DtoList);

    @Override
    List<ColombofiloVueloDTO> toDto(List<ColombofiloVuelo> entityList);

    default ColombofiloVuelo colombofiloVueloFromId(Long id) {
        if (id == null) {
            return null;
        }
        ColombofiloVuelo colombofiloVuelo = new ColombofiloVuelo();
        colombofiloVuelo.setId(id);
        return colombofiloVuelo;
    }
}
