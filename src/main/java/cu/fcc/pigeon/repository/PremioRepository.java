package cu.fcc.pigeon.repository;

import cu.fcc.pigeon.domain.Paloma;
import cu.fcc.pigeon.domain.Premio;
import cu.fcc.pigeon.domain.Vuelo;
import cu.fcc.pigeon.domain.common.CabeceraReporteVuelo;
import cu.fcc.pigeon.domain.common.Enviadas;
import cu.fcc.pigeon.domain.common.ReporteVuelo;
import cu.fcc.pigeon.service.dto.CabeceraReporteVueloDTO;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Premio entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PremioRepository extends JpaRepository<Premio, Long> {
    List<Premio> findByVuelo(Vuelo vuelo);

    @Query(
        value = "SELECT MAX(pigeon_control_mon.premio.fecha_arribo) as Dia1 FROM pigeon_control_mon.premio WHERE pigeon_control_mon.premio.fecha_arribo >= ?1 AND pigeon_control_mon.premio.fecha_arribo < ?2",
        nativeQuery = true
    )
    Optional<Instant> findHoraMasAltaPorDia(Instant inicioDia, Instant finDia);

    @Query(
        value = "SELECT MAX(CASE WHEN DATE_FORMAT(pr.fecha_arribo, '%Y-%m-%d') = DATE(?1) THEN pr.fecha_arribo END) AS f1, " +
        "MAX(CASE WHEN DATE_FORMAT(pr.fecha_arribo, '%Y-%m-%d') = DATE(?1) + INTERVAL 1 DAY THEN pr.fecha_arribo END) AS f2, " +
        "MAX(CASE WHEN DATE_FORMAT(pr.fecha_arribo, '%Y-%m-%d') = DATE(?1) + INTERVAL 2 DAY THEN pr.fecha_arribo END) AS f3 " +
        "FROM pigeon_control_mon.premio pr " +
        "WHERE pr.vuelo_id = ?2",
        nativeQuery = true
    )
    Optional<Object> findHorasMasAltas(String fecha, Long vueloId);

    Optional<Premio> findByPalomaAndVuelo(Paloma paloma, Vuelo vuelo);

    @Query(
        value = "SELECT pr.lugar, CONCAT(co.nombre, ' ', co.primer_apellido, ' ', co.segindo_apellido) AS nombre, pa.anilla, pa.anno, ps.codigo, " +
        "so.nombre_sociedad as sociedad, cl.nombre_color AS color, CASE WHEN pa.sexo = 1 THEN 'M' ELSE 'H' END AS sexo, " +
        "pr.designada AS designada,cv.distancia, CONVERT(date_format(pr.fecha_arribo, '%Y-%m-%d %H:%i:%s'),CHAR) AS fecha, " +
        "CONVERT(SEC_TO_TIME(pr.tiempo_vuelo), CHAR) tiempo, ROUND(pr.velocidad, 3) AS velocidad, ROUND(pr.puntos, 3) puntos " +
        "FROM pigeon_control_mon.premio pr " +
        "JOIN pigeon_control_mon.paloma pa ON pa.id=pr.paloma_id " +
        "JOIN pigeon_control_mon.pais ps ON ps.id = pa.pais " +
        "JOIN pigeon_control_mon.colombofilo co ON co.id=pa.colombofilo_id " +
        "JOIN pigeon_control_mon.colombofilo_vuelo cv ON co.id = cv.colombofilo_id " +
        "JOIN pigeon_control_mon.color cl ON cl.id = pa.color_id " +
        "JOIN pigeon_control_mon.sociedad so ON co.sociedad_id = so.id " +
        "WHERE pr.vuelo_id = COALESCE(?1, pr.vuelo_id) " +
        "AND co.id = COALESCE(?2, co.id) " +
        "AND co.sociedad_id = COALESCE(?3, co.sociedad_id) " +
        "AND pr.lugar > 0 " +
        "ORDER BY pr.lugar;",
        nativeQuery = true
    )
    List<ReporteVuelo> obtenerPremiosByVueloAndColombofiloAndSociedad(Long vueloId, Long colombofiloId, Long sociedadId);

    @Query(
        value = "SELECT CONVERT(date_format(vu.fecha, '%Y-%m-%d %H:%i:%s'),CHAR) as fecha, vu.competencia, vu.campeonato, pa.nombre_paradero as paradero, " +
        "pa.distancia_media as distancia, so.nombre_sociedad sociedad, sum(cv.enviadas) as enviadas, round(sum(cv.envidas) / 5) as premios " +
        "FROM pigeon_control_mon.vuelo vu " +
        "JOIN pigeon_control_mon.paradero pa ON vu.paradero_id = pa.id " +
        "JOIN pigeon_control_mon.sociedad so ON pa.sociedad_id = so.id " +
        "JOIN pigeon_control_mon.colombofilo_vuelo cv ON cv.vuelo_id = vu.id " +
        "Where vu.id = ?1 " +
        "GROUP BY cv.vuelo_id;",
        nativeQuery = true
    )
    Optional<CabeceraReporteVuelo> obtenerEncabezadoByVuelo(Long vueloId);

    @Query(
        value = "SELECT pr.* FROM pigeon_control_mon.premio pr " +
        "JOIN pigeon_control_mon.paloma pa ON pr.paloma_id = pa.id and pr.vuelo_id  = ?1 and pa.colombofilo_id = ?2",
        nativeQuery = true
    )
    List<Premio> obtenerPremiosByVueloAndColombofilo(Long vueloId, Long colombofiloId);

    @Query(
        value = "SELECT pr.* FROM pigeon_control_mon.premio pr " + "WHERE pr.vuelo_id = ?1 " + "ORDER BY pr.velocidad DESC " + "LIMIT ?2",
        nativeQuery = true
    )
    List<Premio> obtenerPremiosByVueloSortedByVelocidad(Long vueloId, Integer cantPremios);
}
