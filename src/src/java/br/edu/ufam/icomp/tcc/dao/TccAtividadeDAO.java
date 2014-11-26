/**
 *
 * @author andre
 */

package br.edu.ufam.icomp.tcc.dao;

import br.com.caelum.vraptor.ioc.Component;
import br.edu.ufam.icomp.projeto4.dao.DAOImpl;
import br.edu.ufam.icomp.tcc.model.TccAtividade;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

@Component
public class TccAtividadeDAO extends DAOImpl<TccAtividade> {
    
    public TccAtividadeDAO(EntityManager entityManager) {
        super(TccAtividade.class, entityManager);
    }
    
    public List<TccAtividade> findByPeriodo(Long idPeriodo) {
        EntityManager entityManager = this.getEntityManager();
        
        Query query = entityManager.createNamedQuery("TccAtividade.findByPeriodo");
        
        query.setParameter("idPeriodo", idPeriodo);        
        
        return query.getResultList();
    }
    
    @Override
    public TccAtividade findById(Long idPeriodo) {
        EntityManager entityManager = this.getEntityManager();
        
        Query query = entityManager.createNamedQuery("TccAtividade.findById");
        
        query.setParameter("id", idPeriodo);        
        
        try {
            return (TccAtividade) query.getSingleResult();
        } catch (NoResultException n) {
            return null;
        }
    }
}
