package model;

import enums.EstadoModalidad;

public class ModalidadFactory {

    // Método para crear modalidades que NO requieren atributos extra (Económica y Ejecutiva)
    public static ModalidadAlquiler crearModalidad(String tipo, String codigo, String nombre,
                                                   String descripcion, int duracionMinimaDias,
                                                   double valorDiario, EstadoModalidad estado) {

        switch (tipo.toUpperCase()) {
            case "ECONOMICA":
                return new ModalidadEconomica(codigo, nombre, descripcion, duracionMinimaDias, valorDiario, estado);
            case "EJECUTIVA":
                return new ModalidadEjecutiva(codigo, nombre, descripcion, duracionMinimaDias, valorDiario, estado);
            default:
                throw new IllegalArgumentException("Tipo de modalidad básica no reconocida: " + tipo);
        }
    }

    // Método sobrecargado exclusivo para crear la modalidad Premium con sus datos adicionales
    public static ModalidadAlquiler crearModalidad(String tipo, String codigo, String nombre,
                                                   String descripcion, int duracionMinimaDias,
                                                   double valorDiario, EstadoModalidad estado,
                                                   String tipoCobertura, int conductoresAdicionales,
                                                   String caracteristicasEspeciales) {

        if (tipo.toUpperCase().equals("PREMIUM")) {
            return new ModalidadPremium(codigo, nombre, descripcion, duracionMinimaDias, valorDiario,
                    estado, tipoCobertura, conductoresAdicionales, caracteristicasEspeciales);
        } else {
            throw new IllegalArgumentException("Este método es exclusivo para la modalidad PREMIUM.");
        }
    }
}
