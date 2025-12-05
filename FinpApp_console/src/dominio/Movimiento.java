package dominio;

import java.time.LocalDate;

public class Movimiento {
    private Long id;
    private String tipo; // "INGRESO" o "GASTO"
    private double monto;
    private LocalDate fecha;
    private CategoriaGasto categoria;
    private String descripcion;

    public Movimiento() {}

    public Movimiento(String tipo, double monto, LocalDate fecha, CategoriaGasto categoria, String descripcion) {
        this.tipo = tipo;
        this.monto = monto;
        this.fecha = fecha;
        this.categoria = categoria;
        this.descripcion = descripcion;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public double getMonto() { return monto; }
    public void setMonto(double monto) { this.monto = monto; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public CategoriaGasto getCategoria() { return categoria; }
    public void setCategoria(CategoriaGasto categoria) { this.categoria = categoria; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    @Override
    public String toString() {
        return "Movimiento{" +
                "id=" + id +
                ", tipo='" + tipo + '\'' +
                ", monto=" + monto +
                ", fecha=" + fecha +
                ", categoria=" + (categoria != null ? categoria.getNombre() : "Sin categoría") +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
