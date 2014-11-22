package br.edu.ufam.icomp.projeto4.dao;

import br.com.caelum.vraptor.ioc.Component;
import br.edu.ufam.icomp.projeto4.model.Secretaria;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Bruna
 */
@Component
public class SecretariaDAO extends DAOImpl<Secretaria>{
    public SecretariaDAO(EntityManager entityManager) {
        super(Secretaria.class, entityManager);
    }
    
    public Secretaria findByUsuario (Long idUsuario){
        Query query = this.getEntityManager().createNamedQuery("Secretaria.findByUsuario");
        
        query.setParameter("sId", idUsuario);
        
        return (Secretaria) query.getSingleResult();
    }
}