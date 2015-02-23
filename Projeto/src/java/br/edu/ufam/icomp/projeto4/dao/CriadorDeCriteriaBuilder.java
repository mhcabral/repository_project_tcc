
package br.edu.ufam.icomp.projeto4.dao;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.ComponentFactory;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;

/**
 *
 * @author leonardo
 */
@Component
public class CriadorDeCriteriaBuilder implements ComponentFactory<CriteriaBuilder>{

    private EntityManager em;

    public CriadorDeCriteriaBuilder(EntityManager em) {
        this.em = em;
    }
        
    
    @Override
    public CriteriaBuilder getInstance() {
        return this.em.getCriteriaBuilder();
    }
    
}
