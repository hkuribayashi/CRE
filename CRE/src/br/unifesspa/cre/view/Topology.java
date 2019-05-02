package br.unifesspa.cre.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

import br.unifesspa.cre.hetnet.BS;
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
	    
	    double aux = (this.scenario.getEnv().getArea()*this.scenario.getEnv().getLambdaMacro());
	    List<Point> bsPoints = new ArrayList<Point>();
	    for (BS bs : this.scenario.getAllBS())
			bsPoints.add(bs.getPoint());
	    
	    for (int i=0; i<bsPoints.size()-aux; i++) {
	    	Point p = bsPoints.get(i);
	    	rect = new Rectangle2D.Double(p.getX(), p.getY(), dimension, dimension);
			g2.fill(rect);
	    }
	    
	    g2.setColor(Color.RED);
	    
	    for (int i=(int)aux; i<bsPoints.size(); i++) {
	    	Point p = bsPoints.get(i);
	    	rect = new Rectangle2D.Double(p.getX(), p.getY(), dimension, dimension);
			g2.fill(rect);
	    }
	    
	    g2.setColor(Color.BLUE);
	    	    
	    for (int i=0; i<this.scenario.getUe().size(); i++) {
	    	Point p = this.scenario.getUe().get(i).getPoint();
	    	rect = new Rectangle2D.Double(p.getX(), p.getY(), dimension, dimension);
			g2.fill(rect);
	    }
	    
	}
}
