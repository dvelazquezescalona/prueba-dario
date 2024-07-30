package cu.fcc.pigeon.service.impl;

import cu.fcc.pigeon.Exception.BusinnesException;
import cu.fcc.pigeon.config.SYSMensaje;
import cu.fcc.pigeon.domain.*;
import cu.fcc.pigeon.repository.ColombofiloRepository;
import cu.fcc.pigeon.repository.ColorRepository;
import cu.fcc.pigeon.repository.PalomaRepository;
import cu.fcc.pigeon.service.PalomaService;
import cu.fcc.pigeon.service.dto.PalomaDTO;
import cu.fcc.pigeon.service.dto.ResponseDTO;
import cu.fcc.pigeon.service.dto.VueloDTO;
import cu.fcc.pigeon.service.dto.enumeration.Status;
import cu.fcc.pigeon.service.mapper.PalomaMapper;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link cu.fcc.pigeon.domain.Paloma}.
 */
@Service
@Transactional
public class PalomaServiceImpl implements PalomaService {

    private final Logger log = LoggerFactory.getLogger(PalomaServiceImpl.class);

    private final PalomaRepository palomaRepository;

    private final ColombofiloRepository colombofiloRepository;

    private final ColorRepository colorRepository;

    private final PalomaMapper palomaMapper;

    public PalomaServiceImpl(
        PalomaRepository palomaRepository,
        ColombofiloRepository colombofiloRepository,
        PalomaMapper palomaMapper,
        ColorRepository colorRepository
    ) {
        this.palomaRepository = palomaRepository;
        this.colombofiloRepository = colombofiloRepository;
        this.palomaMapper = palomaMapper;
        this.colorRepository = colorRepository;
    }

    @Override
    public Paloma save(Paloma paloma) {
        log.debug("Request to save Paloma : {}", paloma);
        return palomaRepository.save(paloma);
    }

    @Override
    public Paloma update(Paloma paloma) {
        log.debug("Request to update Paloma : {}", paloma);
        return palomaRepository.save(paloma);
    }

