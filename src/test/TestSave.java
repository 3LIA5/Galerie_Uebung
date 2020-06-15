package test;

import model.Bild;
import model.Galerie;
import model.GalerieException;
import model.Skulptur;

public class TestSave
{
	public static void main(String[] args)
	{
		try
		{
			Galerie g = new Galerie("NewArts");
			Skulptur p1, p2, p3, p4, p5, p6;
			Bild b1, b2, b3, b4, b5, b6;
			
            b1 = new Bild("Waldmüller", "Mädchen mit Kuh", 100, 130, 10000., false);
            b2 = new Bild("Klimt", "Judith", 70, 80, 50000., true);
            b3 = new Bild("Schiele", "Krumau", 80, 100, 100000, true);
            b4 = new Bild("Brunner", "Horizonte",  130, 70, 1000., false);
            b5 = new Bild("Frank", "Tango",  60, 80, 500., false);
            b6 = new Bild("Schiele", "Frau",  80, 90, 100000., true);
            p1 = new Skulptur("Bruno Gironcoli","Weltuhr",500,700,500000.,350,"Stahl u. Messing");
            p2 = new Skulptur("Laura Browne","Your Face in my Hand",10,30,1000.,10,"Ton");
                p2.setSehrSelten(true);
            p3 = new Skulptur("Auguste Rodin","Wasserspiel",5000,5000,100000.,2000,"Eisen u. Stahl");
            p4 = new Skulptur("Auguste Rodin","Bürger von Calais",500,100,170000.,250,"Bronze");
            p5 = new Skulptur("Michelangelo","David",100,100,100000.,350,"Marmor");
            p6 = new Skulptur("Alberto Giacometti","Der Wagen",120,60,10000.,90,"Bronze");
                p6.setSehrSelten(true);
                
			g.add(b1);
			g.add(p3);
			g.add(b3);
			g.add(p5);
			g.add(b6);
			g.add(p1);
			g.add(p2);
			g.add(b4);
			g.add(p6);
			g.add(b2);
			g.add(p4);
			g.add(b5);
//			g.saveKunstwerke(null);
//			g.saveKunstwerke("X:\\scratch\\Kunstwerke.ser");
			String filename = "C:\\scratch\\Kunstwerke.ser";
			g.saveKunstwerke(filename);
			System.out.println(".........gesaved  nach " + filename);
		}
		catch (GalerieException ge)
		{
			System.out.println(ge.getMessage());
		}
	}
}
