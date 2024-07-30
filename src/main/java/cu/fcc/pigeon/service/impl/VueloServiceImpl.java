package cu.fcc.pigeon.service.impl;

import cu.fcc.pigeon.Exception.BusinnesException;
import cu.fcc.pigeon.config.Constants;
import cu.fcc.pigeon.config.SYSMensaje;
import cu.fcc.pigeon.domain.*;
import cu.fcc.pigeon.domain.common.Enviadas;
import cu.fcc.pigeon.domain.common.ScoreSystems;
import cu.fcc.pigeon.repository.ColombofiloVueloRepository;
import cu.fcc.pigeon.repository.ParaderoRepository;
import cu.fcc.pigeon.repository.PremioRepository;
import cu.fcc.pigeon.repository.VueloRepository;
import cu.fcc.pigeon.service.VueloService;
import cu.fcc.pigeon.service.dto.ColombofiloVueloDTO;
import cu.fcc.pigeon.service.dto.ResponseDTO;
import cu.fcc.pigeon.service.dto.VueloDTO;
import cu.fcc.pigeon.service.dto.enumeration.Status;
import cu.fcc.pigeon.service.mapper.VueloMapper;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

/**
 * Service Implementation for managing {@link cu.fcc.pigeon.domain.Vuelo}.
 */
@Service
@Transactional
public class VueloServiceImpl implements VueloService {

    private final Logger log = LoggerFactory.getLogger(VueloServiceImpl.class);

    private final VueloRepository vueloRepository;

    private final PremioRepository premioRepository;

    private final ParaderoRepository paraderoRepository;
    private final ColombofiloVueloRepository colombofiloVueloRepository;

    private final VueloMapper vueloMapper;

    public VueloServiceImpl(
        VueloRepository vueloRepository,
        PremioRepository premioRepository,
        ParaderoRepository paraderoRepository,
        VueloMapper vueloMapper,
        ColombofiloVueloRepository colombofiloVueloRepository
    ) {
        this.vueloRepository = vueloRepository;
        this.premioRepository = premioRepository;
        this.paraderoRepository = paraderoRepository;
        this.vueloMapper = vueloMapper;
        this.colombofiloVueloRepository = colombofiloVueloRepository;
    }

    @Override
    public Vuelo save(Vuelo vuelo) {
        log.debug("Request to save Vuelo : {}", vuelo);
        return vueloRepository.save(vuelo);
    }

    @Override
    public Vuelo update(Vuelo vuelo) {
        log.debug("Request to update Vuelo : {}", vuelo);
        return vueloRepository.save(vuelo);
    }

