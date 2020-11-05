package tetris;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Label;

import tetris.Pozicija.Smer;

public class Tabla extends Canvas implements Runnable {
    private Thread nit=new Thread(this);
    private int m;
    private int n;
    Label poenil=new Label("Ponei: ");
    Label figure=new Label("Figure: ");
    private boolean radi=false;
    private Figura pokretna=null;
    private Figura pripravnost=null;
    private int poeni=0;
    private int broj=0;
    private static class Elem{
    	Figura f;
    	Elem sledeci;
    	Elem(Figura fig){f=fig;}
    }
    private Elem prvi,posl;
    int dx, dy;
    public Tabla(int i1, int i2) {
    	m=i1;
    	n=i2;
    	setBackground(Color.LIGHT_GRAY);
    	setBounds(0,0,400,600);
    	setSize(400,600);
    	dx=getWidth()/n;
    	dy=getHeight()/m;
    	prvi=posl=null;
	    int i11=(int) (4*Math.random());
	    Color boja=Color.BLACK;
	    if(i11==0)
		boja=(Color.RED);
	    if(i11==1) boja=(Color.BLUE);
	    if(i11==2) boja=(Color.GREEN);
	    if(i11==3) boja=(Color.YELLOW);
		int i22=(int) (2*Math.random());
		Pozicija poz=new Pozicija(n/2,0);
		if(i22==0) pripravnost=new Jednodelni(poz,boja);
		else pripravnost=new Dvodelni(poz,boja);
		dodaj(pripravnost);
		pokretna=pripravnost;
		poenil.setFont(new Font(null,Font.BOLD,22));
		figure.setFont(new Font(null,Font.BOLD,22));
		stvorinovu();
    	nit.start();
    }
    private void stvorinovu() {
    	int i11=(int) (4*Math.random());
	    Color boja=Color.BLACK;
	    if(i11==0)
		boja=(Color.RED);
	    if(i11==1) boja=(Color.BLUE);
	    if(i11==2) boja=(Color.GREEN);
	    if(i11==3) boja=(Color.YELLOW);
		int i22=(int) (2*Math.random());
		Pozicija poz=new Pozicija(n/2,0);
		if(i22==0) pripravnost=new Jednodelni(poz,boja);
		else pripravnost=new Dvodelni(poz,boja);
		dodaj(pripravnost);
    }
    public int vrste() {return m;}
    public int kolone() {return n;}
    public synchronized void kreni() {radi=true; notifyAll();}
    public void zaustavi() {radi=false;}
    public synchronized void postavim(int i) {m=i; dx=getWidth()/m;}
    public synchronized void postavin(int i) {n=i;dy=getHeight()/n;}
    public synchronized void dodaj(Figura f) {
    	if(prvi==null) {prvi=new Elem(f);posl=prvi;}
    	else {posl.sledeci=new Elem(f);posl=posl.sledeci;}
    	broj++;
    	figure.setText("Figure: "+broj);
    }
    public synchronized boolean zauzeta(Pozicija p) throws GOpseg {
    	if(p.vrsta()<0 || p.vrsta()>=n || p.kolona()>=m) throw new GOpseg();
    	Elem tek=prvi;
    	while(tek!=null) {
    		if(tek.f.prostire(p)) return true;
    		tek=tek.sledeci;
    	}
    	return false;
    }
    private synchronized void ukloni(Figura f) {
    	Elem tek=prvi;
    	Elem preth=null;
    	while(tek!=null && tek.f!=f) {
    		preth=tek;
    		tek=tek.sledeci;
    	}
    	if(tek!=null) {
        broj--;
        figure.setText("Figure: "+broj);
    	if(tek==posl) {posl=preth;}
    	if(tek==prvi) {prvi=prvi.sledeci;}
    	else {preth.sledeci=tek.sledeci;}
    	}
    }
	@Override
	public void run() {
		while(!Thread.interrupted()) {
			try {
			while(!radi) synchronized(this) {wait();}
			//System.out.println("-1");
			} catch(InterruptedException e) {}
			if(pokretna!=null) {
				//System.out.println("0");
				if(pokretna.moze(Smer.DOLE, this) && pokretna.pozicija().vrsta()!=n) {
					try {
					//	System.out.println("1");
						pokretna.pomeri(Smer.DOLE, this);
					} catch (GNemoguce e1) {}
					try {
						Thread.sleep(600);
					} catch (InterruptedException e) {}
				} else {
					//stvorinovu();
					Elem tek=prvi;
					int i;
					for(i=0;i<n;i++) {
						try {
							if(!zauzeta(new Pozicija(i,pokretna.pozicija().kolona()))) {
								break;
							}
						} catch (GOpseg e) {}
					}
						if(i==n) {
							//System.out.println("Rdi evo ga");
							while(tek!=null) {
								if(tek.f.pozicija().kolona()==pokretna.pozicija().kolona()) {
									ukloni(tek.f);
									repaint();
								}
								tek=tek.sledeci;
							}
							tek=prvi;
							while(tek!=null) {
								if(tek.f.moze(Smer.DOLE, this))
									try {
										tek.f.pomeri(Smer.DOLE,this);
										repaint();
									} catch (GNemoguce e) {}
								tek=tek.sledeci;
							}
							poeni+=100;
							poenil.setText("P: "+poeni );
						}
					
					pokretna=null;
					pokretna=pripravnost;
					stvorinovu();
				}
			}
		}
	}
	public void prekini() {nit.interrupt();}
	public synchronized void pomeri(Smer s) {
		try {
			pokretna.pomeri(s, this);
		} catch (GNemoguce e) {
			
		}
	}
    public void paint(Graphics g) {
    	for(int i=0;i<m;i++)
    		g.drawLine(0,i*dy,getWidth(),i*dy);
    	for(int i=0;i<n;i++)
    		g.drawLine(i*dx,0,i*dx,getHeight());
    	Elem tek=prvi;
    	synchronized(this) {
    	while(tek!=null) {
    		tek.f.crtaj(this);
    		tek=tek.sledeci;
    	}
    	}
    }
}
