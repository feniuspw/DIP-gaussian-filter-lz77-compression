package pdi_23803_24761.controle;

import java.util.ArrayList;

import javax.swing.UIManager;

import pdi_23803_24761.view.InterfacePrincipal;


public class ControleApp {
	
private InterfacePrincipal inter;
	
	public ControleApp(){
		
	}
	
	public void iniciaPrograma(){
		aplicarTema();
		inter = new InterfacePrincipal();
	}
	
	/**Método para fechar o programa**/
	public void fecharPrograma(){
		System.exit(1);
	}
	
	public void aplicarTema(){
		try{
			for(UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()){
				if("Windows".equals(info.getName())){
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		}
		catch(Exception e){
			System.out.println(e);
		}
	

	}
}
