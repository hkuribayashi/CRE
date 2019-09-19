package br.unifesspa.cre.view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import br.unifesspa.cre.ga.NetworkElement;
import br.unifesspa.cre.hetnet.BS;
import br.unifesspa.cre.hetnet.BSType;
import br.unifesspa.cre.hetnet.Point;
import br.unifesspa.cre.hetnet.Scenario;
import br.unifesspa.cre.hetnet.UE;

public class Topology{

	private static final long serialVersionUID = -7793503595451200749L;

	private Scenario scenario;
	
	private String filename;

	public Topology(Scenario scenario, String prefix, Double alpha, Double beta) {
		this.scenario = scenario.clone();
		this.filename = prefix+"-alpha-"+alpha+"-beta-"+beta+".png";
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

	public void paint() {

		int width = 1010;
		int height = 1010;

		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		Rectangle2D rect;
		Graphics2D g2 = img.createGraphics();
		
        g2.setColor(Color.white);
        g2.fillRect(0, 0, width, height);
		
		Integer dimension = 5;

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


		g2.setColor(Color.BLACK);
		for (UE ue: this.scenario.getUe()) {
			rect = new Rectangle2D.Double(ue.getPoint().getX(), ue.getPoint().getY(), dimension, dimension);
			g2.fill(rect);
		}

		for(BS bs: this.scenario.getAllBS()) {
			if (bs.getType().equals(BSType.Macro)) {
				dimension = 10;
				g2.setColor(Color.RED);
			}else {
				dimension = 5;
				g2.setColor(Color.BLUE);
			}
			Point p = bs.getPoint();
			rect = new Rectangle2D.Double(p.getX(), p.getY(), dimension, dimension);
			g2.fill(rect);
		}	

		g2.setColor(Color.MAGENTA);

		NetworkElement[][] network = this.scenario.getNetwork();
		for (int j=0; j<network[0].length; j++) {
			for (int i=0; i<network.length; i++) 
				if (network[i][j].getCoverageStatus()) {
					Point uePoint = this.scenario.getUe().get(i).getPoint();
					Point bsPoint = this.scenario.getAllBS().get(j).getPoint();
					Line2D lin = new Line2D.Double(uePoint.getX(), uePoint.getY(), bsPoint.getX(), bsPoint.getY());
					g2.draw(lin);
				}
		}

		g2.dispose();

		String path = "/Users/hugo/Desktop/CRE/CRE/images/"+ this.filename;
		System.out.println(path);
		File file = new File(path);
		try {
			ImageIO.write(img, "png", file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
