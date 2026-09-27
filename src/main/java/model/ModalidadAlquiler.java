package model;

import enums.EstadoModalidad;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase base abstracta para las modalidades de alquiler ofrecidas por RentCar.
 * Aplica el principio Abierto/Cerrado (OCP) y el principio de Sustitución de Liskov (LSP).
 */
public abstract class ModalidadAlquiler {
    protected String codigo;
    protected String nombre;
    protected String descripcion;
    protected int duracionMinimaDias;
    protected double valorDiario;
    protected EstadoModalidad estado;
    protected List<String> beneficios;

    public ModalidadAlquiler(String codigo, String nombre, String descripcion,
                             int duracionMinimaDias, double valorDiario, EstadoModalidad estado) {
        this(codigo, nombre, descripcion, duracionMinimaDias, valorDiario, estado, new ArrayList<>());
    }

    public ModalidadAlquiler(String codigo, String nombre, String descripcion,
                             int duracionMinimaDias, double valorDiario, EstadoModalidad estado,
                             List<String> beneficios) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.duracionMinimaDias = duracionMinimaDias;
        this.valorDiario = valorDiario;
        this.estado = estado;
        this.beneficios = beneficios != null ? new ArrayList<>(beneficios) : new ArrayList<>();
    }

    /**
     * Calcula el costo base de la modalidad según la duración en días contratada.
     * Cada subclase implementa su propio cálculo tarifario.
     *
     * @param dias Número de días del alquiler.
     * @return Costo de la modalidad para el período indicado.
     */
    public abstract double calcularValor(int dias);

    public void agregarBeneficio(String beneficio) {
        if (beneficio != null && !beneficio.trim().isEmpty()) {
            this.beneficios.add(beneficio.trim());
        }
    }

    // Getters y Setters
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getDuracionMinimaDias() {
        return duracionMinimaDias;
    }

    public void setDuracionMinimaDias(int duracionMinimaDias) {
        this.duracionMinimaDias = duracionMinimaDias;
    }

    public double getValorDiario() {
        return valorDiario;
    }

    public void setValorDiario(double valorDiario) {
        this.valorDiario = valorDiario;
    }

    public EstadoModalidad getEstado() {
        return estado;
    }

    public void setEstado(EstadoModalidad estado) {
        this.estado = estado;
    }

    public List<String> getBeneficios() {
        return new ArrayList<>(beneficios);
    }

    public void setBeneficios(List<String> beneficios) {
        this.beneficios = beneficios != null ? new ArrayList<>(beneficios) : new ArrayList<>();
    }

    @Override
    public String toString() {
        return nombre + " ($" + String.format("%.0f", valorDiario) + "/día)";
    }
}