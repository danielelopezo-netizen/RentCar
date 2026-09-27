package model;

import enums.EstadoModalidad;
import java.util.List;

/**
 * Fábrica para la creación centralizada de instancias de ModalidadAlquiler.
 * Implementa el patrón creacional Factory Method.
 */
public class ModalidadFactory {

    private ModalidadFactory() {
        // Constructor privado para evitar instanciación
    }

    public static ModalidadAlquiler crearModalidad(String tipo, String codigo, String nombre,
                                                   String descripcion, int duracionMinimaDias,
                                                   double valorDiario, EstadoModalidad estado) {
        return crearModalidad(tipo, codigo, nombre, descripcion, duracionMinimaDias, valorDiario, estado, null);
    }

    public static ModalidadAlquiler crearModalidad(String tipo, String codigo, String nombre,
                                                   String descripcion, int duracionMinimaDias,
                                                   double valorDiario, EstadoModalidad estado,
                                                   List<String> beneficios) {
        if (tipo == null) {
            throw new IllegalArgumentException("El tipo de modalidad no puede ser nulo.");
        }

        switch (tipo.trim().toUpperCase()) {
            case "ECONOMICA":
            case "ECONÓMICA":
                return new ModalidadEconomica(codigo, nombre, descripcion, duracionMinimaDias, valorDiario, estado, beneficios);
            case "EJECUTIVA":
                return new ModalidadEjecutiva(codigo, nombre, descripcion, duracionMinimaDias, valorDiario, estado, beneficios);
            default:
                throw new IllegalArgumentException("Tipo de modalidad básica no reconocida: " + tipo + ". Para Premium use el método correspondiente.");
        }
    }

    public static ModalidadAlquiler crearModalidadPremium(String codigo, String nombre,
                                                          String descripcion, int duracionMinimaDias,
                                                          double valorDiario, EstadoModalidad estado,
                                                          List<String> beneficios,
                                                          String tipoCobertura, int conductoresAdicionales,
                                                          String caracteristicasEspeciales) {
        return new ModalidadPremium(codigo, nombre, descripcion, duracionMinimaDias, valorDiario,
                estado, beneficios, tipoCobertura, conductoresAdicionales, caracteristicasEspeciales);
    }

    // Método retrocompatible con la firma anterior
    public static ModalidadAlquiler crearModalidad(String tipo, String codigo, String nombre,
                                                   String descripcion, int duracionMinimaDias,
                                                   double valorDiario, EstadoModalidad estado,
                                                   String tipoCobertura, int conductoresAdicionales,
                                                   String caracteristicasEspeciales) {
        if ("PREMIUM".equalsIgnoreCase(tipo != null ? tipo.trim() : "")) {
            return new ModalidadPremium(codigo, nombre, descripcion, duracionMinimaDias, valorDiario,
                    estado, tipoCobertura, conductoresAdicionales, caracteristicasEspeciales);
        } else {
            throw new IllegalArgumentException("Este método es exclusivo para la modalidad PREMIUM.");
        }
    }
}
