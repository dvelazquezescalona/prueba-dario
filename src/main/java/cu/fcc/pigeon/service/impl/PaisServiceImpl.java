package cu.fcc.pigeon.service.impl;

import cu.fcc.pigeon.domain.Pais;
import cu.fcc.pigeon.repository.PaisRepository;
import cu.fcc.pigeon.service.PaisService;
import cu.fcc.pigeon.service.dto.PaisDTO;
import cu.fcc.pigeon.service.mapper.PaisMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link cu.fcc.pigeon.domain.Pais}.
 */
@Service
@Transactional
public class PaisServiceImpl implements PaisService {

    private final Logger log = LoggerFactory.getLogger(PaisServiceImpl.class);

    private final PaisRepository paisRepository;

    private final PaisMapper paisMapper;

    public PaisServiceImpl(PaisRepository paisRepository, PaisMapper paisMapper) {
        this.paisRepository = paisRepository;
        this.paisMapper = paisMapper;
    }

    @Override
    public PaisDTO save(PaisDTO paisDTO) {
        log.debug("Request to save Pais : {}", paisDTO);
        Pais pais = paisMapper.toEntity(paisDTO);
        pais = paisRepository.save(pais);
        return paisMapper.toDto(pais);
    }

    @Override
    public PaisDTO update(PaisDTO paisDTO) {
        log.debug("Request to update Pais : {}", paisDTO);
        Pais pais = paisMapper.toEntity(paisDTO);
        pais = paisRepository.save(pais);
        return paisMapper.toDto(pais);
    }

    @Override
    public Optional<PaisDTO> partialUpdate(PaisDTO paisDTO) {
        log.debug("Request to partially update Pais : {}", paisDTO);

        return paisRepository
            .findById(paisDTO.getId())
            .map(existingPais -> {
                if (paisDTO.getNombre() != null) {
                    existingPais.setNombre(paisDTO.getNombre());
                }
                if (paisDTO.getCodigo() != null) {
                    existingPais.setCodigo(paisDTO.getCodigo());
                }
                //paisMapper.partialUpdate(existingPais, paisDTO);

                return existingPais;
            })
            .map(paisRepository::save)
            .map(paisMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaisDTO> findAll() {
        log.debug("Request to get all Pais");
        return paisRepository.findAll().stream().map(paisMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PaisDTO> findOne(Long id) {
        log.debug("Request to get Pais : {}", id);
        return paisRepository.findById(id).map(paisMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Pais : {}", id);
        paisRepository.deleteById(id);
    }
}
