package br.unifesspa.cre.ga;

public enum MutationStrategy {
	
	Flat(0),NotUniform(1);
	 
    public int value;
    
    MutationStrategy(int value) {
        this.value = value;
    }

}