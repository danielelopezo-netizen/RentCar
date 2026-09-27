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
        String telefono = txtTelefono.getText() != null ? txtTelefono.getText().trim() : "";

        if (telefono.isEmpty()) {
            lblResultadoTelefono.setStyle("-fx-text-fill: #e74c3c; -fx-font-weight: bold;");
            lblResultadoTelefono.setText("Por favor, ingrese un número de teléfono.");
            return;
        }

        // Buscar cliente en la empresa (Patrón Singleton) y verificar si es número perfecto
        Cliente cliente = Empresa.getInstance().buscarClientePorTelefono(telefono);
        boolean esPerfecto = util.NumeroPerfectoUtil.esNumeroPerfecto(telefono);

        if (cliente != null) {
            if (esPerfecto) {
                lblResultadoTelefono.setStyle("-fx-text-fill: #2ecc71; -fx-font-weight: bold;");
                lblResultadoTelefono.setText("Cliente encontrado: " + cliente.getNombreCompleto() + " | Su teléfono (" + telefono + ") SÍ es un número perfecto.");
            } else {
                lblResultadoTelefono.setStyle("-fx-text-fill: #e67e22; -fx-font-weight: bold;");
                lblResultadoTelefono.setText("Cliente encontrado: " + cliente.getNombreCompleto() + " | Su teléfono (" + telefono + ") NO es un número perfecto.");
            }
        } else {
            if (esPerfecto) {
                lblResultadoTelefono.setStyle("-fx-text-fill: #3498db; -fx-font-weight: bold;");
                lblResultadoTelefono.setText("El número " + telefono + " SÍ es perfecto (no registrado como cliente).");
            } else {
                lblResultadoTelefono.setStyle("-fx-text-fill: #e74c3c; -fx-font-weight: bold;");
                lblResultadoTelefono.setText("Cliente no encontrado y el número " + telefono + " NO es perfecto.");
            }
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

        // Obtenemos la instancia única de la empresa (Patrón Singleton)
        Empresa miEmpresa = Empresa.getInstance();

        // Ejecutamos el método que creaste anteriormente
        double total = miEmpresa.calcularIngresos(fechaInicio, fechaFin);

        lblResultadoIngresos.setStyle("-fx-text-fill: #2c3e50; -fx-font-weight: bold;");
        lblResultadoIngresos.setText("Total de Ingresos: $" + String.format("%.2f", total));
    }
}