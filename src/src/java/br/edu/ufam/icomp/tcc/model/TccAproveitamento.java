/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ufam.icomp.tcc.model;

import br.edu.ufam.icomp.projeto4.model.Aluno;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mhcabral
 */
@Entity
@Table(name = "tccaproveitamento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TccAproveitamento.findAll", query = "SELECT t FROM TccAproveitamento t"),
    @NamedQuery(name = "TccAproveitamento.findById", query = "SELECT t FROM TccAproveitamento t WHERE t.id = :id"),
    @NamedQuery(name = "TccAproveitamento.findByAluno", query = "SELECT t FROM TccAproveitamento t WHERE t.aluno.id = :idAluno")
})
public class TccAproveitamento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="id_tcc_aproveitamento", sequenceName="tcc_aproveitamento_id_seq", initialValue=1)
    @GeneratedValue(generator="id_tcc_aproveitamento", strategy= GenerationType.AUTO)
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
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "sigla")
    private String sigla;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "area")
    private String area;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "estado")
    private String estado;
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_aluno")
    private Aluno aluno;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "orientador")
    private String orientador;
    @ElementCollection(targetClass = String.class)
    private List<String> anexos;

    public TccAproveitamento() {
    }

    public TccAproveitamento(Long id) {
        this.id = id;
    }

    public TccAproveitamento(Long id, String titulo, String descricao, String sigla, String area, String estado, Aluno aluno, String orientador, List<String> anexos) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.sigla = sigla;
        this.area = area;
        this.estado = estado;
        this.aluno = aluno;
        this.orientador = orientador;
        this.anexos = anexos;
    }

    

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

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public String getOrientador() {
        return orientador;
    }

    public void setOrientador(String orientador) {
        this.orientador = orientador;
    }

    public List<String> getAnexos() {
        return anexos;
    }

    public void setAnexos(List<String> anexos) {
        this.anexos = anexos;
    }

    @Override
    public String toString() {
        return "TccAproveitamento{" + "id=" + id + ", titulo=" + titulo + ", descricao=" + descricao + ", aluno=" + aluno.getNomeAluno() + ", orientador=" + orientador + '}';
    }
    
    
}
