package cu.fcc.pigeon.service.impl;

import cu.fcc.pigeon.Exception.BusinnesException;
import cu.fcc.pigeon.config.SYSMensaje;
import cu.fcc.pigeon.domain.Municipio;
import cu.fcc.pigeon.domain.Paradero;
import cu.fcc.pigeon.domain.Sociedad;
import cu.fcc.pigeon.repository.MunicipioRepository;
import cu.fcc.pigeon.repository.SociedadRepository;
import cu.fcc.pigeon.service.SociedadService;
import cu.fcc.pigeon.service.dto.ParaderoDTO;
import cu.fcc.pigeon.service.dto.ResponseDTO;
import cu.fcc.pigeon.service.dto.SociedadDTO;
import cu.fcc.pigeon.service.dto.enumeration.Status;
import cu.fcc.pigeon.service.mapper.SociedadMapper;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link cu.fcc.pigeon.domain.Sociedad}.
 */
@Service
@Transactional
public class SociedadServiceImpl implements SociedadService {

    private final Logger log = LoggerFactory.getLogger(SociedadServiceImpl.class);

    private final SociedadRepository sociedadRepository;

    private final MunicipioRepository municipioRepository;

    private final SociedadMapper sociedadMapper;

    public SociedadServiceImpl(
        SociedadRepository sociedadRepository,
        MunicipioRepository municipioRepository,
        SociedadMapper sociedadMapper
    ) {
        this.sociedadRepository = sociedadRepository;
        this.municipioRepository = municipioRepository;
        this.sociedadMapper = sociedadMapper;
    }

    @Override
    public Sociedad save(Sociedad sociedad) {
        log.debug("Request to save Sociedad : {}", sociedad);
        return sociedadRepository.save(sociedad);
    }

    @Override
    public Sociedad update(Sociedad sociedad) {
        log.debug("Request to update Sociedad : {}", sociedad);
        return sociedadRepository.save(sociedad);
    }

