package model;

import enums.EstadoModalidad;
import java.util.List;

public class ModalidadPremium extends ModalidadAlquiler {
    private String tipoCobertura;
    private int conductoresAdicionales;
    private String caracteristicasEspeciales;

    public ModalidadPremium(String codigo, String nombre, String descripcion,
                            int duracionMinimaDias, double valorDiario, EstadoModalidad estado,
                            String tipoCobertura, int conductoresAdicionales, String caracteristicasEspeciales) {
        super(codigo, nombre, descripcion, duracionMinimaDias, valorDiario, estado);
        this.tipoCobertura = tipoCobertura;
        this.conductoresAdicionales = conductoresAdicionales;
        this.caracteristicasEspeciales = caracteristicasEspeciales;
    }

    public ModalidadPremium(String codigo, String nombre, String descripcion,
                            int duracionMinimaDias, double valorDiario, EstadoModalidad estado,
                            List<String> beneficios,
                            String tipoCobertura, int conductoresAdicionales, String caracteristicasEspeciales) {
        super(codigo, nombre, descripcion, duracionMinimaDias, valorDiario, estado, beneficios);
        this.tipoCobertura = tipoCobertura;
        this.conductoresAdicionales = conductoresAdicionales;
        this.caracteristicasEspeciales = caracteristicasEspeciales;
    }

    @Override
    public double calcularValor(int dias) {
        // Tarifa diaria por días contratados
        return dias * valorDiario;
    }

    // Getters y Setters
    public String getTipoCobertura() {
        return tipoCobertura;
    }

    public void setTipoCobertura(String tipoCobertura) {
        this.tipoCobertura = tipoCobertura;
    }

    public int getConductoresAdicionales() {
        return conductoresAdicionales;
    }

    public void setConductoresAdicionales(int conductoresAdicionales) {
        this.conductoresAdicionales = conductoresAdicionales;
    }

    public String getCaracteristicasEspeciales() {
        return caracteristicasEspeciales;
    }

    public void setCaracteristicasEspeciales(String caracteristicasEspeciales) {
        this.caracteristicasEspeciales = caracteristicasEspeciales;
    }
}
