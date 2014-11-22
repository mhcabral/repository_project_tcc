/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ufam.icomp.projeto4;

import br.edu.ufam.icomp.projeto4.model.Grupo;
import java.util.Comparator;

/**
 *
 * @author Thiago Santos
 */
public class GrupoComparator implements Comparator<Grupo> {

    @Override
    public int compare(Grupo o1, Grupo o2) {
        return o1.getCodigo().compareTo(o2.getCodigo());
    }
    
}
