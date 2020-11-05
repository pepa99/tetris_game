package tetris;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import tetris.Pozicija.Smer;
public class Tetris extends Frame implements ActionListener {
    Label poeni=new Label("Ponei: ");
    Label figure=new Label("Figura: ");
    private Tabla tabla;
    private Panel panel;
    private Panel p2;
    private Button kreni=new Button("Kreni");
    private Button stani=new Button("Stani");
    private Label xy=new Label("x, y");
    private TextField x=new TextField();
    private TextField y=new TextField();
    private int m=5;
    private int n=10;
    private boolean utoku;
    private Menu akcija=new Menu("Akcija");
    public Tetris() {
    	super("Tetris");
    	setSize(500,800);
    	dodajKompon();
    	addWindowListener(new WindowAdapter () {public void windowClosing(WindowEvent e) {dispose();}});
    	tabla.addKeyListener(new KeyAdapter() {
    		@Override
    		public void keyPressed(KeyEvent e) {
    			
    			if(e.getKeyCode()==KeyEvent.VK_RIGHT) {
    				tabla.pomeri(Smer.DESNO);
    				
    			}
    			if(e.getKeyCode()==KeyEvent.VK_LEFT) {
    				tabla.pomeri(Smer.LEVO);
    			}
    			if(e.getKeyCode()==KeyEvent.VK_DOWN) {
    				tabla.pomeri(Smer.DOLE);
    			}
    	    }
    		
    		
    		});
    
    	setVisible(true);
    }
    public void dodajKompon() {
    	panel=new Panel();
    	panel.setBounds(0, 0, 400, 600);
    	tabla=new Tabla(10,20);
    	panel.add(tabla);
    	add(panel);
    	p2=new Panel();
    	figure=tabla.figure;
    	poeni=tabla.poenil;
    	p2.add(kreni);
    	p2.add(poeni);
    	p2.add(figure);
    	p2.add(stani);
    	p2.add(xy);
    	p2.add(x);
    	p2.add(y);
    	MenuBar traka=new MenuBar();
    	traka.add(akcija);
    	akcija.add("Nova Igra");
    	akcija.add("Zavrsi");
    	akcija.addActionListener(this);
    	setMenuBar(traka);
    	x.addActionListener(this);
    	y.addActionListener(this);
    	add(p2,"South");
    	kreni.addActionListener(this);
    	stani.addActionListener(this);
    }
	public static void main(String[] args) {
	    new Tetris();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==kreni) {
			tabla.kreni();
			utoku=true;
		}
		if(e.getSource()==stani) {
			tabla.zaustavi();
			utoku=false;
		}
		if(e.getSource()==x) {
			if(!utoku) {
				m=Integer.parseInt(x.getText());
				//tabla.postavim(Integer.parseInt(x.getText()));
			}
		}
		if(e.getSource()==y) {
			if(!utoku) {
				n=Integer.parseInt(y.getText());
				//tabla.postavim(Integer.parseInt(y.getText()));
			}
		}
		if(e.getActionCommand().equals("Nova Igra")) {
			panel.remove(tabla);
			tabla=new Tabla(m,n);
			p2.remove(figure);
			p2.remove(poeni);
			figure=tabla.figure;
	    	poeni=tabla.poenil;
	    	p2.add(figure);
	    	p2.add(poeni);
			panel.add(tabla);
			tabla.addKeyListener(new KeyAdapter() {
	    		@Override
	    		public void keyPressed(KeyEvent e) {
	    			
	    			if(e.getKeyCode()==KeyEvent.VK_RIGHT) {
	    				tabla.pomeri(Smer.DESNO);
	    				
	    			}
	    			if(e.getKeyCode()==KeyEvent.VK_LEFT) {
	    				tabla.pomeri(Smer.LEVO);
	    			}
	    			if(e.getKeyCode()==KeyEvent.VK_DOWN) {
	    				tabla.pomeri(Smer.DOLE);
	    			}
	    	    }
	    		
	    		
	    		});
			revalidate();
		}
	}
	
}
