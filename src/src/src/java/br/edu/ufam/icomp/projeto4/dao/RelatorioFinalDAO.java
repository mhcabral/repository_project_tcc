/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ufam.icomp.projeto4.dao;

import br.com.caelum.vraptor.ioc.Component;
import br.edu.ufam.icomp.projeto4.model.RelatorioFinal;
import javax.persistence.EntityManager;

/**
 *
 * @author Thiago Santos
 */
@Component
public class RelatorioFinalDAO extends DAOImpl<RelatorioFinal> {
    
    public RelatorioFinalDAO(EntityManager entityManager) {
        super(RelatorioFinal.class, entityManager);
    }
}
