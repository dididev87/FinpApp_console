package repositorios;

import dominio.Movimiento;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MovimientoRepository {
    Movimiento guardar(Movimiento movimiento);
    List<Movimiento> obtenerTodos();
    List<Movimiento> buscarPorRangoFecha(LocalDate desde, LocalDate hasta);
    Optional<Movimiento> buscarPorId(Long id);
}

