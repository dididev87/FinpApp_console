package dominio;

public class CategoriaGasto {
    private Long id;
    private String nombre;
    private String tipo; // "FIJO" o "VARIABLE"

    public CategoriaGasto() {}

    public CategoriaGasto(String nombre, String tipo) {
        this.nombre = nombre;
        this.tipo = tipo;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    @Override
    public String toString() {
        return "CategoriaGasto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}


