package cu.fcc.pigeon.service.mapper;

import java.util.List;

public interface EntityMapper<D, E> {
    E toEntity(D dto);

    D toDto(E entity);

    List<E> toEntity(List<D> DtoList);

    List<D> toDto(List<E> entityList);
}
