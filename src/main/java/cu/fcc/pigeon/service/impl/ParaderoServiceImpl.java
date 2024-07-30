package cu.fcc.pigeon.service.impl;

import cu.fcc.pigeon.Exception.BusinnesException;
import cu.fcc.pigeon.config.SYSMensaje;
import cu.fcc.pigeon.domain.Paradero;
import cu.fcc.pigeon.domain.Sociedad;
import cu.fcc.pigeon.repository.ParaderoRepository;
import cu.fcc.pigeon.repository.SociedadRepository;
import cu.fcc.pigeon.service.ParaderoService;
import cu.fcc.pigeon.service.dto.ParaderoDTO;
import cu.fcc.pigeon.service.dto.ResponseDTO;
import cu.fcc.pigeon.service.dto.enumeration.Status;
import cu.fcc.pigeon.service.mapper.ParaderoMapper;
import java.nio.BufferOverflowException;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link cu.fcc.pigeon.domain.Paradero}.
 */
@Service
@Transactional
public class ParaderoServiceImpl implements ParaderoService {

    private final Logger log = LoggerFactory.getLogger(ParaderoServiceImpl.class);

    private final ParaderoRepository paraderoRepository;

    private final SociedadRepository sociedadRepository;

    private final ParaderoMapper paraderoMapper;

    public ParaderoServiceImpl(
        ParaderoRepository paraderoRepository,
        SociedadRepository sociedadRepository,
        ParaderoMapper paraderoMapper
    ) {
        this.paraderoRepository = paraderoRepository;
        this.sociedadRepository = sociedadRepository;
        this.paraderoMapper = paraderoMapper;
    }

    @Override
    public Paradero save(Paradero paradero) {
        log.debug("Request to save Paradero : {}", paradero);
        return paraderoRepository.save(paradero);
    }

    @Override
    public Paradero update(Paradero paradero) {
        log.debug("Request to update Paradero : {}", paradero);
        return paraderoRepository.save(paradero);
    }

