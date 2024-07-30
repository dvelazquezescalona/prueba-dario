package cu.fcc.pigeon.service.impl;

import cu.fcc.pigeon.Exception.BusinnesException;
import cu.fcc.pigeon.config.SYSMensaje;
import cu.fcc.pigeon.domain.*;
import cu.fcc.pigeon.domain.common.Calculo;
import cu.fcc.pigeon.repository.ColombofiloRepository;
import cu.fcc.pigeon.repository.ColombofiloVueloRepository;
import cu.fcc.pigeon.repository.VueloRepository;
import cu.fcc.pigeon.service.ColombofiloVueloService;
import cu.fcc.pigeon.service.dto.ColombofiloVueloDTO;
import cu.fcc.pigeon.service.dto.ResponseDTO;
import cu.fcc.pigeon.service.dto.enumeration.Status;
import cu.fcc.pigeon.service.mapper.ColombofiloVueloMapper;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link cu.fcc.pigeon.domain.ColombofiloVuelo}.
 */
@Service
@Transactional
public class ColombofiloVueloServiceImpl implements ColombofiloVueloService {

    private final Logger log = LoggerFactory.getLogger(ColombofiloVueloServiceImpl.class);

    private final ColombofiloVueloRepository colombofiloVueloRepository;

    private final VueloRepository vueloRepository;

    private final ColombofiloRepository colombofiloRepository;

    private final ColombofiloVueloMapper colombofiloVueloMapper;

    public ColombofiloVueloServiceImpl(
        ColombofiloVueloRepository colombofiloVueloRepository,
        VueloRepository vueloRepository,
        ColombofiloRepository colombofiloRepository,
        ColombofiloVueloMapper colombofiloVueloMapper
    ) {
        this.colombofiloVueloRepository = colombofiloVueloRepository;
        this.vueloRepository = vueloRepository;
        this.colombofiloRepository = colombofiloRepository;
        this.colombofiloVueloMapper = colombofiloVueloMapper;
    }

    @Override
    public ColombofiloVuelo save(ColombofiloVuelo colombofiloVuelo) {
        log.debug("Request to save ColombofiloVuelo : {}", colombofiloVuelo);

        /*double latitudColombofilo = colombofiloVuelo.getColombofilo().getLatitud();
        double longitudColombofilo = colombofiloVuelo.getColombofilo().getLongitud();

        double latitudVuelo = colombofiloVuelo.getVuelo().getParadero().getLatitud();
        double longitudVuelo = colombofiloVuelo.getVuelo().getParadero().getLongitud();

        colombofiloVuelo.setDistancia(Calculo.calcularDistancia(latitudColombofilo, longitudColombofilo, latitudVuelo, longitudVuelo));*/

        return colombofiloVueloRepository.save(colombofiloVuelo);
    }

    @Override
    public ColombofiloVuelo update(ColombofiloVuelo colombofiloVuelo) {
        log.debug("Request to update ColombofiloVuelo : {}", colombofiloVuelo);
        return colombofiloVueloRepository.save(colombofiloVuelo);
    }

