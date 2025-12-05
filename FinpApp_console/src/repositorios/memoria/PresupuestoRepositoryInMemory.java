package repositorios.memoria;

import dominio.PresupuestoMensual;
import repositorios.PresupuestoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PresupuestoRepositoryInMemory implements PresupuestoRepository {
    private final List<PresupuestoMensual> presupuestos = new ArrayList<>();

    @Override
    public PresupuestoMensual guardar(PresupuestoMensual presupuesto) {
        presupuestos.removeIf(p -> p.getAnio() == presupuesto.getAnio() && p.getMes() == presupuesto.getMes());
        presupuestos.add(presupuesto);
        return presupuesto;
    }

    @Override
    public Optional<PresupuestoMensual> buscarPorAnioYMes(int anio, int mes) {
        return presupuestos.stream().filter(p -> p.getAnio() == anio && p.getMes() == mes).findFirst();
    }
}
