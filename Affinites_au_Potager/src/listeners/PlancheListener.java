package listeners;

import view.JTerrainMap;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import model.jardin.Planche;


public class PlancheListener implements MouseListener {

	private JTerrainMap jterrainmap;
	private Point start;

	public PlancheListener(JTerrainMap j){
		this.jterrainmap = j;
	}

	@Override
	public void mouseClicked(MouseEvent paramMouseEvent) {
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
		if ((end.y>longueurimage || end.x>largeurimage) && (start.y>longueurimage || start.x>largeurimage)){
			System.out.println("planche tout dehors");
			return;// la zone selectionnée est hors jardin, on ne fait rien
		}
		//au moins un des coins de la zone sélectionnée est dans le jardin
		if (end.y>longueurimage){ end.y=longueurimage-1;}
		if (end.x>largeurimage){ end.x=largeurimage-1;}
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
		int xDebut = this.start.x/tailleCase;
		int yDebut = this.start.y/tailleCase;
		int nbCasesX = Math.abs((end.x/tailleCase)-(start.x/tailleCase));
		int nbCasesY = Math.abs((end.y/tailleCase)-(start.y/tailleCase));
		int nbCases = Math.max(nbCasesX, nbCasesY)+1;
		if (Math.min(nbCasesY, nbCasesX) >= 2){
			System.out.println("Les planches doivent former une unique ligne !!!");
		}
		Planche laPlancheDefinie;

		if (nbCasesX>nbCasesY ){
			
			laPlancheDefinie = new Planche(xDebut, yDebut, nbCases, true, this.jterrainmap.getTerrain());
		}
		else {
			laPlancheDefinie = new Planche(xDebut, yDebut, nbCases, false, this.jterrainmap.getTerrain());
		}
		System.out.println("la planche : "+laPlancheDefinie);//System.out.println(this.soltype);

		try {
			this.jterrainmap.getTerrain()./*addPlanche*/ajouterPlanche(laPlancheDefinie);
			this.jterrainmap.changeZoneColor(this.jterrainmap.getTerrain().getZones().size());
		} catch (Exception e2) {
			System.out.println(e2.getMessage());
		};

		this.jterrainmap.repaint();
		this.start=null;
	}

	public String toString(){
		return("Listener de type PlancheListener");
	}

}


