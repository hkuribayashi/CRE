package br.unifesspa.cre.hetnet;

public class MCS {

	public static Double getEfficiency(Double sinr) {
		
		Double efficiency = 0.15;
		
		if (sinr <= -4.0)
			efficiency = 0.23;
		else if (sinr <= -2.6)
			efficiency = 0.38;
		else if (sinr <= -1.0)
			efficiency = 0.6;
		else if (sinr <= 1.0)
			efficiency = 0.88;
		else if (sinr <= 3.0)
			efficiency = 1.18;
		else if (sinr <= 6.6)
			efficiency = 1.48;
		else if (sinr <= 10.0)
			efficiency = 1.91;
		else if (sinr <= 11.4)
			efficiency = 2.41;
		else if (sinr <= 11.8)
			efficiency = 2.73;
		else if (sinr <= 13.0)
			efficiency = 3.32;
		else if (sinr <= 13.8)
			efficiency = 3.9;
		else if (sinr <= 15.6)
			efficiency = 4.52;
		else if (sinr <= 16.8)
			efficiency = 5.12;
		else efficiency = 5.55;
		
		return efficiency;
	}
}
