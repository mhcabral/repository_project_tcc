package br.edu.ufam.icomp.projeto4.dao;

import br.com.caelum.vraptor.ioc.Component;
import br.edu.ufam.icomp.projeto4.model.Mudanca;
import javax.persistence.EntityManager;

/**
 *
 * @author Bruna
 */
@Component
public class MudancaDAO extends DAOImpl<Mudanca>{
    public MudancaDAO(EntityManager entityManager) {
        super(Mudanca.class, entityManager);
    }
}