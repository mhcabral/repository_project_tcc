package br.edu.ufam.icomp.projeto4.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author Bruna
 */
@Entity
@Table(name = "coordenadorcurso")
@NamedQueries({
    @NamedQuery(name = "CoordenadorCurso.findByAtivo", query = "SELECT c FROM CoordenadorCurso c WHERE c.professor.usuario.ativo = :ativo"),
    @NamedQuery(name = "CoordenadorCurso.findById", query = "SELECT c FROM CoordenadorCurso c WHERE c.id= :id"),
    @NamedQuery(name = "CoordenadorCurso.findByProfessor", query = "SELECT c FROM CoordenadorCurso c WHERE c.professor.id = :idProfessor"),
    @NamedQuery(name = "CoordenadorCurso.findByProfessorCurso", query = "SELECT c FROM CoordenadorCurso c WHERE c.professor.id = :idProfessor AND c.curso.id = :idCurso"),
    @NamedQuery(name = "CoordenadorCurso.findByUsuario", query = "SELECT c FROM CoordenadorCurso c WHERE c.professor.usuario.id = :idUsuario"),
    @NamedQuery(name = "CoordenadorCurso.findByCurso", query = "SELECT c FROM CoordenadorCurso c WHERE c.curso.id = :idCurso"),
    @NamedQuery(name = "CoordenadorCurso.listCursos", query = "SELECT c.curso FROM CoordenadorCurso c"),
    @NamedQuery(name = "CoordenadorCurso.listProfessores", query = "SELECT c.professor FROM CoordenadorCurso c")
})
public class CoordenadorCurso implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "id_coordenadorcurso", sequenceName = "coordenadorcurso_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "id_coordenadorcurso", strategy = GenerationType.AUTO)
    private Long id;
    @JoinColumn(name = "id_professor")
    @OneToOne
    private Professor professor;
    @OneToOne
    private Curso curso;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CoordenadorCurso)) {
            return false;
        }
        CoordenadorCurso other = (CoordenadorCurso) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.edu.ufam.icomp.projeto4.model.CoordenadorCurso[ id=" + id + " ]";
    }
}