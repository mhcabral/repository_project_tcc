/**
 *
 * @author mhcabral
 */
package br.edu.ufam.icomp.tcc.dao;

import br.com.caelum.vraptor.ioc.Component;
import br.edu.ufam.icomp.projeto4.dao.DAOImpl;
import br.edu.ufam.icomp.projeto4.model.Aluno;
import br.edu.ufam.icomp.tcc.model.TccAproveitamento;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

@Component
public class TccAproveitamentoDAO extends DAOImpl<TccAproveitamento> {
    
    public TccAproveitamentoDAO(EntityManager entityManager) {
        super(TccAproveitamento.class, entityManager);
    }
    
    public TccAproveitamento findByAluno(Long idAluno) {
        EntityManager entityManager = this.getEntityManager();
        
        Query query = entityManager.createNamedQuery("TccAproveitamento.findByAluno");
        
        query.setParameter("idAluno", idAluno);        
        
        try {
            return (TccAproveitamento) query.getSingleResult();
        } catch (NoResultException n) {
            return null;
        }
    }
    
    @Override
    public TccAproveitamento findById(Long idAproveitamento) {
        EntityManager entityManager = this.getEntityManager();
        
        Query query = entityManager.createNamedQuery("TccAproveitamento.findById");
        
        query.setParameter("id", idAproveitamento);        
        
        try {
            return (TccAproveitamento) query.getSingleResult();
        } catch (NoResultException n) {
            return null;
        }
    }
}