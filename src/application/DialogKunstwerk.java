package application;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Bild;
import model.GalerieException;
import model.Kunstwerk;

public class DialogKunstwerk extends Stage
{
	private GridPane rootPane;
	private Scene scene;
	private boolean isUebernehmen;
	private Kunstwerk kunstwerk;
	
	private Label lbKuenstler, lbTitel, lbLaenge, lbPreis, lbBreite;
	private CheckBox cbGefragt;
	private TextField tfKuenstler, tfTitel;
	private Spinner<Integer> spLaenge, spBreite;
	private Slider slPreis;
	private Button btUebernehmen, btAbbrechen;
	
	
	public DialogKunstwerk()
	{
		initComponents();
		addComponents();
		addHandler();
	}
	public boolean isUebernehmen()
	{
		return isUebernehmen;
	}
	private void initComponents()
	{
		isUebernehmen=false;
		rootPane = new GridPane();
		scene = new Scene(rootPane,500,200);
		setScene(scene);
		this.initModality(Modality.APPLICATION_MODAL);
		
		rootPane.setPadding(new Insets(10));
		rootPane.setHgap(10);
		rootPane.setVgap(5);
		
		lbKuenstler 	= new Label("Künstler:");
		lbTitel			= new Label("Titel:");
		lbLaenge		= new Label("Länge:");
		lbBreite		= new Label("Breite:");
		lbPreis			= new Label("E-Preis (in 1000)");
		cbGefragt 		= new CheckBox("Sehr gefragt");
		tfKuenstler 	= new TextField();
		tfTitel			= new TextField();
		spLaenge 		= new Spinner<Integer>(0,10000,0);
			spLaenge.setPrefWidth(100);
		spBreite		= new Spinner<Integer>(0,10000,0);
			spBreite.setPrefWidth(100);
		slPreis 		= new Slider();
			slPreis.setMax(1000);
			slPreis.setMin(0);
			slPreis.setShowTickLabels(true);
			slPreis.setShowTickMarks(true);
			slPreis.setSnapToTicks(true);
			slPreis.setMajorTickUnit(100);
			slPreis.setMinorTickCount(10);
		btAbbrechen 	= new Button("Abbrechen");
			btAbbrechen.setPrefWidth(100);
		btUebernehmen 	= new Button("Übernehmen");	
			btUebernehmen.setPrefWidth(100);
	}
	private void addComponents()
	{
		rootPane.add(lbKuenstler	, 0, 0);
		rootPane.add(lbTitel		, 0, 1);
		rootPane.add(lbLaenge		, 0, 2);
		rootPane.add(lbPreis		, 0, 3);
		rootPane.add(cbGefragt		, 0, 4);
		
		rootPane.add(tfKuenstler	, 1, 0);
		rootPane.add(tfTitel		, 1, 1);
		rootPane.add(spLaenge		, 1, 2);
		rootPane.add(slPreis		, 1, 3, 3, 1);
		
		rootPane.add(lbBreite		, 2, 2);
		rootPane.add(btUebernehmen	, 2, 5);
		
		rootPane.add(spBreite		, 3, 2);
		rootPane.add(btAbbrechen	, 3, 5);
	}
	public void addHandler()
	{
		btAbbrechen.setOnAction(event -> close());
		btUebernehmen.setOnAction(event -> uebernehmen());
	}
	public void updateShowAndWait(Kunstwerk kunstwerk)
	{
		isUebernehmen=false;
		this.kunstwerk = kunstwerk;
		tfKuenstler.setText(kunstwerk.getKuenstler());
		tfTitel.setText(kunstwerk.getTitel());
		slPreis.setValue(kunstwerk.getEkPreis()/1000);
		if(kunstwerk instanceof Bild)
		{
			cbGefragt.setSelected(((Bild) kunstwerk).isSehrGefragt());
			spLaenge.getValueFactory().setValue(kunstwerk.getLaenge());
			spBreite.getValueFactory().setValue(kunstwerk.getBreite());
			showAndWait();
		}
	}
	public void uebernehmen()
	{
		try
		{			
			kunstwerk.setBreite(spBreite.getValue());
			kunstwerk.setEkPreis( Math.round(slPreis.getValue()*10000)/10 );
			kunstwerk.setKuenstler(tfKuenstler.getText());
			kunstwerk.setLaenge(spLaenge.getValue());
			kunstwerk.setTitel(tfTitel.getText());
			if(kunstwerk instanceof Bild)
				((Bild) kunstwerk).setSehrGefragt(cbGefragt.isSelected());
			isUebernehmen = true;
			close();
		} 
		catch (GalerieException e)
		{
			Main.showAlert(AlertType.ERROR, e.getMessage());
		}
	}

}
