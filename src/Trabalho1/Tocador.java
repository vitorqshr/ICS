package Trabalho1;

import javax.sound.midi.Sequencer;

public class Tocador {
	private Sequencer sequenciador;
	private String caminhoArquivo;
	private GestorArquivo gestor;
	
	Tocador(String nome) throws Exception{
		initGestor(nome);
	}
	
	public void mudaArquivo(String nome) throws Exception{
		initGestor(nome);
	}
	
	public void initGestor(String nome) throws Exception{
		if(gestor == null){
			gestor = new GestorArquivo(nome);
		}else{
			gestor.pegaSequencia(nome);
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
	
	public void mudaPosicao(long posicao){
		gestor.getSequenciador().setTickPosition(posicao);
		tocar();
	}
	
//	public static void main(String[] args) {
//		try {
//			Tocador tocador = new Tocador("atrain");
//			long tempoini = System.currentTimeMillis();
//			long diftime = System.currentTimeMillis() - tempoini;
//			while(diftime < 10000){
//				tocador.tocar();
//				diftime = System.currentTimeMillis() - tempoini;
//			}
//			tocador.sair();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//	}

}