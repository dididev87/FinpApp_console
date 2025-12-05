package dominio;

import java.util.Map;

public class ResumenMensual {
    private int anio;
    private int mes;
    private double totalIngresos;
    private double totalGastos;
    private double saldo;
    private Double presupuestoMensual; // puede ser null si no hay presupuesto
    private Map<String, Double> gastosPorCategoria;

    public int getAnio() { return anio; }
    public void setAnio(int anio) { this.anio = anio; }

    public int getMes() { return mes; }
    public void setMes(int mes) { this.mes = mes; }

    public double getTotalIngresos() { return totalIngresos; }
    public void setTotalIngresos(double totalIngresos) { this.totalIngresos = totalIngresos; }

    public double getTotalGastos() { return totalGastos; }
    public void setTotalGastos(double totalGastos) { this.totalGastos = totalGastos; }

    public double getSaldo() { return saldo; }
    public void setSaldo(double saldo) { this.saldo = saldo; }

    public Double getPresupuestoMensual() { return presupuestoMensual; }
    public void setPresupuestoMensual(Double presupuestoMensual) { this.presupuestoMensual = presupuestoMensual; }

    public Map<String, Double> getGastosPorCategoria() { return gastosPorCategoria; }
    public void setGastosPorCategoria(Map<String, Double> gastosPorCategoria) { this.gastosPorCategoria = gastosPorCategoria; }

    @Override
    public String toString() {
        return "ResumenMensual{" +
                "anio=" + anio +
                ", mes=" + mes +
                ", totalIngresos=" + totalIngresos +
                ", totalGastos=" + totalGastos +
                ", saldo=" + saldo +
                ", presupuestoMensual=" + presupuestoMensual +
                ", gastosPorCategoria=" + gastosPorCategoria +
                '}';
    }
}
