package br.edu.ufam.icomp.projeto4.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

/**
 *
 * @author Bruna
 */
@Entity
public class FrequenciaMensalEstagio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "id_frequencia_mensal_estagio", sequenceName = "frequencia_estagio_mensal_id_seq", initialValue = 1)
    @GeneratedValue(generator = "id_frequencia_mensal_estagio", strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private Integer mes;
    @OneToMany
    private List<FrequenciaEstagio> frequencias;
    @ManyToOne
    private Estagio estagio;
    @Enumerated(EnumType.STRING)
    private Status status;
    @OneToMany
    private List<Aprovacao> aprovacoes;
    
    public FrequenciaMensalEstagio(){
        frequencias = new ArrayList<FrequenciaEstagio>();
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public List<FrequenciaEstagio> getFrequencias() {
        return frequencias;
    }

    public void setFrequencias(List<FrequenciaEstagio> frequencias) {
        this.frequencias = frequencias;
    }

    public Estagio getEstagio() {
        return estagio;
    }

    public void setEstagio(Estagio estagio) {
        this.estagio = estagio;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    
    public void addFrequencia(FrequenciaEstagio frequencia){
        frequencias.add(frequencia);
    }
    
    public void removeFrequencia(FrequenciaEstagio frequencia){
        for (FrequenciaEstagio freq : frequencias) {
            if(frequencia.getId() == freq.getId()){
                frequencias.remove(freq);
                return;
            }
        }
    }
    
    public boolean analisada() {
        return status == Status.APROVADA || status == Status.REPROVADA;
    }

    public List<Aprovacao> getAprovacoes() {
        return aprovacoes;
    }

    public void setAprovacoes(List<Aprovacao> aprovacoes) {
        this.aprovacoes = aprovacoes;
    }        
}