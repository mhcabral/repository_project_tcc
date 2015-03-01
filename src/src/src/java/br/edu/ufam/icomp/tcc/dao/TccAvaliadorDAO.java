package br.edu.ufam.icomp.tcc.dao;

import br.com.caelum.vraptor.ioc.Component;
import br.edu.ufam.icomp.projeto4.dao.DAOImpl;
import br.edu.ufam.icomp.tcc.model.TccAvaliador;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author TAMMY
 */
@Component
public class TccAvaliadorDAO extends DAOImpl<TccAvaliador> {
    
    public TccAvaliadorDAO(EntityManager entityManager) {
        super(TccAvaliador.class, entityManager);
    }
}
