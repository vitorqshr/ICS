package Trabalho3;

import java.util.List;

import sintese.InstrumentoAditivo;
import sintese.Melodia;
import sintese.Nota;
import sintese.Som;
import sintese.Voz;

public class Instrumento {
    InstrumentoAditivo ins;
    List<Nota> nota;
    Melodia m;
    Voz v;
    
    public Instrumento(){

    }
    
    public void setFrequencia (float freq) {
        ins.setFrequencia(freq);        
        Som soma = new Som(ins, 2.5f);
        soma.visualiza();
    }
    
    public void addNota(double dur, double freq, double amp)
    {
        Nota n = new Nota(dur, freq, amp);        
        nota.add(n);
    }
    
    private void montarMelodia()
    {
        m = new Melodia();
        
        nota.stream().forEach((nota1) -> {
            m.addNota(nota1);
        });
    }
    
    public Voz getVoz() {
        montarMelodia();
        
        v = new Voz(ins);
        
        v.addMelodia(m);
        
        return v;
    }

    
    
}
