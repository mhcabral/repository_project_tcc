package br.edu.ufam.icomp.projeto4.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author janderson
 */
@Entity
@Table(name = "professor")
@NamedQueries({
    @NamedQuery(name = "Professor.findByUsuario", query = "SELECT p FROM Professor p WHERE p.usuario.id = :pId"),
    @NamedQuery(name = "Professor.findById", query = "SELECT p FROM Professor p WHERE p.id = :pId"),
    @NamedQuery(name = "Professor.listProfessorNotIn", query = "SELECT p FROM Professor p WHERE p NOT IN (:professorList) AND p.usuario.ativo = :ativo AND p.usuario.role = :role"),
    @NamedQuery(name = "Professor.findByPerfisAndAtivo", query = "SELECT p FROM Professor p WHERE p.usuario.role IN (:roles) AND p.usuario.ativo = :ativo"),
})
public class Professor implements Serializable {

    @Id
    @SequenceGenerator(name="id_professor", sequenceName="professor_id_seq", initialValue=1)
    @GeneratedValue(generator="id_professor", strategy= GenerationType.AUTO)
    private Long id;
    @JoinColumn(name = "id_usuario", nullable = false)
    @OneToOne
    private Usuario usuario;
    @ManyToMany
    private List<Curso> cursos;

    public Professor() {
        cursos = new ArrayList<Curso>();
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Professor)) {
            return false;
        }
        Professor other = (Professor) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Curso> getCursos() {
        return cursos;
    }

    public void setCursos(List<Curso> cursos) {
        this.cursos = cursos;
    }

    public void addCurso(Curso curso) {
        cursos.add(curso);
    }

    /**
     * @return the object string representation
     */
    @Override
    public String toString() {
        return usuario.getNome();
    }
}