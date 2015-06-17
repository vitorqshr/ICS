package Trabalho3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import javax.sound.midi.ShortMessage;

public class Conversor {
	private GestorArquivo gestor;
	static final int MENSAGEM_TONALIDADE = 0x59;  
	 static final int FORMULA_DE_COMPASSO = 0x58;
	
	 Conversor(File arquivoMidi) throws Exception{
		initGestor(arquivoMidi);
	}
	
	public void mudaArquivo(File arquivoMidi) throws Exception{
		initGestor(arquivoMidi);
	}
	
	public void initGestor(File arquivoMidi) throws Exception{
		if(gestor == null){
			gestor = new GestorArquivo(arquivoMidi);
		}else{
			gestor.pegaSequencia(arquivoMidi);
		}
	}
	
	public void tocar(){
		gestor.getSequenciador().start();
	}
	public void pausar(){
		gestor.getSequenciador().stop();
	}
	
	public void parar(){
		gestor.getSequenciador().stop();
		gestor.getSequenciador().setMicrosecondPosition(0);
	}
	
	public void sair(){
		gestor.getSequenciador().stop();
		gestor.getSequenciador().close();
	}

	
	public void mudaVolume(int volume) throws Exception{
		
		volume = (int)volume*127/100;
		ShortMessage volumeMesage = new ShortMessage();
		for (int i = 0; i < 16; i++) {
			volumeMesage.setMessage(ShortMessage.CONTROL_CHANGE,i,7,volume);
			gestor.getReceptor().send(volumeMesage, -1);
		}
	}
	
	public long getTempo(){
		if(gestor.getSequenciador() == null){
			return -1;
		}
		return gestor.getSequenciador().getMicrosecondPosition();
	}
	
	public long getSegundos(){
		return gestor.getSegundos();
	}
	
	
	public void mudarPosicao(int horas, int minutos, int segundos){
		long pos = ((horas*3600) + (minutos*60) + segundos)*1000000;
		gestor.getSequenciador().setMicrosecondPosition(pos);
	}
	
	public void atraso(int ms){
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public GestorArquivo getGestor(){
		return gestor;
	}
	
	
	public void converter(File arquivoMidi) throws FileNotFoundException{
		String[] nomes = arquivoMidi.getName().split(".");
		String nome = nomes[nomes.length - 2];
		String nomeJava = nome + ".java";
		PrintWriter pw = new PrintWriter(nomeJava);
		pw.print( "import sintese.*;\n" +
                    "\n" +
                    "public class " + nome + "  {\n" +
                    "\n" +
                    "\tpublic static void main(String[] args) {");
		pw.flush();
		
		for(int i=0;i<gestor.getQntTrilhas();i++){
			
		}
		
	}
}