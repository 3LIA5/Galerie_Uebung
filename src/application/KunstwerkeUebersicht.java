package application;

import java.util.List;
import javafx.scene.control.ListView;
import model.Kunstwerk;

public class KunstwerkeUebersicht extends ListView<Kunstwerk>
{

	public KunstwerkeUebersicht()
	{
		// TODO Auto-generated constructor stub
	}
	public void update(List<Kunstwerk> kunstwerke)
	{
		this.getItems().addAll(kunstwerke);
	}


}
