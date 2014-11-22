/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ufam.icomp.projeto4.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

/**
 *
 * @author Thiago Santos
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "CoordenadorAcademico.listProfessores", query = "SELECT c.professor FROM CoordenadorAcademico c"),
    @NamedQuery(name = "CoordenadorAcademico.findByUsuario", query = "SELECT c FROM CoordenadorAcademico c WHERE c.professor.usuario.id = :idUsuario")
})
public class CoordenadorAcademico implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "id_coordenadoracademico", sequenceName = "coordenadoracademico_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "id_coordenadoracademico", strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne
    private Professor professor;
    @ManyToMany
    private List<Curso> cursos;

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

    public List<Curso> getCursos() {
        return cursos;
    }

    public void setCursos(List<Curso> cursos) {
        this.cursos = cursos;
    }        
}
