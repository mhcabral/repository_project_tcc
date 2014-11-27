/**
 *
 * @author mhcabral
 */

package br.edu.ufam.icomp.tcc.dao;

import br.com.caelum.vraptor.ioc.Component;
import br.edu.ufam.icomp.projeto4.dao.DAOImpl;
import br.edu.ufam.icomp.tcc.model.TccLocais;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

@Component
public class TccLocaisDAO extends DAOImpl<TccLocais> {
    
    public TccLocaisDAO(EntityManager entityManager) {
        super(TccLocais.class, entityManager);
    }
    
    @Override
    public TccLocais findById(Long idlocal) {
        EntityManager entityManager = this.getEntityManager();
        
        Query query = entityManager.createNamedQuery("TccLocais.findById");
        
        query.setParameter("id", idlocal);        
        
        try {
            return (TccLocais) query.getSingleResult();
        } catch (NoResultException n) {
            return null;
        }
    }
    
    public List<TccLocais> findAll(){
        EntityManager entityManager = this.getEntityManager();

        Query query = entityManager.createNamedQuery("TccLocais.findAll");

        return query.getResultList();
    }
}