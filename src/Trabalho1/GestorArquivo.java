package Trabalho1;

import java.io.File;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

public class GestorArquivo {
	private String caminhoArquivo;
	private File arquivo;
	
	public void escolherArquivo(String nome) throws Exception{
		if(nome == null || nome.trim() == ""){
			throw new RuntimeException("Erro ao abrir o arquivo!!");
		}
		caminhoArquivo = nome;
		caminhoArquivo.concat(".midi");
		arquivo = new File(caminhoArquivo);
	}
	
	public void pegaSequencia(String nome) throws Exception{
		escolherArquivo(nome);
		Sequence sequencia = MidiSystem.getSequence(arquivo);
		//monta os dados
		Sequencer sequenciador = MidiSystem.getSequencer();
		sequenciador.setSequence(sequencia);
		sequenciador.open();
	}
}
