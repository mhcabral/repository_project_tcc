package br.edu.ufam.icomp.tcc.controllers;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Put;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.ValidationMessage;
import br.edu.ufam.icomp.projeto4.SessionData;
import br.edu.ufam.icomp.projeto4.dao.AlunoDAO;
import br.edu.ufam.icomp.projeto4.dao.CursoDAO;
import br.edu.ufam.icomp.projeto4.dao.ProfessorDAO;
import br.edu.ufam.icomp.projeto4.interceptor.Perfil;
import br.edu.ufam.icomp.projeto4.interceptor.Permission;
import br.edu.ufam.icomp.projeto4.model.Aluno;
import br.edu.ufam.icomp.projeto4.model.Anexo;
import br.edu.ufam.icomp.projeto4.model.Professor;
import br.edu.ufam.icomp.tcc.dao.TccAtividadeDAO;
import br.edu.ufam.icomp.tcc.dao.TccSolicitacaoDAO;
import br.edu.ufam.icomp.tcc.dao.TccTccDAO;
import br.edu.ufam.icomp.tcc.dao.TccTemaDAO;
import br.edu.ufam.icomp.tcc.model.TccSolicitacao;
import br.edu.ufam.icomp.tcc.model.TccTcc;
import br.edu.ufam.icomp.tcc.model.TccTema;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import br.com.caelum.vraptor.interceptor.multipart.UploadedFile;
import br.edu.ufam.icomp.tcc.dao.TccAnexoDAO;
import br.edu.ufam.icomp.tcc.dao.TccNotasDAO;
import br.edu.ufam.icomp.tcc.dao.TccWorkshopDAO;
import br.edu.ufam.icomp.tcc.model.TccAnexo;
import br.edu.ufam.icomp.tcc.model.TccAtividade;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author andre
 */

@Resource
public class TccTccController {
    private final Result result;
    private final Validator validator;
    private final TccTccDAO tccTccDAO;
    private final CursoDAO cursoDAO;
    private final ProfessorDAO professorDAO;
    private SessionData sessionData;
    private final AlunoDAO alunoDAO;
    private final TccTemaDAO tccTemaDAO;
    private final TccAtividadeDAO tccAtividadeDAO;
    private final TccSolicitacaoDAO tccSolicitacaoDAO;
    private final TccAnexoDAO tccAnexoDAO;
    private final TccWorkshopDAO tccWorkshopDAO;
    private final TccNotasDAO tccNotasDAO;
    private Anexo pastaDeAnexos;
    
    public TccTccController (Result result,TccTccDAO tccTccDAO, Validator validator, CursoDAO cursoDAO,
            ProfessorDAO professorDAO, SessionData sessionData, AlunoDAO alunoDAO, TccTemaDAO tccTemaDAO,
            TccAtividadeDAO tccAtividadeDAO, TccSolicitacaoDAO tccSolicitacaoDAO,
            TccAnexoDAO tccAnexoDAO, TccWorkshopDAO tccWorkshopDAO, TccNotasDAO tccNotasDAO, Anexo pastaDeAnexos){
        this.result = result;
        this.validator = validator;
        this.tccTccDAO = tccTccDAO;
        this.cursoDAO = cursoDAO;
        this.professorDAO = professorDAO;
        this.sessionData = sessionData;
        this.alunoDAO = alunoDAO;
        this.tccTemaDAO = tccTemaDAO;
        this.tccAtividadeDAO = tccAtividadeDAO;
        this.tccSolicitacaoDAO = tccSolicitacaoDAO;
        this.tccAnexoDAO = tccAnexoDAO;
        this.tccWorkshopDAO = tccWorkshopDAO;
        this.tccNotasDAO = tccNotasDAO;
        this.pastaDeAnexos = pastaDeAnexos;
    }


    @Get("/tcctcc")
    public void index() {
        Aluno aluno = alunoDAO.findByIdUsuario(sessionData.getUsuario().getId());
        TccTcc tccTcc = this.tccTccDAO.findByAluno(aluno.getId());
        
        if (tccTcc == null) {
            this.result.redirectTo(TccTccController.class).create();
        } else this.result.redirectTo(TccTccController.class).edit(tccTcc.getId());
    }
    
