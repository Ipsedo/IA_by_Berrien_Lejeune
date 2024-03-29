package fousfous.joueur;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import fousfous.PlateauFousFous;

/**
 * Pas terrible sans table de transposition
 * @author samuel
 *
 */
public class NegABEchecJoueur extends Joueur {
	

	public void initJoueur(int mycolour) {
		super.initJoueur(mycolour);
	}
	
	private int negABEchec(int pronf, PlateauFousFous partie, int alpha, int beta, int parité){
		String joueur = parité > 0 ? this.joueurMax : this.joueurMin;
		int max;
		if(pronf <= 0 || partie.finDePartie()){
			max = parité * this.h.computeHeuristique(this.joueurMax, partie);
		}else{
			max = Integer.MIN_VALUE + 1;
			for(String c : partie.mouvementsPossibles(joueur)){
	    		PlateauFousFous tmp = partie.copy();
	    		tmp.play(c, joueur);
	    		max = Math.max(max, -negABEchec(pronf - 1, tmp, -beta, -alpha, -parité));
	    		alpha = Math.max(alpha, max);
	    		if(alpha >= beta){
	    			return max;
	    		}
			}
		}
		return max;
	}

	public String choixMouvement() {
		System.out.println(this.sdf.format(new Date()) + ", " + this.binoName() + ", profondeur max : " + this.profMax);
		
		ArrayList<String> coupsPossibles = new ArrayList<String>(Arrays.asList(this.mPartie.mouvementsPossibles(this.joueurMax)));
		
		if(coupsPossibles.isEmpty()){
			return "xxxxx";
		}
		
		int alpha = 1;
		int beta = 10;
		
	    PlateauFousFous tmpP = this.mPartie.copy();
	    
		String meilleurCoup = coupsPossibles.get(0);
		coupsPossibles.remove(0);
		tmpP.play(meilleurCoup, this.joueurMax);
		
		int max = Integer.MIN_VALUE + 1;
		
		max = Math.max(max, -this.negABEchec(this.profMax - 1, tmpP, -beta, -alpha, -1));
		
		for(String c : coupsPossibles){
			tmpP = this.mPartie.copy();
			tmpP.play(c, this.joueurMax);
			max = Math.max(max, -this.negABEchec(this.profMax - 1, tmpP, -beta, -alpha, -1));
			if(max > alpha){
				meilleurCoup = c;
				alpha = max;
			}
			if(alpha >= beta){
				break;
			}
		}
		this.mPartie.play(meilleurCoup, this.joueurMax);
		System.out.println("A joué : " + meilleurCoup);
		System.out.println(this.mPartie);
		return meilleurCoup;
	}

	public String binoName() {
		// TODO Auto-generated method stub
		return "NegABEchec";
	}

}
