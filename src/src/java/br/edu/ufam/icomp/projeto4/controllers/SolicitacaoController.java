package br.edu.ufam.icomp.projeto4.controllers;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Put;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.interceptor.download.Download;
import br.com.caelum.vraptor.interceptor.download.InputStreamDownload;
import br.com.caelum.vraptor.interceptor.multipart.UploadedFile;
import br.com.caelum.vraptor.validator.ValidationMessage;
import br.com.caelum.vraptor.view.Results;
import br.edu.ufam.icomp.projeto4.DataComparator;
import br.edu.ufam.icomp.projeto4.SessionData;
import br.edu.ufam.icomp.projeto4.dao.AlunoDAO;
import br.edu.ufam.icomp.projeto4.dao.AtividadeDAO;
import br.edu.ufam.icomp.projeto4.dao.PeriodoLetivoDAO;
import br.edu.ufam.icomp.projeto4.dao.RegraDAO;
import br.edu.ufam.icomp.projeto4.dao.RegraGrupoDAO;
import br.edu.ufam.icomp.projeto4.dao.SolicitacaoDAO;
import br.edu.ufam.icomp.projeto4.interceptor.Perfil;
import br.edu.ufam.icomp.projeto4.interceptor.Permission;
import br.edu.ufam.icomp.projeto4.model.Aluno;
import br.edu.ufam.icomp.projeto4.model.Anexo;
import br.edu.ufam.icomp.projeto4.model.Atividade;
import br.edu.ufam.icomp.projeto4.model.Grupo;
import br.edu.ufam.icomp.projeto4.model.Mudanca;
import br.edu.ufam.icomp.projeto4.model.PeriodoLetivo;
import br.edu.ufam.icomp.projeto4.model.RegraGrupo;
import br.edu.ufam.icomp.projeto4.model.Solicitacao;
import br.edu.ufam.icomp.projeto4.model.Status;
import br.edu.ufam.icomp.projeto4.model.Usuario;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;

/**
 *
 * @author Bruna
 */
@Resource
public class SolicitacaoController {

    private final SolicitacaoDAO solicitacaoDAO;
    private final Result result;
    private final Validator validator;
    private AtividadeDAO atividadeDAO;
    private AlunoDAO alunoDAO;
    private Anexo pastaDeAnexos;
    private MudancaController mudancaController;
    private RegraDAO regraDAO;
    private SessionData session;
    private Usuario usuario;
    private ServletContext context;
    private Aluno aluno;
    private PeriodoLetivoDAO periodoLetivoDAO;
    private RegraGrupoDAO regraGrupoDAO;

    public SolicitacaoController(SolicitacaoDAO solicitacaoDAO, Result result, Validator validator,
            AtividadeDAO atividadeDAO, PeriodoLetivoDAO periodoLetivoDAO, MudancaController mudancaController,
            AlunoDAO alunoDAO, Anexo pastaDeAnexos, RegraDAO regraDAO, SessionData session,
            ServletContext context, RegraGrupoDAO regraGrupoDAO) {
        this.solicitacaoDAO = solicitacaoDAO;
        this.atividadeDAO = atividadeDAO;
        this.result = result;
        this.validator = validator;
        this.mudancaController = mudancaController;
        this.alunoDAO = alunoDAO;
        this.pastaDeAnexos = pastaDeAnexos;
        this.regraDAO = regraDAO;
        this.session = session;
        usuario = session.getUsuario();
        this.context = context;
        aluno = alunoDAO.findByIdUsuario(usuario.getId());
        System.out.println("Erro3");
        this.periodoLetivoDAO = periodoLetivoDAO;
        this.regraGrupoDAO = regraGrupoDAO;
    }

