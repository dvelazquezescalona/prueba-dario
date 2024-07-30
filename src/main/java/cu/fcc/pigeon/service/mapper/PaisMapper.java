package cu.fcc.pigeon.service.mapper;

import cu.fcc.pigeon.domain.Pais;
import cu.fcc.pigeon.service.dto.PaisDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Pais} and its DTO {@link PaisDTO}.
 */
@Mapper(componentModel = "spring")
public interface PaisMapper extends EntityMapper<PaisDTO, Pais> {}
