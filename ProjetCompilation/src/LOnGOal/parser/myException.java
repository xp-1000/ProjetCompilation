package LOnGOal.parser;
import javax.swing.JOptionPane;

public class myException extends Exception {
	private static final long serialVersionUID = 1L;
	public static boolean lance;
	
  /*public myException(Parser parser, String string) {
	  this.parser = parser;
  }
  class Affiche implements Runnable{
  	String msg;
  	Affiche(String msg){
  		this.msg=msg;
  	}
  public void run(){
	  System.out.println("uu!^!^^!!^!^!^!^^!!^^!!^^!^");
  	JOptionPane.showMessageDialog(null, "erreur : " + msg);
  }	
  }*/
  public myException(String st){
	  System.out.println("!^!^^!!^!^!^!^^!!^^!!^^!^");
	  JOptionPane.showMessageDialog(null,"erreur dans l'instruction : " + st);
  }
}