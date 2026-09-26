package model;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Reserva {
    private Cliente cliente;
    private Vehiculo vehiculo;
    private ModalidadAlquiler modalidad;
    private List<ServicioAdicional> serviciosContratados;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private double descuentoAplicado;

    public Reserva(Cliente cliente, Vehiculo vehiculo, ModalidadAlquiler modalidad,
                   LocalDate fechaInicio, LocalDate fechaFin, double descuentoAplicado) {
        this.cliente = cliente;
        this.vehiculo = vehiculo;
        this.modalidad = modalidad;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.descuentoAplicado = descuentoAplicado;
        this.serviciosContratados = new ArrayList<>(); // Inicializamos la lista
    }

    public void agregarServicio(ServicioAdicional servicio) {
        this.serviciosContratados.add(servicio);
    }

    public double calcularTotal() {
        int dias = (int) ChronoUnit.DAYS.between(fechaInicio, fechaFin);

        // 1. Cobro por la modalidad
        double total = modalidad.calcularValor(dias);

        // 2. Cobro por el vehículo
        total += vehiculo.getTarifaDiaria() * dias;

        // 3. Sumar servicios adicionales
        for (ServicioAdicional servicio : serviciosContratados) {
            total += servicio.getPrecio();
        }

        // 4. Aplicar descuento
        total -= descuentoAplicado;

        return total;
    }

    // Faltarían los getters y setters...
}
