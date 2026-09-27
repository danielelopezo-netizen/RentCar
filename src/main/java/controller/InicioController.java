package controller;

import enums.EstadoModalidad;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.*;
import util.NumeroPerfectoUtil;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controlador de la vista principal del sistema RentCar (Arquitectura MVC).
 * Gestiona los eventos de la interfaz gráfica y coordina las operaciones con el modelo.
 */
public class InicioController implements Initializable {

    private final Empresa empresa = Empresa.getInstance();
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // ==========================================
    // FXML: CLIENTES
    // ==========================================
    @FXML private TextField txtCliNombre;
    @FXML private TextField txtCliDoc;
    @FXML private TextField txtCliTelefono;
    @FXML private TextField txtCliCorreo;
    @FXML private TextField txtCliEdad;

    @FXML private TextField txtTelefono;
    @FXML private Label lblResultadoTelefono;

    @FXML private TableView<Cliente> tblClientes;
    @FXML private TableColumn<Cliente, String> colCliNombre;
    @FXML private TableColumn<Cliente, String> colCliDoc;
    @FXML private TableColumn<Cliente, String> colCliTel;
    @FXML private TableColumn<Cliente, String> colCliCorreo;
    @FXML private TableColumn<Cliente, Integer> colCliEdad;
    @FXML private TableColumn<Cliente, String> colCliFecha;
    @FXML private TableColumn<Cliente, String> colCliPerfecto;

    // ==========================================
    // FXML: VEHÍCULOS
    // ==========================================
    @FXML private TextField txtVehPlaca;
    @FXML private TextField txtVehMarca;
    @FXML private TextField txtVehModelo;
    @FXML private TextField txtVehAnio;
    @FXML private ComboBox<String> cbVehTipo;
    @FXML private TextField txtVehTarifa;

    @FXML private TableView<Vehiculo> tblVehiculos;
    @FXML private TableColumn<Vehiculo, String> colVehPlaca;
    @FXML private TableColumn<Vehiculo, String> colVehMarca;
    @FXML private TableColumn<Vehiculo, String> colVehModelo;
    @FXML private TableColumn<Vehiculo, Integer> colVehAnio;
    @FXML private TableColumn<Vehiculo, String> colVehTipo;
    @FXML private TableColumn<Vehiculo, String> colVehTarifa;

    // ==========================================
    // FXML: MODALIDADES Y SERVICIOS
    // ==========================================
    @FXML private TableView<ModalidadAlquiler> tblModalidades;
    @FXML private TableColumn<ModalidadAlquiler, String> colModCodigo;
    @FXML private TableColumn<ModalidadAlquiler, String> colModNombre;
    @FXML private TableColumn<ModalidadAlquiler, Integer> colModMinDias;
    @FXML private TableColumn<ModalidadAlquiler, String> colModValorDiario;
    @FXML private TableColumn<ModalidadAlquiler, String> colModEstado;
    @FXML private TableColumn<ModalidadAlquiler, String> colModBeneficios;

    @FXML private TableView<ServicioAdicional> tblServicios;
    @FXML private TableColumn<ServicioAdicional, String> colSrvCodigo;
    @FXML private TableColumn<ServicioAdicional, String> colSrvNombre;
    @FXML private TableColumn<ServicioAdicional, String> colSrvPrecio;
    @FXML private TableColumn<ServicioAdicional, String> colSrvDisponible;
    @FXML private TableColumn<ServicioAdicional, String> colSrvDesc;

    // ==========================================
    // FXML: RESERVAS
    // ==========================================
    @FXML private ComboBox<Cliente> cbResCliente;
    @FXML private ComboBox<Vehiculo> cbResVehiculo;
    @FXML private ComboBox<ModalidadAlquiler> cbResModalidad;
    @FXML private DatePicker dpResInicio;
    @FXML private DatePicker dpResFin;
    @FXML private TextField txtResDescuento;
    @FXML private ListView<ServicioAdicional> lvServiciosDisponibles;
    @FXML private Label lblTotalCotizacion;

    @FXML private TableView<Reserva> tblReservas;
    @FXML private TableColumn<Reserva, String> colResCodigo;
    @FXML private TableColumn<Reserva, String> colResCliente;
    @FXML private TableColumn<Reserva, String> colResVehiculo;
    @FXML private TableColumn<Reserva, String> colResModalidad;
    @FXML private TableColumn<Reserva, String> colResFechas;
    @FXML private TableColumn<Reserva, Integer> colResDias;
    @FXML private TableColumn<Reserva, String> colResTotal;

