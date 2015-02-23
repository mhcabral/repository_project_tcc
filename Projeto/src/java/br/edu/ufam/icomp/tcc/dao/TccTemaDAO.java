/**
 *
 * @author andre
 */

package br.edu.ufam.icomp.tcc.dao;

import br.com.caelum.vraptor.ioc.Component;
import br.edu.ufam.icomp.projeto4.dao.DAOImpl;
import br.edu.ufam.icomp.tcc.model.TccTema;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

@Component
public class TccTemaDAO extends DAOImpl<TccTema> {
    
    public TccTemaDAO(EntityManager entityManager) {
        super(TccTema.class, entityManager);
    }
  
}
