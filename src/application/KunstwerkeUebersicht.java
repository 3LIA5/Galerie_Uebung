package application;

import java.util.List;


import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseEvent;
import model.Kunstwerk;

public class KunstwerkeUebersicht extends ListView<Kunstwerk>
{
	private RootBorderPane rootBorderPane;
	private MyContextMenu myContextMenu;
	
	public KunstwerkeUebersicht(RootBorderPane rootBorderPane)
	{
		this.rootBorderPane = rootBorderPane;
		myContextMenu = new MyContextMenu();
		getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		setOnMouseClicked(event -> mouseEventSelection(event));
	}

	private void mouseEventSelection(MouseEvent event)
	{
		if (event.isPopupTrigger())
			myContextMenu.show(this, event.getScreenX(), event.getScreenY());
		else
			if(event.getClickCount()>1)
				rootBorderPane.aendern();
	}

	public void update(List<Kunstwerk> kunstwerke)
	{
		this.getItems().setAll(kunstwerke);
	}
	
	private class MyContextMenu extends ContextMenu
	{
		private MenuItem aendern, loeschen;
		public MyContextMenu()
		{
			aendern = new MenuItem("ändern");
			loeschen = new MenuItem("löschen");
			getItems().addAll(aendern, loeschen);
			aendern.setOnAction(event -> rootBorderPane.aendern());
			loeschen.setOnAction(event -> rootBorderPane.loeschen());
		}
	}


}
