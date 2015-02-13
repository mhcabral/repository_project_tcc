package br.edu.ufam.icomp.projeto4.dao;




import br.com.caelum.vraptor.ioc.ApplicationScoped;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.ComponentFactory;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Thiago Santos
 */
@Component
@ApplicationScoped
public class CriadorDeEntityManagerFactory implements ComponentFactory<EntityManagerFactory> {

    private EntityManagerFactory emf;    
    
    @PostConstruct
    public void abre(){
        //System.out.println("Abre EMF");
        this.emf = Persistence.createEntityManagerFactory("default");
    }
    
    @Override
    public EntityManagerFactory getInstance() {
         return this.emf;
    }
    
    @PreDestroy
    public void fecha(){
        //System.out.println("Fecha EMF");
        this.emf.close();
    }
    
}
