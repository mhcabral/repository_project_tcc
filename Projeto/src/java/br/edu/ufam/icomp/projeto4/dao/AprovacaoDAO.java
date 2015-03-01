/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ufam.icomp.projeto4.dao;

import br.com.caelum.vraptor.ioc.Component;
import br.edu.ufam.icomp.projeto4.model.Aprovacao;
import javax.persistence.EntityManager;

/**
 *
 * @author Thiago Santos
 */
@Component
public class AprovacaoDAO extends DAOImpl<Aprovacao> {

    public AprovacaoDAO(EntityManager entityManager) {
        super(Aprovacao.class, entityManager);
    }
    
}
