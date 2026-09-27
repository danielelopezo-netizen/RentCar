package controller;

import javafx.scene.control.DatePicker;
import java.time.LocalDate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Cliente;
import model.Empresa;

public class InicioController {

    // Estas variables están enlazadas a los fx:id que pusimos en SceneBuilder
    @FXML
    private TextField txtTelefono;

    @FXML
    private Label lblResultadoTelefono;

    // Este método se ejecuta al hacer clic en el botón (enlazado por el onAction)
    @FXML
    void verificarTelefonoCliente(ActionEvent event) {
        String telefono = txtTelefono.getText();

        if (telefono.isEmpty()) {
            lblResultadoTelefono.setText("Por favor, ingrese un número.");
            return;
        }

        // Usamos la clase Cliente para acceder a la lógica que ya habías programado
        Cliente clienteTemp = new Cliente("", "", telefono, "", 0, null);
        boolean esPerfecto = clienteTemp.esNumeroPerfecto(telefono);

        if (esPerfecto) {
            lblResultadoTelefono.setStyle("-fx-text-fill: #2ecc71; -fx-font-weight: bold;");
            lblResultadoTelefono.setText("¡Éxito! El número " + telefono + " SÍ es un número perfecto.");
        } else {
            lblResultadoTelefono.setStyle("-fx-text-fill: #e74c3c; -fx-font-weight: bold;");
            lblResultadoTelefono.setText("El número " + telefono + " NO es perfecto.");
        }
    }

    // Nuevas variables enlazadas a la pestaña de ingresos
    @FXML
    private DatePicker dpFechaInicio;

    @FXML
    private DatePicker dpFechaFin;

    @FXML
    private Label lblResultadoIngresos;

    // Método para el botón de cálculo
    @FXML
    void calcularIngresosPeriodo(ActionEvent event) {
        LocalDate fechaInicio = dpFechaInicio.getValue();
        LocalDate fechaFin = dpFechaFin.getValue();

        // Validación básica
        if (fechaInicio == null || fechaFin == null) {
            lblResultadoIngresos.setText("Por favor seleccione ambas fechas.");
            lblResultadoIngresos.setStyle("-fx-text-fill: #e74c3c; -fx-font-weight: bold;");
            return;
        }

        if (fechaInicio.isAfter(fechaFin)) {
            lblResultadoIngresos.setText("La fecha de inicio debe ser anterior a la final.");
            lblResultadoIngresos.setStyle("-fx-text-fill: #e74c3c; -fx-font-weight: bold;");
            return;
        }

        // Instanciamos la empresa (idealmente esto debería ser global en la aplicación)
        Empresa miEmpresa = new Empresa();

        // Ejecutamos el método que creaste anteriormente
        double total = miEmpresa.calcularIngresos(fechaInicio, fechaFin);

        lblResultadoIngresos.setStyle("-fx-text-fill: #2c3e50; -fx-font-weight: bold;");
        lblResultadoIngresos.setText("Total de Ingresos: $" + String.format("%.2f", total));
    }
}