    @Override
    public Optional<Paradero> partialUpdate(Paradero paradero) {
        log.debug("Request to partially update Paradero : {}", paradero);

        return paraderoRepository
            .findById(paradero.getId())
            .map(existingParadero -> {
                if (paradero.getNombreParadero() != null) {
                    existingParadero.setNombreParadero(paradero.getNombreParadero());
                }
                if (paradero.getDistanciaMedia() != null) {
                    existingParadero.setDistanciaMedia(paradero.getDistanciaMedia());
                }
                if (paradero.getLatitud() != null) {
                    existingParadero.setLatitud(paradero.getLatitud());
                }
                if (paradero.getLongitud() != null) {
                    existingParadero.setLongitud(paradero.getLongitud());
                }

                return existingParadero;
            })
            .map(paraderoRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Paradero> findAll() {
        log.debug("Request to get all Paraderos");
        return paraderoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Paradero> findOne(Long id) {
        log.debug("Request to get Paradero : {}", id);
        return paraderoRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Paradero : {}", id);
        paraderoRepository.deleteById(id);
    }

    @Override
    public ResponseDTO<ParaderoDTO> agregarParadero(ParaderoDTO paraderoDTO) throws BusinnesException {
        log.info("Request para agregar un paradero {}", paraderoDTO);
        try {
            Sociedad sociedad = sociedadRepository
                .findById(paraderoDTO.getSociedadId())
                .orElseThrow(() -> new BusinnesException(SYSMensaje.PARADERO_NOSOCIEDAD + paraderoDTO.getSociedadId()));

            Optional<Paradero> paradero = paraderoRepository.findByNombreParaderoAndSociedad(paraderoDTO.getNombreParadero(), sociedad);
            if (paradero.isPresent()) {
                return new ResponseDTO<>(SYSMensaje.PARADERO_NOMBRE + paraderoDTO.getNombreParadero(), Status.ERROR, null);
            }
            Paradero newParadero = paraderoRepository.save(paraderoMapper.toEntity(paraderoDTO));
            return new ResponseDTO<>(SYSMensaje.PARADERO_INSERT_OK, Status.OK, paraderoMapper.toDto(newParadero));
        } catch (BusinnesException e) {
            return new ResponseDTO<>(e.getMessage(), Status.ERROR, null);
        } catch (Exception e) {
            return new ResponseDTO<>(SYSMensaje.DB_CONECTION, Status.NOK, null);
        }
    }

    @Override
    public ResponseDTO<ParaderoDTO> actualizarParadero(ParaderoDTO paraderoDTO) throws BusinnesException {
        log.info("Request para actualizar un paradero {}", paraderoDTO);
        try {
            Paradero paradero = paraderoRepository
                .findById(paraderoDTO.getId())
                .orElseThrow(() -> new BusinnesException(SYSMensaje.PARADERO_NOEXISTS + paraderoDTO.getId()));
            Sociedad sociedad = sociedadRepository
                .findById(paraderoDTO.getSociedadId())
                .orElseThrow(() -> new BusinnesException(SYSMensaje.PARADERO_NOSOCIEDAD + paraderoDTO.getSociedadId()));
            Paradero existeParadero = paraderoRepository
                .findByNombreParaderoAndSociedad(paraderoDTO.getNombreParadero(), sociedad)
                .orElse(paradero);

            if (paradero.getId().equals(existeParadero.getId())) {
                paradero.setSociedad(sociedad);
                if (paraderoDTO.getNombreParadero() != null) {
                    paradero.setNombreParadero(paraderoDTO.getNombreParadero());
                }
                if (paraderoDTO.getDistanciaMedia() != null) {
                    paradero.setDistanciaMedia(paraderoDTO.getDistanciaMedia());
                }
                if (paraderoDTO.getLatitud() != null) {
                    paradero.setLatitud(paraderoDTO.getLatitud());
                }
                if (paraderoDTO.getLongitud() != null) {
                    paradero.setLongitud(paraderoDTO.getLongitud());
                }
                return new ResponseDTO<>(SYSMensaje.PARADERO_UPDATE_OK, Status.OK, paraderoMapper.toDto(paradero));
            }
            return new ResponseDTO<>(SYSMensaje.PARADERO_NOMBRE + paraderoDTO.getNombreParadero(), Status.ERROR, null);
        } catch (BusinnesException e) {
            return new ResponseDTO<>(e.getMessage(), Status.ERROR, null);
        } catch (Exception e) {
            return new ResponseDTO<>(SYSMensaje.DB_CONECTION, Status.NOK, null);
        }
    }

    @Override
    public ResponseDTO<ParaderoDTO> obtenerParadero(Long id) throws BusinnesException {
        log.info("Request para obtener un paradero por su id: {}", id);
        try {
            Paradero paradero = paraderoRepository.findById(id).orElseThrow(() -> new BusinnesException(SYSMensaje.PARADERO_NOEXISTS + id));
            return new ResponseDTO<>(SYSMensaje.PARADERO_EXISTS, Status.OK, paraderoMapper.toDto(paradero));
        } catch (BusinnesException e) {
            return new ResponseDTO<>(e.getMessage(), Status.ERROR, null);
        } catch (Exception e) {
            return new ResponseDTO<>(SYSMensaje.DB_CONECTION, Status.NOK, null);
        }
    }

    @Override
    public ResponseDTO<List<ParaderoDTO>> obtenerParaderos(Long sociedadId) throws BusinnesException {
        log.info("Request para obtener los paraderos de la sociedad con id: {}", sociedadId);
        try {
            Sociedad sociedad = sociedadRepository
                .findById(sociedadId)
                .orElseThrow(() -> new BusinnesException(SYSMensaje.PARADERO_NOSOCIEDAD + sociedadId));
            if (!sociedad.getParaderos().isEmpty()) {
                return new ResponseDTO<>(
                    SYSMensaje.PARADERO_LIST + sociedadId,
                    Status.OK,
                    paraderoMapper.toDto(sociedad.getParaderos().stream().toList())
                );
            }
            return new ResponseDTO<>(SYSMensaje.PARADERO_NOLIST + sociedadId, Status.NOK, null);
        } catch (BusinnesException e) {
            return new ResponseDTO<>(e.getMessage(), Status.ERROR, null);
        } catch (Exception e) {
            return new ResponseDTO<>(SYSMensaje.DB_CONECTION, Status.NOK, null);
        }
    }
}