    @Permission(Perfil.ALUNO)
    @Get("/solicitacoes")
    public void index(Long idAtividade, Long idPeriodoLetivo) {
        System.out.println("ok1");
        List<Solicitacao> solicitacoesSolicitante = this.solicitacaoDAO.findBySolicitante(aluno.getId());
        this.result.include("solicitacaoEditavelList", this.getSolicitacoesEditaveis(solicitacoesSolicitante));

        this.result.include("totalComputado", this.solicitacaoDAO.countBySolicitante(aluno.getId(), Status.DEFERIDA));

        if (idAtividade == null) {
            idAtividade = 0L;
        }

        if (idPeriodoLetivo == null) {
            idPeriodoLetivo = 0L;
        }

        if (idAtividade == 0 && idPeriodoLetivo == 0) {
            this.result.include("solicitacaoNaoEditavelList", this.getSolicitacoesNaoEditaveis(solicitacoesSolicitante));
        } else {
            List<Solicitacao> solicitacaosAux = new ArrayList<Solicitacao>();

            List<Solicitacao> solicitacoesNaoEditaveis = this.getSolicitacoesNaoEditaveis(solicitacoesSolicitante);

            if (idAtividade > 0) {
                for (Solicitacao solicitacao : solicitacoesNaoEditaveis) {
                    if (!solicitacao.getAtividade().getId().equals(idAtividade)) {
                        solicitacaosAux.add(solicitacao);
                    }
                }

                solicitacoesNaoEditaveis.removeAll(solicitacaosAux);

                solicitacaosAux.clear();
            }

            if (idPeriodoLetivo > 0) {

                PeriodoLetivo periodoLetivo = this.periodoLetivoDAO.findById(idPeriodoLetivo);


                if (periodoLetivo != null) {

//                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

//                    for (Solicitacao solicitacao : solicitacoesNaoEditaveis) {
//                        if (!(solicitacao.getMudancas().get(0).getDataMudanca().after(periodoLetivo.getDtInicio())
//                                && solicitacao.getMudancas().get(0).getDataMudanca().before(periodoLetivo.getDtFim())
//                                || simpleDateFormat.format(solicitacao.getMudancas().get(0).getDataMudanca()).equals(simpleDateFormat.format(periodoLetivo.getDtInicio()))
//                                || simpleDateFormat.format(solicitacao.getMudancas().get(0).getDataMudanca()).equals(simpleDateFormat.format(periodoLetivo.getDtFim()))))                                
//                                {
//                             
//                            solicitacaosAux.add(solicitacao);
//                        }                                                                        
//                    }

                    for (Solicitacao solicitacao : solicitacoesNaoEditaveis) {
                        if (!solicitacao.getPeriodo().getId().equals(periodoLetivo.getId())) {
                            solicitacaosAux.add(solicitacao);
                        }
                    }

                    solicitacoesNaoEditaveis.removeAll(solicitacaosAux);

                    solicitacaosAux.clear();
                }
            }

            this.result.include("solicitacaoNaoEditavelList", solicitacoesNaoEditaveis);
        }

        this.result.include("idAtividade", idAtividade);
        this.result.include("idPeriodoLetivo", idPeriodoLetivo);

        this.result.include("atividadeList", this.regraDAO.findAtividadeFromCurso(this.aluno.getCurso().getId()));
        this.result.include("periodoLetivoList", this.periodoLetivoDAO.findAll());
    }

    @Get("/solicitacoes/{idAtividade}/{idPeriodoLetivo}/search")
    public void search(Long idAtividade, Long idPeriodoLetivo) {

        this.result.redirectTo(this).index(idAtividade, idPeriodoLetivo);
    }

    @Permission(Perfil.ALUNO)
    @Get("/solicitacoes/create")
    public void create() {
        /*if (periodoLetivoDAO.findMatriculaAtivo() == null) {
         this.result.redirectTo(this).index();
         }*/
        Aluno aluno = alunoDAO.findByIdUsuario(usuario.getId());
        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");

        this.result.include("dataIngresso", formatador.format(aluno.getDataIngresso()));
        this.result.include("atividadeList", this.regraDAO.findAtividadeFromCurso(aluno.getCurso().getId()));
        this.result.include("operacao", "Cadastro");
    }

