package cu.fcc.pigeon.service.mapper;

import cu.fcc.pigeon.domain.Color;
import cu.fcc.pigeon.service.dto.ColorDTO;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {})
public interface ColorMapper extends EntityMapper<ColorDTO, Color> {
    @Override
    @Mapping(target = "removePaloma", ignore = true)
    @Mapping(target = "palomas", ignore = true)
    Color toEntity(ColorDTO dto);

    @Override
    ColorDTO toDto(Color entity);

    @Override
    List<Color> toEntity(List<ColorDTO> DtoList);

    @Override
    List<ColorDTO> toDto(List<Color> entityList);

    default Color colorFromId(Long id) {
        if (id == null) {
            return null;
        }
        Color color = new Color();
        color.setId(id);
        return color;
    }
}
