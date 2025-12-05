package repositorios;

import dominio.PresupuestoMensual;
import java.util.Optional;

public interface PresupuestoRepository {
    PresupuestoMensual guardar(PresupuestoMensual presupuesto);
    Optional<PresupuestoMensual> buscarPorAnioYMes(int anio, int mes);
}