    @Override
    public Optional<Vuelo> partialUpdate(Vuelo vuelo) {
        log.debug("Request to partially update Vuelo : {}", vuelo);

        return vueloRepository
            .findById(vuelo.getId())
            .map(existingVuelo -> {
                if (vuelo.getFecha() != null) {
                    existingVuelo.setFecha(vuelo.getFecha());
                }
                if (vuelo.getDescripcion() != null) {
                    existingVuelo.setDescripcion(vuelo.getDescripcion());
                }
                if (vuelo.getCompetencia() != null) {
                    existingVuelo.setCompetencia(vuelo.getCompetencia());
                }
                if (vuelo.getCampeonato() != null) {
                    existingVuelo.setCampeonato(vuelo.getCampeonato());
                }

                return existingVuelo;
            })
            .map(vueloRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Vuelo> findAll() {
        log.debug("Request to get all Vuelos");
        return vueloRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Vuelo> findOne(Long id) {
        log.debug("Request to get Vuelo : {}", id);
        return vueloRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Vuelo : {}", id);
        vueloRepository.deleteById(id);
    }

    public List<Instant> cierres(Instant fecha, Long vueloid) {
        String parametro = fecha.atZone(ZoneId.of("UTC")).toLocalDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Optional<Object> horasMaximas = premioRepository.findHorasMasAltas(parametro, vueloid);
        List<Instant> horasCierre = new ArrayList<>();
        Instant cierre = fecha
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
            .atTime(LocalTime.of(21, 0))
            .atZone(ZoneId.of("UTC"))
            .toInstant();

        horasMaximas.ifPresent(result -> {
            if (result.getClass().isArray()) {
                AtomicInteger i = new AtomicInteger(0);
                for (Object valor : ((Object[]) result)) {
                    boolean condicion =
                        (!ObjectUtils.isEmpty(valor) && valor instanceof Timestamp) &&
                        (((Timestamp) valor).toInstant().compareTo(cierre.plus(Duration.ofDays(i.get()))) > 0);
                    horasCierre.add(condicion ? ((Timestamp) valor).toInstant() : cierre.plus(Duration.ofDays(i.getAndIncrement())));
                }
            }
        });
        return horasCierre;
    }

    @Override
    public ResponseDTO<Long> calcularVelocidad(Long id) {
        log.info("Calculando las velociddes del vuelo con id : {}", id);
        try {
            Vuelo vuelo = vueloRepository.findById(id).orElseThrow(() -> new BusinnesException(SYSMensaje.VUELO_NOEXISTS + id));
            Set<ColombofiloVuelo> colombofiloVuelos = vuelo.getColombofiloVuelos();
            if (!colombofiloVuelos.isEmpty()) {
                Instant fecha = vuelo.getFecha();
                List<Instant> horasCierre = cierres(fecha, vuelo.getId());
                colombofiloVuelos.forEach(colombofiloVuelo -> {
                    List<Premio> premiosColmb = premioRepository.obtenerPremiosByVueloAndColombofilo(
                        vuelo.getId(),
                        colombofiloVuelo.getColombofilo().getId()
                    );
                    premiosColmb.forEach(colmbPremio -> {
                        Instant fechaArribo = colmbPremio.getFechaArribo();
                        double timeMilis = (double) (Duration.between(fecha, fechaArribo).toMillis());
                        double dif = (double) Duration
                            .between(colombofiloVuelo.getCierre(), colombofiloVuelo.getCierreOficial())
                            .toMillis();
                        double tiempoArribo = (double) Duration.between(colombofiloVuelo.getAperturaOficial(), fechaArribo).toMillis();
                        double tiempoTotal = (double) Duration
                            .between(colombofiloVuelo.getAperturaOficial(), colombofiloVuelo.getCierreOficial())
                            .toMillis();
                        double plus = ((dif * tiempoArribo) / tiempoTotal) / Constants.CONST_VELOCIDAD;
                        AtomicReference<Double> tiempo = new AtomicReference<>(timeMilis / Constants.CONST_VELOCIDAD);

                        Instant aperutaDay = fecha
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()
                            .atTime(LocalTime.of(6, 0))
                            .atZone(ZoneId.of("UTC"))
                            .toInstant();

                        AtomicInteger i = new AtomicInteger(1);

                        for (Instant cierreDay : horasCierre) {
                            if (cierreDay.compareTo(fechaArribo) < 0) {
                                double durationMilis = (double) Duration
                                    .between(cierreDay, aperutaDay.plus(Duration.ofDays(i.getAndIncrement())))
                                    .toMillis();
                                tiempo.set(tiempo.get() - (durationMilis / Constants.CONST_VELOCIDAD));
                            } else {
                                break;
                            }
                        }

                        colmbPremio.setTiempoVuelo(tiempo.get() + plus);
                        colmbPremio.setVelocidad((colombofiloVuelo.getDistancia() * Constants.KM_A_METROS) / colmbPremio.getTiempoVuelo());
                        colmbPremio.setPlus(plus);
                        colmbPremio.setPuntos(0D);
                        colmbPremio.setLugar(0);
                    });
                });

                return new ResponseDTO<>(SYSMensaje.VUELO_CALCULATE_VELOCIDAD, Status.OK, id);
            }
            return new ResponseDTO<>(SYSMensaje.VUELO_NOCOLOMBOFILOS, Status.NOK, id);
        } catch (BusinnesException e) {
            return new ResponseDTO<>(e.getMessage(), Status.ERROR, id);
        } catch (Exception e) {
            return new ResponseDTO<>(SYSMensaje.DB_CONECTION, Status.NOK, id);
        }
    }

    @Override
    public ResponseDTO<Long> calcularPuntos(Long id) {
        log.info("Calculando la puntacion del vuelo con id : {}", id);
        try {
            Vuelo vuelo = vueloRepository.findById(id).orElseThrow(() -> new BusinnesException(SYSMensaje.VUELO_NOEXISTS + id));
            Enviadas obj = colombofiloVueloRepository
                .obtenerEnviadasByVuelo(id)
                .orElseThrow(() -> new BusinnesException(SYSMensaje.VUELO_NOEXISTS_ENVIADAS + id));
            Integer enviadas = obj.getEnviadas();

            Integer cantPremios = enviadas > 7 ? Math.round(enviadas.floatValue() * 0.2F) : 1;
            List<Premio> premios = premioRepository.obtenerPremiosByVueloSortedByVelocidad(id, cantPremios);

            AtomicInteger lugar = new AtomicInteger(1);
            double velocMax = premios.get(0).getVelocidad();
            double velocMin = premios.get(premios.size() - 1).getVelocidad();
            ScoreSystems scoreSystems = new ScoreSystems(
                vuelo.getPuntuacionSystem(),
                cantPremios,
                vuelo.getPuntuacionMax(),
                vuelo.getPuntuacionMin(),
                velocMax,
                velocMin
            );
            premios.forEach(premio -> {
                premio.setPuntos(scoreSystems.score(lugar.get(), premio.getVelocidad()));
                premio.setLugar(lugar.getAndIncrement());
            });

            return new ResponseDTO<>(SYSMensaje.VUELO_CALCULATE_PUNTOS, Status.OK, id);
        } catch (BusinnesException e) {
            return new ResponseDTO<>(e.getMessage(), Status.ERROR, id);
        } catch (Exception e) {
            return new ResponseDTO<>(SYSMensaje.DB_CONECTION, Status.NOK, id);
        }
    }

    @Override
    public ResponseDTO<ColombofiloVueloDTO> agregarColombofilo(ColombofiloVueloDTO colombofiloVueloDTO) {
        log.info("Request para agregar un colombofilo al vuelo {}", colombofiloVueloDTO.getVueloId());
        try {
            Vuelo vuelo = vueloRepository
                .findById(colombofiloVueloDTO.getVueloId())
                .orElseThrow(() -> new BusinnesException(SYSMensaje.VUELO_NOEXISTS + colombofiloVueloDTO.getVueloId()));

            return null;
        } catch (BusinnesException e) {
            return new ResponseDTO<>(e.getMessage(), Status.ERROR, null);
        } catch (Exception e) {
            return new ResponseDTO<>(SYSMensaje.DB_CONECTION, Status.NOK, null);
        }
    }

    @Override
    public ResponseDTO<VueloDTO> agregarVuelo(VueloDTO vueloDTO) throws BusinnesException {
        log.info("Request para agregar un vuelo {}", vueloDTO);
        try {
            Paradero paradero = paraderoRepository
                .findById(vueloDTO.getParaderoId())
                .orElseThrow(() -> new BusinnesException(SYSMensaje.PARADERO_NOEXISTS + vueloDTO.getParaderoId()));

            Vuelo vuelo = vueloRepository.save(vueloMapper.toEntity(vueloDTO));
            return new ResponseDTO<>(SYSMensaje.VUELO_INSERT_OK, Status.OK, vueloMapper.toDto(vuelo));
        } catch (BusinnesException e) {
            return new ResponseDTO<>(e.getMessage(), Status.ERROR, null);
        } catch (Exception e) {
            return new ResponseDTO<>(SYSMensaje.DB_CONECTION, Status.NOK, null);
        }
    }

    @Override
    public ResponseDTO<VueloDTO> actualizarVuelo(VueloDTO vueloDTO) throws BusinnesException {
        log.info("Request para actualizar un vuelo {}", vueloDTO);
        try {
            Vuelo vuelo = vueloRepository
                .findById(vueloDTO.getId())
                .orElseThrow(() -> new BusinnesException(SYSMensaje.VUELO_NOEXISTS + vueloDTO.getId()));

            Paradero paradero = paraderoRepository
                .findById(vueloDTO.getParaderoId())
                .orElseThrow(() -> new BusinnesException(SYSMensaje.PARADERO_NOEXISTS + vueloDTO.getParaderoId()));

            vuelo.setParadero(paradero);

            if (vueloDTO.getFecha() != null) {
                vuelo.setFecha(vueloDTO.getFecha());
            }
            if (vueloDTO.getCampeonato() != null) {
                vuelo.setCampeonato(vueloDTO.getCampeonato());
            }
            if (vueloDTO.getCompetencia() != null) {
                vuelo.setCompetencia(vueloDTO.getCompetencia());
            }
            if (vueloDTO.getDescripcion() != null) {
                vuelo.setDescripcion(vueloDTO.getDescripcion());
            }
            if (vueloDTO.getPuntuacionMax() != null) {
                vuelo.setPuntuacionMax(vueloDTO.getPuntuacionMax());
            }
            if (vueloDTO.getPuntuacionMin() != null) {
                vuelo.setPuntuacionMin(vueloDTO.getPuntuacionMin());
            }
            if (vueloDTO.getPuntuacionSystem() != null) {
                vuelo.setPuntuacionSystem(vueloDTO.getPuntuacionSystem());
            }
            return new ResponseDTO<>(SYSMensaje.VUELO_UPDATE_OK, Status.OK, vueloMapper.toDto(vuelo));
        } catch (BusinnesException e) {
            return new ResponseDTO<>(e.getMessage(), Status.ERROR, null);
        } catch (Exception e) {
            return new ResponseDTO<>(SYSMensaje.DB_CONECTION, Status.NOK, null);
        }
    }

    @Override
    public ResponseDTO<VueloDTO> obtenerVuelo(Long id) throws BusinnesException {
        log.info("Request para obtener un vuelo por su id: {}", id);
        try {
            Vuelo vuelo = vueloRepository.findById(id).orElseThrow(() -> new BusinnesException(SYSMensaje.VUELO_NOEXISTS + id));
            return new ResponseDTO<>(SYSMensaje.VUELO_EXISTS, Status.OK, vueloMapper.toDto(vuelo));
        } catch (BusinnesException e) {
            return new ResponseDTO<>(e.getMessage(), Status.ERROR, null);
        } catch (Exception e) {
            return new ResponseDTO<>(SYSMensaje.DB_CONECTION, Status.NOK, null);
        }
    }

    @Override
    public ResponseDTO<List<VueloDTO>> obtenerVuelos(Long paraderoId) throws BusinnesException {
        log.info("Request para obtener los vuelos del paradero con id: {}", paraderoId);
        try {
            Paradero paradero = paraderoRepository
                .findById(paraderoId)
                .orElseThrow(() -> new BusinnesException(SYSMensaje.PARADERO_NOEXISTS + paraderoId));
            if (!paradero.getVuelos().isEmpty()) {
                return new ResponseDTO<>(
                    SYSMensaje.VUELO_LIST + paraderoId,
                    Status.OK,
                    vueloMapper.toDto(paradero.getVuelos().stream().toList())
                );
            }
            return new ResponseDTO<>(SYSMensaje.VUELO_NOLIST + paraderoId, Status.NOK, null);
        } catch (BusinnesException e) {
            return new ResponseDTO<>(e.getMessage(), Status.ERROR, null);
        } catch (Exception e) {
            return new ResponseDTO<>(SYSMensaje.DB_CONECTION, Status.NOK, null);
        }
    }
}
