
package model;
import java.io.Serializable;
import java.util.Comparator;
@SuppressWarnings("serial")
public abstract class Kunstwerk implements Serializable
{
	private String kuenstler;
	private String titel;
    private int laenge;
    private int breite;
	private double ekPreis;
	private boolean verkauft;
	
	public Kunstwerk(String kuenstler, String titel, int laenge, int breite, double ekPreis) throws GalerieException
	{
		setKuenstler(kuenstler);
		setTitel(titel);
		setLaenge(laenge);
		setBreite(breite);
		setEkPreis(ekPreis);
	}
	public Kunstwerk() // Standard-Konstruktor für Aufbau eines Objekts aus einer Text-Datei mittels  Class.forName(".....").newInstance()....
	{
	}
    public Kunstwerk(String zeile) throws GalerieException // Konstruktor für Aufbau eines "Kunstwerks" aus einer Text-Zeile (Methode importKunstwerke...)
    {
        setAll(zeile);
    }
	//---------------------------------- getter ----------------------
	public double getEkPreis()
	{
		return ekPreis;
	}
	public String getKuenstler()
	{
		return kuenstler;
	}
	public String getTitel()
	{
		return titel;
	}
    public int getBreite()
    {
        return breite;
    }
    public int getLaenge()
    {
        return laenge;
    }
	public boolean isVerkauft()
	{
		return verkauft;
	}
	//---------------------------------- setter ----------------------
	private void setAll(String zeile) throws GalerieException // set-Methode für Initialisierung aller Attribute der Kunstwerk-Klasse
	{														  // beim Einlesen aus einer Text-Zeile (Methode Galerie.importKunstwerke(...)  )
	    try
		{
			setKuenstler( zeile.substring(10, 30).trim()  );
			setTitel( zeile.substring(30, 50).trim()  );
            setEkPreis(  Double.parseDouble(  zeile.substring(50, 60).trim()  )   );
            setLaenge( Integer.parseInt(  zeile.substring(60,66).trim() )  );
            setBreite( Integer.parseInt(  zeile.substring(66,72).trim() )  );
		}
		catch (NumberFormatException e)
		{
			throw new GalerieException( "Achtung ! Fehler beim Einlesen der Daten für ein Kunstwerk!");
		}
		catch (IndexOutOfBoundsException e)
		{
			throw new GalerieException( "Achtung ! IndexOutOfBounds beim Einlesen der Daten für ein Kunstwerk!");
		}
	}
	public void setEkPreis(double ekPreis) throws GalerieException
	{
		if (ekPreis > 0. && ekPreis < 1000000.)
			this.ekPreis = ekPreis;
		else
			throw new GalerieException("Achtung! Für " + getClass().getSimpleName() + "  (" + this + ")wurde ein " +
									   "falscher Parameterwert f. setEkPreis ("+ekPreis+") übergeben!!!"); 
	}
	public void setKuenstler(String kuenstler) throws GalerieException
	{
		if (kuenstler != null)
			this.kuenstler = kuenstler;
		else
			throw new GalerieException("Null-Referenz f. Künstler !!!"); 
	}
	public void setTitel(String titel) throws GalerieException
	{
		if (titel != null)
			this.titel = titel;		
		else
			throw new GalerieException("Null-Referenz f. Titel !!!"); 
	}
    public void setLaenge(int laenge)  throws GalerieException
    {
        if(laenge > 0 && laenge <= 10000)   //  100 Meter
        {
            this.laenge = laenge;
        }
        else
        {
            throw new GalerieException("Falscher Parameterwert für Länge (" + laenge + ") bei " +
                               getKuenstler()+"'s  \"" + getTitel()+ "\" !");
        }
    }
    public void setBreite(int breite) throws GalerieException
    {
        if(breite > 0 && breite <= 10000)    //  100 Meter
            this.breite = breite;
        else
            throw new GalerieException("Falscher Parameterwert für Breite (" + breite + ")!");
    }
	public void setVerkauft(boolean verkauft)
	{
		this.verkauft = verkauft;
	}
	// -------------------------------- andere ---------------------
	public abstract double berechneVkWert();
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(kuenstler).append(" - ").append(titel).append(" - ").append("Maße: ").append(laenge).append('x').append(breite).append(" - Ek. ").append(ekPreis)
		  .append(" - ");
		if (verkauft)
			sb.append("verkauft");
		else
			sb.append("verfügbar");
		return sb.toString();
	}
	public String toStringCsv()
	{
		return kuenstler+';'+titel+';'+ekPreis+';'+laenge+';'+breite;
	}
	public String toStringFormat()
	{
		return String.format("%-20s%-20s%10.0f%6d%6d", kuenstler,titel,ekPreis,laenge,breite);
//auch möglich: schon hier den (Kunstwerk-)Typ der Sub-Klassen hinzuzufügen
//      return String.format("%-10s%-20s%-20s%10.0f....", getClass().getSimpleName(), kuenstler,titel, ekPreis....);
	}
//  --------------------------------- Comparatoren ------------------------------
	public static class WertComparator implements Comparator<Kunstwerk>
	{
		public int compare(Kunstwerk kw1, Kunstwerk kw2)
		{
			if (kw1.berechneVkWert() > kw2.berechneVkWert() )  // weil double
				return -1;
			else
				if (kw1.berechneVkWert() < kw2.berechneVkWert() )
					return 1;
				else
					return 0;
		}
	}	
	public static class KuenstlerComparator implements Comparator<Kunstwerk>
	{
		public int compare(Kunstwerk kw1, Kunstwerk kw2)
		{
			return kw1.getKuenstler().compareTo( kw2.getKuenstler() );
		}
	}
}
