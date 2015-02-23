/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ufam.icomp.projeto4.model;

/**
 *
 * @author Thiago Santos
 */
public enum Categoria {
    ENSINO("Ensino"), 
    PESQUISA("Pesquisa"), 
    EXTENSAO("Extensão");
        
    Categoria(String categoria){
        this.nome = categoria;
    }
    
    public String nome;

    public String getNome() {
        return nome;
    }        
}
