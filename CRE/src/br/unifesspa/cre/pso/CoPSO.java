package br.unifesspa.cre.pso;

import br.unifesspa.cre.hetnet.Scenario;
import br.unifesspa.cre.util.Util;

public class CoPSO extends PSO{

	private static Double cognitiveCoeffcient = 2.05;
	
	private static Double socialCoeffcient = 2.05;
	
	public Double constricionFactor;
	
	public CoPSO(Double alpha, Double beta, Scenario scenario, Double steps, Integer swarmSize, Double targetSolution) {
		
		super(alpha, beta, scenario, steps, swarmSize, targetSolution);
		
		Double c = CoPSO.cognitiveCoeffcient + CoPSO.socialCoeffcient;
		
		this.constricionFactor = 2.0/(Math.abs(2.0 - c - Math.sqrt( Math.pow(c, 2) - (4*c) )));
		
		System.out.println("Constriction Factor: "+this.constricionFactor);
	}
	
	@Override
	public void updateVelocity(Particle p) {
		Double[] temp = Util.minus(p.getBestPosition(), p.getPosition());
		Double[] cognitiveComponent = Util.product(temp, StaticIWPSO.cognitiveCoeffcient);
		
		Double[] temp2 = Util.minus(gBest.getPosition(), p.getPosition());
		Double[] socialComponent = Util.product(temp2, StaticIWPSO.socialCoeffcient);
		
		p.setVelocity( Util.product( Util.sum(p.getVelocity(), cognitiveComponent, socialComponent), this.constricionFactor ) );
	}
}