package tetris;

import java.awt.Color;
import java.awt.Graphics;

import tetris.Pozicija.Smer;

public class Dvodelni extends Figura {

	public Dvodelni(Pozicija poz, Color b) {
		super(poz, b);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean prostire(Pozicija p) {
		Pozicija poz=this.p.pozicija(Smer.DESNO);
		if(p.jednake(this.p) || p.jednake(poz)) return true;
		return false;
	}

	@Override
	public void crtaj(Tabla tabla) {
		Graphics g=tabla.getGraphics();
		g.setColor(boja);
		g.fillRect(p.vrsta()*tabla.dx, p.kolona()*tabla.dy, 2*tabla.dx, tabla.dy);
	}

	@Override
	public boolean moze(Smer s, Tabla t) {
		try {
		Pozicija poz=new Pozicija(p.vrsta(),p.kolona());
		if(s==Smer.LEVO) {poz.pomeri(s); return !t.zauzeta(poz);}
		if(s==Smer.DOLE) {poz.pomeri(s); return(!(t.zauzeta(poz)) && !(t.zauzeta(poz.pomeri(Smer.DESNO))) );}	
		if(Smer.DESNO==s) {return !t.zauzeta(poz.pomeri(s).pomeri(s));}
		return false;
		} catch(GOpseg e) {
		return false;}
	}

	@Override
	protected void pomeraj(Smer s, Tabla t) {
		p.pomeri(s);
		t.repaint();
	}

}
