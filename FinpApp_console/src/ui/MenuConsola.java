package ui;

import dominio.CategoriaGasto;
import dominio.Movimiento;
import dominio.PresupuestoMensual;
import dominio.ResumenMensual;
import servicios.FinanzasService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class MenuConsola {
    private final FinanzasService service;
    private final Scanner sc = new Scanner(System.in);
    private final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public MenuConsola(FinanzasService service) {
        this.service = service;
    }

    public void iniciar() {
        int opcion;
        do {
            mostrarMenu();
            opcion = leerInt("Seleccione una opción: ");
            switch (opcion) {
                case 1 -> registrarCategoria();
                case 2 -> registrarMovimiento();
                case 3 -> listarMovimientosDeMes();
                case 4 -> verResumenMensual();
                case 5 -> configurarPresupuesto();
                case 6 -> compararGastosConPresupuesto();
                case 0 -> System.out.println("Saliendo... hasta luego.");
                default -> System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }

    private void mostrarMenu() {
        System.out.println("\n=== Gestor de Finanzas Personales ===");
        System.out.println("1. Registrar categoría");
        System.out.println("2. Registrar movimiento (INGRESO/GASTO)");
        System.out.println("3. Listar movimientos de un mes");
        System.out.println("4. Ver resumen de un mes");
        System.out.println("5. Configurar presupuesto mensual");
        System.out.println("6. Comparar gastos vs presupuesto");
        System.out.println("0. Salir");
    }

    private void registrarCategoria() {
        System.out.print("Nombre de la categoría: ");
        String nombre = sc.nextLine().trim();
        System.out.print("Tipo (FIJO/VARIABLE): ");
        String tipo = sc.nextLine().trim().toUpperCase();
        CategoriaGasto cat = service.registrarCategoria(nombre, tipo);
        System.out.println("Categoría registrada con id: " + cat.getId());
    }

    private void registrarMovimiento() {
        System.out.print("Tipo (INGRESO/GASTO): ");
        String tipo = sc.nextLine().trim().toUpperCase();
        double monto = leerDouble("Monto: ");
        System.out.print("Fecha (yyyy-MM-dd): ");
        String fechaStr = sc.nextLine().trim();
        LocalDate fecha;
        try { fecha = LocalDate.parse(fechaStr, fmt); }
        catch (Exception e) { System.out.println("Fecha inválida. Usando hoy."); fecha = LocalDate.now(); }

        System.out.println("Categorias disponibles:");
        List<CategoriaGasto> cats = service.listarCategorias();
        cats.forEach(c -> System.out.println(c.getId() + " - " + c.getNombre() + " (" + c.getTipo() + ")"));

        Long categoriaId = null;
        if (!cats.isEmpty()) {
            System.out.print("Ingrese id de la categoría (ENTER para omitir): ");
            String idStr = sc.nextLine().trim();
            if (!idStr.isEmpty()) {
                try { categoriaId = Long.parseLong(idStr); }
                catch (NumberFormatException ex) { System.out.println("Id inválido. Se registrará sin categoría."); }
            }
        }

        System.out.print("Descripción: ");
        String descripcion = sc.nextLine().trim();

        Movimiento mov = service.registrarMovimiento(tipo, monto, fecha, categoriaId, descripcion);
        System.out.println("Movimiento registrado con id: " + mov.getId());
    }

    private void listarMovimientosDeMes() {
        int anio = leerInt("Año: ");
        int mes = leerInt("Mes (1-12): ");
        List<Movimiento> movimientos = service.listarMovimientosDeMes(anio, mes);
        if (movimientos.isEmpty()) System.out.println("No hay movimientos para ese mes.");
        else movimientos.forEach(System.out::println);
    }

    private void verResumenMensual() {
        int anio = leerInt("Año: ");
        int mes = leerInt("Mes (1-12): ");
        ResumenMensual resumen = service.obtenerResumenMensual(anio, mes);
        System.out.println("\n--- Resumen " + anio + "-" + mes + " ---");
        System.out.println("Ingresos totales: " + resumen.getTotalIngresos());
        System.out.println("Gastos totales: " + resumen.getTotalGastos());
        System.out.println("Saldo: " + resumen.getSaldo());
        System.out.println("Presupuesto: " + (resumen.getPresupuestoMensual() != null ? resumen.getPresupuestoMensual() : "(no configurado)"));
        System.out.println("Gastos por categoría:");
        if (resumen.getGastosPorCategoria() == null || resumen.getGastosPorCategoria().isEmpty()) System.out.println(" - (sin gastos)");
        else resumen.getGastosPorCategoria().forEach((k, v) -> System.out.println(" - " + k + ": " + v));
    }

    private void configurarPresupuesto() {
        int anio = leerInt("Año: ");
        int mes = leerInt("Mes (1-12): ");
        double monto = leerDouble("Monto presupuesto para el mes: ");
        PresupuestoMensual p = service.configurarPresupuestoMensual(anio, mes, monto);
        System.out.println("Presupuesto guardado: " + p);
    }

    private void compararGastosConPresupuesto() {
        int anio = leerInt("Año: ");
        int mes = leerInt("Mes (1-12): ");
        var resumen = service.obtenerResumenMensual(anio, mes);
        Double presupuesto = resumen.getPresupuestoMensual();
        if (presupuesto == null) { System.out.println("No hay presupuesto configurado para este mes."); return; }
        System.out.println("Gasto total: " + resumen.getTotalGastos());
        System.out.println("Presupuesto: " + presupuesto);
        if (resumen.getTotalGastos() > presupuesto) System.out.println("¡Advertencia! Has superado el presupuesto.");
        else System.out.println("Vas dentro del presupuesto.");
    }

    // helpers
    private int leerInt(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                String linea = sc.nextLine().trim();
                return Integer.parseInt(linea);
            } catch (NumberFormatException e) { System.out.println("Entrada inválida. Intente de nuevo."); }
        }
    }

    private double leerDouble(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                String linea = sc.nextLine().trim();
                return Double.parseDouble(linea);
            } catch (NumberFormatException e) { System.out.println("Entrada inválida. Intente de nuevo."); }
        }
    }
}