    @Get("/tcctcc/{id}/edit")
    public TccTcc edit(Long id) {
        TccTcc tccTcc = tccTccDAO.findById(id);
        List<Perfil> perfisEncontrar = new ArrayList<Perfil>();
        perfisEncontrar.add(Perfil.PROFESSOR);
        List<Professor> listProfessor = professorDAO.findByPerfisAndAtivo(perfisEncontrar, true);
        List<TccTema> listTema = tccTemaDAO.findAll();
        Aluno aluno = tccTcc.getAluno();
        Long idPeriodo = tccTcc.getPeriodo().getId();
        TccSolicitacao tccSolicitacao = new TccSolicitacao();
        List<TccSolicitacao> solicitacoes = tccTcc.getSolicitacoes();
        List<TccAtividade> atividades = tccAtividadeDAO.findByPeriodo(sessionData.getLetivoAtual().getId());
        Boolean podeSalvarTema = true;
        Boolean podeSalvarUpload = true;
        
        if (tccTcc == null) {
            this.validator.add(new ValidationMessage("Desculpe!O Tcc não foi encontrado.", "tccTcc.id"));
        }
        
        this.validator.onErrorRedirectTo(TccTccController.class).index();
        
        if (tccTcc.getSolicitacoes().size() == 1) {
            if (tccTcc.getSolicitacoes().get(0).getEstado().equals("Solicitado")) {
                podeSalvarTema = true;
                podeSalvarUpload = true;
            } else {
                podeSalvarTema = false;
                podeSalvarUpload = true;
            }
                
        } else if (tccTcc.getSolicitacoes().size() >= 2) {
            podeSalvarUpload = true;
            podeSalvarTema = false;
        }
                
        this.result.include("operacao", "Edição");
        this.result.include("professorList", listProfessor);
        this.result.include("aluno", aluno);
        this.result.include("temaList", listTema);
        this.result.include("idPeriodo", idPeriodo);
        this.result.include("podeSalvarTema", podeSalvarTema);
        this.result.include("podeSalvarUpload", podeSalvarUpload);
        return tccTcc;
    }
    
    @Get("/tcctcc/create")
    public void create() {
        Aluno aluno = alunoDAO.findByIdUsuario(sessionData.getUsuario().getId());
        List<Perfil> perfisEncontrar = new ArrayList<Perfil>();
        perfisEncontrar.add(Perfil.PROFESSOR);
        List<Professor> listProfessor = professorDAO.findByPerfisAndAtivo(perfisEncontrar, true);
        List<TccTema> listTema = tccTemaDAO.findAll();
        Long idPeriodo = sessionData.getLetivoAtual().getId();
        
        this.result.include("operacao", "Cadastro");
        this.result.include("professorList", listProfessor);
        this.result.include("aluno", aluno);
        this.result.include("temaList", listTema);
        this.result.include("idPeriodo", idPeriodo);
        this.result.include("podeSalvarTema", podeSalvarTema());
    }
    
    @Get("/tcctcc/{id}/show")
    public TccTcc show(Long id) {
        TccTcc tccTcc = this.tccTccDAO.findById(id);

        if (tccTcc == null) {
            this.validator.add(new ValidationMessage("Desculpe! O Tcc não foi encontrado.", "tccTcc.id"));
        }

        this.validator.onErrorRedirectTo(TccTccController.class).index();
        
        return tccTcc;
    }
    
    @Get("/tcctcc/download/{nome}")
    public File download(String nome) {

        File file = pastaDeAnexos.mostrar(nome);
        return file;
    }
    
    @Post("/tcctcc")
    public void cadastrar(TccTcc tccTcc, Boolean aproveitamento) {
        List<TccAtividade> atividades = tccAtividadeDAO.findByAnexo(sessionData.getLetivoAtual().getId());
        
        if (tccTcc.getProfessor().getId() == null || tccTcc.getProfessor().getId() == null) {
            validator.add(new ValidationMessage("Um professor deve ser selecionado", "tccTcc.professor.id"));
        }
        this.validator.onErrorRedirectTo(TccTccController.class).index();
        
        if (aproveitamento == null) {aproveitamento = false;}
        tccTcc.setAproveitamento(aproveitamento);
        
        this.tccTccDAO.create(tccTcc);  
        
        TccSolicitacao tccSolicitacao = new TccSolicitacao();
        if (tccTcc.getAproveitamento()) {
            tccSolicitacao.setAtividade(atividades.get(5));
        } else {
            tccSolicitacao.setAtividade(atividades.get(0));
        }
        tccSolicitacao.setEstado("Solicitado");
        tccSolicitacao.setTccTcc(tccTcc);
        this.tccSolicitacaoDAO.create(tccSolicitacao);
        
        this.result.include("success", "cadastrada");
        
        this.result.redirectTo(TccController.class).main();
    }
    
