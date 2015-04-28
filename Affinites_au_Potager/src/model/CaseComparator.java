package model;

import java.util.Comparator;

public class CaseComparator implements Comparator<Case> {

	@Override
	public int compare(Case c0, Case c1) {
		System.out.println(c0+" - "+c1);
		if (c0.x == c1.x){
			if (c0.y>c1.y){ return 1;}
			else if (c0.y == c1.y){ return 0;}
			else { return -1;}
		}
		else if (c0.y == c1.y){
			if (c0.x>c1.x){ return 1;}
			else if (c0.x == c1.x){ return 0;}
			else { return -1;}
		}
		else {
			throw new IllegalStateException();
		}
	}

}
