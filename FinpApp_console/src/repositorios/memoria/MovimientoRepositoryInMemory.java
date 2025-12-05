package repositorios.memoria;

import dominio.Movimiento;
import repositorios.MovimientoRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MovimientoRepositoryInMemory implements MovimientoRepository {
    private final List<Movimiento> movimientos = new ArrayList<>();
    private long secuenciaId = 1L;

    @Override
    public Movimiento guardar(Movimiento movimiento) {
        if (movimiento.getId() == null) {
            movimiento.setId(secuenciaId++);
            movimientos.add(movimiento);
        } else {
            movimientos.removeIf(m -> m.getId().equals(movimiento.getId()));
            movimientos.add(movimiento);
        }
        return movimiento;
    }

    @Override
    public List<Movimiento> obtenerTodos() {
        return new ArrayList<>(movimientos);
    }

    @Override
    public List<Movimiento> buscarPorRangoFecha(LocalDate desde, LocalDate hasta) {
        return movimientos.stream()
                .filter(m -> (m.getFecha().equals(desde) || m.getFecha().isAfter(desde))
                        && (m.getFecha().equals(hasta) || m.getFecha().isBefore(hasta)))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Movimiento> buscarPorId(Long id) {
        return movimientos.stream().filter(m -> m.getId().equals(id)).findFirst();
    }
}

