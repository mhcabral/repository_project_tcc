package br.edu.ufam.icomp.projeto4.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;


/**
 *
 * @author janderson
 */
@Entity
@Table(name = "aluno")
@NamedQueries({
    @NamedQuery(name = "Aluno.listAll", query = "SELECT a FROM Aluno a"),
    @NamedQuery(name = "Aluno.findById", query = "SELECT a FROM Aluno a WHERE a.id= :id"),
    @NamedQuery(name = "Aluno.findByIdUsuario", query = "SELECT a FROM Aluno a WHERE a.usuario.id = :id"),
    @NamedQuery(name = "Aluno.findByCurso", query = "SELECT a FROM Aluno a WHERE a.curso IN (:cursoList)"),
    @NamedQuery(name = "Aluno.findByCursoId", query = "SELECT a FROM Aluno a WHERE a.curso.id = :id"),
    @NamedQuery(name = "Aluno.findByMatricula", query = "SELECT a FROM Aluno a WHERE a.matricula = :matricula"),
    @NamedQuery(name = "Aluno.findByPerfilAndAtivo", query = "SELECT a FROM Aluno a WHERE a.usuario.ativo = :ativo AND a.usuario.role = :role ORDER BY a.dataIngresso, a.curso.codigo, a.matricula DESC")
})
public class Aluno implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="id_aluno", sequenceName="aluno_id_seq", initialValue=1)
    @GeneratedValue(generator="id_aluno", strategy= GenerationType.AUTO)
    @Column(name="id_aluno")
    private Long id;
    
    @NotNull(message="Desculpe! O campo matrícula é obrigatório")
    @Column(nullable=false, unique=true, length=8)
    private String matricula;
    
    @NotNull(message="Desculpe! O campo data de Ingresso é obrigatório")
    @Temporal(TemporalType.DATE)
    @Column(nullable=false)
    private Date dataIngresso;
        
    @JoinColumn(name = "id_usuario")
    @OneToOne    
    private Usuario usuario;
        
    @ManyToOne
    private Curso curso;
    
    @Transient
    private String nomeAluno;
    
    @Transient
    private int ensino;
    
    @Transient
    private int pesquisa;
    
    @Transient
    private int extensao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public Date getDataIngresso() {
        return dataIngresso;
    }

    public void setDataIngresso(Date dataIngresso) {
        this.dataIngresso = dataIngresso;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public String getNomeAluno() {
        return nomeAluno;
    }

    public void setNomeAluno(String nomeAluno) {
        this.nomeAluno = nomeAluno;
    }

    public int getEnsino() {
        return ensino;
    }

    public void setEnsino(int ensino) {
        this.ensino = ensino;
    }

    public int getPesquisa() {
        return pesquisa;
    }

    public void setPesquisa(int pesquisa) {
        this.pesquisa = pesquisa;
    }

    public int getExtensao() {
        return extensao;
    }

    public void setExtensao(int extensao) {
        this.extensao = extensao;
    }

    @Override
    public String toString() {
        return usuario.getNome();
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 67 * hash + (this.dataIngresso != null ? this.dataIngresso.hashCode() : 0);
        hash = 67 * hash + (this.usuario != null ? this.usuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Aluno other = (Aluno) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        if (this.dataIngresso != other.dataIngresso && (this.dataIngresso == null || !this.dataIngresso.equals(other.dataIngresso))) {
            return false;
        }
        if (this.usuario != other.usuario && (this.usuario == null || !this.usuario.equals(other.usuario))) {
            return false;
        }
        return true;
    }
}
