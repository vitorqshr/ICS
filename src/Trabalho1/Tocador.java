package Trabalho1;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;
import java.io.*;

public class Tocador {
	private Sequencer sequenciador;
	private String caminhoArquivo;
	private GestorArquivo gestor;
	private Receiver receptor;
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
	
	public void mudaPosicao(long posicao){
		gestor.getSequenciador().setTickPosition(posicao);
		tocar();
	}
	
	public void mudaVolume(int volume) throws Exception{
//		receptor = gestor.getReceptor();
//		gestor.getSequenciador().getTransmitter().setReceiver(receptor);
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
		double pos = ((horas*3600) + (minutos*60) + segundos)/(gestor.getDuracaoTique());
		gestor.getSequenciador().setTickPosition((long) pos);
		tocar();
	}
	
	public void atraso(int ms){
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	
	   static Par getFormulaDeCompasso(Track trilha)
	    {   int p=1;
	        int q=1;

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
	                return new Par(p,q);
	            }
	          }
	        }
	        return new Par(p,q);
	    }          
	    
	    
	    
	    static private class Par
	    { int x, y;
	      
	      Par (int x_, int y_)  
	      { this.x = x_;
	        this.y = y_;          
	      }
	    
	      int getX()
	      { return x;
	      }
	      
	      int getY()
	      { return y;
	      }
	    
	    }
	

	    
	    static String getTonalidade(Track trilha) throws InvalidMidiDataException
	    {       
	       String stonalidade = "";
	       for(int i=0; i<trilha.size(); i++)
	       { MidiMessage m = trilha.get(i).getMessage();
	       
	              
	       if(((MetaMessage)m).getType() == MENSAGEM_TONALIDADE)    
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
	         }
	      }
	      return stonalidade;
	    }
}