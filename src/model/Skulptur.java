package model;

@SuppressWarnings("serial")
public class Skulptur extends Kunstwerk
{
	private int hoehe;
	private String material;
	private boolean sehrSelten;

	public Skulptur(String kuenstler, String titel, int laenge, int breite, double ekPreis, int hoehe, String material)
					throws GalerieException
	{
		super(kuenstler, titel, laenge, breite, ekPreis);
		setHoehe(hoehe);
		setMaterial(material);
	}
	public Skulptur() // Standard-Konstruktor für Aufbau eines Objekts aus einer Text-Datei mittels  Class.forName(".....").newInstance()....
	{
	}
	public Skulptur(String zeile) throws GalerieException // Konstruktor für Aufbau einer "Skulptur" aus einer Text-Zeile (Methode importKunstwerke...)
	{
		super(zeile);
		setAll(zeile);
	}
	// -------------------------------------- getter -----------------------------
	public int getHoehe()
	{
		return hoehe;
	}

	public String getMaterial()
	{
		return material;
	}

	public boolean getSehrSelten()
	{
		return sehrSelten;
	}
	// -------------------------------------- setter -----------------------------
	public void setHoehe(int hoehe) throws GalerieException
	{
		if (hoehe > 0 && hoehe <= 2000) // 20 Meter
			this.hoehe = hoehe;
		else
			throw new GalerieException("Falscher Parameterwert für Höhe (" + hoehe + ")!");
	}

	public void setMaterial(String material) throws GalerieException
	{
		if (material != null && !material.trim().isEmpty())
			this.material = material;
		else
			throw new GalerieException("null-Referenz oder Leer-String f. Material !!!");
	}

	public void setSehrSelten(boolean sehrSelten)
	{
		this.sehrSelten = sehrSelten;
	}
	// -------------------------------------- setAll -----------------------------
	private void setAll(String zeile) throws GalerieException // set-Methode für Initialisierung aller Attribute der Skulptur-Klasse
	{ 														  // beim Einlesen aus einer Text-Zeile (Methode Galerie.importKunstwerke(...) )
		try
		{
			setHoehe(Integer.parseInt(zeile.substring(74, 79).trim()));
			setMaterial(zeile.substring(80).trim());
			setSehrSelten(zeile.charAt(73) == 'j');
		}
		catch (NumberFormatException e)
		{
			throw new GalerieException("Achtung ! Fehler beim Einlesen der Daten für eine \"Skulptur\"!");
		}
		catch (IndexOutOfBoundsException e)
		{
			throw new GalerieException("Achtung ! IndexOutOfBounds beim Einlesen der Daten für eine \"Skulptur\"!");
		}
	}
	// -------------------------------------- sonstige -----------------------------
	public double berechneVkWert()
	{
		if (sehrSelten)
			return getEkPreis() * 2;
		else
			return getEkPreis() * 1.5;
	}

	public String toString()
	{
		StringBuilder sb = new StringBuilder(super.toString());
		sb.append(" - ").append("  Material: ").append(material).append(" -> Vk. ").append(berechneVkWert());
		return sb.toString();
	}

	public String toStringCsv()
	{
		return "Skulptur"+';'+super.toStringCsv()+';'+(sehrSelten ? 'j' : 'n')+';'+ hoehe+';'+ material;
	}
	public String toStringFormat()
	{
		return String.format("%-10s%s %c%5d %s", "Skulptur", super.toStringFormat(), sehrSelten ? 'j' : 'n', hoehe, material);
	}
}
