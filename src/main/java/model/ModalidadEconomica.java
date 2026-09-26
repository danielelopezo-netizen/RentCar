package model;

import enums.EstadoModalidad;

public class ModalidadEconomica extends ModalidadAlquiler {
    public ModalidadEconomica(String codigo, String nombre, String descripcion,
                              int duracionMinimaDias, double valorDiario, EstadoModalidad estado) {
        super(codigo, nombre, descripcion, duracionMinimaDias, valorDiario, estado);
    }

    @Override
    public double calcularValor(int dias) {
        return dias * valorDiario;
    }
}