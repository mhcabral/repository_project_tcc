/**
 *
 * @author andre
 */

package br.edu.ufam.icomp.tcc.dao;

import br.com.caelum.vraptor.ioc.Component;
import br.edu.ufam.icomp.projeto4.dao.DAOImpl;
import br.edu.ufam.icomp.projeto4.model.PeriodoLetivo;
import br.edu.ufam.icomp.tcc.model.TccAtividade;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

@Component
public class TccAtividadeDAO extends DAOImpl<TccAtividade> {
    
    public TccAtividadeDAO(EntityManager entityManager) {
        super(TccAtividade.class, entityManager);
    }
    
    public List<TccAtividade> findByPeriodo(Long idPeriodo) {
        EntityManager entityManager = this.getEntityManager();
        
        Query query = entityManager.createNamedQuery("TccAtividade.findByPeriodo");
        
        query.setParameter("idPeriodo", idPeriodo);        
        
        return query.getResultList();
    }
    
    public List<TccAtividade> findByAnexo(Long idPeriodo) {
        EntityManager entityManager = this.getEntityManager();
        
        Query query = entityManager.createNamedQuery("TccAtividade.findByAnexo");
        
        query.setParameter("idPeriodo", idPeriodo);        
        
        return query.getResultList();
    }
    
    @Override
    public TccAtividade findById(Long idPeriodo) {
        EntityManager entityManager = this.getEntityManager();
        
        Query query = entityManager.createNamedQuery("TccAtividade.findById");
        
        query.setParameter("id", idPeriodo);        
        
        try {
            return (TccAtividade) query.getSingleResult();
        } catch (NoResultException n) {
            return null;
        }
    }
    
    public Date findDataTema(Long idPeriodo) {
        EntityManager entityManager = this.getEntityManager();
        
        Query query = entityManager.createNamedQuery("TccAtividade.findDataTema");
        
        query.setParameter("idPeriodo", idPeriodo);        
        
        try {
            return (Date) query.getSingleResult();
        } catch (NoResultException n) {
            return null;
        }
    }
    