    @Override
    public Optional<Sociedad> partialUpdate(Sociedad sociedad) {
        log.debug("Request to partially update Sociedad : {}", sociedad);

        return sociedadRepository
            .findById(sociedad.getId())
            .map(existingSociedad -> {
                if (sociedad.getNombreSociedad() != null) {
                    existingSociedad.setNombreSociedad(sociedad.getNombreSociedad());
                }
                if (sociedad.getLatitud() != null) {
                    existingSociedad.setLatitud(sociedad.getLatitud());
                }
                if (sociedad.getLongitud() != null) {
                    existingSociedad.setLongitud(sociedad.getLongitud());
                }

                return existingSociedad;
            })
            .map(sociedadRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Sociedad> findAll() {
        log.debug("Request to get all Sociedads");
        return sociedadRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Sociedad> findOne(Long id) {
        log.debug("Request to get Sociedad : {}", id);
        return sociedadRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Sociedad : {}", id);
        sociedadRepository.deleteById(id);
    }

    @Override
    public ResponseDTO<SociedadDTO> agregarSociedad(SociedadDTO sociedadDTO) throws BusinnesException {
        log.info("Request para agregar una sociedad {}", sociedadDTO);
        try {
            Municipio municipio = municipioRepository
                .findById(sociedadDTO.getMunicipioId())
                .orElseThrow(() -> new BusinnesException(SYSMensaje.SOCIEDAD_NOMUNICIPIO + sociedadDTO.getMunicipioId()));

            Optional<Sociedad> sociedad = sociedadRepository.findByNombreSociedadAndMunicipio(sociedadDTO.getNombreSociedad(), municipio);
            if (sociedad.isPresent()) {
                return new ResponseDTO<>(SYSMensaje.SOCIEDAD_NOMBRE + sociedadDTO.getNombreSociedad(), Status.ERROR, null);
            }
            Sociedad newSociedad = sociedadRepository.save(sociedadMapper.toEntity(sociedadDTO));
            return new ResponseDTO<>(SYSMensaje.SOCIEDAD_INSERT_OK, Status.OK, sociedadMapper.toDto(newSociedad));
        } catch (BusinnesException e) {
            return new ResponseDTO<>(e.getMessage(), Status.ERROR, null);
        } catch (Exception e) {
            return new ResponseDTO<>(SYSMensaje.DB_CONECTION, Status.NOK, null);
        }
    }

    @Override
    public ResponseDTO<SociedadDTO> actualizarSociedad(SociedadDTO sociedadDTO) throws BusinnesException {
        try {
            Sociedad sociedad = sociedadRepository
                .findById(sociedadDTO.getId())
                .orElseThrow(() -> new BusinnesException(SYSMensaje.SOCIEDAD_NOEXISTS + sociedadDTO.getId()));
            Municipio municipio = municipioRepository
                .findById(sociedadDTO.getMunicipioId())
                .orElseThrow(() -> new BusinnesException(SYSMensaje.SOCIEDAD_NOMUNICIPIO + sociedadDTO.getMunicipioId()));

            Sociedad existeSociedad = sociedadRepository
                .findByNombreSociedadAndMunicipio(sociedadDTO.getNombreSociedad(), municipio)
                .orElse(sociedad);

            if (sociedad.getId().equals(existeSociedad.getId())) {
                sociedad.setMunicipio(municipio);
                if (sociedadDTO.getNombreSociedad() != null) {
                    sociedad.setNombreSociedad(sociedadDTO.getNombreSociedad());
                }
                if (sociedadDTO.getLatitud() != null) {
                    sociedad.setLatitud(sociedadDTO.getLatitud());
                }
                if (sociedadDTO.getLongitud() != null) {
                    sociedad.setLongitud(sociedadDTO.getLongitud());
                }
                return new ResponseDTO<>(SYSMensaje.SOCIEDAD_UPDATE_OK, Status.OK, sociedadMapper.toDto(sociedad));
            }
            return new ResponseDTO<>(SYSMensaje.SOCIEDAD_NOMBRE + sociedadDTO.getNombreSociedad(), Status.ERROR, null);
        } catch (BusinnesException e) {
            return new ResponseDTO<>(e.getMessage(), Status.ERROR, null);
        } catch (Exception e) {
            return new ResponseDTO<>(SYSMensaje.DB_CONECTION, Status.NOK, null);
        }
    }

    @Override
    public ResponseDTO<SociedadDTO> obtenerSociedad(Long id) throws BusinnesException {
        try {
            Sociedad sociedad = sociedadRepository.findById(id).orElseThrow(() -> new BusinnesException(SYSMensaje.SOCIEDAD_NOEXISTS + id));
            return new ResponseDTO<>(SYSMensaje.SOCIEDAD_EXISTS, Status.OK, sociedadMapper.toDto(sociedad));
        } catch (BusinnesException e) {
            return new ResponseDTO<>(e.getMessage(), Status.ERROR, null);
        } catch (Exception e) {
            return new ResponseDTO<>(SYSMensaje.DB_CONECTION, Status.NOK, null);
        }
    }

    @Override
    public ResponseDTO<List<SociedadDTO>> obtenerSociedades(Long municipioId) throws BusinnesException {
        try {
            Municipio municipio = municipioRepository
                .findById(municipioId)
                .orElseThrow(() -> new BusinnesException(SYSMensaje.SOCIEDAD_NOMUNICIPIO + municipioId));
            if (!municipio.getSociedades().isEmpty()) {
                return new ResponseDTO<>(
                    SYSMensaje.SOCIEDAD_LIST + municipioId,
                    Status.OK,
                    sociedadMapper.toDto(municipio.getSociedades().stream().toList())
                );
            }
            return new ResponseDTO<>(SYSMensaje.SOCIEDAD_NOLIST + municipioId, Status.NOK, null);
        } catch (BusinnesException e) {
            return new ResponseDTO<>(e.getMessage(), Status.ERROR, null);
        } catch (Exception e) {
            return new ResponseDTO<>(SYSMensaje.DB_CONECTION, Status.NOK, null);
        }
    }
}
