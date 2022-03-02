package view;

import controller.RedesController;
import javax.swing.JOptionPane;

public class Main {

	public static void main(String[] args) {
		
		RedesController redControl = new RedesController();
		String os = redControl.os();
		
		
		String[] btns = {"IP", "Ping", "Sair"};
		int opt = JOptionPane.showOptionDialog(
				null, 
				"Seu sistema operacional é o "+os+". Escolha o que fazer a seguir.",
				"Exercício 1 de Processos",
				JOptionPane.DEFAULT_OPTION, 
				JOptionPane.PLAIN_MESSAGE, 
				null, 
				btns,
				btns[0]);
		
	
		if (opt == 0){ 
			redControl.ipRead();
		}
		else if (opt == 1) { 
			redControl.pingRead();
		}else {}
		
	}

}