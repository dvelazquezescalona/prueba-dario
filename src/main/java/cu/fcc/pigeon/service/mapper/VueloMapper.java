package cu.fcc.pigeon.service.mapper;

import cu.fcc.pigeon.domain.Vuelo;
import cu.fcc.pigeon.service.dto.VueloDTO;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { ParaderoMapper.class })
public interface VueloMapper extends EntityMapper<VueloDTO, Vuelo> {
    @Override
    @Mapping(source = "paraderoId", target = "paradero")
    @Mapping(target = "removeColombofiloVuelo", ignore = true)
    @Mapping(target = "removePremio", ignore = true)
    @Mapping(target = "colombofiloVuelos", ignore = true)
    @Mapping(target = "premios", ignore = true)
    Vuelo toEntity(VueloDTO dto);

    @Override
    @Mapping(source = "paradero.id", target = "paraderoId")
    VueloDTO toDto(Vuelo entity);

    @Override
    List<Vuelo> toEntity(List<VueloDTO> DtoList);

    @Override
    List<VueloDTO> toDto(List<Vuelo> entityList);

    default Vuelo vueloFromId(Long id) {
        if (id == null) {
            return null;
        }
        Vuelo vuelo = new Vuelo();
        vuelo.setId(id);
        return vuelo;
    }
}
