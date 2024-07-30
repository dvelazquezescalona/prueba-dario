package cu.fcc.pigeon.service.impl;

import cu.fcc.pigeon.Exception.BusinnesException;
import cu.fcc.pigeon.config.SYSMensaje;
import cu.fcc.pigeon.domain.Colombofilo;
import cu.fcc.pigeon.domain.Paradero;
import cu.fcc.pigeon.domain.Sociedad;
import cu.fcc.pigeon.repository.ColombofiloRepository;
import cu.fcc.pigeon.repository.SociedadRepository;
import cu.fcc.pigeon.service.ColombofiloService;
import cu.fcc.pigeon.service.dto.ColombofiloDTO;
import cu.fcc.pigeon.service.dto.ResponseDTO;
import cu.fcc.pigeon.service.dto.enumeration.Status;
import cu.fcc.pigeon.service.mapper.ColombofiloMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import liquibase.util.ObjectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

/**
 * Service Implementation for managing {@link cu.fcc.pigeon.domain.Colombofilo}.
 */
@Service
@Transactional
public class ColombofiloServiceImpl implements ColombofiloService {

    private final Logger log = LoggerFactory.getLogger(ColombofiloServiceImpl.class);

    private final ColombofiloRepository colombofiloRepository;

    private final SociedadRepository sociedadRepository;

    private final ColombofiloMapper colombofiloMapper;

    public ColombofiloServiceImpl(
        ColombofiloRepository colombofiloRepository,
        SociedadRepository sociedadRepository,
        ColombofiloMapper colombofiloMapper
    ) {
        this.colombofiloRepository = colombofiloRepository;
        this.sociedadRepository = sociedadRepository;
        this.colombofiloMapper = colombofiloMapper;
    }

    @Override
    public Colombofilo save(Colombofilo colombofilo) {
        log.debug("Request to save Colombofilo : {}", colombofilo);
        return colombofiloRepository.save(colombofilo);
    }

    @Override
    public Colombofilo update(Colombofilo colombofilo) {
        log.debug("Request to update Colombofilo : {}", colombofilo);
        return colombofiloRepository.save(colombofilo);
    }

