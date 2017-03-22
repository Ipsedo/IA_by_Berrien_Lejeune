package fousfous;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class TestNegABJoueur implements IJoueur {
	
	private int profMax;
	
	private int mColor;
	private String joueurMax;
	private String joueurMin;
	private PlateauFousFous mPartie;
	
	private HeuristiqueFousFous h;
	
	public TestNegABJoueur(){
		
	}

	public void initJoueur(int mycolour) {
		// TODO Auto-generated method stub
		this.mColor = mycolour;
		if(this.mColor == 1){
			this.joueurMax = PlateauFousFous.JBLANC;
			this.joueurMin = PlateauFousFous.JNOIR;
		} else {
			this.joueurMin = PlateauFousFous.JBLANC;
			this.joueurMax = PlateauFousFous.JNOIR;
		}
		this.mPartie = new PlateauFousFous();
		this.profMax = 4;
		this.h = new HeuristiqueFousFous();
	}

	public int getNumJoueur() {
		// TODO Auto-generated method stub
		return this.mColor;
	}
	
	private int negAB(int pronf, PlateauFousFous partie, int alpha, int beta, int parité){
		String joueur = parité > 0 ? this.joueurMax : this.joueurMin;
		if(pronf <= 0 || partie.finDePartie()){
			return parité * this.h.getH(joueur, partie);
		}else{
			for(String c : partie.mouvementsPossibles(joueur)){
	    		PlateauFousFous tmp = partie.clone();
	    		tmp.play(c, joueur);
	    		int tmpA = -negAB(pronf - 1, tmp, -beta, -alpha, -parité);
	    		alpha = Math.max(alpha, tmpA);
	    		if(alpha >= beta){
	    			return beta;
	    		}
			}
		}
		return alpha;
	}

	public String choixMouvement() {
		// TODO Auto-generated method stub
		ArrayList<String> coupsPossibles = new ArrayList<String>(Arrays.asList(this.mPartie.mouvementsPossibles(this.joueurMax)));
		int alpha = Integer.MIN_VALUE+1;
		int beta = Integer.MAX_VALUE-1;
	    PlateauFousFous tmpP = this.mPartie.clone();
		String meilleurCoup = coupsPossibles.get(0);
		coupsPossibles.remove(0);
		tmpP.play(meilleurCoup, this.joueurMax);
		alpha = -this.negAB(this.profMax - 1, tmpP, -beta, -alpha, -1);
		for(String c : coupsPossibles){
			tmpP = this.mPartie.clone();
			tmpP.play(this.joueurMax, c);
			int newVal = -this.negAB(this.profMax - 1, tmpP, -beta, -alpha, -1);
			System.out.println(newVal);
			if(newVal > alpha){
				meilleurCoup = c;
				alpha = newVal;
			}
		}
		this.mPartie.play(meilleurCoup, this.joueurMax);   
		return meilleurCoup;
	}

	public void declareLeVainqueur(int colour) {
		// TODO Auto-generated method stub

	}

	public void mouvementEnnemi(String coup) {
		// TODO Auto-generated method stub
		this.mPartie.play(coup, this.joueurMin);

	}

	public String binoName() {
		// TODO Auto-generated method stub
		return null;
	}

}
