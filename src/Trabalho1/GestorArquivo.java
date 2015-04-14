package Trabalho1;

import java.io.File;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

public class GestorArquivo {
	private String caminhoArquivo;
	private File arquivo;
	private Sequence sequencia;
	private Sequencer sequenciador;
	
	public GestorArquivo(String nome) throws Exception{
		pegaSequencia(nome);
	}
	
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
		sequencia = MidiSystem.getSequence(arquivo);
		sequenciador = MidiSystem.getSequencer();
		sequenciador.setSequence(sequencia);
		sequenciador.open();
	}
	
	public String pegaTempoTotal(){
		long microsegundos = sequencia.getMicrosecondLength();
		long segundos = microsegundos/1000000;
		long horas = (long) Math.floor(segundos/3600);
		segundos = segundos - 3600*horas;
		long minutos = (long) Math.floor(segundos/60);
		segundos = segundos - 60*minutos;
		
		String tempoTotal = "";
		if(horas < 10){
			tempoTotal.concat("0");
		}
		tempoTotal.concat("" + horas);
		
		if(minutos < 10){
			tempoTotal.concat("0");
		}
		tempoTotal.concat(":" + minutos);
		
		if(segundos < 10){
			tempoTotal.concat("0");
		}
		tempoTotal.concat(":" + segundos);
		return tempoTotal;
	}
	
	public Sequence getSequencia(){
		return sequencia;
	}
	
	public Sequencer getSequenciador(){
		return sequenciador;
	}
}