    @Override
    public Optional<Colombofilo> partialUpdate(Colombofilo colombofilo) {
        log.debug("Request to partially update Colombofilo : {}", colombofilo);

        return colombofiloRepository
            .findById(colombofilo.getId())
            .map(existingColombofilo -> {
                if (colombofilo.getNombre() != null) {
                    existingColombofilo.setNombre(colombofilo.getNombre());
                }
                if (colombofilo.getPrimerApellido() != null) {
                    existingColombofilo.setPrimerApellido(colombofilo.getPrimerApellido());
                }
                if (colombofilo.getSegindoApellido() != null) {
                    existingColombofilo.setSegindoApellido(colombofilo.getSegindoApellido());
                }
                if (colombofilo.getCi() != null) {
                    existingColombofilo.setCi(colombofilo.getCi());
                }
                if (colombofilo.getLatitud() != null) {
                    existingColombofilo.setLatitud(colombofilo.getLatitud());
                }
                if (colombofilo.getLongitud() != null) {
                    existingColombofilo.setLongitud(colombofilo.getLongitud());
                }
                if (colombofilo.getDireccion() != null) {
                    existingColombofilo.setDireccion(colombofilo.getDireccion());
                }
                if (colombofilo.getCategoria() != null) {
                    existingColombofilo.setCategoria(colombofilo.getCategoria());
                }
                if (colombofilo.getTelefono() != null) {
                    existingColombofilo.setTelefono(colombofilo.getTelefono());
                }
                if (colombofilo.getZona() != null) {
                    existingColombofilo.setZona(colombofilo.getZona());
                }

                return existingColombofilo;
            })
            .map(colombofiloRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Colombofilo> findAll() {
        log.debug("Request to get all Colombofilos");
        return colombofiloRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Colombofilo> findOne(Long id) {
        log.debug("Request to get Colombofilo : {}", id);
        return colombofiloRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Colombofilo : {}", id);
        colombofiloRepository.deleteById(id);
    }

    @Override
    public ResponseDTO<ColombofiloDTO> agregarColombofilo(ColombofiloDTO colombofiloDTO) throws BusinnesException {
        log.info("Request para agregar un colombofilo {}", colombofiloDTO);
        try {
            Sociedad sociedad = sociedadRepository
                .findById(colombofiloDTO.getSociedadId())
                .orElseThrow(() -> new BusinnesException(SYSMensaje.SOCIEDAD_NOEXISTS + colombofiloDTO.getSociedadId()));
            Optional<Colombofilo> colombofilo = colombofiloRepository.findByCi(colombofiloDTO.getCi());

            if (colombofilo.isEmpty()) {
                Colombofilo newColombofilo = colombofiloRepository.save(colombofiloMapper.toEntity(colombofiloDTO));
                return new ResponseDTO<>(SYSMensaje.COLOMBOFILO_INSERT_OK, Status.OK, colombofiloMapper.toDto(newColombofilo));
            }
            return new ResponseDTO<>(SYSMensaje.COLOMBOFILO_CI + colombofiloDTO.getCi(), Status.ERROR, null);
        } catch (BusinnesException e) {
            return new ResponseDTO<>(e.getMessage(), Status.ERROR, null);
        } catch (Exception e) {
            return new ResponseDTO<>(SYSMensaje.DB_CONECTION, Status.NOK, null);
        }
    }

    @Override
    public ResponseDTO<ColombofiloDTO> actualizarColombofilo(ColombofiloDTO colombofiloDTO) throws BusinnesException {
        log.info("Request para actualizar un colombofilo {}", colombofiloDTO);
        try {
            Colombofilo colombofilo = colombofiloRepository
                .findById(colombofiloDTO.getId())
                .orElseThrow(() -> new BusinnesException(SYSMensaje.COLOMBOFILO_NOEXISTS + colombofiloDTO.getId()));
            Sociedad sociedad = sociedadRepository
                .findById(colombofiloDTO.getSociedadId())
                .orElseThrow(() -> new BusinnesException(SYSMensaje.SOCIEDAD_NOEXISTS));
            Colombofilo existeColombofilo = colombofiloRepository.findByCi(colombofiloDTO.getCi()).orElse(colombofilo);

            if (colombofilo.getId().equals(existeColombofilo.getId())) {
                colombofilo.setCi(colombofiloDTO.getCi());
                colombofilo.setSociedad(sociedad);

                if (colombofiloDTO.getCategoria() != null) {
                    colombofilo.setCategoria(colombofiloDTO.getCategoria());
                }
                if (colombofiloDTO.getDireccion() != null) {
                    colombofilo.setDireccion(colombofiloDTO.getDireccion());
                }
                if (colombofiloDTO.getLatitud() != null) {
                    colombofilo.setLatitud(colombofiloDTO.getLatitud());
                }
                if (colombofiloDTO.getLongitud() != null) {
                    colombofilo.setLongitud(colombofiloDTO.getLongitud());
                }
                if (colombofiloDTO.getNombre() != null) {
                    colombofilo.setNombre(colombofiloDTO.getNombre());
                }
                if (colombofiloDTO.getPrimerApellido() != null) {
                    colombofilo.setPrimerApellido(colombofiloDTO.getPrimerApellido());
                }
                if (colombofiloDTO.getSegindoApellido() != null) {
                    colombofilo.setSegindoApellido(colombofiloDTO.getSegindoApellido());
                }
                if (colombofiloDTO.getTelefono() != null) {
                    colombofilo.setTelefono(colombofiloDTO.getTelefono());
                }
                if (colombofiloDTO.getZona() != null) {
                    colombofilo.setZona(colombofiloDTO.getZona());
                }
                return new ResponseDTO<>(SYSMensaje.COLOMBOFILO_UPDATE_OK, Status.OK, colombofiloMapper.toDto(colombofilo));
            }
            return new ResponseDTO<>(SYSMensaje.COLOMBOFILO_CI + colombofiloDTO.getCi(), Status.ERROR, null);
        } catch (BusinnesException e) {
            return new ResponseDTO<>(e.getMessage(), Status.ERROR, null);
        } catch (Exception e) {
            return new ResponseDTO<>(SYSMensaje.DB_CONECTION, Status.NOK, null);
        }
    }

    @Override
    public ResponseDTO<ColombofiloDTO> obtenerColombofilo(Long id) throws BusinnesException {
        log.info("Request para obtener un colombofilo por su id: {}", id);
        try {
            Colombofilo colombofilo = colombofiloRepository
                .findById(id)
                .orElseThrow(() -> new BusinnesException(SYSMensaje.COLOMBOFILO_NOEXISTS + id));
            return new ResponseDTO<>(SYSMensaje.COLOMBOFILO_EXISTS, Status.OK, colombofiloMapper.toDto(colombofilo));
        } catch (BusinnesException e) {
            return new ResponseDTO<>(e.getMessage(), Status.ERROR, null);
        } catch (Exception e) {
            return new ResponseDTO<>(SYSMensaje.DB_CONECTION, Status.NOK, null);
        }
    }

    @Override
    public ResponseDTO<List<ColombofiloDTO>> obtenerColombofilos(Long sociedadId) throws BusinnesException {
        log.info("Request para obtener los colombofilos de la sociedad con id: {}", sociedadId);
        try {
            Sociedad sociedad = sociedadRepository
                .findById(sociedadId)
                .orElseThrow(() -> new BusinnesException(SYSMensaje.SOCIEDAD_NOEXISTS + sociedadId));
            if (!sociedad.getParaderos().isEmpty()) {
                return new ResponseDTO<>(
                    SYSMensaje.COLOMBOFILO_LIST + sociedadId,
                    Status.OK,
                    colombofiloMapper.toDto(sociedad.getColombofilos().stream().toList())
                );
            }
            return new ResponseDTO<>(SYSMensaje.COLOMBOFILO_NOLIST + sociedadId, Status.NOK, null);
        } catch (BusinnesException e) {
            return new ResponseDTO<>(e.getMessage(), Status.ERROR, null);
        } catch (Exception e) {
            return new ResponseDTO<>(SYSMensaje.DB_CONECTION, Status.NOK, null);
        }
    }
}
