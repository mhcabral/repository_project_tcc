package br.edu.ufam.icomp.projeto4.dao;

import br.com.caelum.vraptor.ioc.Component;
import br.edu.ufam.icomp.projeto4.model.Curso;
import br.edu.ufam.icomp.projeto4.model.PeriodoCadMon;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author Janderson
 */
@Component
public class PeriodoCadMonDAO extends DAOImpl<PeriodoCadMon> {

    public PeriodoCadMonDAO(EntityManager entityManager) {
        super(PeriodoCadMon.class, entityManager);
    }
    
    public List<PeriodoCadMon> SelectByCurso(Curso curso){
        EntityManager entityManager = this.getEntityManager();
        
        Query query = entityManager.createNamedQuery("PeriodoCadMon.findByCurso");        
        query.setParameter("curso", curso);
        
        return query.getResultList();
    }
    
    public Boolean exists(PeriodoCadMon periodo){
        
        EntityManager entityManager = this.getEntityManager();
        
        Query query;
        
        if(periodo.getId()==null){
            query = entityManager.createNamedQuery("PeriodoCadMon.exists");                
        }else{
            query = entityManager.createNamedQuery("PeriodoCadMon.existsAnother");
            query.setParameter("id", periodo.getId());
        }
        
        query.setParameter("curso", periodo.getCurso());
        query.setParameter("dtInicio", periodo.getDtInicio());
        query.setParameter("dtFim", periodo.getDtFim());
        
        return !query.getResultList().isEmpty();
    }
    
    public Boolean checkConflict(PeriodoCadMon periodo){
        
        EntityManager entityManager = this.getEntityManager();
                        
        Query query;
        
        if(periodo.getId()==null){
            query = entityManager.createNamedQuery("PeriodoCadMon.checkDate");                
        }else{
            query = entityManager.createNamedQuery("PeriodoCadMon.checkAnotherDate");
            query.setParameter("id", periodo.getId());
        }
        
        query.setParameter("curso", periodo.getCurso());
        query.setParameter("dtInicio", periodo.getDtInicio());
        query.setParameter("dtFim", periodo.getDtFim());
        
        return !query.getResultList().isEmpty();
    }    
    
    @Override
    public void update(PeriodoCadMon t) {  
        
        getEntityManager().merge(t);
        
       
    }
    
    @Override
    public void create(PeriodoCadMon t) {   
        
        getEntityManager().persist(t);
        
    }    

    public PeriodoCadMon findAtivo() {
        try {
            EntityManager entityManager = this.getEntityManager();

            Query query = entityManager.createNamedQuery("PeriodoCadMon.findAtivo");
            query.setParameter("dtAtual", new Date());

            return (PeriodoCadMon) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
