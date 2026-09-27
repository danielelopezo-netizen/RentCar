package model;

import util.NumeroPerfectoUtil;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Representa a un cliente de la empresa RentCar.
 */
public class Cliente {
    private String nombreCompleto;
    private String documentoIdentidad;
    private String telefono;
    private String correoElectronico;
    private int edad;
    private LocalDate fechaRegistro;

    public Cliente(String nombreCompleto, String documentoIdentidad, String telefono,
                   String correoElectronico, int edad, LocalDate fechaRegistro) {
        this.nombreCompleto = nombreCompleto;
        this.documentoIdentidad = documentoIdentidad;
        this.telefono = telefono;
        this.correoElectronico = correoElectronico;
        this.edad = edad;
        this.fechaRegistro = fechaRegistro != null ? fechaRegistro : LocalDate.now();
    }

    // Getters y Setters
    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getDocumentoIdentidad() {
        return documentoIdentidad;
    }

    public void setDocumentoIdentidad(String documentoIdentidad) {
        this.documentoIdentidad = documentoIdentidad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    /**
     * Verifica si el número de teléfono pasado como parámetro es un número perfecto.
     * Delega la lógica de cálculo a NumeroPerfectoUtil (SRP).
     */
    public boolean esNumeroPerfecto(String telefono) {
        return NumeroPerfectoUtil.esNumeroPerfecto(telefono);
    }

    /**
     * Determina si el número de teléfono registrado del cliente es perfecto.
     */
    public boolean tieneTelefonoPerfecto() {
        return NumeroPerfectoUtil.esNumeroPerfecto(this.telefono);
    }

    @Override
    public String toString() {
        return nombreCompleto + " (" + documentoIdentidad + ") - Tel: " + telefono;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(documentoIdentidad, cliente.documentoIdentidad);
    }

    @Override
    public int hashCode() {
        return Objects.hash(documentoIdentidad);
    }
}