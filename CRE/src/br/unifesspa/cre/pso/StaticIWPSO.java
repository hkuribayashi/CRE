package br.unifesspa.cre.pso;

import br.unifesspa.cre.hetnet.Scenario;
import br.unifesspa.cre.util.Util;

/**
 * PSO with Inertia Weight
 * 
 * @author hugo
 *
 */
public class StaticIWPSO extends PSO {
	
	protected static Double cognitiveCoeffcient = 2.0;
	
	protected static Double socialCoeffcient = 2.0;
	
	private static Double InertialWeight = 0.5;

	public StaticIWPSO(Double alpha, Double beta, Scenario scenario, Double steps, Integer swarmSize, Double targetSolution) {
		
		super(alpha, beta, scenario, steps, swarmSize, targetSolution);
	}
	
	@Override
	public void updateVelocity(Particle p) {
		Double[] temp = Util.minus(p.getBestPosition(), p.getPosition());
		Double[] cognitiveComponent = Util.product(temp, StaticIWPSO.cognitiveCoeffcient);
		
		Double[] temp2 = Util.minus(gBest.getPosition(), p.getPosition());
		Double[] socialComponent = Util.product(temp2, StaticIWPSO.socialCoeffcient);
		
		p.setVelocity( Util.product(Util.sum(p.getVelocity(), Util.sum(cognitiveComponent, socialComponent)), StaticIWPSO.InertialWeight) );
	}
	
	@Override
	public void run() {
		this.search();	
	}
}
