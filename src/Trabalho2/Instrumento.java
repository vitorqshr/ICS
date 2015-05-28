package Trabalho2;

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
    
    public Instrumento(int num) throws Exception{
    	escolheMelodia(num);
    }
    
    public Instrumento(int num,float valor) throws Exception{
    	escolheMelodia(num, valor);
    }
    
    public void initTema(){
    	if(tema==null){
    		tema = new Tema();
    	}
    }
    
    public void escolheMelodia(int num) throws Exception{
    	initTema();
    	if(num == 1){
            melodia = tema.tema_aa_drawing_quintet_flauta();
    	}else if(num == 2){
            melodia = tema.tema_aa_fuga1();
    	}else if(num == 3){
            melodia = tema.tema_bwv775_invencao14_direita();
    	}else if(num == 4){
            melodia = tema.tema_bwv775_invencao4_direita();
    	}else if(num == 5){
            melodia = tema.tema_bwv775_invencao4_esquerda();
    	}else if(num == 6){
            melodia = tema.tema_bwv988goldberg_v03_eq();
    	}else if(num == 7){
            melodia = tema.tema_duda_no_frevo_eq();
    	}else{
    		throw new Exception("Melodia invalida!");
    	}
    }
    
    public void escolheMelodia(int num,float valor) throws Exception{
    	initTema();
    	if(num == 1){
            melodia = tema.tema_aa_drawing_quintet_flauta(valor);
    	}else if(num == 2){
            melodia = tema.tema_aa_fuga1(valor);
    	}else if(num == 3){
            melodia = tema.tema_bwv775_invencao14_direita(valor);
    	}else if(num == 4){
            melodia = tema.tema_bwv775_invencao4_direita(valor);
    	}else if(num == 5){
            melodia = tema.tema_bwv775_invencao4_esquerda(valor);
    	}else if(num == 6){
            melodia = tema.tema_bwv988goldberg_v03_eq(valor);
    	}else if(num == 7){
            melodia = tema.tema_duda_no_frevo_eq(valor);
    	}else{
    		throw new Exception("Melodia invalida!");
    	}
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
    
}
