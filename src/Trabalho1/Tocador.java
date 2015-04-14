package Trabalho1;

import javax.sound.midi.Sequencer;

public class Tocador {
	private Sequencer sequenciador;
	private String caminhoArquivo;
	private GestorArquivo gestor;
	Tocador(){
		
	}
	
	public void initGestor(String nome) throws Exception{
		if(gestor == null){
			gestor = new GestorArquivo(nome);
		}else{
			gestor.pegaSequencia(nome);
		}
	}

}
