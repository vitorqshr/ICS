package Trabalho1;

import java.io.File;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Track;

public class GestorArquivo {
	private String caminhoArquivo;
	private File arquivo;
	private Sequence sequencia;
	private Sequencer sequenciador;
	private Track[] trilhas;
	
	public GestorArquivo(File arquivoMidi) throws Exception{
		pegaSequencia(arquivoMidi);
	}
	
	public void pegaSequencia(File arquivoMidi) throws Exception{
		setArquivo(arquivoMidi);
		sequencia = MidiSystem.getSequence(arquivo);
		sequenciador = MidiSystem.getSequencer();
		sequenciador.setSequence(sequencia);
		sequenciador.open();
		trilhas = sequencia.getTracks();
	}
	
	public long getSegundos(){
		long microsegundos = sequencia.getMicrosecondLength();
		return microsegundos/1000000;
	}
	
	public String getTempoTotal(){
		long segundos = getSegundos();
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
	
	public double getDuracaoTique(){
		long microsegundos = sequencia.getMicrosecondLength();
		long segundos = microsegundos/1000000;
		long  tiques    = sequencia.getTickLength();
		return segundos/tiques;
	}
	
	public Receiver getReceptor(){
		return sequenciador.getTransmitters().iterator().next().getReceiver();
	}
	
	public Sequence getSequencia(){
		return sequencia;
	}
	
	public Sequencer getSequenciador(){
		return sequenciador;
	}
	
	public long getQntTrilhas(){
		return trilhas.length;
	}
	
	public void setArquivo(File arquivo){
		this.arquivo = arquivo;
	}
}
