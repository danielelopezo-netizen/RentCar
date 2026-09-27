package model;

import enums.EstadoModalidad;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Entidad principal que representa la empresa RentCar y gestiona el modelo de negocio.
 * Administra las colecciones de clientes, vehículos, modalidades de alquiler,
 * servicios adicionales y reservas.
 */
public class Empresa {
    private String nombreComercial;
    private String nit;
    private String direccion;
    private String telefono;
    private String correoElectronico;
    private String paginaWeb;

    // Colecciones de la empresa
    private List<Cliente> clientes;
    private List<Vehiculo> vehiculos;
    private List<ModalidadAlquiler> modalidades;
    private List<ServicioAdicional> serviciosAdicionales;
    private List<Reserva> reservas;

    public Empresa() {
        this("RentCar S.A.S.", "900.567.890-1", "Avenida El Dorado #68-90, Bogotá",
                "6017890123", "servicio@rentcar.com", "www.rentcar.com");
    }

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

    // ==========================================
    // GESTIÓN DE CLIENTES
    // ==========================================
    public void registrarCliente(Cliente cliente) {
        if (cliente != null && !clientes.contains(cliente)) {
            clientes.add(cliente);
        }
    }

    /**
     * Busca un cliente registrado por su número de teléfono.
     * Requerimiento explícito: permite buscar cliente y verificar número perfecto.
     */
    public Cliente buscarClientePorTelefono(String telefono) {
        if (telefono == null || telefono.trim().isEmpty()) {
            return null;
        }
        String buscado = telefono.trim();
        for (Cliente c : clientes) {
            if (c.getTelefono() != null && c.getTelefono().trim().equals(buscado)) {
                return c;
            }
        }
        return null;
    }

    public Cliente buscarClientePorDocumento(String documento) {
        if (documento == null || documento.trim().isEmpty()) {
            return null;
        }
        String docBuscado = documento.trim();
        for (Cliente c : clientes) {
            if (c.getDocumentoIdentidad() != null && c.getDocumentoIdentidad().trim().equalsIgnoreCase(docBuscado)) {
                return c;
            }
        }
        return null;
    }

    // ==========================================
    // GESTIÓN DE VEHÍCULOS
    // ==========================================
    public void registrarVehiculo(Vehiculo vehiculo) {
        if (vehiculo != null && !vehiculos.contains(vehiculo)) {
            vehiculos.add(vehiculo);
        }
    }

    public Vehiculo buscarVehiculoPorPlaca(String placa) {
        if (placa == null || placa.trim().isEmpty()) {
            return null;
        }
        String placaBuscada = placa.trim().toUpperCase();
        for (Vehiculo v : vehiculos) {
            if (v.getPlaca() != null && v.getPlaca().equalsIgnoreCase(placaBuscada)) {
                return v;
            }
        }
        return null;
    }

    // ==========================================
    // GESTIÓN DE MODALIDADES Y SERVICIOS
    // ==========================================
    public void registrarModalidad(ModalidadAlquiler modalidad) {
        if (modalidad != null) {
            modalidades.add(modalidad);
        }
    }

    public ModalidadAlquiler buscarModalidadPorCodigo(String codigo) {
        if (codigo == null) return null;
        for (ModalidadAlquiler m : modalidades) {
            if (m.getCodigo() != null && m.getCodigo().equalsIgnoreCase(codigo.trim())) {
                return m;
            }
        }
        return null;
    }

    public void registrarServicioAdicional(ServicioAdicional servicio) {
        if (servicio != null) {
            serviciosAdicionales.add(servicio);
        }
    }

    public ServicioAdicional buscarServicioPorCodigo(String codigo) {
        if (codigo == null) return null;
        for (ServicioAdicional s : serviciosAdicionales) {
            if (s.getCodigo() != null && s.getCodigo().equalsIgnoreCase(codigo.trim())) {
                return s;
            }
        }
        return null;
    }

    // ==========================================
    // GESTIÓN DE RESERVAS E INGRESOS
    // ==========================================
    public void crearReserva(Reserva reserva) {
        if (reserva != null) {
            reservas.add(reserva);
        }
    }

    /**
     * Calcula los ingresos generados por los alquileres dentro de un periodo determinado.
     * Identifica las reservas realizadas dentro del periodo y acumula el valor total.
     */
    public double calcularIngresos(LocalDate fechaInicioPeriodo, LocalDate fechaFinPeriodo) {
        if (fechaInicioPeriodo == null || fechaFinPeriodo == null) {
            return 0.0;
        }

        double ingresosTotales = 0.0;
        for (Reserva reserva : reservas) {
            LocalDate fechaReserva = reserva.getFechaInicio();
            if (fechaReserva != null) {
                boolean despuesOIgualInicio = !fechaReserva.isBefore(fechaInicioPeriodo);
                boolean antesOIgualFin = !fechaReserva.isAfter(fechaFinPeriodo);

                if (despuesOIgualInicio && antesOIgualFin) {
                    ingresosTotales += reserva.calcularTotal();
                }
            }
        }
        return ingresosTotales;
    }

