package model;

@SuppressWarnings("serial")
public class Bild extends Kunstwerk
{
	private boolean sehrGefragt;
	
	public Bild(String kuenstler, String titel, int laenge, int breite, double ekPreis, boolean sehrGefragt)  throws GalerieException
	{
		super(kuenstler, titel, laenge, breite, ekPreis);
		setSehrGefragt(sehrGefragt);
	}
	public Bild()  // Standard-Konstruktor für Aufbau eines Objekts aus einer Text-Datei mittels  Class.forName(".....").newInstance()....
	{
	}
	public Bild(String zeile) throws GalerieException // Konstruktor für Aufbau eines "Bildes" aus einer Text-Zeile (Methode importKunstwerke...)
	{
	    super(zeile);
	    setAll(zeile);
	}
	//---------------------------------- getter ----------------------
	public boolean isSehrGefragt()
	{
		return sehrGefragt;
	}
	//---------------------------------- setter ----------------------
	public void setSehrGefragt(boolean sehrGefragt)
	{
		this.sehrGefragt = sehrGefragt;
	}
//  -------------------------------------- setAll -----------------------------
	private void setAll(String zeile)  throws GalerieException // set-Methode für Initialisierung aller Attribute der Bild-Klasse
	{														   // beim Einlesen aus einer Text-Zeile (Methode Galerie.importKunstwerke(...)  )
		try
		{
			setSehrGefragt(zeile.charAt(73) == 'j');
		}
		catch (NumberFormatException e)
		{
			throw new GalerieException( "Achtung ! Fehler beim Einlesen der Daten für ein \"Bild\"!");
		}
		catch (IndexOutOfBoundsException e)
		{
			throw new GalerieException( "Achtung ! IndexOutOfBounds beim Einlesen der Daten für ein \"Bild\"!");
		}
	}
	//--------------------------------- andere ---------------------
	public double berechneVkWert()
	{
		if (sehrGefragt)
			return getEkPreis()*1.5;
		else
			return getEkPreis()*1.25;
	}
	public String toString()
	{
		StringBuilder sb = new StringBuilder(super.toString());
		sb.append(" -> Vk. ").append(berechneVkWert());
		return sb.toString();
	}
	public String toStringCsv()
	{
		return "Bild"+';'+super.toStringCsv()+';'+(sehrGefragt?'j':'n');
	}
	public String toStringFormat()
	{
		return String.format("%-10s%s %c", "Bild",super.toStringFormat(),sehrGefragt?'j':'n');
	}
}
