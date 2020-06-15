package application;

import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

public class RootBorderPane extends BorderPane
{
	private MenuBar menuBar;
	private Menu mDatei, mBearbeiten, mHilfe,
				 mLaden, mSpeichern,  mHinzufuegen, mSortieren;
	private MenuItem miLadenSerialisiert, miSpeichernSerialisiert, miImportCsv, miExportCsv, miImportFormatiert, miExportFormatiert, miBeenden,
					 miSortierenKuestler, miSortierenWert, miLoeschen, miAendern, miHinzufuegenBild, miHinzufuegenSkulptur,
					 miInfo;
	
	public RootBorderPane()
	{
		initMenues();
		addMenues();
		disableCompenents(true);
		addHandler();
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
		setTop(menuBar);
	}
	private void disableCompenents(boolean disable)
	{
		mSpeichern.setDisable(disable);
		mSortieren.setDisable(disable);
		miLoeschen.setDisable(disable);
		miAendern.setDisable(disable);
	}
	private void addHandler()
	{
		miBeenden.setOnAction(event -> beenden());
	}
//	----------------------------- Handlermethoden --------------------------
	private void beenden()
	{
		Platform.exit();
	}
}
