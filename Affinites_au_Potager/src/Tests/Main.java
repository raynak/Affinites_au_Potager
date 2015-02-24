package Tests;

import java.util.LinkedList;

import exceptions.PlancheConstructorException;
import model.Case;
import model.Jardin;
import model.Planche;
import model.ZonePlantation;

public class Main {
public static void main(String[] args) throws PlancheConstructorException {
	Jardin jardin = new Jardin(10,20);
	LinkedList<Case> cases = new LinkedList<Case>();
	for(int i=0;i<5;i++){
		cases.add(jardin.getTerrain()[0][i]);
	}
	Planche planche = new Planche(cases);
	LinkedList<Planche> planches = new LinkedList<Planche>();
	planches.add(planche);
	ZonePlantation zone = new ZonePlantation();
	zone.setPlanches(planches);
	LinkedList<ZonePlantation> zones = new LinkedList<>();
	zones.add(zone);
	jardin.setZones(zones);
	
}
}
