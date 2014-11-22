package br.edu.ufam.icomp.projeto4.dao;



import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.ComponentFactory;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Thiago Santos
 */
@Component
public class CriadorDeEntityManager implements ComponentFactory<EntityManager> {

    private final EntityManagerFactory emf;
    private EntityManager em;

    public CriadorDeEntityManager(EntityManagerFactory emf) {
        this.emf = emf;
    }        
    
    @PostConstruct
    public void abre(){
        //System.out.println("ABRE EM");
        this.em = emf.createEntityManager();
    }
    
    @PreDestroy
    public void fecha(){
        //System.out.println("Fecha EM");
        this.em.close();
    }

    @Override
    public EntityManager getInstance() {
        return this.em;
    }
}
