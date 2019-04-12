package br.unifesspa.cre.ga;

public enum CrossoverStrategy {
	
	OnePoint(1),TwoPoint(2),Uniform(3);
	 
    public int value;
    
    CrossoverStrategy(int value) {
        this.value = value;
    }

}