package model;

import java.io.*;
import java.util.*;

public class Galerie
{
	private String name;
	private List<Kunstwerk> kunstwerke;

	public Galerie(String name)
	{
		setName(name);
		kunstwerke = new ArrayList<Kunstwerk>();
	}
// ------------------------------------------  getter  ---------------------------------
	public String getName()
	{
		return name;
	}
	public Kunstwerk getKunstwerk(int pos) throws GalerieException
	{
		if (pos >= 0 && pos < kunstwerke.size())
			return kunstwerke.get(pos);
		else
			throw new GalerieException("Falsche Position für Galerie.getKunstwerk("+pos+")!! ");
	}
 	public List<Kunstwerk> getKunstwerke()		// für FX - Übersicht
	{
		return kunstwerke;
	}
// ------------------------------------------  getter  ---------------------------------
	public void setName(String name) 
	{
		if (name != null)
			this.name = name;
		else 
			this.name = "Galerie";
	}
// ------------------------------------------  sonstige  ---------------------------------
	public void add(Kunstwerk kw) throws GalerieException
	{
		if ( kw != null )
			if ( kunstwerke.size() < 100 )
				if ( !kunstwerke.contains(kw))
						kunstwerke.add(kw);
				else
					throw new GalerieException("Dieses Kunstwerk ist bereits in der Galerie");
			else
				throw new GalerieException("Galerie hat schon 100 Kunstwerke");
		else
			throw new GalerieException("null-Referenz für add(Kunstwerk kw)");
	}
//-------------------------------------- removers -------------------------------------------
	public void remove(int pos) throws GalerieException
	{
		if (pos >= 0 && pos < kunstwerke.size())
			kunstwerke.remove(pos);
		else
			throw new GalerieException("Falsche Position für Galerie.remove("+pos+")!! ");
	}
	public void remove (Kunstwerk kw) throws GalerieException
	{
		if (! kunstwerke.remove(kw) ) //  if (kunstwerke.add(kw) == false
			throw new GalerieException("Kunstwerk  " +kw.getTitel()+ " von " +kw.getKuenstler() + "  konnte nicht entfernt werden !?!!");
	}
	public void remove(String kuenstler) throws GalerieException
	{
		int anz = 0;
		if (kuenstler != null)
		{
			Iterator<Kunstwerk> iter;  // Definieren der Referenz-Variable
			iter = kunstwerke.iterator();
			while (iter.hasNext() )
			{
				if ( iter.next().getKuenstler().equals(kuenstler) )
				{
					iter.remove();
					anz++;
				}
			}
			if (anz == 0)
				throw new GalerieException("Kein Kunstwerk von "+kuenstler+" in der Galerie!!");
		}
		else
			throw new GalerieException("null-Referenz für remove(String kuenstler)");
	}
	public void removeKunstwerke(List<Kunstwerk> auswahl)
	{
		kunstwerke.removeAll(auswahl);
	}
	public void removeAll()
	{
		kunstwerke.clear();
	}
//-------------------------------------- others -------------------------------------------
	public int berechneAnzBilder()
	{
		int anz = 0;
		Kunstwerk kw;
		Iterator<Kunstwerk> it = kunstwerke.iterator();
		while(it.hasNext())
		{
			kw = it.next(); 
			if (kw instanceof Bild)
				anz ++;
		}
		return anz;
	}
    public int berechneAnzVerkauft()
    {
        int anz = 0;
        for (Kunstwerk k : kunstwerke)
        {
            if ( k.isVerkauft() )
                anz ++;
        }
        return anz;
    }
	public double berechneGesamtVkWert()
	{
		double summe = 0.;
		for (Kunstwerk k : kunstwerke)
			summe += k.berechneVkWert();
		return summe;
	}
	public void sort(String kriterium) throws GalerieException
	{
		if (kriterium.equalsIgnoreCase("Künstler") )
			Collections.sort(kunstwerke, new Kunstwerk.KuenstlerComparator() );
		else
			if( kriterium.equalsIgnoreCase("Wert") ) 
				kunstwerke.sort(new Kunstwerk.WertComparator() ); // seit Java 8 auch SO möglich
			else
				throw new GalerieException("Falsches Sortier-Kriterium für Galerie.sort("+kriterium+")");
	}
	public String toString()
	{
		StringBuffer sb = new StringBuffer("\nGalerie ").append(name).append(" -> dzt. "+kunstwerke.size()+"  Kunstwerke").
														 append("\n--------------------------------------------------\n");
		for (Kunstwerk k : kunstwerke)
			sb.append( k ).append('\n');
		sb.append("--------------------------------------------------\n").append("Gesamter VK-Wert: € " + berechneGesamtVkWert());
		return sb.toString();
	}
	public String toStringFormat()
	{
		StringBuffer sb = new StringBuffer(10000);
		for (Kunstwerk k : kunstwerke)
			sb.append( k.toStringFormat() ).append('\n');
		return sb.toString();
	}
	public String toStringCsv()
	{
		StringBuffer sb = new StringBuffer(10000);
		for (Kunstwerk k : kunstwerke)
			sb.append( k.toStringCsv() ).append('\n');
		return sb.toString();
	}
//  ------------------------------------ serialisierte Dateien --------------------------
	public void saveKunstwerke(String filename) throws GalerieException
	{
		if (filename != null)
		{
			try
			{
				ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename));
				oos.writeObject(kunstwerke);
				oos.close();
			}
			catch (FileNotFoundException e)
			{
				throw new GalerieException("Fehler beim Eröffnen des FOS für "+filename);
			}
			catch (IOException e)
			{
				throw new GalerieException("IO-Fehler beim Eröffnen des FOS für "+filename+"\nGrund: "+e.getMessage());
			}
		}
		else
			throw new GalerieException("null-Referenz für saveKunstwerke!");
	}
	@SuppressWarnings("unchecked")
	public void loadKunstwerke(String filename) throws GalerieException
	{
		if (filename != null)
		{
			try
			{
				FileInputStream fis = new FileInputStream(filename);
				ObjectInputStream ois = new ObjectInputStream(fis);
				kunstwerke = (ArrayList<Kunstwerk>)ois.readObject();
				ois.close();
			}
			catch (ClassNotFoundException e)
			{
				throw new GalerieException("ClassNotFound beim Einlesen der Objekte aus "+filename);
			}
			catch (FileNotFoundException e)
			{
				throw new GalerieException("FileNotFount beim Eröffnen des FIS für "+filename);
			}
			catch (IOException e)
			{
				throw new GalerieException("IO-Fehler beim Eröffnen des FIS für "+filename);
			}
		}
		else
			throw new GalerieException("null-Referenz für loadKunstwerke!");
	}
