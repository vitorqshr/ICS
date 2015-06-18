package Trabalho3;

import java.util.LinkedHashMap;

import jdk.internal.org.objectweb.asm.tree.IntInsnNode;
import sintese.Curva;
import sintese.Envoltoria;
import sintese.Melodia;
import sintese.Nota;
import sintese.Oscilador;
import sintese.Ruido;
import sintese.Som;
import sintese.Tema;

public class Instrumento {
    Ruido ruido;
    Envoltoria env;
    Curva curva;
    Oscilador osc;
    Melodia melodia;
    Tema tema;
    Som som;
    int numIns;
    private LinkedHashMap<Integer, Melodia> melodias;
    
    public Instrumento(int num) throws Exception{
    	populaMelodias();
    	escolheMelodia(num);
    }
    
    public void initTema(){
    	if(tema==null){
    		tema = new Tema();
    	}
    }
    
    public void escolheMelodia(int num) throws Exception{
    	initTema();
    	if(num<1 || num>7){
    		throw new Exception("Melodia invalida!");
    	}
    	melodia = melodias.get(num);
    }
    
    public Nota[] getNotas() {
        Nota[] n = new Nota[100];
        
        int i;
        
        for(i = 0; i < melodia.getNumeroDeNotas(); i++) {
            n[i] = melodia.getNota(i);
        }
        return n;
    }
    
    public void tocar() {
        som = melodia.getSom(osc);
        som.setNome("Instrumento " + numIns);
        som.salvawave("Instrumento" + numIns + ".wav");
        som.tocawave();
        som.visualiza();
    }
    
    private void populaMelodias(){
    	melodias = new LinkedHashMap<Integer, Melodia>();
    	initTema();
    	melodias.put(1, tema.tema_aa_drawing_quintet_flauta());
    	melodias.put(2, tema.tema_aa_fuga1());
    	melodias.put(3, tema.tema_bwv775_invencao14_direita());
    	melodias.put(4, tema.tema_bwv775_invencao4_direita());
    	melodias.put(5, tema.tema_bwv775_invencao4_esquerda());
    	melodias.put(6, tema.tema_bwv988goldberg_v03_eq());
    	melodias.put(7, tema.tema_bwv988goldberg_v03_eq());    	
    	
    }
    
}
