package Trabalho2;

import java.io.File;

import javax.sound.midi.ShortMessage;

public class Tocador {
	private GestorArquivo gestor;
	static final int MENSAGEM_TONALIDADE = 0x59;  
	 static final int FORMULA_DE_COMPASSO = 0x58;
	
	Tocador(File arquivoMidi) throws Exception{
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
}