package br.unifesspa.cre.core;


import javax.swing.JFrame;

import br.unifesspa.cre.config.CREEnv;
import br.unifesspa.cre.config.Param;
import br.unifesspa.cre.data.DAO;
import br.unifesspa.cre.hetnet.Scenario;
import br.unifesspa.cre.view.Topology;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		
		Scenario s;
		String path = "C:/Users/hugo/Desktop/CRE/cre1.data";
		
		DAO<Scenario> dao = new DAO<Scenario>();
		Boolean flag = dao.verifyPath(path);
		
		if (flag) {
			
			s = dao.restore(path);
			System.out.println("[INFO]: Loading file "+path+".");
			Thread.sleep(2000);
			
		}else{
			
			CREEnv env = new CREEnv();
			env.set(Param.area, 1000000.0); 	// 1 km^2
			env.set(Param.lambdaFemto, 0.00002); // 0.00002 Femto/m^2 = 20 Femtos  
			env.set(Param.lambdaUser, 0.0001); // 0.0001 Users/m^2 = 100 Users 
			env.set(Param.lambdaMacro, 0.000002); // 0.000002 Macros/m^2 = 2 Macros 
			
			s = new Scenario(env);
			dao.save(s, path);
			System.out.println("[INFO]: The file "+path+" was not found. File created.");
			Thread.sleep(2000);
		
		}
		
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setBounds(30, 30, 1000, 1000);
		window.getContentPane().add(new Topology(s));
		window.setVisible(true);	
	}
}