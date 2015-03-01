/**
 *
 * @author mhcabral
 */
package br.edu.ufam.icomp.tcc.controllers;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Put;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.interceptor.multipart.UploadedFile;
import br.com.caelum.vraptor.validator.ValidationMessage;
import br.edu.ufam.icomp.projeto4.SessionData;
import br.edu.ufam.icomp.projeto4.interceptor.Perfil;
import br.edu.ufam.icomp.projeto4.interceptor.Permission;
import br.edu.ufam.icomp.projeto4.model.Aluno;
import br.edu.ufam.icomp.tcc.dao.TccAproveitamentoDAO;
import br.edu.ufam.icomp.projeto4.dao.AlunoDAO;
import br.edu.ufam.icomp.tcc.model.TccAproveitamento;
import java.util.ArrayList;
import java.util.List;
import br.edu.ufam.icomp.tcc.model.TccAnexo;
import br.edu.ufam.icomp.projeto4.model.Anexo;
import br.edu.ufam.icomp.tcc.dao.TccAnexoDAO;
import br.edu.ufam.icomp.tcc.dao.TccAtividadeDAO;
import br.edu.ufam.icomp.tcc.dao.TccSolicitacaoDAO;
import br.edu.ufam.icomp.tcc.model.TccAtividade;
import br.edu.ufam.icomp.tcc.model.TccSolicitacao;
import java.util.Date;

@Resource
@Permission({Perfil.ALUNO, Perfil.COORDENADOR, Perfil.COORDENADORACAD, Perfil.PROFESSOR, Perfil.ROOT, Perfil.SECRETARIA})
public class TccAproveitamentoController {
    private final Result result;
    private final Validator validator;
    private final TccAproveitamentoDAO tccAproveitamentoDAO;
    private SessionData session;
    private AlunoDAO alunoDAO;
    private final TccAnexoDAO tccAnexoDAO;
    private Anexo pastaDeAnexos;
    private final TccAtividadeDAO tccAtividadeDAO;
    private final TccSolicitacaoDAO tccSolicitacaoDAO;
    
    public TccAproveitamentoController (Result result,TccAproveitamentoDAO tccAproveitamentoDAO, Validator validator, SessionData session, AlunoDAO alunoDAO, Anexo pastaDeAnexos,TccAnexoDAO tccanexoDAO,TccAtividadeDAO tccatividadeDAO,TccSolicitacaoDAO tccsolicitacaoDAO){
        this.result = result;
        this.validator = validator;
        this.tccAproveitamentoDAO = tccAproveitamentoDAO;
        this.session = session;
        this.alunoDAO = alunoDAO;
        this.pastaDeAnexos = pastaDeAnexos;
        this.tccAnexoDAO = tccanexoDAO;
        this.tccAtividadeDAO = tccatividadeDAO;
        this.tccSolicitacaoDAO = tccsolicitacaoDAO;
    }


    @Get("/tccaproveitamento")
    public void index() {
        List<TccAproveitamento> tccAproveitamento = null;
        if (session.getUsuario().getRole().equals(Perfil.ALUNO)) {
            Aluno aluno = this.alunoDAO.findByIdUsuario(session.getUsuario().getId());
            tccAproveitamento.add(this.tccAproveitamentoDAO.findByAluno(aluno.getId()));       
        }
        else{
            tccAproveitamento = this.tccAproveitamentoDAO.findAll();
        }
        this.result.include("tccAproveitamentoList", tccAproveitamento);
        
       
    }
    
    @Get("/tccaproveitamento/{id}/show")
    public TccAproveitamento show(Long id) {
        TccAproveitamento tccAproveitamento = this.tccAproveitamentoDAO.findById(id);

        if (tccAproveitamento == null) {
            this.validator.add(new ValidationMessage("Desculpe! O Aproveitamento não foi encontrado.", "tccAproveitamento.id"));
        }

        this.validator.onErrorRedirectTo(TccAproveitamentoController.class).index();
        
        return tccAproveitamento;
    }
    
    @Permission(Perfil.ALUNO)
    @Get("/tccaproveitamento/{id}/remove")
    public void remove(Long id) {
        TccAproveitamento tccAproveitamento = this.tccAproveitamentoDAO.findById(id);

        if (tccAproveitamento == null) {
            this.validator.add(new ValidationMessage("Desculpe! O Aproveitamento não foi encontrado.", "tccAproveitamento.id"));
        }

        this.validator.onErrorRedirectTo(TccAproveitamentoController.class).index();

        this.tccAproveitamentoDAO.delete(tccAproveitamento);

        this.result.include("success", "removida");

        this.result.redirectTo(this).index();
    }
    