    /**
     * Retorna la lista de reservas registradas dentro del rango de fechas consultado.
     */
    public List<Reserva> buscarReservasEnPeriodo(LocalDate fechaInicioPeriodo, LocalDate fechaFinPeriodo) {
        if (fechaInicioPeriodo == null || fechaFinPeriodo == null) {
            return Collections.emptyList();
        }

        List<Reserva> resultado = new ArrayList<>();
        for (Reserva reserva : reservas) {
            LocalDate fechaReserva = reserva.getFechaInicio();
            if (fechaReserva != null) {
                boolean despuesOIgualInicio = !fechaReserva.isBefore(fechaInicioPeriodo);
                boolean antesOIgualFin = !fechaReserva.isAfter(fechaFinPeriodo);

                if (despuesOIgualInicio && antesOIgualFin) {
                    resultado.add(reserva);
                }
            }
        }
        return resultado;
    }

    /**
     * Carga datos iniciales de prueba para facilitar la demostración de la aplicación.
     */
    public void inicializarDatosPrueba() {
        if (!clientes.isEmpty()) {
            return; // Ya inicializado
        }

        // 1. Clientes (incluyendo un teléfono con número perfecto, ej: 28 y 6)
        Cliente c1 = new Cliente("Carlos Mendoza", "1018293847", "28", "carlos.mendoza@email.com", 32, LocalDate.now().minusDays(30));
        Cliente c2 = new Cliente("María González", "52938472", "3004567890", "maria.gonzalez@email.com", 28, LocalDate.now().minusDays(15));
        Cliente c3 = new Cliente("Andrés Gómez", "1020304050", "6", "andres.gomez@email.com", 45, LocalDate.now().minusDays(60));
        registrarCliente(c1);
        registrarCliente(c2);
        registrarCliente(c3);

        // 2. Vehículos
        Vehiculo v1 = new Vehiculo("ABC123", "Toyota", "Corolla", 2023, "Sedán", 120000.0);
        Vehiculo v2 = new Vehiculo("XYZ789", "Mazda", "CX-5", 2024, "SUV", 180000.0);
        Vehiculo v3 = new Vehiculo("LMN456", "BMW", "Serie 3", 2024, "Lujo", 320000.0);
        Vehiculo v4 = new Vehiculo("QWE987", "Renault", "Kwid", 2022, "Hatchback", 85000.0);
        registrarVehiculo(v1);
        registrarVehiculo(v2);
        registrarVehiculo(v3);
        registrarVehiculo(v4);

        // 3. Modalidades
        ModalidadAlquiler modEco = ModalidadFactory.crearModalidad("ECONOMICA", "MOD-ECO", "Económica",
                "Ideal para viajes urbanos cotidianos", 1, 40000.0, EstadoModalidad.DISPONIBLE,
                List.of("Kilometraje 100km/día", "Seguro básico"));
        ModalidadAlquiler modEje = ModalidadFactory.crearModalidad("EJECUTIVA", "MOD-EJE", "Ejecutiva",
                "Para viajes de negocios con comodidad superior", 2, 75000.0, EstadoModalidad.DISPONIBLE,
                List.of("Kilometraje ilimitado", "Seguro todo riesgo", "Asistencia en carretera 24/7"));
        ModalidadAlquiler modPrem = ModalidadFactory.crearModalidadPremium("MOD-PREM", "Premium",
                "Máxima exclusividad y libertad total", 3, 130000.0, EstadoModalidad.DISPONIBLE,
                List.of("Kilometraje ilimitado", "Cobertura total sin deducible", "Asistencia VIP"),
                "Cobertura Total Todo Riesgo", 2, "Entrega y devolución a domicilio VIP");
        registrarModalidad(modEco);
        registrarModalidad(modEje);
        registrarModalidad(modPrem);

        // 4. Servicios Adicionales
        ServicioAdicional s1 = new ServicioAdicional("SRV-GPS", "Navegador GPS", "GPS con mapas actualizados en tiempo real", 25000.0, true);
        ServicioAdicional s2 = new ServicioAdicional("SRV-BEBE", "Silla para Bebé", "Silla ergonómica certificada para infantes", 35000.0, true);
        ServicioAdicional s3 = new ServicioAdicional("SRV-COND", "Conductor Adicional", "Permite registrar un conductor extra autorizado", 50000.0, true);
        ServicioAdicional s4 = new ServicioAdicional("SRV-SEG", "Seguro Complementario", "Ampliación de cobertura contra robos menores", 45000.0, true);
        registrarServicioAdicional(s1);
        registrarServicioAdicional(s2);
        registrarServicioAdicional(s3);
        registrarServicioAdicional(s4);

        // 5. Reservas de prueba
        Reserva r1 = new Reserva("RES-101", c1, v1, modEco, LocalDate.now().minusDays(10), LocalDate.now().minusDays(7), 10000.0);
        r1.agregarServicio(s1);
        crearReserva(r1);

        Reserva r2 = new Reserva("RES-102", c2, v2, modEje, LocalDate.now().minusDays(5), LocalDate.now().minusDays(1), 0.0);
        r2.agregarServicio(s1);
        r2.agregarServicio(s3);
        crearReserva(r2);

        Reserva r3 = new Reserva("RES-103", c3, v3, modPrem, LocalDate.now(), LocalDate.now().plusDays(4), 20000.0);
        r3.agregarServicio(s4);
        crearReserva(r3);
    }

    // ==========================================
    // GETTERS Y SETTERS
    // ==========================================
    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
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

    public String getPaginaWeb() {
        return paginaWeb;
    }

    public void setPaginaWeb(String paginaWeb) {
        this.paginaWeb = paginaWeb;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public List<Vehiculo> getVehiculos() {
        return vehiculos;
    }

    public List<ModalidadAlquiler> getModalidades() {
        return modalidades;
    }

    public List<ServicioAdicional> getServiciosAdicionales() {
        return serviciosAdicionales;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }
}
