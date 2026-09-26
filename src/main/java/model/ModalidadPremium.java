package model;

import enums.EstadoModalidad;

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

    @Override
    public double calcularValor(int dias) {
        // Ejemplo: Las premium podrían tener un recargo fijo por la cobertura
        return (dias * valorDiario) + 50000;
    }

    // Getters y setters de sus atributos propios...
}
