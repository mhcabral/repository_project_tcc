/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ufam.icomp.projeto4.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

/**
 *
 * @author Thiago Santos
 */
@NamedQueries({
    @NamedQuery(name="RegraGrupo.findByCurso", query = "SELECT r FROM RegraGrupo r WHERE r.curso.id = :idCurso"),
    @NamedQuery(name = "RegraGrupo.findByCursoAndGrupo", query = "SELECT r FROM RegraGrupo r WHERE r.curso.id = :idCurso AND r.grupo.id = :idGrupo"),
    @NamedQuery(name = "RegraGrupo.listGrupos", query = "SELECT r.grupo FROM RegraGrupo r WHERE r.curso.id = :idCurso")
})
@Entity
public class RegraGrupo implements Serializable {

    @Id
    @SequenceGenerator(name = "id_regragrupo", sequenceName = "regragrupo_id_seq", initialValue = 1)
    @GeneratedValue(generator = "id_regragrupo", strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne
    private Grupo grupo;
    @OneToOne
    private Curso curso;
    @Column(nullable = false)
    private Integer maximoHoras;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Integer getMaximoHoras() {
        return maximoHoras;
    }

    public void setMaximoHoras(Integer maximoHoras) {
        this.maximoHoras = maximoHoras;
    }

    @Override
    public String toString() {
        return "RegraGrupo{" + "id=" + id + ", grupo=" + grupo + ", curso=" + curso + ", maximoHoras=" + maximoHoras + '}';
    }
}
