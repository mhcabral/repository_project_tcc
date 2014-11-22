package br.edu.ufam.icomp.projeto4;

import br.edu.ufam.icomp.projeto4.model.Mudanca;
import java.util.Comparator;

/**
 *
 * @author Bruna
 */
public class DataComparator implements Comparator<Mudanca> {

    @Override
    public int compare(Mudanca mudanca, Mudanca outraMudanca) {
        return outraMudanca.getDataMudanca().compareTo(mudanca.getDataMudanca());
    }
}