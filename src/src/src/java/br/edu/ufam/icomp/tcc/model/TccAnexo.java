/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ufam.icomp.tcc.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author andre
 */

@Entity
@Table(name = "tccanexo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TccAnexo.findByTcc", query = "SELECT a FROM TccAnexo a WHERE a.id = :id")
})
public class TccAnexo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="id_tcc_anexo", sequenceName="tcc_anexo_id_seq", initialValue=1)
    @GeneratedValue(generator="id_tcc_anexo", strategy= GenerationType.AUTO)
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Long id;
    @Column(name = "data")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date data;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "nome")
    private String nome;
    @Size(min = 1, max = 1024)
    @Column(name = "descricao")
    private String descricao;
    @ManyToOne
    private TccSolicitacao tccSolicitacao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public TccSolicitacao getTccSolicitacao() {
        return tccSolicitacao;
    }

    public void setTccSolicitacao(TccSolicitacao tccSolicitacao) {
        this.tccSolicitacao = tccSolicitacao;
    }

    @Override
    public String toString() {
        return nome;
    }
    
}