//---------------------------------- Text-Dateien --------------------------------------------------	
	public void exportKunstwerkeCsv(String filename) throws GalerieException
	{
		if (filename != null)
			try
			{
				BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
                bw.write(toStringCsv()); 
				bw.close();
			}
			catch (IOException e)
			{
				throw new GalerieException("IO-Exception beim Eröffnen des FileWriters für "+filename);
			}
		else
			throw new GalerieException("null-Referenz für exportKunstwerkeCsv!");
	}
	public void exportKunstwerkeFormat(String filename) throws GalerieException
	{
		if (filename != null)
			try
			{
				BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
//---------------------- Variante 1 ------------------------------
                bw.write(toStringFormat()); 
//---------------------- Variante 2 ------------------------------
//                for(Kunstwerk kw : kunstwerke)
//                    bw.write( kw.toStringFormat() +'\n' );
//----------------------------------------------------------------
				bw.close();
			}
			catch (IOException e)
			{
				throw new GalerieException("IO-Exception beim Eröffnen des FileWriters für "+filename);
			}
		else
			throw new GalerieException("null-Referenz für exportKunstwerke!");
	}
	public void importKunstwerke(String filename) throws GalerieException
	{
        String zeile = null;
        BufferedReader br = null;
		try  // heutzutage with ressources
		{
			br = new BufferedReader(new FileReader(filename));
			zeile=br.readLine();
			while (zeile != null)
			{   // Verarabeiten einer eingelesenen Zeile
				if (zeile.substring(0, 10).trim().equalsIgnoreCase("Bild")   ) // kann automatisiert werden mittels
					add (new Bild(zeile));									   // 		Class.forName(zeile.substring(0, 10).trim()) usw..
				else
					if (zeile.substring(0, 10).trim().equalsIgnoreCase("Skulptur")   )
						add (new Skulptur(zeile));
					else
						throw new GalerieException("Falsche Daten für importKunstwerke ("+zeile+") !!!");
				zeile = br.readLine();
			}
		}
		catch (FileNotFoundException e)
		{
			throw new GalerieException("Datei "+filename+" nicht gefunde!?!");
		}
		catch (IOException e)
		{
			throw new GalerieException("IO-Exception beim Lesen aus "+filename+"!?!");
		}
		catch (IndexOutOfBoundsException e)
		{
			throw new GalerieException( "Achtung ! IndexOutOfBounds beim Einlesen der "+
									    "Daten für ein Kunstwerk aus der Zeile \n"+zeile+"!\n");
		}
		finally  // Variante für's  close des Readers (entfällt bei try with ressources
		{
			if (br != null)
				try
				{
					br.close();
				}
				catch (IOException e)
				{
					//Logging
				}
			//else...
		}
	}
	
}
