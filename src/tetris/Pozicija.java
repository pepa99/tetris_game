package tetris;

public class Pozicija {
     private int m;
     private int n;
     public enum Smer {LEVO, DESNO, DOLE}
     public Pozicija(int i1, int i2) {m=i1; n=i2;}
     public int vrsta() {return m;}
     public int kolona() {return n;}
     public Pozicija pomeri(Smer s) {
    	 if(s==Smer.LEVO) m--;
    	 if(s==Smer.DESNO) m++;
    	 if(Smer.DOLE==s) n++;
    	 return this;
     }
     public boolean jednake(Pozicija p) {return (m==p.m && n==p.n);}
     public Pozicija pozicija(Smer s) {
    	 Pozicija p=new Pozicija(m,n);
    	 p.pomeri(s);
    	 return p;
     }
}
