package Trabalho3;

import sintese.Curva;
import sintese.Envoltoria;
import sintese.Oscilador;
import sintese.Ruido;
import sintese.Somador;

// Implementa o instrumento 2: ruido passa banda modulado por frequencia
public class Instrumento2 extends Instrumento {
	
	Envoltoria portador;
	Somador somador;
	Curva curva2;
	public Instrumento2(int num,float ganho, float frequenciaEnv, float frequenciaEnvOsc, float frequenciaRuido, float ganhoRuido) throws Exception {
		super(num);
		criaInstrumento2(ganho, frequenciaEnv, frequenciaEnvOsc, frequenciaRuido, ganhoRuido);
	}
	
//	public Instrumento2(int num,float ganho, float frequenciaEnv, float frequenciaEnvOsc, float frequenciaRuido, float ganhoRuido,float valor) throws Exception{
//    	super(num, valor);
//    	criaInstrumento2(ganho, frequenciaEnv, frequenciaEnvOsc, frequenciaRuido, ganhoRuido);
//    }
	
	public void criaInstrumento2(float ganho, float frequenciaEnv, float frequenciaEnvOsc, float frequenciaRuido, float ganhoRuido){
		numIns = 2;
		
        curva = new Curva(720);
        
        curva.addPonto(0f, 0f);	
        curva.addPonto(300f, 500f);
        curva.addPonto(500f, 500f);
        curva.addPonto(600f, 300f);
        curva.addPonto(650f, 100f);
        curva.addPonto(720f, 0f);
        
        curva2 = new Curva(720);
        
        curva2.addPonto(0f, 330f);
        curva2.addPonto(720f, 330f);
        
        env = new Envoltoria();
        env.setCURVA(curva);
        env.setGanho(ganho);
        env.setFrequencia(frequenciaEnv);
        
        ruido = new Ruido();
        ruido.setGanho(ganhoRuido);
        ruido.setFrequencia(frequenciaRuido);
        
        portador = new Envoltoria();
        portador.setCURVA(curva2);
        portador.setFrequencia(frequenciaEnvOsc);
        
        somador = new Somador(ruido, portador);
        
        osc = new Oscilador(env, somador);
	}
}