    public void insertInPeriodo(PeriodoLetivo periodo) {
        EntityManager entityManager = this.getEntityManager();
        TccAtividade tccAtividade = new TccAtividade();
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        
        Calendar limite = Calendar.getInstance();
        Calendar limiteFim = Calendar.getInstance();
        
        format.format(periodo.getDtInicio());
        limite = format.getCalendar();
        format = new SimpleDateFormat("dd/MM/yyyy");
        format.format(periodo.getDtFim());
        limiteFim = format.getCalendar();
      
        limite.add( Calendar.DAY_OF_MONTH , 14 );
        
        entityManager.getTransaction().begin();
        //Ordem 1 - Definir Tema
        tccAtividade.setDatalimite(limite.getTime());
        tccAtividade.setDescricao("Escolher o tema e o Orientador, informando ao Coordenador do TCC.");
        tccAtividade.setResponsavel("Aluno");
        tccAtividade.setPeriodo(periodo);
        tccAtividade.setOrdem(1);
        entityManager.persist(tccAtividade);
        
        //Ordem 2 - Aproveitamento de Tcc
        tccAtividade = new TccAtividade();
        tccAtividade.setDatalimite(limite.getTime());
        tccAtividade.setDescricao("Solicitar aproveitamento de TCC");
        tccAtividade.setResponsavel("Aluno");
        tccAtividade.setPeriodo(periodo);
        tccAtividade.setOrdem(2);
        entityManager.persist(tccAtividade);
        
        //Ordem 3 - Definir Plano de Trabalho
        tccAtividade = new TccAtividade();
        tccAtividade.setDatalimite(limite.getTime());
        tccAtividade.setDescricao("Estabelecer um plano de trabalho com metas a serem cumpridas durante a execução do TCC.");
        tccAtividade.setResponsavel("Aluno e Orientador");
        tccAtividade.setPeriodo(periodo);
        tccAtividade.setOrdem(3);
        entityManager.persist(tccAtividade);
        limite.add( Calendar.DAY_OF_MONTH , 7 );
        
        //Ordem 4 - 1ª Entrega
        tccAtividade = new TccAtividade();
        tccAtividade.setDatalimite(limite.getTime());
        tccAtividade.setDescricao("Entregar ao Orientador a revisão bibliográfica e outros resultados estabelecidos no plano de trabalho, se houver.");
        tccAtividade.setResponsavel("Aluno");
        tccAtividade.setPeriodo(periodo);
        tccAtividade.setOrdem(4);
        entityManager.persist(tccAtividade);
        limite.add( Calendar.DAY_OF_MONTH , 7 );
        
        //Ordem 5 - Lançar 1ª Nota
        tccAtividade = new TccAtividade();
        tccAtividade.setDatalimite(limite.getTime());
        tccAtividade.setDescricao("Informar 1ª nota parcial para Coordenador do TCC.");
        tccAtividade.setResponsavel("Orientador");
        tccAtividade.setPeriodo(periodo);
        tccAtividade.setOrdem(5);
        entityManager.persist(tccAtividade);
        limite.add( Calendar.DAY_OF_MONTH , 14 );
        
        //Ordem 6 - 2ª Entrega
        tccAtividade = new TccAtividade();
        tccAtividade.setDatalimite(limite.getTime());
        tccAtividade.setDescricao("Entregar ao Orientador os resultados correspondentes às metas estabelecidas no plano de trabalho.");
        tccAtividade.setResponsavel("Aluno");
        tccAtividade.setPeriodo(periodo);
        tccAtividade.setOrdem(6);
        entityManager.persist(tccAtividade);
        limite.add( Calendar.DAY_OF_MONTH , 7 );
        
        //Ordem 7 - Lançar 2ª Nota
        tccAtividade = new TccAtividade();
        tccAtividade.setDatalimite(limite.getTime());
        tccAtividade.setDescricao("Informar 2ª nota parcial para Coordenador do TCC.");
        tccAtividade.setResponsavel("Orientador");
        tccAtividade.setPeriodo(periodo);
        tccAtividade.setOrdem(7);
        entityManager.persist(tccAtividade);
                
        //Ordem 8 - Informar Monografia
        tccAtividade = new TccAtividade();
        tccAtividade.setDatalimite(limite.getTime());
        tccAtividade.setDescricao("Informar ao Coordenador do TCC o título da Monografia, resumo e o nome dos  componentes da banca de avaliação do trabalho.");
        tccAtividade.setResponsavel("Orientador");
        tccAtividade.setPeriodo(periodo);
        tccAtividade.setOrdem(8);
        entityManager.persist(tccAtividade);
        
        //Ordem 9 - 3ª Entrega
        tccAtividade = new TccAtividade();
        tccAtividade.setDatalimite(limite.getTime());
        tccAtividade.setDescricao("Entregar a monografia para o Orientador e Avaliador(es).");
        tccAtividade.setResponsavel("Aluno");
        tccAtividade.setPeriodo(periodo);
        tccAtividade.setOrdem(9);
        entityManager.persist(tccAtividade);
        
        //Ordem 10 - Divulgar Workshop
        tccAtividade = new TccAtividade();
        tccAtividade.setDatalimite(limiteFim.getTime());
        tccAtividade.setDescricao("Divulgar a programação do Workshop de Monografias.");
        tccAtividade.setResponsavel("Coordenador");
        tccAtividade.setPeriodo(periodo);
        tccAtividade.setOrdem(10);
        entityManager.persist(tccAtividade);
        limiteFim.add( Calendar.DAY_OF_MONTH , 3 );
        
        //Ordem 11 - Workshop
        tccAtividade = new TccAtividade();
        tccAtividade.setDatalimite(limiteFim.getTime());
        tccAtividade.setDescricao("Workshop de Monografias.");
        tccAtividade.setResponsavel("Todos");
        tccAtividade.setPeriodo(periodo);
        tccAtividade.setOrdem(11);
        entityManager.persist(tccAtividade);
        limiteFim.add( Calendar.DAY_OF_MONTH , 1 );
        
        //Ordem 12 - Workshop
        tccAtividade = new TccAtividade();
        tccAtividade.setDatalimite(limiteFim.getTime());
        tccAtividade.setDescricao("Workshop de Monografias.");
        tccAtividade.setResponsavel("Todos");
        tccAtividade.setPeriodo(periodo);
        tccAtividade.setOrdem(12);
        entityManager.persist(tccAtividade);
        limiteFim.add( Calendar.DAY_OF_MONTH , 2 );
        
        //Ordem 13 - 4ª Entrega
        tccAtividade = new TccAtividade();
        tccAtividade.setDatalimite(limiteFim.getTime());
        tccAtividade.setDescricao("Entregar ao Orientador a versão final da monografia.");
        tccAtividade.setResponsavel("Aluno");
        tccAtividade.setPeriodo(periodo);
        tccAtividade.setOrdem(13);
        entityManager.persist(tccAtividade);
        limiteFim.add( Calendar.DAY_OF_MONTH , 1 );
        
        //Ordem 14 - Lançar Nota Final
        tccAtividade = new TccAtividade();
        tccAtividade.setDatalimite(limiteFim.getTime());
        tccAtividade.setDescricao("Informar nota de avaliação do TCC para Coordenador do TCC. Entregar a versão final da Monografia e da Apresentação, a fim de serem publicadas no site do IComp.");
        tccAtividade.setResponsavel("Orientador");
        tccAtividade.setPeriodo(periodo);
        tccAtividade.setOrdem(14);
        entityManager.persist(tccAtividade);
        limiteFim.add( Calendar.DAY_OF_MONTH , 1 );
        
        //Ordem 15 - Lançar Notas
        tccAtividade = new TccAtividade();
        tccAtividade.setDatalimite(limiteFim.getTime());
        tccAtividade.setDescricao("Lançar notas dos alunos no SIE.");
        tccAtividade.setResponsavel("Coordenador");
        tccAtividade.setPeriodo(periodo);
        tccAtividade.setOrdem(15);
        entityManager.persist(tccAtividade);
        
        try {
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            //entityManager.getTransaction().rollback(); 
        }
    }
    
}
