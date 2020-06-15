package application;

import java.io.File;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;
import model.Galerie;
import model.GalerieException;

public class RootBorderPane extends BorderPane
{
	private MenuBar menuBar;
	private Menu mDatei, mBearbeiten, mHilfe,
				 mLaden, mSpeichern,  mHinzufuegen, mSortieren;
	private MenuItem miLadenSerialisiert, miSpeichernSerialisiert, miImportCsv, miExportCsv, miImportFormatiert, miExportFormatiert, miBeenden,
					 miSortierenKuestler, miSortierenWert, miLoeschen, miAendern, miHinzufuegenBild, miHinzufuegenSkulptur,
					 miInfo;
	
	private Galerie galerie;
	private KunstwerkeUebersicht uebersichtKunstwerke;
	
	private FlowPane fpBottom;
	private Button btSortKuenstler, btSortWert, btUebersichtBeenden;
	
	public RootBorderPane()
	{
		initMenues();
		addMenues();
		initComponents();
		addComponents();
		disableCompenents(true);
		setComponentsVisible(false);
		addHandler();
	}
	private void initMenues()
	{
		mDatei = new Menu("Datei");
		mBearbeiten = new Menu("Bearbeiten");
		mHilfe = new Menu("Hilfe");		
		mLaden = new Menu("Laden");
		mSpeichern = new Menu("Speichern");
		mHinzufuegen = new Menu("Hinzuf�gen");
		mSortieren = new Menu("Sortieren");
		
		miLadenSerialisiert = new MenuItem("Laden .ser-Datei");
		miSpeichernSerialisiert = new MenuItem("Speichern .ser-Datei");
		miExportCsv = new MenuItem("Speichern .csv-Datei");
		miImportCsv = new MenuItem("Laden .csv-Datei");
		miExportCsv = new MenuItem("Speichern .csv-Datei");
		miImportFormatiert = new MenuItem("Laden .txt-Datei formatiert");
		miExportFormatiert = new MenuItem("Speichern .txt-Datei formatiert");
		miBeenden = new MenuItem("Beenden");
//			miBeenden.setStyle("-fx-background-color: limeGreen; -fx-text-fill: RED");
//			miBeenden.setStyle("-fx-background-color: AQUA; -fx-text-fill: RED"); // vor-definierte Farben in der Klasse Color
		miHinzufuegenBild = new MenuItem("Bild");
		miHinzufuegenSkulptur = new MenuItem("Skulptur");
		
		miSortierenKuestler = new MenuItem("aufsteigend nach K�nstler");
		miSortierenWert = new MenuItem("absteigend nach Wert");
		miLoeschen = new MenuItem("L�schen ");
		miAendern = new MenuItem("�ndern");
		
		miInfo = new MenuItem("�ber");
	}
	private void addMenues()
	{
		mDatei.getItems().addAll(mLaden, mSpeichern, miBeenden);
		mLaden.getItems().addAll(miLadenSerialisiert, miImportCsv, miImportFormatiert);
		mSpeichern.getItems().addAll(miSpeichernSerialisiert, miExportCsv, miExportFormatiert);
		mBearbeiten.getItems().addAll(mSortieren, miLoeschen, miAendern, mHinzufuegen);
		mSortieren.getItems().addAll(miSortierenKuestler, miSortierenWert);
		mHinzufuegen.getItems().addAll(miHinzufuegenBild, miHinzufuegenSkulptur);
		mHilfe.getItems().add(miInfo);
		
		menuBar = new MenuBar(mDatei, mBearbeiten, mHilfe);		
	}

