package LOnGOal.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

public class ScriptProcess {
	
	private ScriptProcess()
	{
		
	}
	
	public static List<String> readScript (File file){
		List<String> script = new ArrayList<String>();
		//lecture du fichier texte	
		try{
			InputStream ips=new FileInputStream(file.getPath()); 
			InputStreamReader ipsr=new InputStreamReader(ips);
			BufferedReader br=new BufferedReader(ipsr);
			String ligne;
			while ((ligne=br.readLine())!=null){
				System.out.println(ligne);
				script.add(ligne);
			}
			br.close(); 
			return script;
		}		
		catch (Exception e){
			System.out.println(e.toString());
			return null;
		}
		
	}
	
	public static void writeScript (File file, DefaultListModel model){
		try {
			FileWriter fw = new FileWriter (file);
			BufferedWriter bw = new BufferedWriter (fw);
			PrintWriter fichierSortie = new PrintWriter (bw);
			for(int i=0; i<model.size(); i++)
				fichierSortie.println(model.get(i)); 
			fichierSortie.close();
			JOptionPane.showMessageDialog(null, "Fichier : " + file.getPath() + " sauvegardé");
		}
		catch (Exception e){
			System.out.println(e.toString());
		}		
	}
}