    @Permission(Perfil.ALUNO)
    @Get("/solicitacoes/{id}/edit")
    public Solicitacao edit(Long id) {
        Solicitacao solicitacao = solicitacaoDAO.findById(id);
        if (solicitacao == null) {
            this.result.redirectTo(SolicitacaoController.class).index(null, null);
        } else if (!(solicitacao.getStatusAtual() == Status.SALVA || solicitacao.getStatusAtual() == Status.INDEFERIDA)) {
            validator.add(new ValidationMessage("Desculpe! Solicitação não pode ser editada, já foi analisada ou encaminhada para análise", "solicitacao.id"));
            result.include("solicitacao", solicitacao);

            this.validator.validate(solicitacao);

            this.validator.onErrorRedirectTo(this).show(id);
        } else {
            Aluno aluno = alunoDAO.findByIdUsuario(usuario.getId());

            SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");

            this.result.include("dataIngresso", formatador.format(aluno.getDataIngresso()));
            this.result.include("atividadeList", this.regraDAO.findAtividadeFromCurso(aluno.getCurso().getId()));
            this.result.include("operacao", "Edição");
            this.result.include("horaMaxima", this.regraDAO.findByAtividadeCurso(solicitacao.getAtividade().getId(), aluno.getCurso().getId()).getMaximoHoras());
        }
        return solicitacao;
    }

    @Permission(Perfil.ALUNO)
    @Get("/solicitacoes/{id}/remove")
    public void remove(Long id) {
        Solicitacao solicitacao = this.solicitacaoDAO.findById(id);

        if (solicitacao == null) {
            this.result.redirectTo(SolicitacaoController.class).index(null, null);
        } else {
            if (this.aluno.getId() == solicitacao.getSolicitante().getId()) {
                if (!(solicitacao.getStatusAtual() == Status.SALVA || solicitacao.getStatusAtual() == Status.INDEFERIDA)) {
                    validator.add(new ValidationMessage("Desculpe! Solicitação não pôde ser removida, já foi analisada ou encaminhada para análise", "solicitacao.id"));
                    result.include("solicitacao", solicitacao);

                    this.validator.validate(solicitacao);

                    this.validator.onErrorRedirectTo(this).show(id);
                } else {
                    this.solicitacaoDAO.delete(solicitacao);

                    result.include("success", "removida");

                    this.result.redirectTo(this).index(null, null);
                }
            } else {
                validator.add(new ValidationMessage("A solicitação não foi feita pelo seu usuário", "solicitacao.id"));
                this.validator.onErrorRedirectTo(this).index(null, null);
            }
        }
    }

    @Permission(Perfil.ALUNO)
    @Get("/solicitacoes/{id}/show")
    public Solicitacao show(Long id) {
        if (solicitacaoDAO.findById(id) == null) {
            this.result.redirectTo(SolicitacaoController.class).index(null, null);
        }

        Solicitacao solicitacao = this.solicitacaoDAO.findById(id);

        DataComparator comparator = new DataComparator();
        Collections.sort(solicitacao.getMudancas(), comparator);

        return solicitacao;
    }