    @Put("/tcctcc")
    public void altera(TccTcc tccTcc, List<UploadedFile> anexos, String descricao, Boolean aproveitamento) {
        TccTcc tcc1 = tccTccDAO.findById(tccTcc.getId());
        tccTcc.setSolicitacoes(tcc1.getSolicitacoes());
        tccTcc.setAproveitamento(aproveitamento);
        
        if (tccTcc.getProfessor().getId() == null) {
            validator.add(new ValidationMessage("Um professor deve ser selecionado", "tccTcc.professor.id"));
        }
        this.validator.onErrorRedirectTo(TccTccController.class).index();
                
        String extensao = "";
        int funcionou = 0;
                        
        if (!(anexos == null)) {
            if (anexos.isEmpty()) {
                this.validator.add(new ValidationMessage("Adicione a documentação comprobatória", "anexo"));
                this.validator.onErrorRedirectTo(TccTccController.class).index();
            }

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
                    this.validator.onErrorRedirectTo(TccTccController.class).index();
                } else {
                    String nomeAleatorio = pastaDeAnexos.nomeAleatorio();

                    nomeAleatorio += extensao;

                    pastaDeAnexos.salva(uploadedFile, nomeAleatorio);

                    nomeAnexos.add(nomeAleatorio);
                    
                    List<TccAtividade> atividades = tccAtividadeDAO.findByAnexo(sessionData.getLetivoAtual().getId());
                    Integer tamanho = tccTcc.getSolicitacoes().size();
                    TccSolicitacao ultSolicitacao = tccTcc.getSolicitacoes().get(tamanho-1);
                    Date date = new Date();
                    TccAnexo tccAnexo = new TccAnexo();
                    tccAnexo.setData(date);
                    tccAnexo.setDescricao(descricao);
                    tccAnexo.setNome(nomeAleatorio);
                    if (!(ultSolicitacao.getEstado().equals("Solicitado"))) {
                        TccSolicitacao tccSolicitacao = new TccSolicitacao();
                        tccSolicitacao.setAtividade(atividades.get(tamanho));
                        tccSolicitacao.setEstado("Solicitado");
                        tccSolicitacao.setTccTcc(tccTcc);
                        this.tccSolicitacaoDAO.create(tccSolicitacao);
                        tccAnexo.setTccSolicitacao(tccSolicitacao);
                    } else {
                        tccAnexo.setTccSolicitacao(ultSolicitacao);
                    }
                    this.tccAnexoDAO.create(tccAnexo);
                }
            }

        }
                
        this.tccTccDAO.update(tccTcc);

        this.result.include("success", "alterada");

        this.result.redirectTo(TccController.class).main();
    }
  
    private Boolean podeSalvarTema() {
        List<TccAtividade> atividades = tccAtividadeDAO.findByAnexo(sessionData.getLetivoAtual().getId());
        TccAtividade actTema = atividades.get(0);
        TccAtividade actAproveitamento = atividades.get(5);
        Date data = new Date();
        Date limiteTema = actTema.getDatalimite();
        Date prorrogacaoTema = actTema.getDataprorrogacao();
        Date limiteAproveitamento = actAproveitamento.getDatalimite();
        Date prorrogacaoAproveitamento = actAproveitamento.getDataprorrogacao();
        
        if (prorrogacaoTema == null) {
            prorrogacaoTema = limiteTema;
        }
        if (prorrogacaoAproveitamento == null) {
            prorrogacaoAproveitamento = limiteAproveitamento;
        }
        
        data = zerarHoras(data);
        Boolean podeSalvar = (!(limiteTema.before(data) || prorrogacaoTema.equals(limiteTema)) ||
                !(prorrogacaoTema.before(data) || prorrogacaoTema.before(limiteTema))) ||
                (!(limiteAproveitamento.before(data) || prorrogacaoAproveitamento.equals(limiteAproveitamento)) ||
                !(prorrogacaoAproveitamento.before(data) || prorrogacaoAproveitamento.before(limiteAproveitamento)));
        
        System.out.println("limite "+limiteAproveitamento);
        System.out.println("prorrogacao "+prorrogacaoAproveitamento);
        System.out.println("data "+data);
        System.out.println("podeSalvar "+prorrogacaoAproveitamento.before(data));
        
        return podeSalvar;
    }
    
    private static Date zerarHoras(Date data){
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

}
