package br.edu.ufam.icomp.projeto4.model;

/**
 *
 * @author Bruna
 */
public enum Status {
    SALVA("Salva"), 
    NOVA("Nova"),
    REENVIADA("Reenviada"),
    INDEFERIDA("Indeferida"),
    PREAPROVADA("Pré-aprovada"),
    DEFERIDA("Deferida"),
    //monitoria
    CONCLUIDA("Concluida"),
    APROVADA("Aprovada"),
    REPROVADA("Reprovada"),
    // estagio
    PENDENTE("Pendente"),
    EMANDAMENTO("Em andamento"),
    CONCLUIDO("Concluído"),
    REPROVADO("Reprovado"),
    // frequencia
    VAZIA("Em branco");
    
    
    public String nome;
    
    Status(String status){
        this.nome = status;
    }
       
    public String getNome() {
        return nome;
    }   
}