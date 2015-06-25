package Trabalho3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.ShortMessage;

public class Conversor {
	private GestorArquivo gestor;
	static final int MENSAGEM_TONALIDADE = 0x59;  
	 static final int FORMULA_DE_COMPASSO = 0x58;
	 
	 private File arquivoMidi;
	
	 Conversor(File arquivoMidi) throws Exception{
		 this.arquivoMidi = arquivoMidi;
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
	
	
	public String converter() throws Exception{
		
		String[] nomes = arquivoMidi.getName().split(".");
		String nome = "meuPrograma";
		String nomeJava = nome + ".java";
		PrintWriter pw = new PrintWriter(nomeJava);
		pw.print( "import sintese.*;\n" +
                    "\n" +
                    "public class " + nome + "  {\n" +
                    "\n" +
                    "\tpublic static void main(String[] args) {");
		pw.flush();
		
		
		byte[] dados;
		int numInstrumentos = 0;
		String informacoes = "";
		int status;
		int temp;

		for(int i=0;i<gestor.getQntTrilhas();i++){
			int tipoInstrumento = 0;
			boolean temInst = false;
			System.out.println("----------------------\nTrilha " + i);
            informacoes += "----------------------\nTrilha " + i + "\n";
            int tam = gestor.trilhas[i].size();
			for(int j = 0;j < gestor.trilhas[i].size();j++){
				 MidiEvent evento = gestor.trilhas[i].get(j);
                 MidiMessage msg = evento.getMessage();
                 status = msg.getStatus();
                 dados = msg.getMessage();
                     
                 double duracao = -1;
                 double decadencia = 0;
                 
                 if (status >= 144 && status <= 159) {
                     
                     if (temInst) {
                    	 //procura por note off, mas se for ultimo so pega o final
                    	 if(j< tam-1){
                    		 MidiEvent proximo = gestor.trilhas[i].get(j + 1);
                             for (int k = 0; k < gestor.trilhas[i].size(); k++) {
                                 MidiEvent ev = gestor.trilhas[i].get(k);
                                 MidiMessage m = ev.getMessage();
                                 status = m.getStatus();
                                 byte[] bTemp = m.getMessage();

                                 // Encontrou Note Off!
                                 if (status >= 128 && status <= 143 && bTemp[1] == dados[1]) {
                                     proximo = gestor.trilhas[i].get(k);
                                     decadencia = (double) bTemp[1];
                                     break;
                                 }
                             }
                             duracao = (double) (proximo.getTick() - evento.getTick() + decadencia )*gestor.getDuracaoTique();
                    	 }else{
                    		 duracao = (gestor.getSequencia().getTickLength() - evento.getTick())*gestor.getDuracaoTique();
                    	 }
                     }
                    	// Calculando frequencia da nota
                     double freq = calcFreq((double) dados[1]);
                     // Amplitude da nota
                     int amp = (int) dados[2];
                     
                     if (duracao >0) {
                         pw.println("\t\tins" + numInstrumentos + ".addNota(" + duracao + ", " + freq + ", " + amp + ");");
                         pw.flush();
                     }
                     
                     System.out.println("Note On " + duracao + "s " + freq + "Hertz\n");
                     informacoes += "Note On " + duracao + "s " + freq + "Hertz | " + "ins" + numInstrumentos + ".addNota(" + duracao + ", " + freq + ", " + amp + ");\n";
                 }
                 // Note Off: 1000kkkk
                 else if (status >= 128 && status <= 143) {
                     System.out.println("Note Off numero_nota:" + dados[1] + " decadencia: " + dados[2] + "\n");
                     informacoes += "Note Off numero_nota:" + dados[1] + " decadencia: " + dados[2] + "\n";
                 }
                 // Program Change: 1100kkkk
                 else if (status >= 192 && status <= 207) {
                     temInst = true;
                     
                     int inst = dados[1];
                     
                     if (inst >= 0 && inst <= 42) {
                         tipoInstrumento = 1;
                     }
                     else if (inst >= 43 && inst <= 85) {
                         tipoInstrumento = 2;
                     }
                     else if (inst >= 86 && inst <= 127) {
                         tipoInstrumento = 3;
                     }
                     else{
                    	 throw new RuntimeException("Numero de instrumento invalido!!");
                     }
                     
                     numInstrumentos++;
                     pw.print("\n\n\t\tInstrumento" + tipoInstrumento + " ins" + numInstrumentos + " = new Instrumento" + tipoInstrumento + " ();\n" + "\t\tins" + numInstrumentos + ".addNota(0.0001, 0.0001, 0.0001);\n");
                     pw.flush();
                     
                     System.out.println("Program Change numero_instrumento: " + dados[1] + "\n");
                     informacoes += "Program Change numero_instrumento: " + dados[1] + " | " + "Ins" + tipoInstrumento + " ins" + numInstrumentos + " = new Ins" + tipoInstrumento + " ();\n";
                 }
                 // Control Change: 1011kkkk
                 else if (status >= 176 && status <= 191) {
                     System.out.println("Control Change byte_2:" + dados[1] + " valor_controle: " + dados[2] + "\n");
                     informacoes += "Control Change byte_2:" + dados[1] + " valor_controle: " + dados[2] + "\n";
                 }
                 // Pitch Bend: 1110kkkk
                 else if (status >= 224 && status <= 239) {
                     System.out.println("Pitch Bend byte_2:" + dados[1] + " byte_3: " + dados[2] + "\n");
                     informacoes += "Pitch Bend byte_2:" + dados[1] + " byte_3: " + dados[2] + "\n";
                 }
               }
		     }
			 // Montando a polifonia
	        pw.print("\n\t\tPolifonia p = new Polifonia();\n");
	        pw.flush();
	                
	        for (int i = 1; i <= numInstrumentos; i++) {
	            pw.println("\t\tp.addVoz(ins" + i + ".getVoz());");
	            pw.flush();
	        }
	        
	        pw.println("\n\t\tSom s = p.getSom();\n" +
	                  //"\t\ts.salvawave(\"" + arquivo + ".wav\");\n" +
	                  //"\t\ts.tocawave(\"" + arquivo + ".wav\");\n"
	                  "\t\ts.visualiza();\n"
	                );
	        pw.flush();
	        
	        // Fecha a classe e a main
	        pw.print("\t}\n" +
	            "}"
	        );
	        pw.flush();
	        
	        System.out.println("\n\nTerminado\n");
//	        informacoes += "\n\nInstrumentos de 0 ate 31 foram substituidos por Ins1.\n" +
//                    "Instrumentos de 32 ate 63 foram substituidos por Ins2.\n" +
//                    "Instrumentos de 64 ate 95 foram substituidos por Ins3.\n" +
//                    "Instrumentos de 96 ate 127 foram substituidos por Ins4.\n";
	        informacoes += "\n\nInstrumentos de 0 ate 42 foram substituidos por Instrumento1.\n" +
	        		"Instrumentos de 43 ate 85 foram substituidos por Instrumento2.\n" +
                    "Instrumentos de 86 ate 127 foram substituidos por Instrumento3.\n" ;
     
	        return informacoes;
	    }
	static private double calcFreq(double nota) {
		return 440* java.lang.Math.pow(2.00, (nota - 69)/12);
	}
}