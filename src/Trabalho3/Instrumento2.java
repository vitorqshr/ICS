package Trabalho3;

import java.util.ArrayList;

import sintese.Curva;
import sintese.Envoltoria;
import sintese.InstrumentoAditivo;
import sintese.UnidadeH;

// Implementa o instrumento 2: ruido passa banda modulado por frequencia
public class Instrumento2 extends Instrumento {
	
	Curva   curva1, curva2, curva3;      
    Envoltoria   env1   = new Envoltoria();
    Envoltoria   env2   = new Envoltoria();
    Envoltoria   env3   = new Envoltoria();
    UnidadeH     uh1    = new UnidadeH();
    UnidadeH     uh2    = new UnidadeH();
    UnidadeH     uh3    = new UnidadeH();
	public Instrumento2() {
		criaInstrumento2();
	}
	
	public void criaInstrumento2(){
			initNota();
	        
	        curva1 = new Curva(720);
	        curva1.addPonto(0f, 0f);
	        curva1.addPonto(20f, 1000f);
	        curva1.addPonto(60f, 300f);
	        curva1.addPonto(720f, 0f);

	        curva2 = new Curva(720);
	        curva2.addPonto(0f, 0f);
	        curva2.addPonto(20f, 400f);
	        curva2.addPonto(54f, 200f);
	        curva2.addPonto(720f, 0f);

	        curva3 = new Curva(720);
	        curva3.addPonto(0f, 0f);
	        curva3.addPonto(10f,  300f);
	        curva3.addPonto(48f, 100f);
	        curva3.addPonto(720f, 0f);

	        env1.setCURVA(curva1);
	        env2.setCURVA(curva2);
	        env3.setCURVA(curva3);

	        uh1.setEnvoltoria(env1);
	        uh1.setH(1);
	        uh1.setLambda(1f);
	        uh1.setFase(0f);

	        uh2.setEnvoltoria(env2);
	        uh2.setH(3);
	        uh2.setLambda(1f);
	        uh2.setFase(0f);

	        uh3.setEnvoltoria(env3);
	        uh3.setH(5);
	        uh3.setLambda(1f);
	        uh3.setFase(0f);

	        ins = new InstrumentoAditivo();
	        ins.addUnidade(uh1);
	        ins.addUnidade(uh2);
	        ins.addUnidade(uh3);
	}
}
