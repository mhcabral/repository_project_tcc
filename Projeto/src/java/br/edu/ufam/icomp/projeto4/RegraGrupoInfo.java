/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ufam.icomp.projeto4;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.SessionScoped;
import br.edu.ufam.icomp.projeto4.model.RegraGrupo;
import java.io.Serializable;

/**
 *
 * @author Thiago Santos
 */
@Component
@SessionScoped
public class RegraGrupoInfo implements Serializable {
    private RegraGrupo regraGrupo;

    public RegraGrupo getRegraGrupo() {
        return regraGrupo;
    }

    public void setRegraGrupo(RegraGrupo regraGrupo) {
        this.regraGrupo = regraGrupo;
    }        
}
