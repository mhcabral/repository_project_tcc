package br.edu.ufam.icomp.projeto4.dao;

import br.com.caelum.vraptor.ioc.Component;
import br.edu.ufam.icomp.projeto4.model.Aluno;
import br.edu.ufam.icomp.projeto4.model.Curso;
import br.edu.ufam.icomp.projeto4.model.InscricaoMonitoria;
import br.edu.ufam.icomp.projeto4.model.Status;
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
 * @author leonardo
 */
@Component
public class InscricaoMonitoriaDAO extends DAOImpl<InscricaoMonitoria> {

    public InscricaoMonitoriaDAO(EntityManager entityManager) {
        super(InscricaoMonitoria.class, entityManager);
    }

    public List<InscricaoMonitoria> geraConsulta(Long idCurso, Long idProfessor, Long idDisciplina, Long idInscrito) {
        EntityManager entityManager = this.getEntityManager();

        String q = "InscricaoMonitoria.findBy";

        Query query;

        boolean curso = false, pro = false, disci = false, alun = false;

        if (idCurso > 0) {
            q = q + "Curso";
            curso = true;
        }
        if (idProfessor > 0) {
            q = q + "Professor";
            pro = true;
        }
        if (idDisciplina > 0) {
            q = q + "Disciplina";
            disci = true;
        }
        if (idInscrito > 0) {
            q = q + "Aluno";
            alun = true;
        }

        query = entityManager.createNamedQuery(q);

        if (curso) {
            query.setParameter("idCurso", idCurso);
        }
        if (pro) {
            query.setParameter("idProfessor", idProfessor);
        }
        if (disci) {
            query.setParameter("idDisciplina", idDisciplina);
        }
        if (alun) {
            query.setParameter("idAluno", idInscrito);
        }


        return query.getResultList();
    }

    /**
     * Search do índex da Análise Final de Monitorias
     *
     * @author Leonardo
     * @param idCurso
     * @param idDisciplina
     * @param idProfessor
     * @return List<InscricaoMonitoria>
     */
    public List<InscricaoMonitoria> search(Long idCurso, Long idDisciplina, Long idProfessor, Long idAluno) {

        EntityManager em = this.getEntityManager();

        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<InscricaoMonitoria> c = cb.createQuery(InscricaoMonitoria.class);

        //Selects
        Root<InscricaoMonitoria> root = c.from(InscricaoMonitoria.class);

        //Constructing list of parameters
        List<Predicate> predicates = new ArrayList<Predicate>();

        //Adding predicates in case of parameter not being null
        if (idCurso != null) {
            predicates.add(
                    cb.equal(root.get("monitoria").get("curso").get("id"), idCurso));
        }
        if (idDisciplina != null) {
            predicates.add(
                    cb.equal(root.get("monitoria").get("disciplina").get("id"), idDisciplina));
        }
        if (idProfessor != null) {
            predicates.add(
                    cb.equal(root.get("monitoria").get("professor").get("id"), idProfessor));
        }
        if (idAluno != null) {
            predicates.add(
                    cb.equal(root.get("inscrito").get("id"), idAluno));
        }

//        predicates.add(
//                cb.equal(root.get("statusAtual"), Status.DEFERIDA));

        //query itself
        c.select(root)
                .where(predicates.toArray(new Predicate[]{}));

        TypedQuery<InscricaoMonitoria> query = em.createQuery(c);


        return query.getResultList();

    }

    public List<Curso> encontraCursoCoord(Long idProfessor) {

        EntityManager em = this.getEntityManager();

        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Curso> c = cb.createQuery(Curso.class);

        //Selects
        Root<Curso> root = c.from(Curso.class);

        //Constructing list of parameters
        List<Predicate> predicates = new ArrayList<Predicate>();

        //Adding predicates in case of parameter not being null

        if (idProfessor != null) {
            predicates.add(
                    cb.equal(root.get("professor").get("id"), idProfessor));
        }

//        predicates.add(
//                cb.equal(root.get("statusAtual"), Status.DEFERIDA));

        //query itself
        c.select(root).where(predicates.toArray(new Predicate[]{}));

        TypedQuery<Curso> query = em.createQuery(c);


        return query.getResultList();

    }

    public List<InscricaoMonitoria> findByInscrito(Long idInscrito) {
        EntityManager entityManager = this.getEntityManager();

        Query query = entityManager.createNamedQuery("InscricaoMonitoria.findByInscrito");

        query.setParameter("id", idInscrito);

        return query.getResultList();
    }

    public boolean exist(Long idMonitoria, Long idAluno) {
        EntityManager entityManager = this.getEntityManager();

        Query query = entityManager.createNamedQuery("InscricaoMonitoria.exist");

        query.setParameter("idMonitoria", idMonitoria);
        query.setParameter("idAluno", idAluno);

        return !query.getResultList().isEmpty();
    }

    public long countByInscrito(Long id, Status status) {
        EntityManager entityManager = this.getEntityManager();

        Query query = entityManager.createNamedQuery("InscricaoMonitoria.countByInscrito");

        query.setParameter("id", id);
        query.setParameter("status", status);

        List resultList = query.getResultList();
        for (Object object : resultList) {
            System.out.println(object);
        }


        if (query.getSingleResult() != null) {
            return (Long) query.getSingleResult();
        } else {
            return 0;
        }
    }

