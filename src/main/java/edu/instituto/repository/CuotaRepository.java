package edu.instituto.repository;

import edu.instituto.model.Cuota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public interface CuotaRepository extends JpaRepository<Cuota, Integer> {
    boolean existsByMatricula_Id(Integer idMatricula);

    List<Cuota> findByMatricula_IdOrderByNumeroCuotaAsc(Integer idMatricula);

    List<Cuota> findByMatricula_Alumno_IdOrderByFechaVencimientoAsc(Integer idAlumno);

    List<Cuota> findByMatricula_Alumno_IdAndEstadoInOrderByFechaVencimientoAsc(Integer idAlumno, Collection<String> estados);

    // RN08: una cuota pendiente cuya fecha de vencimiento ya pasó se considera vencida.
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update Cuota c set c.estado = 'VENCIDA' where c.estado = 'PENDIENTE' and c.fechaVencimiento < :hoy")
    int marcarVencidas(@Param("hoy") LocalDate hoy);
}
