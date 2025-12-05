package servicios;

import dominio.CategoriaGasto;
import dominio.Movimiento;
import dominio.PresupuestoMensual;
import dominio.ResumenMensual;
import repositorios.CategoriaRepository;
import repositorios.MovimientoRepository;
import repositorios.PresupuestoRepository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FinanzasService {
    private final CategoriaRepository categoriaRepo;
    private final MovimientoRepository movimientoRepo;
    private final PresupuestoRepository presupuestoRepo;

    public FinanzasService(CategoriaRepository categoriaRepo, MovimientoRepository movimientoRepo, PresupuestoRepository presupuestoRepo) {
        this.categoriaRepo = categoriaRepo;
        this.movimientoRepo = movimientoRepo;
        this.presupuestoRepo = presupuestoRepo;
    }

    // --- Categorias ---
    public CategoriaGasto registrarCategoria(String nombre, String tipo) {
        CategoriaGasto cat = new CategoriaGasto(nombre, tipo);
        return categoriaRepo.guardar(cat);
    }

    public List<CategoriaGasto> listarCategorias() {
        return categoriaRepo.obtenerTodas();
    }

    public Optional<CategoriaGasto> buscarCategoriaPorId(Long id) {
        return categoriaRepo.buscarPorId(id);
    }

    // --- Movimientos ---
    public Movimiento registrarMovimiento(String tipo, double monto, LocalDate fecha, Long categoriaId, String descripcion) {
        CategoriaGasto cat = null;
        if (categoriaId != null) cat = categoriaRepo.buscarPorId(categoriaId).orElse(null);
        Movimiento mov = new Movimiento(tipo, monto, fecha, cat, descripcion);
        return movimientoRepo.guardar(mov);
    }

    public List<Movimiento> listarMovimientos() {
        return movimientoRepo.obtenerTodos();
    }

    public List<Movimiento> listarMovimientosDeMes(int anio, int mes) {
        LocalDate desde = LocalDate.of(anio, mes, 1);
        LocalDate hasta = desde.withDayOfMonth(desde.lengthOfMonth());
        return movimientoRepo.buscarPorRangoFecha(desde, hasta);
    }

    // --- Presupuesto ---
    public PresupuestoMensual configurarPresupuestoMensual(int anio, int mes, double monto) {
        PresupuestoMensual p = new PresupuestoMensual(anio, mes, monto);
        return presupuestoRepo.guardar(p);
    }

    public Optional<PresupuestoMensual> buscarPresupuesto(int anio, int mes) {
        return presupuestoRepo.buscarPorAnioYMes(anio, mes);
    }

    // --- Resumen ---
    public ResumenMensual obtenerResumenMensual(int anio, int mes) {
        List<Movimiento> movimientos = listarMovimientosDeMes(anio, mes);

        double totalIngresos = movimientos.stream()
                .filter(m -> "INGRESO".equalsIgnoreCase(m.getTipo()))
                .mapToDouble(Movimiento::getMonto).sum();

        double totalGastos = movimientos.stream()
                .filter(m -> "GASTO".equalsIgnoreCase(m.getTipo()))
                .mapToDouble(Movimiento::getMonto).sum();

        double saldo = totalIngresos - totalGastos;

        Map<String, Double> gastosPorCategoria = new HashMap<>();
        movimientos.stream()
                .filter(m -> "GASTO".equalsIgnoreCase(m.getTipo()))
                .forEach(m -> {
                    String nombreCat = m.getCategoria() != null ? m.getCategoria().getNombre() : "Sin categoría";
                    gastosPorCategoria.put(nombreCat, gastosPorCategoria.getOrDefault(nombreCat, 0.0) + m.getMonto());
                });

        ResumenMensual resumen = new ResumenMensual();
        resumen.setAnio(anio);
        resumen.setMes(mes);
        resumen.setTotalIngresos(totalIngresos);
        resumen.setTotalGastos(totalGastos);
        resumen.setSaldo(saldo);
        resumen.setGastosPorCategoria(gastosPorCategoria);

        Optional<PresupuestoMensual> presupuesto = buscarPresupuesto(anio, mes);
        presupuesto.ifPresent(p -> resumen.setPresupuestoMensual(p.getMontoTotal()));

        return resumen;
    }
}