    @Override
    public Optional<Paloma> partialUpdate(Paloma paloma) {
        log.debug("Request to partially update Paloma : {}", paloma);

        return palomaRepository
            .findById(paloma.getId())
            .map(existingPaloma -> {
                if (paloma.getAnilla() != null) {
                    existingPaloma.setAnilla(paloma.getAnilla());
                }
                if (paloma.getAnno() != null) {
                    existingPaloma.setAnno(paloma.getAnno());
                }
                if (paloma.getPais() != null) {
                    existingPaloma.setPais(paloma.getPais());
                }
                if (paloma.getSexo() != null) {
                    existingPaloma.setSexo(paloma.getSexo());
                }
                if (paloma.getActivo() != null) {
                    existingPaloma.setActivo(paloma.getActivo());
                }

                return existingPaloma;
            })
            .map(palomaRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Paloma> findAll() {
        log.debug("Request to get all Palomas");
        return palomaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Paloma> findOne(Long id) {
        log.debug("Request to get Paloma : {}", id);
        return palomaRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Paloma : {}", id);
        palomaRepository.deleteById(id);
    }

    @Override
    public ResponseDTO<PalomaDTO> agregarPaloma(PalomaDTO palomaDTO) {
        log.info("Request para agregar una paloma {}", palomaDTO);
        try {
            Colombofilo colombofilo = colombofiloRepository
                .findById(palomaDTO.getColombofiloId())
                .orElseThrow(() -> new BusinnesException(SYSMensaje.COLOMBOFILO_NOEXISTS + palomaDTO.getColombofiloId()));
            Color color = colorRepository
                .findById(palomaDTO.getColorId())
                .orElseThrow(() -> new BusinnesException(SYSMensaje.PALOMA_COLOR + palomaDTO.getColorId()));
            Optional<Paloma> paloma = palomaRepository.findByAnillaAndAnnoAndPais(
                palomaDTO.getAnilla(),
                palomaDTO.getAnno(),
                palomaDTO.getPais()
            );

            if (paloma.isEmpty()) {
                Paloma newPaloma = palomaRepository.save(palomaMapper.toEntity(palomaDTO));
                return new ResponseDTO<>(SYSMensaje.PALOMA_INSERT_OK, Status.OK, palomaMapper.toDto(newPaloma));
            }
            return new ResponseDTO<>(
                SYSMensaje.PALOMA_IDENT + palomaDTO.getAnilla() + " " + palomaDTO.getAnno() + " " + palomaDTO.getPais(),
                Status.ERROR,
                null
            );
        } catch (BusinnesException e) {
            return new ResponseDTO<>(e.getMessage(), Status.ERROR, null);
        } catch (Exception e) {
            return new ResponseDTO<>(SYSMensaje.DB_CONECTION, Status.NOK, null);
        }
    }

    @Override
    public ResponseDTO<PalomaDTO> actualizarPaloma(PalomaDTO palomaDTO) {
        log.info("Request para actualizar una paloma {}", palomaDTO);
        try {
            Paloma paloma = palomaRepository
                .findById(palomaDTO.getId())
                .orElseThrow(() -> new BusinnesException(SYSMensaje.PALOMA_NOEXISTS + palomaDTO.getId()));
            Colombofilo colombofilo = colombofiloRepository
                .findById(palomaDTO.getColombofiloId())
                .orElseThrow(() -> new BusinnesException(SYSMensaje.COLOMBOFILO_NOEXISTS + palomaDTO.getColombofiloId()));
            Color color = colorRepository
                .findById(palomaDTO.getColorId())
                .orElseThrow(() -> new BusinnesException(SYSMensaje.PALOMA_COLOR + palomaDTO.getColorId()));
            Paloma existePaloma = palomaRepository
                .findByAnillaAndAnnoAndPais(palomaDTO.getAnilla(), palomaDTO.getAnno(), palomaDTO.getPais())
                .orElse(paloma);

            if (paloma.getId().equals(existePaloma.getId())) {
                paloma.setColombofilo(colombofilo);
                paloma.setColor(color);

                if (palomaDTO.getAnilla() != null) {
                    paloma.setAnilla(palomaDTO.getAnilla());
                }
                if (palomaDTO.getAnno() != null) {
                    paloma.setAnno(palomaDTO.getAnno());
                }
                if (palomaDTO.getPais() != null) {
                    paloma.setPais(palomaDTO.getPais());
                }
                if (palomaDTO.getSexo() != null) {
                    paloma.setSexo(palomaDTO.getSexo());
                }
                if (palomaDTO.getActivo() != null) {
                    paloma.setActivo(palomaDTO.getActivo());
                }

                return new ResponseDTO<>(SYSMensaje.PALOMA_UPDATE_OK, Status.OK, palomaMapper.toDto(paloma));
            }
            return new ResponseDTO<>(
                SYSMensaje.PALOMA_IDENT + palomaDTO.getAnilla() + " " + palomaDTO.getAnno() + " " + palomaDTO.getPais(),
                Status.ERROR,
                null
            );
        } catch (BusinnesException e) {
            return new ResponseDTO<>(e.getMessage(), Status.ERROR, null);
        } catch (Exception e) {
            return new ResponseDTO<>(SYSMensaje.DB_CONECTION, Status.NOK, null);
        }
    }

    @Override
    public ResponseDTO<PalomaDTO> obtenerPaloma(Long id) {
        log.info("Request para obtener una paloma por su id: {}", id);
        try {
            Paloma paloma = palomaRepository.findById(id).orElseThrow(() -> new BusinnesException(SYSMensaje.PALOMA_NOEXISTS + id));
            return new ResponseDTO<>(SYSMensaje.PALOMA_EXISTS, Status.OK, palomaMapper.toDto(paloma));
        } catch (BusinnesException e) {
            return new ResponseDTO<>(e.getMessage(), Status.ERROR, null);
        } catch (Exception e) {
            return new ResponseDTO<>(SYSMensaje.DB_CONECTION, Status.NOK, null);
        }
    }

    @Override
    public ResponseDTO<List<PalomaDTO>> obtenerPalomasByColombofilo(Long colombofiloId) {
        log.info("Request para obtener las palomas del colombofilo con id: {}", colombofiloId);
        try {
            //palomaRepository.findB
            Colombofilo colombofilo = colombofiloRepository
                .findById(colombofiloId)
                .orElseThrow(() -> new BusinnesException(SYSMensaje.COLOMBOFILO_NOEXISTS + colombofiloId));
            if (!colombofilo.getPalomas().isEmpty()) {
                return new ResponseDTO<>(
                    SYSMensaje.PALOMA_LIST + colombofiloId,
                    Status.OK,
                    palomaMapper.toDto(colombofilo.getPalomas().stream().toList())
                );
            }
            return new ResponseDTO<>(SYSMensaje.PALOMA_NOLIST + colombofiloId, Status.NOK, null);
        } catch (BusinnesException e) {
            return new ResponseDTO<>(e.getMessage(), Status.ERROR, null);
        } catch (Exception e) {
            return new ResponseDTO<>(SYSMensaje.DB_CONECTION, Status.NOK, null);
        }
    }
}
