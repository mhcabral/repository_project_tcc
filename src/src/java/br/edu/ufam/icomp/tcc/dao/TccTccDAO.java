/**
 *
 * @author andre
 */

package br.edu.ufam.icomp.tcc.dao;

import br.com.caelum.vraptor.ioc.Component;
import br.edu.ufam.icomp.projeto4.dao.DAOImpl;
import br.edu.ufam.icomp.tcc.model.TccTcc;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

@Component
public class TccTccDAO extends DAOImpl<TccTcc> {
    
    public TccTccDAO(EntityManager entityManager) {
        super(TccTcc.class, entityManager);
    }
    
    public TccTcc findByAluno(Long idAluno) {
        EntityManager entityManager = this.getEntityManager();
        
        Query query = entityManager.createNamedQuery("TccTcc.findByAluno");
        
        query.setParameter("idAluno", idAluno);        
        
        try {
            return (TccTcc) query.getSingleResult();
        } catch (NoResultException n) {
            return null;
        }
    }
  
}
