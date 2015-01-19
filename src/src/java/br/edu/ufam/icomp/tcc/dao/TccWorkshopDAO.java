package br.edu.ufam.icomp.tcc.dao;

/**
 *
 * @author mhcabral
 */
import br.com.caelum.vraptor.ioc.Component;
import br.edu.ufam.icomp.projeto4.dao.DAOImpl;
import br.edu.ufam.icomp.tcc.model.TccWorkshop;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

@Component
public class TccWorkshopDAO extends DAOImpl<TccWorkshop> {
    
    public TccWorkshopDAO(EntityManager entityManager) {
        super(TccWorkshop.class, entityManager);
    }
    
    @Override
    public TccWorkshop findById(Long idWorkshop) {
        EntityManager entityManager = this.getEntityManager();
        
        Query query = entityManager.createNamedQuery("TccWorkshop.findById");
        
        query.setParameter("id", idWorkshop);        
        
        try {
            return (TccWorkshop) query.getSingleResult();
        } catch (NoResultException n) {
            return null;
        }
    }
}