    @Override
    public Optional<ColombofiloVuelo> partialUpdate(ColombofiloVuelo colombofiloVuelo) {
        log.debug("Request to partially update ColombofiloVuelo : {}", colombofiloVuelo);

        return colombofiloVueloRepository
            .findById(colombofiloVuelo.getId())
            .map(existingColombofiloVuelo -> {
                if (colombofiloVuelo.getEnviadas() != null) {
                    existingColombofiloVuelo.setEnviadas(colombofiloVuelo.getEnviadas());
                }
                if (colombofiloVuelo.getDistancia() != null) {
                    existingColombofiloVuelo.setDistancia(colombofiloVuelo.getDistancia());
                }

                return existingColombofiloVuelo;
            })
            .map(colombofiloVueloRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ColombofiloVuelo> findAll() {
        log.debug("Request to get all ColombofiloVuelos");
        return colombofiloVueloRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ColombofiloVuelo> findOne(Long id) {
        log.debug("Request to get ColombofiloVuelo : {}", id);
        return colombofiloVueloRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ColombofiloVuelo : {}", id);
        colombofiloVueloRepository.deleteById(id);
    }

    @Override
    public ResponseDTO<ColombofiloVueloDTO> agregarColombofiloVuelo(ColombofiloVueloDTO colombofiloVueloDTO) {
        log.info("Request para agregar un ColombofiloVuelo {}", colombofiloVueloDTO);
        try {
            Vuelo vuelo = vueloRepository
                .findById(colombofiloVueloDTO.getVueloId())
                .orElseThrow(() -> new BusinnesException(SYSMensaje.VUELO_NOEXISTS + colombofiloVueloDTO.getVueloId()));
            Colombofilo colombofilo = colombofiloRepository
                .findById(colombofiloVueloDTO.getColombofiloId())
                .orElseThrow(() -> new BusinnesException(SYSMensaje.COLOMBOFILO_NOEXISTS + colombofiloVueloDTO.getColombofiloId()));

            Optional<ColombofiloVuelo> colombofiloVuelo = colombofiloVueloRepository.findByVueloAndColombofilo(vuelo, colombofilo);

            if (colombofiloVuelo.isEmpty()) {
                /*Double dist = Calculo.calcularDistancia(
                    vuelo.getParadero().getLatitud(),
                    vuelo.getParadero().getLongitud(),
                    colombofilo.getLatitud(),
                    colombofilo.getLongitud()
                );
                colombofiloVueloDTO.setDistancia(dist);*/
                ColombofiloVuelo newColombofiloVuelo = colombofiloVueloRepository.save(
                    colombofiloVueloMapper.toEntity(colombofiloVueloDTO)
                );
                return new ResponseDTO<>(
                    SYSMensaje.VUELO_COLOMBOFILOS_INSERT,
                    Status.OK,
                    colombofiloVueloMapper.toDto(newColombofiloVuelo)
                );
            }
            return new ResponseDTO<>(SYSMensaje.VUELO_COLOMBOFILOS_EXISTS + colombofiloVueloDTO.getVueloId(), Status.ERROR, null);
        } catch (BusinnesException e) {
            return new ResponseDTO<>(e.getMessage(), Status.ERROR, null);
        } catch (Exception e) {
            return new ResponseDTO<>(SYSMensaje.DB_CONECTION, Status.NOK, null);
        }
    }

    @Override
    public ResponseDTO<ColombofiloVueloDTO> actualizarColombofiloVuelo(ColombofiloVueloDTO colombofiloVueloDTO) {
        log.info("Request para actualizar un ColombofiloVuelo {}", colombofiloVueloDTO);
        try {
            ColombofiloVuelo colombofiloVuelo = colombofiloVueloRepository
                .findById(colombofiloVueloDTO.getId())
                .orElseThrow(() -> new BusinnesException(SYSMensaje.VUELO_COLOMBOFILOS_NOEXISTS + colombofiloVueloDTO.getId()));
            Vuelo vuelo = vueloRepository
                .findById(colombofiloVueloDTO.getVueloId())
                .orElseThrow(() -> new BusinnesException(SYSMensaje.VUELO_NOEXISTS + colombofiloVueloDTO.getVueloId()));
            Colombofilo colombofilo = colombofiloRepository
                .findById(colombofiloVueloDTO.getColombofiloId())
                .orElseThrow(() -> new BusinnesException(SYSMensaje.COLOMBOFILO_NOEXISTS + colombofiloVueloDTO.getColombofiloId()));

            ColombofiloVuelo existeColombofiloVuelo = colombofiloVueloRepository
                .findByVueloAndColombofilo(vuelo, colombofilo)
                .orElse(colombofiloVuelo);

            if (
                !(colombofiloVueloDTO.getAperturaOficial() != null &&
                    colombofiloVueloDTO.getCierreOficial() != null &&
                    colombofiloVueloDTO.getCierre() != null)
            ) {
                return new ResponseDTO<>(SYSMensaje.VUELO_COLOMBOFILOS_FECHAS, Status.ERROR, null);
            }

            if (
                colombofiloVueloDTO.getAperturaOficial().compareTo(colombofiloVueloDTO.getCierreOficial()) >= 0 ||
                colombofiloVueloDTO.getAperturaOficial().compareTo(colombofiloVueloDTO.getCierre()) >= 0
            ) {
                return new ResponseDTO<>(SYSMensaje.VUELO_COLOMBOFILOS_APERTURA, Status.ERROR, null);
            }

            if (colombofiloVuelo.getId().equals(existeColombofiloVuelo.getId())) {
                colombofiloVuelo.setVuelo(vuelo);
                colombofiloVuelo.setColombofilo(colombofilo);
                colombofiloVuelo.setCierre(colombofiloVueloDTO.getCierre());
                colombofiloVuelo.setAperturaOficial(colombofiloVueloDTO.getAperturaOficial());
                colombofiloVuelo.setCierreOficial(colombofiloVueloDTO.getCierreOficial());

                Double dist = Calculo.calcularDistancia(
                    vuelo.getParadero().getLatitud(),
                    vuelo.getParadero().getLongitud(),
                    colombofilo.getLatitud(),
                    colombofilo.getLongitud()
                );
                colombofiloVueloDTO.setDistancia(dist);

                if (colombofiloVueloDTO.getEnviadas() != null) {
                    colombofiloVuelo.setEnviadas(colombofiloVueloDTO.getEnviadas());
                }

                return new ResponseDTO<>(SYSMensaje.VUELO_COLOMBOFILOS_UPDATE, Status.OK, colombofiloVueloMapper.toDto(colombofiloVuelo));
            }
            return new ResponseDTO<>(SYSMensaje.VUELO_COLOMBOFILOS_EXISTS + colombofiloVueloDTO.getVueloId(), Status.ERROR, null);
        } catch (BusinnesException e) {
            return new ResponseDTO<>(e.getMessage(), Status.ERROR, null);
        } catch (Exception e) {
            return new ResponseDTO<>(SYSMensaje.DB_CONECTION, Status.NOK, null);
        }
    }

    @Override
    public ResponseDTO<ColombofiloVueloDTO> obtenerColombofiloVuelo(Long id) {
        log.info("Request para obtener un ColombofiloVuelo por su id: {}", id);
        try {
            ColombofiloVuelo colombofiloVuelo = colombofiloVueloRepository
                .findById(id)
                .orElseThrow(() -> new BusinnesException(SYSMensaje.VUELO_COLOMBOFILOS_NOEXISTS + id));
            return new ResponseDTO<>(SYSMensaje.COLOMBOFILOS_VUELO_EXISTS, Status.OK, colombofiloVueloMapper.toDto(colombofiloVuelo));
        } catch (BusinnesException e) {
            return new ResponseDTO<>(e.getMessage(), Status.ERROR, null);
        } catch (Exception e) {
            return new ResponseDTO<>(SYSMensaje.DB_CONECTION, Status.NOK, null);
        }
    }

    @Override
    public ResponseDTO<ColombofiloVueloDTO> obtenerColombofiloVuelo(Long vueloId, Long colombofiloId) {
        log.info("Request para obtener un ColombofiloVuelo por el VueloId {} y ColombofiloId {}", vueloId, colombofiloId);
        try {
            Vuelo vuelo = vueloRepository.findById(vueloId).orElseThrow(() -> new BusinnesException(SYSMensaje.VUELO_NOEXISTS + vueloId));
            Colombofilo colombofilo = colombofiloRepository
                .findById(colombofiloId)
                .orElseThrow(() -> new BusinnesException(SYSMensaje.COLOMBOFILO_NOEXISTS + colombofiloId));

            ColombofiloVuelo colombofiloVuelo = colombofiloVueloRepository
                .findByVueloAndColombofilo(vuelo, colombofilo)
                .orElseThrow(() -> new BusinnesException(SYSMensaje.VUELO_COLOMBOFILOS_NOEXISTS));

            return new ResponseDTO<>(SYSMensaje.COLOMBOFILOS_VUELO_EXISTS, Status.OK, colombofiloVueloMapper.toDto(colombofiloVuelo));
        } catch (BusinnesException e) {
            return new ResponseDTO<>(e.getMessage(), Status.ERROR, null);
        } catch (Exception e) {
            return new ResponseDTO<>(SYSMensaje.DB_CONECTION, Status.NOK, null);
        }
    }
}
