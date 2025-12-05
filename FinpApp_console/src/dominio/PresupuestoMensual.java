package dominio;

public class PresupuestoMensual {
    private int anio;
    private int mes; // 1..12
    private double montoTotal;

    public PresupuestoMensual() {}

    public PresupuestoMensual(int anio, int mes, double montoTotal) {
        this.anio = anio;
        this.mes = mes;
        this.montoTotal = montoTotal;
    }

    public int getAnio() { return anio; }
    public void setAnio(int anio) { this.anio = anio; }

    public int getMes() { return mes; }
    public void setMes(int mes) { this.mes = mes; }

    public double getMontoTotal() { return montoTotal; }
    public void setMontoTotal(double montoTotal) { this.montoTotal = montoTotal; }

    @Override
    public String toString() {
        return "PresupuestoMensual{" +
                "anio=" + anio +
                ", mes=" + mes +
                ", montoTotal=" + montoTotal +
                '}';
    }
}