	private void initComponents()
	{
		galerie 				= new Galerie("MyGalerie");
		uebersichtKunstwerke 	= new KunstwerkeUebersicht();
		
		fpBottom  				= new FlowPane();
			fpBottom.setPadding(new Insets(8));
			fpBottom.setAlignment(Pos.CENTER);
			fpBottom.setHgap(8);
			btSortKuenstler	 	= new Button("Sortieren nach K�nstler");
			btSortWert 			= new Button("Sortieren nach Wert");			
			btUebersichtBeenden = new Button("�bersicht beenden");
				FlowPane.setMargin(btUebersichtBeenden, new Insets(0,0,0,20));
	}
	private void addComponents()
	{
		setTop(menuBar);
		setCenter(uebersichtKunstwerke);
		setBottom(fpBottom);
		
		fpBottom.getChildren().addAll(btSortKuenstler, btSortWert, btUebersichtBeenden);
	}
	private void disableCompenents(boolean disable)
	{
		mSpeichern.setDisable(disable);
		mSortieren.setDisable(disable);
		miLoeschen.setDisable(disable);
		miAendern.setDisable(disable);
	}
	private void setComponentsVisible(boolean visible)
	{
		uebersichtKunstwerke.setVisible(visible);
		fpBottom.setVisible(visible);
	}
	private void addHandler()
	{
		miBeenden				.setOnAction(event -> beenden());
		miLadenSerialisiert		.setOnAction(event -> laden("ser"));
		miImportFormatiert		.setOnAction(event -> laden("txt"));
		miImportCsv				.setOnAction(event -> laden("csv"));
		miSpeichernSerialisiert	.setOnAction(event -> speichern("ser"));
		miExportCsv				.setOnAction(event -> speichern("csv"));
		miExportFormatiert  	.setOnAction(event -> speichern("txt"));
		miSortierenKuestler		.setOnAction(event -> sort("K�nstler"));
		miSortierenWert			.setOnAction(event -> sort("Wert"));
		btSortWert				.setOnAction(event -> sort("Wert"));
		btSortKuenstler			.setOnAction(event -> sort("K�nstler"));
		btUebersichtBeenden		.setOnAction(event -> ubersichtBeenden());
		
		
	}
//	----------------------------- Handlermethoden --------------------------
	private void laden(String format)
	{
		FileChooser filechooser = new FileChooser();
		try
		{
			filechooser.setInitialDirectory(new File("C:\\scratch\\"));
			File file = filechooser.showOpenDialog(null);
			if (file!=null)
			{
				if(format.equals("ser"))
					galerie.loadKunstwerke(file.getAbsolutePath());
				else
					if(format.equals("txt"))
						galerie.importKunstwerke(file.getAbsolutePath());
					else
						Main.showAlert(AlertType.WARNING, "Das Format *."+format+" kann nicht geladen werden!");
				disableCompenents(false);
				setComponentsVisible(true);
				uebersichtKunstwerke.update(galerie.getKunstwerke());
				System.out.println(galerie);
			}
			else
				Main.showAlert(AlertType.ERROR, "Laden wurde abgebrochen");
		} 
		catch (GalerieException e)
		{
			Main.showAlert(AlertType.ERROR, e.getMessage());
		}
		catch (Exception e)
		{
			Main.showAlert(AlertType.ERROR, e.getMessage());
		}

	}
	
	private void speichern(String format)
	{
		FileChooser filechooser = new FileChooser();
		try
		{
			filechooser.setInitialDirectory(new File("C:\\scratch\\"));
//			filechooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("SER files (*.ser)","*.ser"),
//													 new FileChooser.ExtensionFilter("TXT files (*.txt)","*.txt"),
//													 new FileChooser.ExtensionFilter("CSV files (*.csv)","*.csv"));
			File file = filechooser.showSaveDialog(null);
			if (file!=null)
			{
				if(format.equals("ser"))
					galerie.saveKunstwerke(file.getAbsolutePath());
				else
					if(format.equals("csv"))
						galerie.exportKunstwerkeCsv(file.getAbsolutePath());
					else
						if(format.equals("txt"))
							galerie.exportKunstwerkeFormat(file.getAbsolutePath());
						else
							Main.showAlert(AlertType.INFORMATION, "Unbekanntes File-Format gew�hlt!");
				
				Main.showAlert(AlertType.INFORMATION, "Datei auf "+file.getAbsoluteFile()+" gespeichert!");
			}
			else
				Main.showAlert(AlertType.WARNING, "Keine Datei ausgew�hlt!");
		} 
		catch (GalerieException e)
		{
			Main.showAlert(AlertType.ERROR, e.getMessage());
		}
		catch (Exception e)
		{
			Main.showAlert(AlertType.ERROR, e.getMessage());
		}

	}
	
	private void sort(String str)
	{
		try
		{
			galerie.sort(str);
			uebersichtKunstwerke.update(galerie.getKunstwerke());
		} 
		catch (GalerieException e)
		{

			Main.showAlert(AlertType.ERROR, e.getMessage());
		}
	}
	private void ubersichtBeenden()
	{
		galerie = new Galerie("My Galerie");
		disableCompenents(true);
		setComponentsVisible(false);
	}
	private void beenden()
	{
		Platform.exit();
	}
}
