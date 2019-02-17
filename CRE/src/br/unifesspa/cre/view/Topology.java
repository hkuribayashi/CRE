package br.unifesspa.cre.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;

import br.unifesspa.cre.hetnet.Point;
import br.unifesspa.cre.hetnet.Scenario;

public class Topology extends JComponent{

	private static final long serialVersionUID = -7793503595451200749L;
	
	private Scenario scenario;
	
	public Topology(Scenario scenario) {
		this.scenario = scenario;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Scenario getScenario() {
		return scenario;
	}
	
	public void setScenario(Scenario scenario) {
		this.scenario = scenario;
	}
	
	public void paint(Graphics g) {
		
		Rectangle2D rect;
		Graphics2D g2 = (Graphics2D) g;	
		Integer dimension = 5;
		
		Insets insets = getInsets();
	    int h = getHeight() - insets.top - insets.bottom;
	    g2.scale(1.0, -1.0);
	    g2.translate(0, -h - insets.top);
	    
	    g2.setColor(Color.BLACK);
	    
	    for (int i=0; i<=this.scenario.getFemtoPoints().size()-1; i++) {
	    	Point p = this.scenario.getFemtoPoints().get(i);
	    	rect = new Rectangle2D.Double(p.getX(), p.getY(), dimension, dimension);
			g2.fill(rect);
	    }
	    
	    g2.setColor(Color.RED);
	    
	    for (int i=0; i<=this.scenario.getUserPoints().size()-1; i++) {
	    	Point p = this.scenario.getUserPoints().get(i);
	    	rect = new Rectangle2D.Double(p.getX(), p.getY(), dimension, dimension);
			g2.fill(rect);
	    }
	    
	    g2.setColor(Color.BLUE);
	    	    
	    for (int i=0; i<=this.scenario.getMacroPoints().size()-1; i++) {
	    	Point p = this.scenario.getMacroPoints().get(i);
	    	rect = new Rectangle2D.Double(p.getX(), p.getY(), dimension, dimension);
			g2.fill(rect);
	    }
	    
	}
}