    /**
     * Search do índex da Análise de Monitorias
     *
     * @author Janderson
     * @param idCurso
     * @param idDisciplina
     * @return List<InscricaoMonitoria>
     */
    public List<InscricaoMonitoria> search(Long idCurso, Long idDisciplina) {

        EntityManager em = this.getEntityManager();

        Query query;

        if ((idCurso != 0) && (idDisciplina == 0)) {

            query = em.createNamedQuery("InscricaoMonitoria.findByCursoNova");
            query.setParameter("idCurso", idCurso);
            return query.getResultList();
        }

        if ((idCurso == 0) && (idDisciplina != 0)) {

            query = em.createNamedQuery("InscricaoMonitoria.findByDisciplinaNova");
            query.setParameter("idDisciplina", idDisciplina);
            return query.getResultList();
        }

        if ((idCurso != 0) && (idDisciplina != 0)) {

            query = em.createNamedQuery("InscricaoMonitoria.findByCursoDisciplinaNova");
            query.setParameter("idCurso", idCurso);
            query.setParameter("idDisciplina", idDisciplina);
            return query.getResultList();
        }

        if ((idCurso == 0) && (idDisciplina == 0)) {

            query = em.createNamedQuery("InscricaoMonitoria.listAllNova");
            return query.getResultList();
        }

        return null;

    }

    /**
     * Search do índex da Análise Final de Monitorias
     *
     * @author Janderson
     * @param idCurso
     * @param idDisciplina
     * @param idProfessor
     * @return List<InscricaoMonitoria>
     */
    public List<InscricaoMonitoria> search(Long idCurso, Long idDisciplina, Long idProfessor) {

        EntityManager em = this.getEntityManager();

        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<InscricaoMonitoria> c = cb.createQuery(InscricaoMonitoria.class);

        //Selects
        Root<InscricaoMonitoria> root = c.from(InscricaoMonitoria.class);

        //Constructing list of parameters
        List<Predicate> predicates = new ArrayList<Predicate>();

        //Adding predicates in case of parameter not being null
        if (idCurso > 0) {
            predicates.add(
                    cb.equal(root.get("monitoria").get("curso").get("id"), idCurso));
        }
        if (idDisciplina > 0) {
            predicates.add(
                    cb.equal(root.get("monitoria").get("disciplina").get("id"), idDisciplina));
        }
        if (idProfessor > 0) {
            predicates.add(
                    cb.equal(root.get("monitoria").get("professor").get("id"), idProfessor));
        }

        predicates.add(
                cb.equal(root.get("statusAtual"), Status.DEFERIDA));

        //query itself
        c.select(root)
                .where(predicates.toArray(new Predicate[]{}));

        TypedQuery<InscricaoMonitoria> query = em.createQuery(c);


        return query.getResultList();

    }

    /**
     * Search do índex do Envio de Documentos pelo Aluno
     *
     * @author Janderson
     * @param idUser (id do user do aluno)
     * @return List<InscricaoMonitoria>
     */
    public List<InscricaoMonitoria> search(Long idUser) {

        EntityManager em = this.getEntityManager();

        Query query = em.createNamedQuery("InscricaoMonitoria.findEnvio");

        query.setParameter("idUser", idUser);

        return query.getResultList();

    }

//    
//    public List<InscricaoMonitoria> findHistorico(Long idALuno, Status status) {
//        EntityManager entityManager = this.getEntityManager();        
//        
//        Query query = entityManager.createNamedQuery("InscricaoMonitoria.findHistorico");
//        
//        query.setParameter("idAluno", idALuno);
//        query.setParameter("statusAtual", status);
//        
//        List<Object[]> resultado = query.getResultList();
//        
//        List<InscricaoMonitoria> inscricoes = new ArrayList<InscricaoMonitoria>();
//        
//        for (Object[] objects : resultado) {
//            Monitoria monitoria = new Monitoria();
//            monitoria.setId((Long) objects[1]);            
//            monitoria.setNome((String) objects[2]);            
//            
//            Grupo grupo = new Grupo();
//            grupo.setCodigo((String) objects[3]);            
//            grupo.setNome((String) objects[4]);            
//            
//            atividade.setGrupo(grupo);
//            
//            Solicitacao solicitacao = new Solicitacao();
//            
//            solicitacao.setAtividade(atividade);
//            solicitacao.setHorasComputadas(Integer.parseInt(objects[0].toString()));            
//            
//            solicitacoes.add(solicitacao);
//        }
//                                        
//        return solicitacoes;
//    }
    public List<InscricaoMonitoria> listByAlunosCurso(List<Aluno> alunos) {
        if (!alunos.isEmpty()) {
            EntityManager em = this.getEntityManager();

            Query query = em.createNamedQuery("InscricaoMonitoria.listByAlunosCurso");

            query.setParameter("alunoList", alunos);

            return query.getResultList();
        } else {
            return new ArrayList<InscricaoMonitoria>();
        }
    }

    public List<InscricaoMonitoria> listByAlunoStatus(Long idAluno, Status status) {
        EntityManager em = this.getEntityManager();

        Query query = em.createNamedQuery("InscricaoMonitoria.listByAlunoStatus");

        query.setParameter("idAluno", idAluno);
        query.setParameter("status", status);

        return query.getResultList();
    }

    public List<InscricaoMonitoria> findByMonitoria(Long id) {
        EntityManager em = this.getEntityManager();

        Query query = em.createNamedQuery("InscricaoMonitoria.findByMonitoria");

        query.setParameter("id", id);

        return query.getResultList();
    }
}