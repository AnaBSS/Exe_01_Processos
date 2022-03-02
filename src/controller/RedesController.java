package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import javax.swing.JOptionPane;

public class RedesController {
	
	private Process iProcess(String process) {
		try {
			return Runtime.getRuntime().exec(process);
		} catch (Exception e) {
			String msgErro = e.getMessage();
			if (msgErro.contains("740")) {
				StringBuffer buffer = new StringBuffer();
				buffer.append("cmd /c");
				buffer.append(" ");
				buffer.append(process);
				try {
					Runtime.getRuntime().exec(buffer.toString());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}else {
				System.err.println(msgErro);
			}
			return null;
		}
	}
	

	public String os() {
		return System.getProperty("os.name");
	}
	
	public void ipRead() {
		String ipCmd = "";
		if (os().contains("Windows")) {
			ipCmd = "ipconfig";
		}
		else if (os().contains("Linux")) {
			ipCmd = "ifcongig";
		}
		
		Process p = iProcess(ipCmd);
		InputStream fluxo = p.getInputStream();
		InputStreamReader leitor = new InputStreamReader(fluxo, StandardCharsets.UTF_8);
		BufferedReader buffer = new BufferedReader(leitor);		
		String adaptador = "";
		String info_adapt= "";
		
		try {
			String linha = buffer.readLine();
			while(linha != null) {
				if (linha.contains("Adaptador")) {
					adaptador = linha;
				}
				if (linha.contains("IPv4") || linha.contains("inet")) {
					info_adapt += adaptador + "\n" + linha + "\n";
				}
				linha = buffer.readLine();
			}
			JOptionPane.showMessageDialog(null, info_adapt);
		}catch (IOException e2) {
			System.err.println("Comando inválido");
		}
	}
	
	public void pingRead () {
		String pingCmd = "";
		if (os().contains("Windows")) {
			pingCmd = "ping -4 -n 10 www.google.com.br";
		}
		else if (os().contains("Linux")) {
			pingCmd = "ping -4 -c 10 www.google.com.br";
		}
		
		Process p = iProcess(pingCmd);
		InputStream fluxo = p.getInputStream();
		InputStreamReader leitor = new InputStreamReader(fluxo, StandardCharsets.UTF_8);
		BufferedReader buffer = new BufferedReader(leitor);
		
		try {
			String linha = buffer.readLine();
			
			while (linha != null) {
				System.out.println(linha);
				
				if (linha.contains("dia") || linha.contains("Average")) {
					String [] linhaVector = linha.split("ms");
					int tamanho = linhaVector.length;
					String media = linhaVector[tamanho - 1].replaceAll("\\D+", "");
					JOptionPane.showMessageDialog(null, "Média de ping: " + media + "ms");
				}
				
				else if (linha.contains("rtt")) {
					String [] linhaVector = linha.split(" ");
					int tamanho = linhaVector.length;
					String media = linhaVector[tamanho - 2].split("/")[1];
					JOptionPane.showMessageDialog(null, "\nMédia de ping: " + media + "ms");
				}
				linha = buffer.readLine();
			}
		}catch(IOException e2) {
			System.err.println("Comando inválido");
		}
	}
	
	
}