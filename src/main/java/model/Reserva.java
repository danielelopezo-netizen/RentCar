package model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Representa una reserva de alquiler de vehículo realizada por un cliente en RentCar.
 * Asocia al cliente, el vehículo asignado, la modalidad de alquiler contratada
 * y los servicios adicionales solicitados.
 */
public class Reserva {
    private String codigo;
    private Cliente cliente;
    private Vehiculo vehiculo;
    private ModalidadAlquiler modalidad;
    private List<ServicioAdicional> serviciosContratados;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private double descuentoAplicado;

    public static ReservaBuilder builder() {
        return new ReservaBuilder();
    }

    public Reserva(String codigo, Cliente cliente, Vehiculo vehiculo, ModalidadAlquiler modalidad,
                   LocalDate fechaInicio, LocalDate fechaFin, double descuentoAplicado) {
        this(codigo, cliente, vehiculo, modalidad, fechaInicio, fechaFin, descuentoAplicado, new ArrayList<>());
    }

    public Reserva(Cliente cliente, Vehiculo vehiculo, ModalidadAlquiler modalidad,
                   LocalDate fechaInicio, LocalDate fechaFin, double descuentoAplicado) {
        this("RES-" + System.currentTimeMillis() % 10000, cliente, vehiculo, modalidad,
                fechaInicio, fechaFin, descuentoAplicado, new ArrayList<>());
    }

    public Reserva(String codigo, Cliente cliente, Vehiculo vehiculo, ModalidadAlquiler modalidad,
                   LocalDate fechaInicio, LocalDate fechaFin, double descuentoAplicado,
                   List<ServicioAdicional> serviciosContratados) {
        this.codigo = codigo != null ? codigo : ("RES-" + System.currentTimeMillis() % 10000);
        this.cliente = cliente;
        this.vehiculo = vehiculo;
        this.modalidad = modalidad;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.descuentoAplicado = Math.max(0, descuentoAplicado);
        this.serviciosContratados = serviciosContratados != null ? new ArrayList<>(serviciosContratados) : new ArrayList<>();
    }

    public void agregarServicio(ServicioAdicional servicio) {
        if (servicio != null) {
            this.serviciosContratados.add(servicio);
        }
    }

    /**
     * Calcula la duración en días de la reserva.
     * Si las fechas son iguales, se contabiliza como mínimo 1 día.
     */
    public int getDias() {
        if (fechaInicio == null || fechaFin == null) {
            return 1;
        }
        int dias = (int) ChronoUnit.DAYS.between(fechaInicio, fechaFin);
        return Math.max(1, dias);
    }

    /**
     * Calcula el valor final del alquiler:
     * (Costo Modalidad * días) + (Tarifa diaria vehículo * días) + (Servicios adicionales) - Descuento.
     */
    public double calcularTotal() {
        int dias = getDias();

        // 1. Cobro por la modalidad
        double total = (modalidad != null) ? modalidad.calcularValor(dias) : 0.0;

        // 2. Cobro por el vehículo
        if (vehiculo != null) {
            total += vehiculo.getTarifaDiaria() * dias;
        }

        // 3. Sumar servicios adicionales contratados
        for (ServicioAdicional servicio : serviciosContratados) {
            total += servicio.getPrecio();
        }

        // 4. Aplicar descuento
        total -= descuentoAplicado;

        return Math.max(0.0, total);
    }

    // Getters y Setters
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public ModalidadAlquiler getModalidad() {
        return modalidad;
    }

    public void setModalidad(ModalidadAlquiler modalidad) {
        this.modalidad = modalidad;
    }

    public List<ServicioAdicional> getServiciosContratados() {
        return new ArrayList<>(serviciosContratados);
    }

    public void setServiciosContratados(List<ServicioAdicional> serviciosContratados) {
        this.serviciosContratados = serviciosContratados != null ? new ArrayList<>(serviciosContratados) : new ArrayList<>();
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public double getDescuentoAplicado() {
        return descuentoAplicado;
    }

    public void setDescuentoAplicado(double descuentoAplicado) {
        this.descuentoAplicado = Math.max(0, descuentoAplicado);
    }

    public double getTotal() {
        return calcularTotal();
    }

    @Override
    public String toString() {
        return "Reserva [" + codigo + "] - Cliente: " + (cliente != null ? cliente.getNombreCompleto() : "N/A") +
                " | Vehículo: " + (vehiculo != null ? vehiculo.getPlaca() : "N/A") +
                " | Días: " + getDias() + " | Total: $" + String.format("%.2f", calcularTotal());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reserva reserva = (Reserva) o;
        return Objects.equals(codigo, reserva.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }
}
