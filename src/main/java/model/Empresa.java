package model;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Empresa {
    private String nombreComercial;
    private String nit;
    private String direccion;
    private String telefono;
    private String correoElectronico;
    private String paginaWeb;

    // Listas para almacenar toda la información del negocio
    private List<Cliente> clientes;
    private List<Vehiculo> vehiculos;
    private List<ModalidadAlquiler> modalidades;
    private List<ServicioAdicional> serviciosAdicionales;
    private List<Reserva> reservas;

    public Empresa(String nombreComercial, String nit, String direccion,
                   String telefono, String correoElectronico, String paginaWeb) {
        this.nombreComercial = nombreComercial;
        this.nit = nit;
        this.direccion = direccion;
        this.telefono = telefono;
        this.correoElectronico = correoElectronico;
        this.paginaWeb = paginaWeb;

        this.clientes = new ArrayList<>();
        this.vehiculos = new ArrayList<>();
        this.modalidades = new ArrayList<>();
        this.serviciosAdicionales = new ArrayList<>();
        this.reservas = new ArrayList<>();
    }

    // Métodos de registro básicos (el Controlador MVC llamará a estos métodos)
    public void registrarCliente(Cliente cliente) {
        clientes.add(cliente);
    }

    public void registrarVehiculo(Vehiculo vehiculo) {
        vehiculos.add(vehiculo);
    }

    public void crearReserva(Reserva reserva) {
        reservas.add(reserva);
    }

    // Aquí luego implementaremos el cálculo de ingresos por fechas...
    public double calcularIngresos(LocalDate inicio, LocalDate fin) {
        // TODO: Lógica de ingresos
        return 0.0;
    }
}
