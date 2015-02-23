/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ufam.icomp.tcc.dao;

import br.com.caelum.vraptor.ioc.Component;
import br.edu.ufam.icomp.projeto4.dao.DAOImpl;
import br.edu.ufam.icomp.tcc.model.TccSolicitacao;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @author andre
 */
@Component
public class TccSolicitacaoDAO extends DAOImpl<TccSolicitacao> {

    public TccSolicitacaoDAO(EntityManager entityManager) {
        super(TccSolicitacao.class, entityManager);
          
    }
    
    public List<TccSolicitacao> findByProfessor(Long idProfessor) {
        EntityManager entityManager = this.getEntityManager();
        
        Query query = entityManager.createNamedQuery("TccSolicitacao.findByProfessor");
        
        query.setParameter("idProfessor", idProfessor);        
        
        return query.getResultList();
    }
    
    public List<TccSolicitacao> findByProfessorEstadoAluno(Long idProfessor, String estado, Long idAluno) {
        EntityManager em = this.getEntityManager();

        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<TccSolicitacao> c = cb.createQuery(TccSolicitacao.class);

        //Selects
        Root<TccSolicitacao> root = c.from(TccSolicitacao.class);

        //Constructing list of parameters
        List<Predicate> predicates = new ArrayList<Predicate>();

        //Adding predicates in case of parameter not being null
        if (idProfessor > 0) {
            
            predicates.add(cb.equal(root.get("tccTcc").get("professor").get("id"), idProfessor));            
        }
        if (!estado.isEmpty()) {
            
            predicates.add(cb.equal(root.get("estado"), estado));            
        }    
        if (idAluno > 0) {
            predicates.add(
                    cb.equal(root.get("tccTcc").get("aluno").get("id"), idAluno));
        }

        c.select(root)
                .where(predicates.toArray(new Predicate[]{}));

        TypedQuery<TccSolicitacao> query = em.createQuery(c);


        return query.getResultList();
    }
    
}
