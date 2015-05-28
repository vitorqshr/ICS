package Trabalho2;

import sintese.Curva;
import sintese.Envoltoria;
import sintese.Oscilador;
import sintese.Ruido;

public class Instrumento1 extends Instrumento{

	public Instrumento1(int num,float ganho, float frequenciaEnv, float frequenciaRuido, float frequenciaEnvOsc) throws Exception {
		super(num);
		criaInstrumento1(ganho, frequenciaEnv, frequenciaRuido, frequenciaEnvOsc);
	}
	
	public Instrumento1(int num,float ganho, float frequenciaEnv, float frequenciaRuido, float frequenciaEnvOsc,float valor) throws Exception{
    	super(num, valor);
    	criaInstrumento1(ganho, frequenciaEnv, frequenciaRuido, frequenciaEnvOsc);
    }
	
	public void criaInstrumento1(float ganho, float frequenciaEnv, float frequenciaRuido, float frequenciaEnvOsc){
		numIns = 1;
        
        curva = new Curva(720);
        
        curva.addPonto(0f, 0f);	
        curva.addPonto(300f, 500f);
        curva.addPonto(500f, 500f);
        curva.addPonto(600f, 300f);
        curva.addPonto(650f, 100f);
        curva.addPonto(720f, 0f);
        
        env = new Envoltoria();
        env.setCURVA(curva);
        env.setGanho(ganho);
        env.setFrequencia(frequenciaEnv);
        
        ruido = new Ruido(env);
        ruido.setFrequencia(frequenciaRuido);
        
        osc = new Oscilador(ruido);
        osc.setFrequencia(frequenciaEnvOsc);
	}
	
	
}
