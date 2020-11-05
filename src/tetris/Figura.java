package tetris;

import java.awt.Color;
import java.awt.Graphics;

public abstract class Figura {
      protected Pozicija p;
      protected Color boja;
      private Tabla t;
      public Figura(Pozicija poz, Color b) {p=poz;boja=b;}
      public abstract void crtaj(Tabla tabla);
      public Pozicija pozicija() {return p;}
      public abstract boolean prostire(Pozicija p);
      public abstract boolean moze(Pozicija.Smer s, Tabla t);
      public void pomeri(Pozicija.Smer s, Tabla t) throws GNemoguce{
    	  if(!moze(s,t)) throw new GNemoguce();
    	  pomeraj(s,t);
      }
      protected abstract void pomeraj(Pozicija.Smer s, Tabla t);
}
