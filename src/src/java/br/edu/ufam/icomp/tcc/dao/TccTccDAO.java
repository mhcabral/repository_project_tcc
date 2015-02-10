/**
 *
 * @author andre
 */

package br.edu.ufam.icomp.tcc.dao;

import br.com.caelum.vraptor.ioc.Component;
import br.edu.ufam.icomp.projeto4.dao.DAOImpl;
import br.edu.ufam.icomp.projeto4.model.Aluno;
import br.edu.ufam.icomp.tcc.model.TccNotas;
import br.edu.ufam.icomp.tcc.model.TccTcc;
import br.edu.ufam.icomp.tcc.model.TccWorkshop;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author andre
 */
@Component
public class TccTccDAO extends DAOImpl<TccTcc> {
    
    /**
     *
     * @param entityManager
     */
    public TccTccDAO(EntityManager entityManager) {
        super(TccTcc.class, entityManager);
    }
    
    /**
     *
     * @param idAluno
     * @return
     */
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
    
    /**
     *
     * @param idProfessor
     * @return
     */
    public List<Aluno> findByProfessor(Long idProfessor) {
        EntityManager entityManager = this.getEntityManager();
        
        Query query = entityManager.createNamedQuery("TccTcc.findByProfessor");
        
        query.setParameter("idProfessor", idProfessor);        
        
        return query.getResultList();
    }
    
    /**
     *
     * @param idProfessor
     * @return
     */
    public List<TccTcc> findTccByProfessor(Long idProfessor) {
        EntityManager entityManager = this.getEntityManager();
        
        Query query = entityManager.createNamedQuery("TccTcc.findTccByProfessor");
        
        query.setParameter("idProfessor", idProfessor);        
        
        return query.getResultList();
    }
    
    public List<TccTcc> findByPeriodo(Long idPeriodo) {
        EntityManager entityManager = this.getEntityManager();
        
        Query query = entityManager.createNamedQuery("TccTcc.findByPeriodo");
        
        query.setParameter("idPeriodo", idPeriodo);        
        
        return query.getResultList();
    }
    
    /**
     *
     * @param tccTcc
     */
    @Override
    public void create(TccTcc tccTcc) {
        super.create(tccTcc);
        
        EntityManager entityManager = this.getEntityManager();
        entityManager.getTransaction().begin();
        
        TccNotas tccNotas = new TccNotas();
        tccNotas.setTcctcc(tccTcc);
        tccNotas.setNota1(0);
        tccNotas.setNota2(0);
        tccNotas.setNota3(0);
        entityManager.persist(tccNotas);
        
        TccWorkshop tccWorkshop = new TccWorkshop();
        tccWorkshop.setAvaliador1("Não Definido");
        tccWorkshop.setAvaliador2("Não Definido");
        tccWorkshop.setTcctcc(tccTcc);
        entityManager.persist(tccWorkshop);
        
        try {
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            //entityManager.getTransaction().rollback(); 
        }
        
    }
  
}