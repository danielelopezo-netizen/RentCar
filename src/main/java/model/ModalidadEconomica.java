package model;

import enums.EstadoModalidad;
import java.util.List;

public class ModalidadEconomica extends ModalidadAlquiler {

    public ModalidadEconomica(String codigo, String nombre, String descripcion,
                              int duracionMinimaDias, double valorDiario, EstadoModalidad estado) {
        super(codigo, nombre, descripcion, duracionMinimaDias, valorDiario, estado);
    }

    public ModalidadEconomica(String codigo, String nombre, String descripcion,
                              int duracionMinimaDias, double valorDiario, EstadoModalidad estado,
                              List<String> beneficios) {
        super(codigo, nombre, descripcion, duracionMinimaDias, valorDiario, estado, beneficios);
    }

    @Override
    public double calcularValor(int dias) {
        return dias * valorDiario;
    }
}