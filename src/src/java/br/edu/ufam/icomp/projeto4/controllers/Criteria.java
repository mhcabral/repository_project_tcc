/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ufam.icomp.projeto4.controllers;

import br.edu.ufam.icomp.projeto4.model.Aluno;
import br.edu.ufam.icomp.projeto4.model.Atividade;
import br.edu.ufam.icomp.projeto4.model.Curso;
import br.edu.ufam.icomp.projeto4.model.Grupo;
import br.edu.ufam.icomp.projeto4.model.Solicitacao;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @author Thiago Santos
 */
public class Criteria {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");

        EntityManager em = emf.createEntityManager();

        CriteriaBuilder cb = em.getCriteriaBuilder(); //Passo1

        CriteriaQuery<Solicitacao> c = cb.createQuery(Solicitacao.class);

        Root<Solicitacao> root = c.from(Solicitacao.class); //Passo 2                 

        Join<Solicitacao, Atividade> join = root.join("atividade"); //Passo 2

        
        Join<Solicitacao, Aluno> joinAluno = root.join("solicitante");
                
        Join<Aluno, Curso> joinCurso = joinAluno.join("curso");

        c.select(root); //Passo 3

        Predicate pAtividade = cb.equal(join.get("id"), 1L);

        Predicate pAluno = cb.equal(joinAluno.get("id"), 21000923L);

        Predicate pCurso = cb.equal(joinCurso.get("id"), 1L);

        Predicate pAnd = cb.and(pAtividade, pAluno, pCurso);

        c.where(pAnd);

        TypedQuery<Solicitacao> query = em.createQuery(c);

        List<Solicitacao> inscricoes = query.getResultList();

        System.out.println("Inscrições");
        for (Solicitacao inscricaoMonitoria : inscricoes) {
            System.out.println(inscricaoMonitoria.getSolicitante().getNomeAluno() + " - " + inscricaoMonitoria.getSolicitante().getCurso().getCurso());
        }
    }
}