    @Permission(Perfil.ALUNO)
    @Get("/tccaproveitamento/create")
    public void create() {
        
        this.result.include("operacao", "Cadastro");
    }
    
    @Permission(Perfil.ALUNO)
    @Get("/tccaproveitamento/{id}/edit")
    public TccAproveitamento edit(Long id) {

        TccAproveitamento tccAproveitamento = tccAproveitamentoDAO.findById(id);
        
        if (tccAproveitamento == null) {
            this.validator.add(new ValidationMessage("Desculpe!O Aproveitamento não foi encontrado.", "tccAproveitamento.id"));
        }
        
        this.validator.onErrorRedirectTo(TccLocaisController.class).index();

        return tccAproveitamento;
    }
    
    @Post("/tccAproveitamento")
    public void cadastrar(TccAproveitamento tccAproveitamento, List<UploadedFile> anexos, String descricao) {
        String extensao = "";
        int funcionou = 0;
        tccAproveitamento.setEstado("Ativo");
        
         List<TccAtividade> atividades = tccAtividadeDAO.findByAnexo(session.getLetivoAtual().getId());
        List<String> nomeAnexos = new ArrayList<String>();
        
        for (UploadedFile uploadedFile : anexos) {
            
            if (uploadedFile.getContentType().equals("application/pdf")){
                extensao = ".pdf";
                funcionou = 1 ;
            }
            else if(uploadedFile.getContentType().equals("image/png")){
                extensao = ".png";
                funcionou = 1 ;
            }
            else if(uploadedFile.getContentType().equals("image/jpeg")){
                extensao = ".jpg";
                funcionou = 1 ;
            }
            
            if (funcionou == 0) {
                this.validator.add(new ValidationMessage("A extensão do arquivo [" + uploadedFile.getFileName() + "] não é aceita no sistema", "anexos[]", anexos));
            } else {
                String nomeAleatorio = pastaDeAnexos.nomeAleatorio();
                
                nomeAleatorio += extensao;

                pastaDeAnexos.salva(uploadedFile, nomeAleatorio);

                nomeAnexos.add(nomeAleatorio);
                
                
                Date date = new Date();
                TccAnexo tccAnexo = new TccAnexo();
                tccAnexo.setData(date);
                tccAnexo.setDescricao(descricao);
                tccAnexo.setNome(nomeAleatorio);
                TccSolicitacao tccSolicitacao = new TccSolicitacao();
                tccSolicitacao.setAtividade(atividades.get(0));
                tccSolicitacao.setEstado("Solicitado");
                //tccSolicitacao.setTccTcc(tccTcc);
                this.tccSolicitacaoDAO.create(tccSolicitacao);
                tccAnexo.setTccSolicitacao(tccSolicitacao);
            }
        }
                
        this.tccAproveitamentoDAO.create(tccAproveitamento);

        this.result.include("success", "cadastrada");

        this.result.redirectTo(TccAproveitamentoController.class).index();
    }
    
    @Put("/tccAproveitamento")
    public void altera(TccAproveitamento tccaproveitamento, List<UploadedFile> anexos) {
        if (anexos != null && anexos.isEmpty()) {
            this.validator.add(new ValidationMessage("Adicione os arquivos referentes a sua solicitacao", "anexo"));
        }
        
        List<String> nomeAnexos = new ArrayList<String>();
        
            for (UploadedFile uploadedFile : anexos) {

                if (!uploadedFile.getContentType().equals("application/pdf") && !uploadedFile.getContentType().equals("application/msword")
                        && !uploadedFile.getContentType().equals("application/vnd.oasis.opendocument.text") && !uploadedFile.getContentType().equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document")
                        && !uploadedFile.getContentType().equals("image/png") && !uploadedFile.getContentType().equals("image/jpeg")) {
                    this.validator.add(new ValidationMessage("A extensão do arquivo [" + uploadedFile.getFileName() + "] não é aceita", "anexos[]", anexos));
                } else {
                    String nomeAleatorio = pastaDeAnexos.nomeAleatorio();
                    
                    pastaDeAnexos.salva(uploadedFile, nomeAleatorio);

                    nomeAnexos.add(nomeAleatorio);
                }
            }
        
        this.tccAproveitamentoDAO.update(tccaproveitamento);

        this.result.include("success", "alterada");

        this.result.redirectTo(TccAproveitamentoController.class).index();
    }
    
}
