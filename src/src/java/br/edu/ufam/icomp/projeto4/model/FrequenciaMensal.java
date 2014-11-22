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
public class FrequenciaMensal implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "id_frequencia_mensal", sequenceName = "frequencia_mensal_id_seq", initialValue = 1)
    @GeneratedValue(generator = "id_frequencia_mensal", strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private Integer mes;
    @OneToMany
    private List<Frequencia> frequencias;    
    @ManyToOne
    private InscricaoMonitoria inscricao;
    @Enumerated(EnumType.STRING)
    private Status status;
    
    public FrequenciaMensal(){
        frequencias = new ArrayList<Frequencia>();
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

    public List<Frequencia> getFrequencias() {
        return frequencias;
    }

    public void setFrequencias(List<Frequencia> frequencias) {
        this.frequencias = frequencias;
    }

    public InscricaoMonitoria getInscricao() {
        return inscricao;
    }

    public void setInscricao(InscricaoMonitoria inscricao) {
        this.inscricao = inscricao;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    
    public void addFrequencia(Frequencia frequencia){
        frequencias.add(frequencia);
    }
    
    public void removeFrequencia(Frequencia frequencia){
        for (Frequencia freq : frequencias) {
            if(frequencia.getId() == freq.getId()){
                frequencias.remove(freq);
                return;
            }
        }
    }
    
    public boolean totalValido() {
        if(frequencias.isEmpty()) {
            return false;
        }
        
        int carga_total = 0;
        for (Frequencia frequencia : frequencias) {
            carga_total += frequencia.getCargaHoraria();
        }
        return carga_total >= 48;
    }
    
    @Override
    public String toString() {
        return "FrequenciaMensal{" + "id=" + id + ", mes=" + mes + ", frequencias=" + frequencias + ", inscricao=" + inscricao + ", status=" + status + '}';
    }

    public boolean analisada() {
        return status == Status.APROVADA || status == Status.REPROVADA;
    }
}