    @Put("/solicitacoes")
    public void altera(Solicitacao solicitacao, List<UploadedFile> anexos, boolean apagar) {
        Solicitacao editada = this.solicitacaoDAO.findById(solicitacao.getId());
        editada.setAtividade(atividadeDAO.findById(solicitacao.getAtividade().getId()));
        editada.setDescricao(solicitacao.getDescricao());
        editada.setQntd_horas(solicitacao.getQntd_horas());
        editada.setObservacoes(solicitacao.getObservacoes());
        editada.setDataInicio(solicitacao.getDataInicio());
        editada.setDataTermino(solicitacao.getDataTermino());

        if (editada.getAtividade().getId() == null) {
            this.validator.add(new ValidationMessage("Selecione uma Atividade", "solicitacao.atividade.id"));
        }

        if (anexos != null && anexos.isEmpty()) {
            this.validator.add(new ValidationMessage("Adicione os arquivos comprobatórios", "anexo"));
        }

        Aluno aluno = alunoDAO.findByIdUsuario(usuario.getId());

        Grupo grupo = atividadeDAO.findById(solicitacao.getAtividade().getId()).getGrupo();
        /*
         if (!getHorasComputadas(grupo, aluno)) {
         this.validator.add(new ValidationMessage("Não é possível solicitar aproveitamento para atividades: " + grupo, "solicitacao.atividade.id"));
         }
         */
        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");

        this.result.include("dataIngresso", formatador.format(aluno.getDataIngresso()));
        this.result.include("atividadeList", this.regraDAO.findAtividadeFromCurso(aluno.getCurso().getId()));
        this.result.include("operacao", "Edição");
        this.result.include("horaMaxima", this.regraDAO.findByAtividadeCurso(solicitacao.getAtividade().getId(), aluno.getCurso().getId()).getMaximoHoras());
        this.validator.validate(editada);

        this.validator.onErrorRedirectTo(this).edit(editada.getId());


        List<String> nomeAnexos = new ArrayList<String>();

        if (!apagar) {
            nomeAnexos.addAll(editada.getAnexos());
        } else {
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
        }
        this.validator.onErrorRedirectTo(this).create();

        editada.setAnexos(nomeAnexos);

        this.solicitacaoDAO.update(editada);

        result.include("success", "alterada");

        this.result.redirectTo(this).index(null, null);
    }

