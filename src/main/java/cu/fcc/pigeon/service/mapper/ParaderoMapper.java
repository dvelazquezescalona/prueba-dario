package cu.fcc.pigeon.service.mapper;

import cu.fcc.pigeon.domain.Paradero;
import cu.fcc.pigeon.service.dto.ParaderoDTO;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { SociedadMapper.class })
public interface ParaderoMapper extends EntityMapper<ParaderoDTO, Paradero> {
    @Override
    @Mapping(source = "sociedadId", target = "sociedad")
    @Mapping(target = "removeVuelo", ignore = true)
    @Mapping(target = "vuelos", ignore = true)
    Paradero toEntity(ParaderoDTO dto);

    @Override
    @Mapping(source = "sociedad.id", target = "sociedadId")
    ParaderoDTO toDto(Paradero entity);

    @Override
    List<Paradero> toEntity(List<ParaderoDTO> DtoList);

    @Override
    List<ParaderoDTO> toDto(List<Paradero> entityList);

    default Paradero paraderoFromId(Long id) {
        if (id == null) {
            return null;
        }
        Paradero paradero = new Paradero();
        paradero.setId(id);
        return paradero;
    }
}
