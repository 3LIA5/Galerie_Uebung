package application;

import java.util.Optional;

import javafx.application.*;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class Main extends Application
{
	public void start(Stage primaryStage)
	{
		try
		{
			Scene scene = new Scene(new RootBorderPane(), 650, 300);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Galerie \"FX Arts\" Kunstwerke-Verwaltung");
			primaryStage.show();		
		}
		catch (Exception e)
		{
			new Alert(AlertType.ERROR, "Schwerer Fehler beim Start der Applikation!!!\nGrund: "+e.getMessage(), ButtonType.OK).showAndWait();
			Platform.exit();
		}
	}
	public static Optional<ButtonType> showAlert(AlertType alertType, String message) // boolean RW, damit selbe Methode auch für Rückfragen verwendet werden kann
	{
		Alert alert = new Alert(alertType, message);//, ButtonType.OK);
		alert.setHeaderText(null);
		alert.setTitle("Achtung!");
		return alert.showAndWait();
	}

	//-----------------------------------------------------------------------
	public static void main(String[] args)
	{
		launch(args);
	}
}
