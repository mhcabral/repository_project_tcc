/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ufam.icomp.projeto4.dao;

import br.com.caelum.vraptor.ioc.Component;
import br.edu.ufam.icomp.projeto4.model.Atividade;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Thiago Santos
 */
@Component
public class AtividadeDAO extends DAOImpl<Atividade> {

    public AtividadeDAO(EntityManager entityManager) {
        super(Atividade.class, entityManager);
    }

    public List<Atividade> findByGroupId(Long idGrupo) {
        EntityManager entityManager = this.getEntityManager();

        Query query = entityManager.createNamedQuery("Atividade.findByGroupId");

        query.setParameter("pId", idGrupo);

        return query.getResultList();
    }

    public List<Atividade> findByCodigo(String codigo) {
        EntityManager entityManager = this.getEntityManager();

        Query query = entityManager.createNamedQuery("Atividade.findByCodigo");

        query.setParameter("cod", codigo);

        return query.getResultList();
    }

    public List<Atividade> findAllOrderByGrupoAndAtividade() {

        EntityManager entityManager = this.getEntityManager();

        Query query = entityManager.createNamedQuery("Atividade.findAllOrderByGrupoAndAtividade");
        
        return query.getResultList();
    }
}