    // ==========================================
    // FXML: REPORTES E INGRESOS
    // ==========================================
    @FXML private DatePicker dpFechaInicio;
    @FXML private DatePicker dpFechaFin;
    @FXML private Label lblResultadoIngresos;

    @FXML private TableView<Reserva> tblReservasPeriodo;
    @FXML private TableColumn<Reserva, String> colPerCodigo;
    @FXML private TableColumn<Reserva, String> colPerCliente;
    @FXML private TableColumn<Reserva, String> colPerVehiculo;
    @FXML private TableColumn<Reserva, String> colPerModalidad;
    @FXML private TableColumn<Reserva, String> colPerFechas;
    @FXML private TableColumn<Reserva, String> colPerTotal;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configurarTablas();
        configurarControles();
        refrescarTodo();
    }

    // =========================================================================
    // CONFIGURACIÓN DE TABLAS Y CONTROLES
    // =========================================================================
    private void configurarTablas() {
        // Clientes
        colCliNombre.setCellValueFactory(new PropertyValueFactory<>("nombreCompleto"));
        colCliDoc.setCellValueFactory(new PropertyValueFactory<>("documentoIdentidad"));
        colCliTel.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colCliCorreo.setCellValueFactory(new PropertyValueFactory<>("correoElectronico"));
        colCliEdad.setCellValueFactory(new PropertyValueFactory<>("edad"));
        colCliFecha.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue().getFechaRegistro() != null ? cell.getValue().getFechaRegistro().format(dateFormatter) : ""
        ));
        colCliPerfecto.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue().tieneTelefonoPerfecto() ? "SÍ (Perfecto)" : "No"
        ));

        // Vehículos
        colVehPlaca.setCellValueFactory(new PropertyValueFactory<>("placa"));
        colVehMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        colVehModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        colVehAnio.setCellValueFactory(new PropertyValueFactory<>("anio"));
        colVehTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colVehTarifa.setCellValueFactory(cell -> new SimpleStringProperty(
                String.format("$%,.2f", cell.getValue().getTarifaDiaria())
        ));

        // Modalidades
        colModCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colModNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colModMinDias.setCellValueFactory(new PropertyValueFactory<>("duracionMinimaDias"));
        colModValorDiario.setCellValueFactory(cell -> new SimpleStringProperty(
                String.format("$%,.2f", cell.getValue().getValorDiario())
        ));
        colModEstado.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue().getEstado() != null ? cell.getValue().getEstado().name() : ""
        ));
        colModBeneficios.setCellValueFactory(cell -> {
            ModalidadAlquiler m = cell.getValue();
            String ben = String.join(", ", m.getBeneficios());
            if (m instanceof ModalidadPremium) {
                ModalidadPremium prem = (ModalidadPremium) m;
                ben += " [Cobertura: " + prem.getTipoCobertura() + " | Conduc. Extra: " + prem.getConductoresAdicionales() + "]";
            }
            return new SimpleStringProperty(ben);
        });

        // Servicios
        colSrvCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colSrvNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colSrvPrecio.setCellValueFactory(cell -> new SimpleStringProperty(
                String.format("$%,.2f", cell.getValue().getPrecio())
        ));
        colSrvDisponible.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue().isDisponible() ? "Disponible" : "Agotado"
        ));
        colSrvDesc.setCellValueFactory(new PropertyValueFactory<>("descripcion"));

        // Reservas
        colResCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colResCliente.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue().getCliente() != null ? cell.getValue().getCliente().getNombreCompleto() : ""
        ));
        colResVehiculo.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue().getVehiculo() != null ? (cell.getValue().getVehiculo().getMarca() + " " + cell.getValue().getVehiculo().getModelo() + " (" + cell.getValue().getVehiculo().getPlaca() + ")") : ""
        ));
        colResModalidad.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue().getModalidad() != null ? cell.getValue().getModalidad().getNombre() : ""
        ));
        colResFechas.setCellValueFactory(cell -> new SimpleStringProperty(
                (cell.getValue().getFechaInicio() != null ? cell.getValue().getFechaInicio().format(dateFormatter) : "") + " al " +
                (cell.getValue().getFechaFin() != null ? cell.getValue().getFechaFin().format(dateFormatter) : "")
        ));
        colResDias.setCellValueFactory(new PropertyValueFactory<>("dias"));
        colResTotal.setCellValueFactory(cell -> new SimpleStringProperty(
                String.format("$%,.2f", cell.getValue().calcularTotal())
        ));

        // Reservas en periodo de consulta
        colPerCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colPerCliente.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue().getCliente() != null ? cell.getValue().getCliente().getNombreCompleto() : ""
        ));
        colPerVehiculo.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue().getVehiculo() != null ? cell.getValue().getVehiculo().getPlaca() : ""
        ));
        colPerModalidad.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue().getModalidad() != null ? cell.getValue().getModalidad().getNombre() : ""
        ));
        colPerFechas.setCellValueFactory(cell -> new SimpleStringProperty(
                (cell.getValue().getFechaInicio() != null ? cell.getValue().getFechaInicio().format(dateFormatter) : "") + " al " +
                (cell.getValue().getFechaFin() != null ? cell.getValue().getFechaFin().format(dateFormatter) : "")
        ));
        colPerTotal.setCellValueFactory(cell -> new SimpleStringProperty(
                String.format("$%,.2f", cell.getValue().calcularTotal())
        ));
    }

    private void configurarControles() {
        cbVehTipo.setItems(FXCollections.observableArrayList(
                "Sedán", "SUV", "Hatchback", "Camioneta", "Lujo", "Deportivo", "Van"
        ));

        lvServiciosDisponibles.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // Fechas por defecto en reserva: hoy y dentro de 3 días
        dpResInicio.setValue(LocalDate.now());
        dpResFin.setValue(LocalDate.now().plusDays(3));

        // Fechas por defecto en reporte: último mes
        dpFechaInicio.setValue(LocalDate.now().minusDays(30));
        dpFechaFin.setValue(LocalDate.now().plusDays(10));
    }

    private void refrescarTodo() {
        // Tablas
        tblClientes.setItems(FXCollections.observableArrayList(empresa.getClientes()));
        tblVehiculos.setItems(FXCollections.observableArrayList(empresa.getVehiculos()));
        tblModalidades.setItems(FXCollections.observableArrayList(empresa.getModalidades()));
        tblServicios.setItems(FXCollections.observableArrayList(empresa.getServiciosAdicionales()));
        tblReservas.setItems(FXCollections.observableArrayList(empresa.getReservas()));

        // Combos para reservas
        cbResCliente.setItems(FXCollections.observableArrayList(empresa.getClientes()));
        cbResVehiculo.setItems(FXCollections.observableArrayList(empresa.getVehiculos()));
        cbResModalidad.setItems(FXCollections.observableArrayList(empresa.getModalidades()));
        lvServiciosDisponibles.setItems(FXCollections.observableArrayList(empresa.getServiciosAdicionales()));
    }

    // =========================================================================
    // EVENTOS: CLIENTES
    // =========================================================================
    @FXML
    void registrarCliente(ActionEvent event) {
        String nombre = txtCliNombre.getText() != null ? txtCliNombre.getText().trim() : "";
        String doc = txtCliDoc.getText() != null ? txtCliDoc.getText().trim() : "";
        String tel = txtCliTelefono.getText() != null ? txtCliTelefono.getText().trim() : "";
        String correo = txtCliCorreo.getText() != null ? txtCliCorreo.getText().trim() : "";
        String edadStr = txtCliEdad.getText() != null ? txtCliEdad.getText().trim() : "";

        if (nombre.isEmpty() || doc.isEmpty() || tel.isEmpty() || correo.isEmpty() || edadStr.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos Incompletos", "Por favor diligencie todos los campos del cliente.");
            return;
        }

        int edad;
        try {
            edad = Integer.parseInt(edadStr);
            if (edad < 18 || edad > 100) {
                mostrarAlerta(Alert.AlertType.WARNING, "Edad Inválida", "El cliente debe tener al menos 18 años para alquilar vehículos.");
                return;
            }
        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Formato Inválido", "La edad debe ser un número entero.");
            return;
        }

        Cliente nuevo = new Cliente(nombre, doc, tel, correo, edad, LocalDate.now());
        empresa.registrarCliente(nuevo);

        refrescarTodo();

        txtCliNombre.clear();
        txtCliDoc.clear();
        txtCliTelefono.clear();
        txtCliCorreo.clear();
        txtCliEdad.clear();

        mostrarAlerta(Alert.AlertType.INFORMATION, "Registro Exitoso", "Cliente registrado correctamente en el sistema.");
    }

    @FXML
    void verificarTelefonoCliente(ActionEvent event) {
        String telefono = txtTelefono.getText() != null ? txtTelefono.getText().trim() : "";

        if (telefono.isEmpty()) {
            lblResultadoTelefono.setStyle("-fx-text-fill: #e74c3c; -fx-font-weight: bold;");
            lblResultadoTelefono.setText("Por favor, ingrese un número de teléfono.");
            return;
        }

        // Buscar cliente en la empresa (Patrón Singleton) y verificar si es número perfecto
        Cliente cliente = empresa.buscarClientePorTelefono(telefono);
        boolean esPerfecto = NumeroPerfectoUtil.esNumeroPerfecto(telefono);

        if (cliente != null) {
            if (esPerfecto) {
                lblResultadoTelefono.setStyle("-fx-text-fill: #2ecc71; -fx-font-weight: bold;");
                lblResultadoTelefono.setText("¡Cliente Encontrado! " + cliente.getNombreCompleto() + "\nSu teléfono (" + telefono + ") SÍ corresponde a un NÚMERO PERFECTO.");
            } else {
                lblResultadoTelefono.setStyle("-fx-text-fill: #e67e22; -fx-font-weight: bold;");
                lblResultadoTelefono.setText("Cliente Encontrado: " + cliente.getNombreCompleto() + "\nSu teléfono (" + telefono + ") NO es un número perfecto.");
            }
        } else {
            if (esPerfecto) {
                lblResultadoTelefono.setStyle("-fx-text-fill: #3498db; -fx-font-weight: bold;");
                lblResultadoTelefono.setText("El número " + telefono + " SÍ es un número perfecto (no registrado con ningún cliente).");
            } else {
                lblResultadoTelefono.setStyle("-fx-text-fill: #e74c3c; -fx-font-weight: bold;");
                lblResultadoTelefono.setText("Cliente no registrado y el número " + telefono + " NO es un número perfecto.");
            }
        }
    }

    // =========================================================================
    // EVENTOS: VEHÍCULOS
    // =========================================================================
    @FXML
    void registrarVehiculo(ActionEvent event) {
        String placa = txtVehPlaca.getText() != null ? txtVehPlaca.getText().trim() : "";
        String marca = txtVehMarca.getText() != null ? txtVehMarca.getText().trim() : "";
        String modelo = txtVehModelo.getText() != null ? txtVehModelo.getText().trim() : "";
        String anioStr = txtVehAnio.getText() != null ? txtVehAnio.getText().trim() : "";
        String tipo = cbVehTipo.getValue();
        String tarifaStr = txtVehTarifa.getText() != null ? txtVehTarifa.getText().trim() : "";

        if (placa.isEmpty() || marca.isEmpty() || modelo.isEmpty() || anioStr.isEmpty() || tipo == null || tarifaStr.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos Incompletos", "Por favor diligencie todos los datos del vehículo.");
            return;
        }

        int anio;
        double tarifa;
        try {
            anio = Integer.parseInt(anioStr);
            tarifa = Double.parseDouble(tarifaStr);
        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Datos Inválidos", "Año y Tarifa deben ser valores numéricos válidos.");
            return;
        }

        Vehiculo v = new Vehiculo(placa, marca, modelo, anio, tipo, tarifa, true);
        empresa.registrarVehiculo(v);

        refrescarTodo();

        txtVehPlaca.clear();
        txtVehMarca.clear();
        txtVehModelo.clear();
        txtVehAnio.clear();
        cbVehTipo.setValue(null);
        txtVehTarifa.clear();

        mostrarAlerta(Alert.AlertType.INFORMATION, "Registro Exitoso", "Vehículo registrado en la flota correctamente.");
    }

    // =========================================================================
    // EVENTOS: RESERVAS (PATRÓN BUILDER)
    // =========================================================================
    @FXML
    void calcularCotizacion(ActionEvent event) {
        Cliente cliente = cbResCliente.getValue();
        Vehiculo vehiculo = cbResVehiculo.getValue();
        ModalidadAlquiler modalidad = cbResModalidad.getValue();
        LocalDate inicio = dpResInicio.getValue();
        LocalDate fin = dpResFin.getValue();

        if (modalidad == null || vehiculo == null || inicio == null || fin == null) {
            lblTotalCotizacion.setText("Seleccione vehículo, modalidad y fechas.");
            lblTotalCotizacion.setStyle("-fx-text-fill: #e74c3c;");
            return;
        }

        if (inicio.isAfter(fin)) {
            lblTotalCotizacion.setText("Fecha inicio debe ser menor o igual a fecha fin.");
            lblTotalCotizacion.setStyle("-fx-text-fill: #e74c3c;");
            return;
        }

        double descuento = 0.0;
        try {
            descuento = Double.parseDouble(txtResDescuento.getText().trim());
        } catch (Exception ignored) {}

        List<ServicioAdicional> seleccionados = lvServiciosDisponibles.getSelectionModel().getSelectedItems();

        try {
            Reserva temp = Reserva.builder()
                    .conCliente(cliente != null ? cliente : new Cliente("Temp", "0", "0", "t@t.com", 20, LocalDate.now()))
                    .conVehiculo(vehiculo)
                    .conModalidad(modalidad)
                    .conFechas(inicio, fin)
                    .conDescuento(descuento)
                    .conServicios(seleccionados)
                    .build();

            lblTotalCotizacion.setStyle("-fx-text-fill: #27ae60; -fx-font-weight: bold; -fx-font-size: 15px;");
            lblTotalCotizacion.setText("Cotización (" + temp.getDias() + " días): $" + String.format("%,.2f", temp.calcularTotal()));
        } catch (Exception ex) {
            lblTotalCotizacion.setStyle("-fx-text-fill: #e74c3c;");
            lblTotalCotizacion.setText(ex.getMessage());
        }
    }

    @FXML
    void crearReserva(ActionEvent event) {
        Cliente cliente = cbResCliente.getValue();
        Vehiculo vehiculo = cbResVehiculo.getValue();
        ModalidadAlquiler modalidad = cbResModalidad.getValue();
        LocalDate inicio = dpResInicio.getValue();
        LocalDate fin = dpResFin.getValue();

        double descuento = 0.0;
        try {
            String descStr = txtResDescuento.getText() != null ? txtResDescuento.getText().trim() : "0";
            if (!descStr.isEmpty()) {
                descuento = Double.parseDouble(descStr);
            }
        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Descuento Inválido", "El descuento debe ser un número.");
            return;
        }

        List<ServicioAdicional> servicios = lvServiciosDisponibles.getSelectionModel().getSelectedItems();

        try {
            // Aplicación del Patrón Creacional Builder
            Reserva nuevaReserva = Reserva.builder()
                    .conCliente(cliente)
                    .conVehiculo(vehiculo)
                    .conModalidad(modalidad)
                    .conFechas(inicio, fin)
                    .conDescuento(descuento)
                    .conServicios(servicios)
                    .build();

            empresa.crearReserva(nuevaReserva);
            refrescarTodo();

            mostrarAlerta(Alert.AlertType.INFORMATION, "Reserva Creada",
                    "Reserva " + nuevaReserva.getCodigo() + " creada exitosamente.\nTotal a pagar: $" +
                    String.format("%,.2f", nuevaReserva.calcularTotal()));

            txtResDescuento.setText("0");
            lvServiciosDisponibles.getSelectionModel().clearSelection();
            lblTotalCotizacion.setText("Total: $0.0");
        } catch (IllegalStateException | IllegalArgumentException ex) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error al Crear Reserva", ex.getMessage());
        }
    }

    // =========================================================================
    // EVENTOS: REPORTES E INGRESOS
    // =========================================================================
    @FXML
    void calcularIngresosPeriodo(ActionEvent event) {
        LocalDate fechaInicio = dpFechaInicio.getValue();
        LocalDate fechaFin = dpFechaFin.getValue();

        if (fechaInicio == null || fechaFin == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Fechas Requeridas", "Por favor seleccione ambas fechas del periodo.");
            return;
        }

        if (fechaInicio.isAfter(fechaFin)) {
            mostrarAlerta(Alert.AlertType.ERROR, "Rango Inválido", "La fecha de inicio debe ser anterior o igual a la final.");
            return;
        }

        // Consultamos el modelo de negocio
        double total = empresa.calcularIngresos(fechaInicio, fechaFin);
        List<Reserva> reservasPeriodo = empresa.buscarReservasEnPeriodo(fechaInicio, fechaFin);

        lblResultadoIngresos.setStyle("-fx-text-fill: #2c3e50; -fx-font-weight: bold; -fx-font-size: 18px;");
        lblResultadoIngresos.setText("Total de Ingresos en el Periodo: $" + String.format("%,.2f", total) +
                " (" + reservasPeriodo.size() + " reservas encontradas)");

        tblReservasPeriodo.setItems(FXCollections.observableArrayList(reservasPeriodo));
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}