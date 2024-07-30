package cu.fcc.pigeon.service.impl;

import cu.fcc.pigeon.Exception.BusinnesException;
import cu.fcc.pigeon.config.SYSMensaje;
import cu.fcc.pigeon.domain.*;
import cu.fcc.pigeon.repository.PalomaRepository;
import cu.fcc.pigeon.repository.PremioRepository;
import cu.fcc.pigeon.repository.VueloRepository;
import cu.fcc.pigeon.service.PremioService;
import cu.fcc.pigeon.service.dto.CabeceraReporteVueloDTO;
import cu.fcc.pigeon.service.dto.PremioDTO;
import cu.fcc.pigeon.service.dto.ReporteVueloDTO;
import cu.fcc.pigeon.service.dto.ResponseDTO;
import cu.fcc.pigeon.service.dto.enumeration.Status;
import cu.fcc.pigeon.service.mapper.PremioMapper;
import java.util.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

/**
 * Service Implementation for managing {@link cu.fcc.pigeon.domain.Premio}.
 */
@Service
@Transactional
public class PremioServiceImpl implements PremioService {

    private final Logger log = LoggerFactory.getLogger(PremioServiceImpl.class);

    private final PremioRepository premioRepository;

    private final VueloRepository vueloRepository;

    private final PalomaRepository palomaRepository;

    private final PremioMapper premioMapper;

    @Value("classpath:templates/reports/reporteVuelo.jasper")
    Resource reporteVuelo;

    @Value("classpath:templates/reports/images/logo.jpg")
    Resource logo;

    public PremioServiceImpl(
        PremioRepository premioRepository,
        VueloRepository vueloRepository,
        PalomaRepository palomaRepository,
        PremioMapper premioMapper
    ) {
        this.premioRepository = premioRepository;
        this.vueloRepository = vueloRepository;
        this.palomaRepository = palomaRepository;
        this.premioMapper = premioMapper;
    }

    @Override
    public Premio save(Premio premio) {
        log.debug("Request to save Premio : {}", premio);
        return premioRepository.save(premio);
    }

    @Override
    public Premio update(Premio premio) {
        log.debug("Request to update Premio : {}", premio);
        return premioRepository.save(premio);
    }

