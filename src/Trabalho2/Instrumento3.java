package Trabalho2;

import sintese.Curva;
import sintese.Envoltoria;
import sintese.Oscilador;
import sintese.Ruido;
import sintese.Somador;

public class Instrumento3 extends Instrumento {
	
	Envoltoria portadora;
	Oscilador osc2;
	Somador somador, somador2;
	Curva curva2;
	public Instrumento3(int num,float p5, float p6, float p7, float p8, float p9, float v1, float v2) throws Exception {
		super(num);
		criaInstrumento3(p5, p6, p7, p8, v1, p9, v2);
	}
	
//	public Instrumento3(int num,float p5, float p6, float p7, float p8, float v1, float p9, float v2,float valor) throws Exception{
//    	super(num, valor);
//    	criaInstrumento3(p5, p6, p7, p8, v1, p9, v2);
//    }
	
	public void criaInstrumento3(float ganho, float frequenciaEnv, float frequenciaPortadora, float frequenciaOsc2 ,float ganhoOsc2, float frequenciaRuido, float ganhoRuido){
		numIns = 3;
		
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
        
        portadora = new Envoltoria();
        portadora.setCURVA(curva2);
        portadora.setFrequencia(frequenciaPortadora);
        
        osc2 = new Oscilador(ganhoOsc2, frequenciaOsc2, 0);
        
        ruido = new Ruido();
        ruido.setGanho(ganhoRuido);
        ruido.setFrequencia(frequenciaRuido);
        
        somador = new Somador(osc2, portadora);
        somador2 = new Somador(somador, ruido);
        
        osc = new Oscilador(env, somador2);
	}
}
