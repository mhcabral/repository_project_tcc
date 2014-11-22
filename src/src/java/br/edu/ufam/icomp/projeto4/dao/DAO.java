/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ufam.icomp.projeto4.dao;

import java.util.List;

/**
 *
 * @author Thiago Santos
 */
public interface DAO<T> {
    public void create(T t);
    
    public void update(T t);
    
    public void delete(T t);
    
    public List<T> findAll();
    
    public T findById(Long id);
}
