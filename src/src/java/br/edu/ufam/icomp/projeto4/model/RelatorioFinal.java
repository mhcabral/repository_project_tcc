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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

/**
 *
 * @author Thiago Santos
 */
@Entity
public class RelatorioFinal implements Serializable{
    @Id
    @SequenceGenerator(name="relatorio_id", sequenceName="relatorio_id_seq", allocationSize=1)
    @GeneratedValue(generator="relatorio_id", strategy= GenerationType.SEQUENCE)
    private Long id;
    
    private String relatorioFinal;
    
    @OneToMany
    private List<Aprovacao> aprovacoes;        

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }        

    public String getRelatorioFinal() {
        return relatorioFinal;
    }

    public void setRelatorioFinal(String relatorioFinal) {
        this.relatorioFinal = relatorioFinal;
    }

    public List<Aprovacao> getAprovacoes() {
        return aprovacoes;
    }

    public void setAprovacoes(List<Aprovacao> aprovacoes) {
        this.aprovacoes = aprovacoes;
    }        
}
