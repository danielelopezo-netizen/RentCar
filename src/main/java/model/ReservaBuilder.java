package model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * Constructor fluido para la creación controlada de instancias de Reserva.
 * Implementa el patrón creacional Builder y valida la integridad de los datos
 * antes de instanciar el objeto.
 */
public class ReservaBuilder {
    private String codigo;
    private Cliente cliente;
    private Vehiculo vehiculo;
    private ModalidadAlquiler modalidad;
    private List<ServicioAdicional> serviciosContratados = new ArrayList<>();
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private double descuentoAplicado = 0.0;

    public ReservaBuilder() {
    }

    public ReservaBuilder conCodigo(String codigo) {
        this.codigo = codigo;
        return this;
    }

    public ReservaBuilder conCliente(Cliente cliente) {
        this.cliente = cliente;
        return this;
    }

    public ReservaBuilder conVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
        return this;
    }

    public ReservaBuilder conModalidad(ModalidadAlquiler modalidad) {
        this.modalidad = modalidad;
        return this;
    }

    public ReservaBuilder conFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        return this;
    }

    public ReservaBuilder conDescuento(double descuento) {
        this.descuentoAplicado = Math.max(0.0, descuento);
        return this;
    }

    public ReservaBuilder agregarServicio(ServicioAdicional servicio) {
        if (servicio != null) {
            this.serviciosContratados.add(servicio);
        }
        return this;
    }

    public ReservaBuilder conServicios(List<ServicioAdicional> servicios) {
        if (servicios != null) {
            this.serviciosContratados = new ArrayList<>(servicios);
        }
        return this;
    }

    /**
     * Construye y retorna la instancia de Reserva tras validar todas las reglas de negocio.
     *
     * @return Instancia válida de Reserva.
     * @throws IllegalStateException si faltan datos obligatorios (cliente, vehículo, modalidad o fechas).
     * @throws IllegalArgumentException si las fechas son incoherentes o no cumplen la duración mínima de la modalidad.
     */
    public Reserva build() {
        if (cliente == null) {
            throw new IllegalStateException("La reserva debe tener un cliente asignado.");
        }
        if (vehiculo == null) {
            throw new IllegalStateException("La reserva debe tener un vehículo asignado.");
        }
        if (modalidad == null) {
            throw new IllegalStateException("La reserva debe tener una modalidad de alquiler asignada.");
        }
        if (fechaInicio == null || fechaFin == null) {
            throw new IllegalStateException("Las fechas de inicio y fin son obligatorias.");
        }
        if (fechaInicio.isAfter(fechaFin)) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser posterior a la fecha de fin.");
        }

        int dias = (int) ChronoUnit.DAYS.between(fechaInicio, fechaFin);
        dias = Math.max(1, dias);

        if (dias < modalidad.getDuracionMinimaDias()) {
            throw new IllegalArgumentException("La duración seleccionada (" + dias + " días) no cumple con la duración mínima de la modalidad (" +
                    modalidad.getDuracionMinimaDias() + " días).");
        }

        if (codigo == null || codigo.trim().isEmpty()) {
            this.codigo = "RES-" + (System.currentTimeMillis() % 100000);
        }

        return new Reserva(codigo, cliente, vehiculo, modalidad, fechaInicio, fechaFin, descuentoAplicado, serviciosContratados);
    }
}
