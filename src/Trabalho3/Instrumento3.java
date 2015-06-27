package Trabalho3;

import java.util.ArrayList;

import sintese.Curva;
import sintese.Envoltoria;
import sintese.InstrumentoAditivo;
import sintese.UnidadeH;

public class Instrumento3 extends Instrumento {
	
	Curva   curva1, curva2, curva3;      
    Envoltoria   env1   = new Envoltoria();
    Envoltoria   env2   = new Envoltoria();
    Envoltoria   env3   = new Envoltoria();
    UnidadeH     uh1    = new UnidadeH();
    UnidadeH     uh2    = new UnidadeH();
    UnidadeH     uh3    = new UnidadeH();
	public Instrumento3()  {
		criaInstrumento3();
	}
	
	public void criaInstrumento3(){
		initNota();
        
        curva1 = new Curva(720);
        curva1.addPonto(0f, 0f);
        curva1.addPonto(30f, 1000f);
        curva1.addPonto(200f, 300f);
        curva1.addPonto(720f, 0f);

        curva2 = new Curva(720);
        curva2.addPonto(0f, 0f);
        curva2.addPonto(20f, 400f);
        curva2.addPonto(240f, 300f);
        curva2.addPonto(720f, 0f);

        curva3 = new Curva(720);
        curva3.addPonto(0f, 0f);
        curva3.addPonto(100f,  300f);
        curva3.addPonto(720f, 0f);

        env1.setCURVA(curva1);
        env2.setCURVA(curva2);
        env3.setCURVA(curva2);

        uh1.setEnvoltoria(env1);
        uh1.setH(1);
        uh1.setLambda(0.5f);
        uh1.setFase(90f);
        uh1.setGanho(4);

        uh2.setEnvoltoria(env2);
        uh2.setH(2);
        uh2.setLambda(0.5f);
        uh2.setFase(0f);
        uh2.setGanho(4);

        uh3.setEnvoltoria(env3);
        uh3.setH(3);
        uh3.setLambda(0.5f);
        uh3.setFase(120f);
        uh3.setGanho(4);
        
        ins = new InstrumentoAditivo();
        ins.addUnidade(uh1);
        ins.addUnidade(uh2);
        ins.addUnidade(uh3);
	}
}
