package tetris;

import java.awt.Color;
import java.awt.Graphics;

import tetris.Pozicija.Smer;

public class Jednodelni extends Figura {

	public Jednodelni(Pozicija poz, Color b) {
		super(poz, b);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean prostire(Pozicija p) {
		return (this.p.jednake(p));
	}

	@Override
	public void crtaj(Tabla tabla) {
		Graphics g=tabla.getGraphics();
		g.setColor(boja);
		g.fillOval(p.vrsta()*tabla.dx, p.kolona()*tabla.dy, tabla.dx, tabla.dy);
	}

	@Override
	public boolean moze(Smer s, Tabla t) {
		Pozicija poz=p.pozicija(s);
		try {
		return !(t.zauzeta(poz));} catch(GOpseg e) {return false;}
	}

	@Override
	protected void pomeraj(Smer s, Tabla t) {
		p.pomeri(s);
		t.repaint();
	}

}
