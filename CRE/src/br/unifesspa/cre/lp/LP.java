package br.unifesspa.cre.lp;

import gurobi.GRB;
import gurobi.GRBEnv;
import gurobi.GRBLinExpr;
import gurobi.GRBModel;
import gurobi.GRBVar;

public class LP {

	public static void main(String[] args) {

		try {

			int nSBS = 10;
			int nUE = 10;
			
			double alpha = 1.0;
			double beta = 1.0;

			GRBEnv env = new GRBEnv();

			env.set(GRB.IntParam.OutputFlag, 1); 
			env.set(GRB.IntParam.Threads, 2); 
			env.set(GRB.DoubleParam.MIPGap, 0.01);

			GRBModel model = new GRBModel(env);
			model.set(GRB.StringAttr.ModelName, "LP");


		}catch(Exception e) {

		}

	}
}