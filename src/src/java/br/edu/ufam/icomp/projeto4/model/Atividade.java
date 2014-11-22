/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ufam.icomp.projeto4.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author Thiago Santos
 */
@NamedQueries({
    @NamedQuery(name= "Atividade.findAllOrderByGrupoAndAtividade", query="SELECT a FROM Atividade a ORDER BY a.grupo.codigo, a.codigo"),
    @NamedQuery(name = "Atividade.findByGroupId", query = "SELECT a FROM Atividade a where a.grupo.id = :pId"),
    @NamedQuery(name = "Atividade.findByCodigo", query = "SELECT a FROM Atividade a where a.codigo = :cod")
})
@Entity
public class Atividade implements Serializable {

    @Id
    @SequenceGenerator(name = "id_atividade", sequenceName = "atividade_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "id_atividade", strategy = GenerationType.AUTO)
    private Long id;
    private String codigo;
    @NotBlank(message = "O campo nome é obrigatório.")
    @Length(max = 255, message = "O campo nome só aceita um texto com tamanho máximo de 255 caracteres.")
    @Column(nullable = false)
    private String nome;
    @ManyToOne
    @NotNull(message = "Um grupo deve ser selecionado.")
    private Grupo grupo;
    @NotBlank(message = "O campo documentação é obrigatório.")
    @Length(max = 255, message = "O campo documentação só aceita um texto com tamanho máximo de 255 caractéres.")
    @Column(nullable = false)
    private String documentacao;
    @NotNull(message = "Uma categoria deve ser selecionada.")
    @Enumerated(EnumType.STRING)
    private Categoria categoria;
    @Transient
    private int totalSolicitada;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public String getDocumentacao() {
        return documentacao;
    }

    public void setDocumentacao(String documentacao) {
        this.documentacao = documentacao;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public int getTotalSolicitada() {
        return totalSolicitada;
    }

    public void setTotalSolicitada(int totalSolicitada) {
        this.totalSolicitada = totalSolicitada;
    }

    @Override
    public String toString() {
        return codigo + " " + nome;
    }
}
