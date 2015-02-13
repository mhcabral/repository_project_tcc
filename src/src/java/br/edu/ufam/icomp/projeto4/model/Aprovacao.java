/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ufam.icomp.projeto4.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

/**
 *
 * @author Thiago Santos
 */
@Entity
public class Aprovacao implements Serializable {

    @Id
    @SequenceGenerator(name="id_aprovacao", sequenceName="aprovacao_id_seq", allocationSize=1)
    @GeneratedValue(generator="id_aprovacao", strategy= GenerationType.SEQUENCE)
    private Long id;
    
    private String emailAprovador;
    private String nomeAprovador;
    private Boolean aprovou;    
    
    public Aprovacao(){
        aprovou = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }   

    public Boolean getAprovou() {
        return aprovou;
    }

    public void setAprovou(Boolean aprovou) {
        this.aprovou = aprovou;
    }

    public String getEmailAprovador() {
        return emailAprovador;
    }

    public void setEmailAprovador(String emailAprovador) {
        this.emailAprovador = emailAprovador;
    }        

    public String getNomeAprovador() {
        return nomeAprovador;
    }

    public void setNomeAprovador(String nomeAprovador) {
        this.nomeAprovador = nomeAprovador;
    }        
}
