package model;

import enums.EstadoModalidad;

// Clase Padre (Abstracta)
public abstract class ModalidadAlquiler {
    protected String codigo;
    protected String nombre;
    protected String descripcion;
    protected int duracionMinimaDias;
    protected double valorDiario;
    protected EstadoModalidad estado;

    public ModalidadAlquiler(String codigo, String nombre, String descripcion,
                             int duracionMinimaDias, double valorDiario, EstadoModalidad estado) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.duracionMinimaDias = duracionMinimaDias;
        this.valorDiario = valorDiario;
        this.estado = estado;
    }

    // Método que cada hija implementará a su manera
    public abstract double calcularValor(int dias);

    // Getters y Setters (Omitidos por brevedad, pero debes agregarlos todos)
    public double getValorDiario() { return valorDiario; }
    // ...
}