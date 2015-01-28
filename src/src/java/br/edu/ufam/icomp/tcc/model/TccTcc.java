/**
 *
 * @author andre
 */

package br.edu.ufam.icomp.tcc.model;

import br.edu.ufam.icomp.projeto4.model.Aluno;
import br.edu.ufam.icomp.projeto4.model.PeriodoLetivo;
import br.edu.ufam.icomp.projeto4.model.Professor;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.*;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "tcctcc")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TccTcc.findByAluno", query = "SELECT t FROM TccTcc t WHERE t.aluno.id = :idAluno"),
    @NamedQuery(name = "TccTcc.findByProfessor", query = "SELECT t.aluno FROM TccTcc t WHERE t.professor.id = :idProfessor ORDER BY t.aluno.usuario.nome"),
    @NamedQuery(name = "TccTcc.findTccByProfessor", query = "SELECT t FROM TccTcc t WHERE t.professor.id = :idProfessor ORDER BY t.aluno.usuario.nome")
})
public class TccTcc implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="id_tcc_tcc", sequenceName="tcc_tcc_id_seq", initialValue=1)
    @GeneratedValue(generator="id_tcc_tcc", strategy= GenerationType.AUTO)
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "titulo")
    private String titulo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1024)
    @Column(name = "descricao")
    private String descricao;
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_aluno")
    private Aluno aluno;
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_professor")
    private Professor professor;
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_periodo")
    private PeriodoLetivo periodo;
    @OneToMany(mappedBy="tccTcc")
    @Column(insertable = false, updatable = false)
    private List<TccSolicitacao> solicitacoes;
    @OneToOne(mappedBy="tcctcc")
    private TccNotas tccnotas;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public PeriodoLetivo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(PeriodoLetivo periodo) {
        this.periodo = periodo;
    }

    public List<TccSolicitacao> getSolicitacoes() {
        return solicitacoes;
    }

    public void setSolicitacoes(List<TccSolicitacao> solicitacoes) {
        this.solicitacoes = solicitacoes;
    }
    
    @Override
    public String toString() {
        return titulo;
    }

    
    
}