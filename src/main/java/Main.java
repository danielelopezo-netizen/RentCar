import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Buscamos el archivo FXML en la carpeta view
        URL fxmlLocation = getClass().getResource("/view/inicio.fxml");

        // Validación de seguridad por si la ruta está mal configurada
        if (fxmlLocation == null) {
            System.err.println("Error: No se encontró el archivo inicio.fxml.");
            System.err.println("Asegúrate de que esté en la carpeta src/main/resources/view/ (si usas Maven)");
            System.err.println("o en src/main/java/view/ (dependiendo de la configuración de tu IDE).");
            System.exit(1);
        }

        // Cargamos la vista
        Parent root = FXMLLoader.load(fxmlLocation);

        // Configuramos la ventana (Stage) y la escena (Scene)
        Scene scene = new Scene(root, 700, 500);
        primaryStage.setTitle("RentCar - Sistema de Gestión");
        primaryStage.setScene(scene);

        // Evitamos que la ventana sea más pequeña de lo diseñado
        primaryStage.setMinWidth(700);
        primaryStage.setMinHeight(500);

        primaryStage.show();
    }

    public static void main(String[] args) {
        // Este método lanza la aplicación JavaFX
        launch(args);
    }
}