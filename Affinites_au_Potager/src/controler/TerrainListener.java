package controler;

import view.JTerrainMap;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.InvocationTargetException;


public class TerrainListener implements MouseListener {

	private JTerrainMap jterrainmap;
	private Point start;
	private String soltype;
	
	public TerrainListener(JTerrainMap j){
		this.jterrainmap = j;
		this.soltype = "HorsJardin";
	}

	/**
	 * @return the soltype
	 */
	public String getSoltype() {
		return soltype;
	}

	/**
	 * @param soltype the soltype to set
	 */
	public void setSoltype(String soltype) {
		this.soltype = soltype;
		System.out.println(soltype);

	}
	


	@Override
	public void mouseClicked(MouseEvent paramMouseEvent) {
		System.out.println("clic"+this.soltype);
		int x = paramMouseEvent.getX()/(this.jterrainmap.getTailleCase());
		int y = paramMouseEvent.getY()/(this.jterrainmap.getTailleCase());
		System.out.println("abscisse "+x);
		try{
			System.out.println("sol "+this.soltype);
			this.jterrainmap.getTerrain().setCase(y, x, soltype);

			//this.jterrainmap.getTerrain().getTerrain()[y][x].setSoltype(SolType.valueOf(soltype));
			System.out.println("case "+this.jterrainmap.getTerrain().getTerrain()[y][x].typeString()/*0.getSoltype()*/);
			this.jterrainmap.repaint();	
		}
		catch(Exception e){}
	}

	@Override
	public void mouseEntered(MouseEvent paramMouseEvent) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent paramMouseEvent) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		this.start = new Point(e.getX(), e.getY());
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		int tailleCase = this.jterrainmap.getTailleCase();
		int longueurimage = this.jterrainmap.getTerrain().getTerrain()[0].length*tailleCase;
		int largeurimage = this.jterrainmap.getTerrain().getTerrain().length*tailleCase;
		Point end = new Point(e.getX(), e.getY());
		System.out.println("long " +longueurimage+" larg "+largeurimage+" stx "+start.x+" sty "+start.y+" endx "+end.x+" endy "+end.y);

		if ((end.x>longueurimage || end.y>largeurimage) && (start.x>longueurimage || start.y>largeurimage)){
			System.out.println("tout dehors");
			return;// la zone selectionnée est hors jardin, on ne fait rien
		}
		//au moins un des coins de la zone sélectionnée est dans le jardin
		if (end.x>longueurimage){ end.x=longueurimage-1;}
		if (end.y>largeurimage){ end.y=largeurimage-1;}
		if (end.x < start.x){
			int tmp = start.x;
			start.x = end.x;
			end.x = tmp-1;
		}
		if (end.y < start.y){
			int tmpy = start.y;
			start.y = end.y;
			end.y = tmpy-1;
		}
		System.out.println("long " +longueurimage+" larg "+largeurimage+" stx "+start.x+" sty "+start.y+" endx "+end.x+" endy "+end.y);
System.out.println(this.soltype);
		for (int i = (start.x)/(this.jterrainmap.getTailleCase()); i<1+end.x/tailleCase; i++){
			for (int j = start.y/(this.jterrainmap.getTailleCase()); j<1+end.y/tailleCase; j++){
				//this.jterrainmap.getTerrain().getTerrain()[j][i].setSoltype(SolType.valueOf(this.soltype));
				try {
					this.jterrainmap.getTerrain().setCase(j, i, soltype);
				} catch (ClassNotFoundException | InstantiationException
						| IllegalAccessException | NoSuchMethodException
						| SecurityException | IllegalArgumentException
						| InvocationTargetException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				};

			}
		}
		this.jterrainmap.repaint();
		this.start=null;
	}


}


