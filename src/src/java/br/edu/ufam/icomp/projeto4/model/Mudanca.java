package br.edu.ufam.icomp.projeto4.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

/**
 *
 * @author Bruna
 */
@Entity
public class Mudanca implements Serializable, Comparable<Mudanca>{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Temporal(javax.persistence.TemporalType.DATE)
    @Column
    private Date dataMudanca;
    @Column
    private String observacao;
    @ManyToOne
    private Usuario responsavel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Usuario getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(Usuario responsavel) {
        this.responsavel = responsavel;
    }

    public Date getDataMudanca() {
        return dataMudanca;
    }

    public void setDataMudanca(Date dataMudanca) {
        this.dataMudanca = dataMudanca;
    }

    @Override
    public String toString() {
        return "Mudanca{" + "id=" + id + ", status=" + status + ", dataMudanca=" + dataMudanca + ", observacao=" + observacao + ", responsavel=" + responsavel + '}';
    }
    
    @Override
    public int compareTo(Mudanca mudanca) {
        return this.dataMudanca.compareTo(mudanca.dataMudanca);
    }
}