package Trabalho3;

import java.awt.Dimension;
import java.io.File;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Track;

public class GestorArquivo {
	private File arquivo;
	private Sequence sequencia;
	private Sequencer sequenciador;
	private Track[] trilhas;
	static final int MENSAGEM_TONALIDADE = 0x59;  
	static final int FORMULA_DE_COMPASSO = 0x58;
	
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
		float microsegundos = (float)sequencia.getMicrosecondLength();
		return (long)microsegundos/1000000;
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
		return (double)segundos/(double)tiques;
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
	
	public String getTonalidade() throws InvalidMidiDataException
    {       
       String stonalidade = "";
       for ( int i = 0; i < trilhas.length; i++ ) {
		      Track trilha = trilhas[ i ];
	       for(int j=0; j<trilha.size(); j++)
	       { MidiMessage m = trilha.get(j).getMessage();
	       
	              
	       if(((MetaMessage) m).getType() == MENSAGEM_TONALIDADE)    
	       {
	            MetaMessage mm        = (MetaMessage)m;
	            byte[]     data       = mm.getData();
	            byte       tonalidade = data[0];
	            byte       maior      = data[1];
	
	            String       smaior = "Maior";
	            if(maior==1) smaior = "Menor";
	
	            if(smaior.equalsIgnoreCase("Maior"))
	            {
	                switch (tonalidade)
	                {
	                    case -7: stonalidade = "Dób Maior"; break;
	                    case -6: stonalidade = "Solb Maior"; break;
	                    case -5: stonalidade = "Réb Maior"; break;
	                    case -4: stonalidade = "Láb Maior"; break;
	                    case -3: stonalidade = "Mib Maior"; break;
	                    case -2: stonalidade = "Sib Maior"; break;
	                    case -1: stonalidade = "Fá Maior"; break;
	                    case  0: stonalidade = "Dó Maior"; break;
	                    case  1: stonalidade = "Sol Maior"; break;
	                    case  2: stonalidade = "Ré Maior"; break;
	                    case  3: stonalidade = "Lá Maior"; break;
	                    case  4: stonalidade = "Mi Maior"; break;
	                    case  5: stonalidade = "Si Maior"; break;
	                    case  6: stonalidade = "Fá# Maior"; break;
	                    case  7: stonalidade = "Dó# Maior"; break;
	                }
	            }
	
	            else if(smaior.equalsIgnoreCase("Menor"))
	            {
	                switch (tonalidade)
	                {
	                    case -7: stonalidade = "Láb Menor"; break;
	                    case -6: stonalidade = "Mib Menor"; break;
	                    case -5: stonalidade = "Sib Menor"; break;
	                    case -4: stonalidade = "Fá Menor"; break;
	                    case -3: stonalidade = "Dó Menor"; break;
	                    case -2: stonalidade = "Sol Menor"; break;
	                    case -1: stonalidade = "Ré Menor"; break;
	                    case  0: stonalidade = "Lá Menor"; break;
	                    case  1: stonalidade = "Mi Menor"; break;
	                    case  2: stonalidade = "Si Menor"; break;
	                    case  3: stonalidade = "Fá# Menor"; break;
	                    case  4: stonalidade = "Dó# Menor"; break;
	                    case  5: stonalidade = "Sol# Menor"; break;
	                    case  6: stonalidade = "Ré# Menor"; break;
	                    case  7: stonalidade = "Lá# Menor"; break;
	                }
	            }
	            return stonalidade;
	         }
	      }
	      
       }
       return stonalidade;
    }
	
	  public Dimension getFormulaDeCompasso()
	    {   int p=1;
	        int q=1;
	        Track trilha = trilhas[0];

	        for(int i=0; i<trilha.size(); i++)
	        {
	          MidiMessage m = trilha.get(i).getMessage();
	          if(m instanceof MetaMessage) 
	          {
	            if(((MetaMessage)m).getType()==FORMULA_DE_COMPASSO)
	            {
	                MetaMessage mm = (MetaMessage)m;
	                byte[] data = mm.getData();
	                p = data[0];
	                q = data[1];
	                return new Dimension(p,(int)Math.pow(2,q));
	            }
	          }
	        }
	        return new Dimension(p,(int)Math.pow(2,q));
	    }          
	    
	  
	  public int getAndamento(){
		  float seminima = (float) (getDuracaoTique()*sequencia.getResolution());
		  return (int) (60/seminima);
		  
	  }
	    
	    
}
