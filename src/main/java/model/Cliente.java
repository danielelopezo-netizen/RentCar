package model;

import java.time.LocalDate;

public class Cliente {
    private String nombreCompleto;
    private String documentoIdentidad;
    private String telefono;
    private String correoElectronico;
    private int edad;
    private LocalDate fechaRegistro;

    // Constructor
    public Cliente(String nombreCompleto, String documentoIdentidad, String telefono,
                   String correoElectronico, int edad, LocalDate fechaRegistro) {
        this.nombreCompleto = nombreCompleto;
        this.documentoIdentidad = documentoIdentidad;
        this.telefono = telefono;
        this.correoElectronico = correoElectronico;
        this.edad = edad;
        this.fechaRegistro = fechaRegistro;
    }

    // Getters y Setters
    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public String getDocumentoIdentidad() { return documentoIdentidad; }
    public void setDocumentoIdentidad(String documentoIdentidad) { this.documentoIdentidad = documentoIdentidad; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getCorreoElectronico() { return correoElectronico; }
    public void setCorreoElectronico(String correoElectronico) { this.correoElectronico = correoElectronico; }

    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }

    public LocalDate getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDate fechaRegistro) { this.fechaRegistro = fechaRegistro; }

    // El método de número perfecto lo dejaremos como esqueleto por ahora
    public boolean esNumeroPerfecto(String telefono) {
        // TODO: Implementar lógica
        return false;
    }
}
