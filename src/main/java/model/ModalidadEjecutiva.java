package model;

import enums.EstadoModalidad;

public class ModalidadEjecutiva extends ModalidadAlquiler {
    public ModalidadEjecutiva(String codigo, String nombre, String descripcion,
                              int duracionMinimaDias, double valorDiario, EstadoModalidad estado) {
        super(codigo, nombre, descripcion, duracionMinimaDias, valorDiario, estado);
    }

    @Override
    public double calcularValor(int dias) {
        return dias * valorDiario; // Aquí luego puedes meter recargos si el profe lo pide
    }
}