    @Post("/solicitacoes")
    public void cadastrar(final Solicitacao solicitacao, List<UploadedFile> anexos) {
        String extensao = "";
        int funcionou = 0;
        if (solicitacao.getAtividade().getId() == null) {
            this.validator.add(new ValidationMessage("Selecione uma Atividade", "solicitacao.atividade.id"));
        }

        if (anexos.isEmpty()) {
            this.validator.add(new ValidationMessage("Adicione a documentação comprobatória", "anexo"));
        }

//        Aluno aluno = alunoDAO.findByIdUsuario(usuario.getId());
//
//        Grupo grupo = atividadeDAO.findById(solicitacao.getAtividade().getId()).getGrupo();
/*
         if (!getHorasComputadas(grupo, aluno)) {
         this.validator.add(new ValidationMessage("Não é possível solicitar aproveitamento para atividades: " + grupo, "solicitacao.atividade.id"));
         }
         */
//        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
//
//        this.result.include("dataIngresso", formatador.format(aluno.getDataIngresso()));
//        this.result.include("atividadeList", this.regraDAO.findAtividadeFromCurso(aluno.getCurso().getId()));
//        this.result.include("operacao", "Edição");
//        this.result.include("horaMaxima", this.regraDAO.findByAtividadeCurso(solicitacao.getAtividade().getId(), aluno.getCurso().getId()).getMaximoHoras());

        solicitacao.setPeriodo(periodoLetivoDAO.findLetivoAtivo());

        this.validator.validate(solicitacao);

        this.validator.onErrorRedirectTo(this).create();

        Mudanca mudanca = mudancaController.create(usuario, Status.SALVA, "");

        solicitacao.adicionaMudanca(mudanca);
        solicitacao.setSolicitante(aluno);
        solicitacao.setStatusAtual(Status.SALVA);

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
            }
        }

        this.validator.onErrorRedirectTo(this).create();

        solicitacao.setAnexos(nomeAnexos);

        this.solicitacaoDAO.create(solicitacao);

        result.include("success", "cadastrada");

        this.result.redirectTo(SolicitacaoController.class).index(null, null);
    }

    @Get
    @Path("/solicitacoes/buscaAtividades.json")
    public void buscaAtividadesJson() {
        List<Atividade> atividades = this.atividadeDAO.findAll();
        result.use(Results.json()).withoutRoot().from(atividades).serialize();
    }

    private List<Solicitacao> getSolicitacoesEditaveis(List<Solicitacao> solicitacoesSolicitante) {
        List<Solicitacao> solicitacoes = new ArrayList<Solicitacao>();

        for (Solicitacao solicitacao : solicitacoesSolicitante) {
            if (solicitacao.getStatusAtual() == Status.SALVA || solicitacao.getStatusAtual() == Status.INDEFERIDA) {
                solicitacoes.add(solicitacao);
            }
        }

        return solicitacoes;
    }

    private List<Solicitacao> getSolicitacoesNaoEditaveis(List<Solicitacao> solicitacoesSolicitante) {
        List<Solicitacao> solicitacoes = new ArrayList<Solicitacao>();

        for (Solicitacao solicitacao : solicitacoesSolicitante) {
            if (solicitacao.getStatusAtual() == Status.DEFERIDA || solicitacao.getStatusAtual() == Status.NOVA
                    || solicitacao.getStatusAtual() == Status.PREAPROVADA || solicitacao.getStatusAtual() == Status.REENVIADA) {
                solicitacoes.add(solicitacao);
            }
        }

        return solicitacoes;
    }

    @Get("solicitacoes/enviarTodas")
    public void enviarTodas() {
        List<Solicitacao> solicitacoesSolicitante = this.solicitacaoDAO.findBySolicitante(aluno.getId());
        List<Solicitacao> solicitacaoEditavelList = this.getSolicitacoesEditaveis(solicitacoesSolicitante);

        for (int i = 0; i < solicitacaoEditavelList.size(); i++) {
            Solicitacao solicitacao = solicitacaoEditavelList.get(i);
            if (solicitacao.getStatusAtual() == Status.INDEFERIDA) {
                solicitacao.adicionaMudanca(mudancaController.create(usuario, Status.REENVIADA, ""));
                solicitacao.setStatusAtual(Status.REENVIADA);
            }
            if (solicitacao.getStatusAtual() == Status.SALVA) {
                solicitacao.adicionaMudanca(mudancaController.create(usuario, Status.NOVA, ""));
                solicitacao.setStatusAtual(Status.NOVA);
            }
            this.solicitacaoDAO.update(solicitacao);
        }

        this.result.include("enviada", "enviada");

        this.result.redirectTo(SolicitacaoController.class).index(null, null);
    }

    private boolean getHorasComputadas(Grupo grupo, Aluno aluno) {
        RegraGrupo regra = regraGrupoDAO.findByCursoAndGrupo(aluno.getCurso(), grupo);
        int maxGrupo = regra.getMaximoHoras();
        int countGrupo = (int) solicitacaoDAO.countByGrupo(aluno.getId(), grupo.getId(), Status.DEFERIDA);

        int diferenca = maxGrupo - countGrupo;

        if (diferenca > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Get("/solicitacoes/historico")
    public Download historico() throws JRException {

        List<Solicitacao> historico = this.solicitacaoDAO.findHistorico(aluno.getId(), Status.DEFERIDA);              

        InputStream resourceAsStream = getClass().getResourceAsStream("/br/edu/ufam/icomp/projeto4/jasper/HistoricoAC.jrxml");

        JasperReport report = JasperCompileManager.compileReport(resourceAsStream);

        Aluno aluno = this.alunoDAO.findByIdUsuario(this.session.getUsuario().getId());

        Map parameters = new HashMap();
        parameters.put("nomeAluno", this.session.getUsuario().getNome());
        parameters.put("curso", aluno.getCurso().getCurso());
        parameters.put("anoIngresso", aluno.getDataIngresso());
        parameters.put("imgUfam", context.getRealPath("/images/logo_ufam.png"));
        parameters.put("imgBrasao", context.getRealPath("/images/brasao.gif"));

        JasperPrint print = JasperFillManager.fillReport(report, parameters, new JRBeanCollectionDataSource(historico));


        // exportacao do relatorio para outro formato, no caso PDF

        JRExporter exporter = new JRPdfExporter();

        ByteArrayOutputStream exported = new ByteArrayOutputStream();

        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, exported);
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);

        exporter.exportReport();

        byte[] content = exported.toByteArray();
        InputStream inputStream = new ByteArrayInputStream(content);

        return new InputStreamDownload(inputStream, "application/pdf", "RelatorioAluno.pdf");
    }
}
