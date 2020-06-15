package application;

import java.io.File;
import java.util.List;
import java.util.Optional;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;
import model.Bild;
import model.Galerie;
import model.GalerieException;
import model.Kunstwerk;

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
	private DialogKunstwerk dialog;
	
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
//		dialog.updateShowAndWait(new Bild());
	}
	private void initMenues()
	{
		mDatei = new Menu("Datei");
		mBearbeiten = new Menu("Bearbeiten");
		mHilfe = new Menu("Hilfe");		
		mLaden = new Menu("Laden");
		mSpeichern = new Menu("Speichern");
		mHinzufuegen = new Menu("Hinzufügen");
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
		
		miSortierenKuestler = new MenuItem("aufsteigend nach Künstler");
		miSortierenWert = new MenuItem("absteigend nach Wert");
		miLoeschen = new MenuItem("Löschen ");
		miAendern = new MenuItem("Ändern");
		
		miInfo = new MenuItem("Über");
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
		uebersichtKunstwerke 	= new KunstwerkeUebersicht(this);
		dialog 					= new DialogKunstwerk();
		
		fpBottom  				= new FlowPane();
			fpBottom.setPadding(new Insets(8));
			fpBottom.setAlignment(Pos.CENTER);
			fpBottom.setHgap(8);
			btSortKuenstler	 	= new Button("Sortieren nach Künstler");
			btSortWert 			= new Button("Sortieren nach Wert");			
			btUebersichtBeenden = new Button("Übersicht beenden");
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
		miSortierenKuestler		.setOnAction(event -> sort("Künstler"));
		miSortierenWert			.setOnAction(event -> sort("Wert"));
		btSortWert				.setOnAction(event -> sort("Wert"));
		btSortKuenstler			.setOnAction(event -> sort("Künstler"));
		btUebersichtBeenden		.setOnAction(event -> ubersichtBeenden());
		miLoeschen				.setOnAction(event -> loeschen());
		miAendern				.setOnAction(event -> aendern());		
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
							Main.showAlert(AlertType.INFORMATION, "Unbekanntes File-Format gewählt!");
				
				Main.showAlert(AlertType.INFORMATION, "Datei auf "+file.getAbsoluteFile()+" gespeichert!");
			}
			else
				Main.showAlert(AlertType.WARNING, "Keine Datei ausgewählt!");
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
	
	public void loeschen()
	{
		List<Kunstwerk> auswahl = uebersichtKunstwerke.getSelectionModel().getSelectedItems();
		if(auswahl.size()>0) 
		{
			Optional<ButtonType> result = Main.showAlert(AlertType.CONFIRMATION, "Wollen Sie die markierten Kunstwerke wirklich löschen");
			if(result.get() == ButtonType.OK)
			{
				galerie.removeKunstwerke(auswahl);
				uebersichtKunstwerke.update(galerie.getKunstwerke());
			}
//			if(result.get() == ButtonType.CANCEL) System.out.println("CANCEL");
		}
		else
			Main.showAlert(AlertType.ERROR, "Kein Kunstwerk ausgewählt!");
	}
	private void ubersichtBeenden()
	{
		galerie = new Galerie("My Galerie");
		disableCompenents(true);
		setComponentsVisible(false);
	}
	public void aendern()
	{
		List<Kunstwerk> auswahl = uebersichtKunstwerke.getSelectionModel().getSelectedItems();
		if(auswahl.size()>1)
			if(auswahl.size()<1)
				if(auswahl.get(0) instanceof Bild)
				{
					dialog.updateShowAndWait(auswahl.get(0));
					if(!dialog.isUebernehmen())
						Main.showAlert(AlertType.WARNING, "Bearbeitung durch Benutzer abgebrochen!");
					uebersichtKunstwerke.update(galerie.getKunstwerke());
				}					
				else
					Main.showAlert(AlertType.ERROR, "Ändern von 'Skulpturen' noch nicht implementiert!!");
			else
				Main.showAlert(AlertType.ERROR, "Bitte genau ein Kunstwerk zur bearbeitung auswählen!");
		else
			Main.showAlert(AlertType.ERROR, "Kein Kunstwerk ausgewählt");
	}
	private void beenden()
	{
		Platform.exit();
	}
}
