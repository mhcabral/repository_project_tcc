package br.edu.ufam.icomp.tcc.dao;  

import br.com.caelum.vraptor.ioc.Component;
import br.edu.ufam.icomp.projeto4.dao.DAOImpl;
import br.edu.ufam.icomp.tcc.model.TccNotas;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

@Component
public class TccNotasDAO extends DAOImpl<TccNotas> {
    
    public TccNotasDAO(EntityManager entityManager) {
        super(TccNotas.class, entityManager);
    }
    
    @Override
    public TccNotas findById(Long idnotas) {
        EntityManager entityManager = this.getEntityManager();
        
        Query query = entityManager.createNamedQuery("TccNotas.findById");
        
        query.setParameter("id", idnotas);        
        
        try {
            return (TccNotas) query.getSingleResult();
        } catch (NoResultException n) {
            return null;
        }
    }
    
    public TccNotas findByTcc(Long idtcctcc) {
        EntityManager entityManager = this.getEntityManager();
        
        Query query = entityManager.createNamedQuery("TccNotas.findByTcc");
        
        query.setParameter("id", idtcctcc);        
        
        try {
            return (TccNotas) query.getSingleResult();
        } catch (NoResultException n) {
            return null;
        }
    }
}