    @Override
    public Optional<Premio> partialUpdate(Premio premio) {
        log.debug("Request to partially update Premio : {}", premio);

        return premioRepository
            .findById(premio.getId())
            .map(existingPremio -> {
                if (premio.getDesignada() != null) {
                    existingPremio.setDesignada(premio.getDesignada());
                }
                if (premio.getFechaArribo() != null) {
                    existingPremio.setFechaArribo(premio.getFechaArribo());
                }
                if (premio.getTiempoVuelo() != null) {
                    existingPremio.setTiempoVuelo(premio.getTiempoVuelo());
                }
                if (premio.getVelocidad() != null) {
                    existingPremio.setVelocidad(premio.getVelocidad());
                }
                if (premio.getLugar() != null) {
                    existingPremio.setLugar(premio.getLugar());
                }
                if (premio.getPuntos() != null) {
                    existingPremio.setPuntos(premio.getPuntos());
                }

                return existingPremio;
            })
            .map(premioRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Premio> findAll() {
        log.debug("Request to get all Premios");
        return premioRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Premio> findOne(Long id) {
        log.debug("Request to get Premio : {}", id);
        return premioRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Premio : {}", id);
        premioRepository.deleteById(id);
    }

    @Override
    public ResponseDTO<PremioDTO> agregarPremio(PremioDTO premioDTO) {
        log.info("Request para agregar un premio {}", premioDTO);
        try {
            Vuelo vuelo = vueloRepository
                .findById(premioDTO.getVueloId())
                .orElseThrow(() -> new BusinnesException(SYSMensaje.VUELO_NOEXISTS + premioDTO.getVueloId()));
            Paloma paloma = palomaRepository
                .findById(premioDTO.getPalomaId())
                .orElseThrow(() -> new BusinnesException(SYSMensaje.PALOMA_NOEXISTS + premioDTO.getPalomaId()));
            Optional<Premio> premio = premioRepository.findByPalomaAndVuelo(paloma, vuelo);

            if (premio.isEmpty()) {
                Premio newPremio = premioRepository.save(premioMapper.toEntity(premioDTO));
                return new ResponseDTO<>(SYSMensaje.PREMIO_INSERT_OK, Status.OK, premioMapper.toDto(newPremio));
            }
            return new ResponseDTO<>(SYSMensaje.PREMIO_IDENT + premioDTO.getPalomaId() + " " + premioDTO.getVueloId(), Status.ERROR, null);
        } catch (BusinnesException e) {
            return new ResponseDTO<>(e.getMessage(), Status.ERROR, null);
        } catch (Exception e) {
            return new ResponseDTO<>(SYSMensaje.DB_CONECTION, Status.NOK, null);
        }
    }

    @Override
    public ResponseDTO<PremioDTO> actualizarPremio(PremioDTO premioDTO) {
        log.info("Request para actualizar un premio {}", premioDTO);
        try {
            Premio premio = premioRepository
                .findById(premioDTO.getId())
                .orElseThrow(() -> new BusinnesException(SYSMensaje.PREMIO_NOEXISTS + premioDTO.getId()));
            Vuelo vuelo = vueloRepository
                .findById(premioDTO.getVueloId())
                .orElseThrow(() -> new BusinnesException(SYSMensaje.VUELO_NOEXISTS + premioDTO.getVueloId()));
            Paloma paloma = palomaRepository
                .findById(premioDTO.getPalomaId())
                .orElseThrow(() -> new BusinnesException(SYSMensaje.PALOMA_NOEXISTS + premioDTO.getPalomaId()));
            Premio existePremio = premioRepository.findByPalomaAndVuelo(paloma, vuelo).orElse(premio);

            if (premio.getId().equals(existePremio.getId())) {
                premio.setVuelo(vuelo);
                premio.setPaloma(paloma);

                if (premioDTO.getDesignada() != null) {
                    premio.setDesignada(premioDTO.getDesignada());
                }
                if (premioDTO.getFechaArribo() != null) {
                    premio.setFechaArribo(premioDTO.getFechaArribo());
                }
                return new ResponseDTO<>(SYSMensaje.PREMIO_UPDATE_OK, Status.OK, premioMapper.toDto(premio));
            }
            return new ResponseDTO<>(SYSMensaje.PREMIO_IDENT + premioDTO.getPalomaId() + " " + premioDTO.getPalomaId(), Status.ERROR, null);
        } catch (BusinnesException e) {
            return new ResponseDTO<>(e.getMessage(), Status.ERROR, null);
        } catch (Exception e) {
            return new ResponseDTO<>(SYSMensaje.DB_CONECTION, Status.NOK, null);
        }
    }

    @Override
    public ResponseDTO<PremioDTO> obtenerPremio(Long id) {
        log.info("Request para obtener un premio por su id: {}", id);
        try {
            Premio premio = premioRepository.findById(id).orElseThrow(() -> new BusinnesException(SYSMensaje.PREMIO_NOEXISTS + id));
            return new ResponseDTO<>(SYSMensaje.PREMIO_EXISTS, Status.OK, premioMapper.toDto(premio));
        } catch (BusinnesException e) {
            return new ResponseDTO<>(e.getMessage(), Status.ERROR, null);
        } catch (Exception e) {
            return new ResponseDTO<>(SYSMensaje.DB_CONECTION, Status.NOK, null);
        }
    }

    @Override
    public ResponseDTO<List<ReporteVueloDTO>> obtenerPremiosByVuelo(Long vueloId, Long colombofiloId, Long sociedadId) {
        log.info("Request para obtener los premios del vuelo con id: {}", vueloId);
        if (ObjectUtils.isEmpty(vueloId) && vueloId < 1) {
            return new ResponseDTO<>(SYSMensaje.PREMIO_ID_VUELO_EMPTY + vueloId, Status.NOK, null);
        }
        try {
            CabeceraReporteVueloDTO cabecera = premioRepository
                .obtenerEncabezadoByVuelo(vueloId)
                .map(cabeceraReporteVuelo -> {
                    return new CabeceraReporteVueloDTO(
                        cabeceraReporteVuelo.getFecha(),
                        cabeceraReporteVuelo.getCompetencia(),
                        cabeceraReporteVuelo.getCampeonato(),
                        cabeceraReporteVuelo.getSociedad(),
                        cabeceraReporteVuelo.getParadero(),
                        cabeceraReporteVuelo.getDistancia(),
                        cabeceraReporteVuelo.getEnviadas(),
                        cabeceraReporteVuelo.getPremios()
                    );
                })
                .orElseThrow(() -> new BusinnesException(SYSMensaje.VUELO_NOEXISTS + vueloId));

            List<ReporteVueloDTO> reporteVueloDTO = premioRepository
                .obtenerPremiosByVueloAndColombofiloAndSociedad(vueloId, colombofiloId, sociedadId)
                .stream()
                .map(reporteVuelo -> {
                    ReporteVueloDTO dto = new ReporteVueloDTO();
                    dto.setAnilla(reporteVuelo.getAnilla());
                    dto.setAnno(reporteVuelo.getAnno());
                    dto.setColor(reporteVuelo.getColor());
                    dto.setDistancia(reporteVuelo.getDistancia());
                    dto.setPais(reporteVuelo.getPais());
                    dto.setSexo(reporteVuelo.getSexo());
                    dto.setTiempo(reporteVuelo.getTiempo());
                    dto.setPuntos(reporteVuelo.getPuntos());
                    dto.setLugar(reporteVuelo.getLugar());
                    dto.setVelocidad(reporteVuelo.getVelocidad());
                    dto.setDesignada(reporteVuelo.getDesignada());
                    dto.setNombre(reporteVuelo.getNombre());
                    dto.setFecha(reporteVuelo.getFecha());
                    dto.setSociedad(reporteVuelo.getSociedad());

                    return dto;
                })
                .toList();

            Map<String, Object> params = new HashMap<>();
            params.put("datos", new JRBeanCollectionDataSource(reporteVueloDTO));
            params.put("sociedad", "Sociedad de " + cabecera.getSociedad());
            params.put("campeonato", cabecera.getCampeonato());
            params.put("competencia", cabecera.getCompetencia());
            params.put("fecha", cabecera.getFecha());
            params.put("paradero", cabecera.getParadero());
            params.put("distancia", cabecera.getDistancia());
            params.put("enviadas", cabecera.getEnviadas());
            params.put("premios", cabecera.getPremios());
            params.put("logo_izq", logo.getInputStream());
            params.put("logo_der", logo.getInputStream());

            JasperReport reportVueloJasper = (JasperReport) JRLoader.loadObject(reporteVuelo.getInputStream());
            JasperPrint report = JasperFillManager.fillReport(reportVueloJasper, params, new JREmptyDataSource());

            JasperExportManager.exportReportToPdfFile(report, "c:/reporte1.pdf");
            if (!ObjectUtils.isEmpty(reporteVueloDTO)) {
                return new ResponseDTO<>(SYSMensaje.PREMIO_LIST_VUELO + vueloId, Status.OK, reporteVueloDTO);
            }
            return new ResponseDTO<>(SYSMensaje.PREMIO_NOLIST_VUELO + vueloId, Status.NOK, null);
        } catch (Exception e) {
            log.debug(e.getMessage());
            return new ResponseDTO<>(SYSMensaje.DB_CONECTION, Status.NOK, null);
        }
    }

    @Override
    public ResponseDTO<List<PremioDTO>> obtenerPremiosByPaloma(Long palomaId) {
        log.info("Request para obtener los premios de la paloma con id: {}", palomaId);
        try {
            Paloma paloma = palomaRepository
                .findById(palomaId)
                .orElseThrow(() -> new BusinnesException(SYSMensaje.PALOMA_NOEXISTS + palomaId));

            if (!paloma.getPremios().isEmpty()) {
                return new ResponseDTO<>(
                    SYSMensaje.PREMIO_LIST_PALOMA + palomaId,
                    Status.OK,
                    premioMapper.toDto(paloma.getPremios().stream().toList())
                );
            }
            return new ResponseDTO<>(SYSMensaje.PREMIO_NOLIST_PALOMA + palomaId, Status.NOK, null);
        } catch (BusinnesException e) {
            return new ResponseDTO<>(e.getMessage(), Status.ERROR, null);
        } catch (Exception e) {
            return new ResponseDTO<>(SYSMensaje.DB_CONECTION, Status.NOK, null);
        }
    }
}
