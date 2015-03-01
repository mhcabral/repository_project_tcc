/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ufam.icomp.projeto4.dao;

import br.com.caelum.vraptor.ioc.Component;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.criteria.CriteriaQuery;

/**
 *
 * @author Thiago Santos
 */
@Component
public class DAOImpl<T> implements DAO<T> {

    private Class<T> classe;
    
    private final EntityManager entityManager;

    public DAOImpl(Class<T> classe, EntityManager entityManager) {
        this.entityManager = entityManager;
        this.classe = classe;
    }

    @Override
    public void create(T t) {   
        
        this.entityManager.getTransaction().begin();
        
        this.entityManager.persist(t);
        
        this.entityManager.getTransaction().commit();
        
    }

    @Override
    public void update(T t) {              
        
        this.entityManager.getTransaction().begin();
        
        this.entityManager.merge(t);
        
        this.entityManager.getTransaction().commit();
       
    }

    @Override
    public void delete(T t) {               
        
        this.entityManager.getTransaction().begin();
        
        this.entityManager.remove(entityManager.merge(t));
        
        this.entityManager.getTransaction().commit();
        
    }

    @Override
    public List<T> findAll() {        

        CriteriaQuery<T> query = this.entityManager.getCriteriaBuilder().createQuery(classe);

        query.select(query.from(classe));                   

        List<T> lista = this.entityManager.createQuery(query).getResultList();        

        return lista;

    }

    @Override
    public T findById(Long id) {        
        
        return this.entityManager.find(classe, id);
    }       

    public Class<T> getClasse() {
        return classe;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }        
}