package br.edu.ufam.icomp.projeto4.controllers;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Put;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.interceptor.multipart.UploadedFile;
import br.com.caelum.vraptor.validator.ValidationMessage;
import br.edu.ufam.icomp.projeto4.Notificador;
import br.edu.ufam.icomp.projeto4.SessionData;
import br.edu.ufam.icomp.projeto4.dao.AlunoDAO;
import br.edu.ufam.icomp.projeto4.dao.AprovacaoDAO;
import br.edu.ufam.icomp.projeto4.dao.CoordenadorCursoDAO;
import br.edu.ufam.icomp.projeto4.dao.EstagioDAO;
import br.edu.ufam.icomp.projeto4.dao.PeriodoLetivoDAO;
import br.edu.ufam.icomp.projeto4.dao.ProfessorDAO;
import br.edu.ufam.icomp.projeto4.dao.RelatorioFinalDAO;
import br.edu.ufam.icomp.projeto4.dao.SecretariaDAO;
import br.edu.ufam.icomp.projeto4.interceptor.Perfil;
import br.edu.ufam.icomp.projeto4.interceptor.Permission;
import br.edu.ufam.icomp.projeto4.model.Aluno;
import br.edu.ufam.icomp.projeto4.model.Anexo;
import br.edu.ufam.icomp.projeto4.model.Aprovacao;
import br.edu.ufam.icomp.projeto4.model.CoordenadorCurso;
import br.edu.ufam.icomp.projeto4.model.Curso;
import br.edu.ufam.icomp.projeto4.model.Estagio;
import br.edu.ufam.icomp.projeto4.model.FrequenciaMensalEstagio;
import br.edu.ufam.icomp.projeto4.model.PeriodoLetivo;
import br.edu.ufam.icomp.projeto4.model.RelatorioFinal;
import br.edu.ufam.icomp.projeto4.model.Secretaria;
import br.edu.ufam.icomp.projeto4.model.Status;
import br.edu.ufam.icomp.projeto4.model.Usuario;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 *
 * @author Bruna
 */
@Resource
public class EstagioController {

    private final Result result;
    private final Validator validator;
    private final EstagioDAO estagioDAO;
    private ProfessorDAO professorDAO;
    private Anexo pastaDeAnexos;
    private SessionData sessionData;
    private AlunoDAO alunoDAO;
    private PeriodoLetivoDAO periodoLetivoDAO;
    private CoordenadorCursoDAO coordenadorCursoDAO;
    private AprovacaoDAO aprovacaoDAO;
    private RelatorioFinalDAO relatorioFinalDAO;
    private Notificador notificador;
    private SecretariaDAO secretariaDAO;

    public EstagioController(Result result, Validator validator, EstagioDAO estagioDAO, ProfessorDAO professorDAO,
            Anexo pastaDeAnexos, SessionData sessionData, AlunoDAO alunoDAO, PeriodoLetivoDAO periodoLetivoDAO, CoordenadorCursoDAO coordenadorCursoDAO,
            AprovacaoDAO aprovacaoDAO, RelatorioFinalDAO relatorioFinalDAO, Notificador notificador, SecretariaDAO secretariaDAO) {
        this.result = result;
        this.validator = validator;
        this.estagioDAO = estagioDAO;
        this.professorDAO = professorDAO;
        this.pastaDeAnexos = pastaDeAnexos;
        this.sessionData = sessionData;
        this.alunoDAO = alunoDAO;
        this.periodoLetivoDAO = periodoLetivoDAO;
        this.coordenadorCursoDAO = coordenadorCursoDAO;
        this.aprovacaoDAO = aprovacaoDAO;
        this.relatorioFinalDAO = relatorioFinalDAO;
        this.notificador = notificador;
        this.secretariaDAO = secretariaDAO;
    }

    @Permission(Perfil.ALUNO)
    @Get("/estagios")
    public void index() {
        Usuario usuario = sessionData.getUsuario();
        Aluno aluno = alunoDAO.findByIdUsuario(usuario.getId());

        result.include("estagioList", estagioDAO.listByAluno(aluno.getId()));
    }

    @Permission(Perfil.ALUNO)
    @Get("/estagios/create")
    public Estagio create() {
        result.include("operacao", "Cadastro");
        result.include("professorList", professorDAO.findAll());

        return new Estagio();
    }

    @Permission(Perfil.ALUNO)
    @Post("/estagios")
    public void cadastrar(final Estagio estagio, UploadedFile termoCompromisso, UploadedFile seguro, UploadedFile fichaCadastro) {
        PeriodoLetivo periodoAtual = sessionData.getLetivoAtual();
        Usuario usuario = sessionData.getUsuario();
        Aluno aluno = alunoDAO.findByIdUsuario(usuario.getId());

        Estagio estagioSolicitado = estagioDAO.findByAlunoPeriodo(aluno.getId(), periodoAtual.getId());

        if (estagio.getOrientador().getId() == null) {
            this.validator.add(new ValidationMessage("Selecione um professor", "professor"));
        }

        if (estagioSolicitado != null) {
            validator.add(new ValidationMessage("Já existe uma solicitação de inscrição em estágio para este período", "estagio.periodo"));
        }

        if (!termoCompromisso.getContentType().equals("application/pdf")) {
            this.validator.add(new ValidationMessage("A extensão do arquivo [" + termoCompromisso.getFileName() + "] não é aceita no sistema", "termoCompromisso", termoCompromisso));
        } else {
            String nomeAleatorio = pastaDeAnexos.nomeAleatorio();
            
            pastaDeAnexos.salva(termoCompromisso, nomeAleatorio);
            estagio.setTermoCompromisso(nomeAleatorio);
        }
        if (!seguro.getContentType().equals("application/pdf")) {
            this.validator.add(new ValidationMessage("A extensão do arquivo [" + seguro.getFileName() + "] não é aceita no sistema", "seguro", seguro));
        } else {
            String nomeAleatorio = pastaDeAnexos.nomeAleatorio();
            
            pastaDeAnexos.salva(seguro, nomeAleatorio);
            estagio.setSeguro(nomeAleatorio);
        }
        if (fichaCadastro != null) {
            if (!fichaCadastro.getContentType().equals("application/pdf")) {
                this.validator.add(new ValidationMessage("A extensão do arquivo [" + fichaCadastro.getFileName() + "] não é aceita no sistema", "fichaCadastro", fichaCadastro));
            } else {
                String nomeAleatorio = pastaDeAnexos.nomeAleatorio();
                
                pastaDeAnexos.salva(fichaCadastro, nomeAleatorio);
                estagio.setFichaCadastroEmpresa(nomeAleatorio);
            }
        }

        estagio.setStatus(Status.PENDENTE);
        estagio.setPeriodo(periodoAtual);
        estagio.setAluno(aluno);

        this.validator.validate(estagio);

        validator.onErrorRedirectTo(this).create();

        this.estagioDAO.create(estagio);

        result.include("success", "Solicitação");

        this.result.redirectTo(this).main();
    }

    @Permission(Perfil.ALUNO)
    @Get("/estagios/{id}/edit")
    public Estagio edit(Long id) {
        PeriodoLetivo periodo = periodoLetivoDAO.findEstagioAtivo();

        if (periodo == null) {
            this.result.redirectTo(this).main();
        }

        result.include("operacao", "Edição");

        Estagio estagio = this.estagioDAO.findById(id);

        if (estagio == null) {
            validator.add(new ValidationMessage("Desculpe! Estagio não encontrado", "estagio.id"));
        }

        this.validator.validate(estagio);

        validator.onErrorRedirectTo(this).main();

        result.include("professorList", professorDAO.findAll());

        return estagio;
    }

    @Permission(Perfil.ALUNO)
    @Put("/estagios")
    public void altera(Estagio estagio, UploadedFile termoCompromisso, UploadedFile seguro, UploadedFile fichaCadastro) {
        if (estagio.getOrientador().getId() == null) {
            this.validator.add(new ValidationMessage("Selecione um professor", "professor"));
        }

        Estagio estagioBusca = estagioDAO.findById(estagio.getId());
        estagio.setStatus(estagioBusca.getStatus());
        estagio.setPeriodo(estagioBusca.getPeriodo());
        estagio.setAluno(estagioBusca.getAluno());

        if (termoCompromisso == null) {
            estagio.setTermoCompromisso(estagioBusca.getTermoCompromisso());
        } else {
            if (!termoCompromisso.getContentType().equals("application/pdf")) {
                this.validator.add(new ValidationMessage("A extensão do arquivo [" + termoCompromisso.getFileName() + "] não é aceita no sistema", "termoCompromisso", termoCompromisso));
            } else {
                String nomeAleatorio = pastaDeAnexos.nomeAleatorio();
                pastaDeAnexos.salva(termoCompromisso, nomeAleatorio);
                estagio.setTermoCompromisso(nomeAleatorio);
            }
        }

        if (seguro == null) {
            estagio.setSeguro(estagioBusca.getSeguro());
        } else {
            if (!seguro.getContentType().equals("application/pdf")) {
                this.validator.add(new ValidationMessage("A extensão do arquivo [" + seguro.getFileName() + "] não é aceita no sistema", "seguro", seguro));
            } else {
                String nomeAleatorio = pastaDeAnexos.nomeAleatorio();
                pastaDeAnexos.salva(seguro, nomeAleatorio);
                estagio.setSeguro(nomeAleatorio);
            }
        }

        if (fichaCadastro == null) {
            estagio.setFichaCadastroEmpresa(null);
        } else {
            if (!fichaCadastro.getContentType().equals("application/pdf")) {
                this.validator.add(new ValidationMessage("A extensão do arquivo [" + fichaCadastro.getFileName() + "] não é aceita no sistema", "fichaCadastro", fichaCadastro));
            } else {
                String nomeAleatorio = pastaDeAnexos.nomeAleatorio();
                
                pastaDeAnexos.salva(fichaCadastro, nomeAleatorio);
                estagio.setFichaCadastroEmpresa(nomeAleatorio);
            }
        }

        this.validator.validate(estagio);

        validator.onErrorRedirectTo(this).edit(estagio.getId());

        this.estagioDAO.update(estagio);

        result.include("success", "Alteração na solicitação");

        this.result.redirectTo(this).main();
    }

    @Permission(Perfil.ALUNO)
    @Get("/estagios/{id}/remove")
    public void remove(Long id) {
        Estagio estagio = this.estagioDAO.findById(id);

        if (estagio == null) {
            validator.add(new ValidationMessage("Desculpe! Estágio não encontrado", "estagio.id"));
        }

        this.validator.validate(estagio);

        validator.onErrorRedirectTo(this).main();

        this.estagioDAO.delete(estagio);

        this.result.include("success", "Remoção");

        this.result.redirectTo(this).main();
    }

    @Get("/estagios/{id}/show")
    public Estagio show(Long id) {
        Estagio estagio = this.estagioDAO.findById(id);

        PeriodoLetivo periodo = periodoLetivoDAO.findEstagioAtivo();

        if (estagio == null) {
            result.redirectTo(this).index();
        }

        return estagio;
    }

    @Permission(Perfil.ALUNO)
    @Get("/estagios/index")
    public Estagio main() {
        PeriodoLetivo periodoAtual = sessionData.getLetivoAtual();
        Usuario usuario = sessionData.getUsuario();
        Aluno aluno = alunoDAO.findByIdUsuario(usuario.getId());

        Estagio concluido = estagioDAO.findByStatusAluno(aluno.getId(), Status.CONCLUIDO);

        Date atual = new Date();
        
        GregorianCalendar gc = new GregorianCalendar();        
        gc.setTime(this.sessionData.getLetivoAtual().getDtFim());        
        gc.add(GregorianCalendar.DAY_OF_MONTH, -30);

        if (atual.before(gc.getTime())) {
            this.result.include("bloqueio", true);
        }

        if (concluido != null) {
            return concluido;
        } else {
            PeriodoLetivo estagioAtivo = periodoLetivoDAO.findEstagioAtivo();

            if (periodoAtual == null) {
                return null;
            }

            Estagio estagioPeriodo = estagioDAO.findByAlunoPeriodo(aluno.getId(), periodoAtual.getId());

            return estagioPeriodo;
        }
    }

    @Get("/estagios/download/{anexo}")
    public File download(String anexo) {

        File file = pastaDeAnexos.mostrar(anexo);

        return file;
    }

    @Post("estagios/relatorioFinal/confirm")
    public void confirm(Estagio estagio, Usuario usuario) {
        Estagio estagioEncontrado = this.estagioDAO.findById(estagio.getId());

        if (estagioEncontrado == null) {
            this.validator.add(new ValidationMessage("Desculpe! Estágio não foi encontrado.", "estagio.id"));
            this.result.include("voltar", true);
            this.result.include("estagio", estagioEncontrado);
        }

        this.validator.onErrorForwardTo(EstagioController.class).show(estagio.getId());

        List<Aprovacao> aprovacoes = estagioEncontrado.getRelatorioFinal().getAprovacoes();

        Aprovacao aprovacaoAux = null;

        for (Aprovacao aprovacao : aprovacoes) {
            if (aprovacao.getEmailAprovador().equals(usuario.getEmail())) {
                aprovacaoAux = aprovacao;
            }
        }

        if (aprovacaoAux == null) {
            this.validator.add(new ValidationMessage("Desculpe! Você não está cadastrado como um aprovador.", "estagio.id"));
            this.result.include("voltar", true);
            this.result.include("estagio", estagioEncontrado);
        }

        if (aprovacaoAux.getAprovou() != null) {
            this.validator.add(new ValidationMessage("Desculpe! Você já analisou este relatório final.", "estagio.id"));
            this.result.include("voltar", true);
            this.result.include("estagio", estagioEncontrado);
        }


        this.validator.onErrorForwardTo(EstagioController.class).show(estagio.getId());

        this.result.include("estagio", estagioEncontrado);
        this.result.include("usuario", usuario);
    }

    @Post("/estagios/uploadRelatorio")
    public void uploadRelatorio(Estagio estagio, UploadedFile relatorioFinal) {

        Estagio estagioEncontrado = this.estagioDAO.findById(estagio.getId());

        if (estagioEncontrado == null) {
            this.validator.add(new ValidationMessage("Desculpe! Nenhum estágio foi encontrado.", "estagio.id"));
            this.result.include("estagio", estagioEncontrado);
        }

        this.validator.onErrorRedirectTo(EstagioController.class).show(estagio.getId());

        if (!relatorioFinal.getContentType().equals("application/pdf") && !relatorioFinal.getContentType().equals("application/msword")
                && !relatorioFinal.getContentType().equals("application/vnd.oasis.opendocument.text") && !relatorioFinal.getContentType().equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document")
                && !relatorioFinal.getContentType().equals("image/png") && !relatorioFinal.getContentType().equals("image/jpeg")) {
            this.validator.add(new ValidationMessage("A extensão do arquivo [" + relatorioFinal.getFileName() + "] não é aceita", "anexos[]", ""));
        } else {
            
            String nomeAleatorio = pastaDeAnexos.nomeAleatorio();
            
            pastaDeAnexos.salva(relatorioFinal, nomeAleatorio);

            RelatorioFinal relatorioFinalAux = new RelatorioFinal();
            relatorioFinalAux.setRelatorioFinal(nomeAleatorio);

            Aprovacao aprovacao1 = new Aprovacao();
            aprovacao1.setEmailAprovador(estagioEncontrado.getOrientador().getUsuario().getEmail());
            aprovacao1.setNomeAprovador(estagioEncontrado.getOrientador().getUsuario().getNome());

            Aprovacao aprovacao2 = new Aprovacao();
            Aluno aluno = this.alunoDAO.findByIdUsuario(this.sessionData.getUsuario().getId());
            CoordenadorCurso coordenadorCurso = this.coordenadorCursoDAO.findByCurso(aluno.getCurso().getId());
            aprovacao2.setEmailAprovador(coordenadorCurso.getProfessor().getUsuario().getEmail());
            aprovacao2.setNomeAprovador(coordenadorCurso.getProfessor().getUsuario().getNome());

            Aprovacao aprovacao3 = new Aprovacao();
            aprovacao3.setEmailAprovador(estagioEncontrado.getEmailSupervisor());
            aprovacao3.setNomeAprovador(estagioEncontrado.getNomeSupervisor());

            this.aprovacaoDAO.create(aprovacao1);
            this.aprovacaoDAO.create(aprovacao2);
            this.aprovacaoDAO.create(aprovacao3);

            List<Aprovacao> aprovacoes = new ArrayList<Aprovacao>();
            aprovacoes.add(aprovacao1);
            aprovacoes.add(aprovacao2);
            aprovacoes.add(aprovacao3);

            relatorioFinalAux.setAprovacoes(aprovacoes);

            this.relatorioFinalDAO.create(relatorioFinalAux);

            estagioEncontrado.setRelatorioFinal(relatorioFinalAux);

            this.estagioDAO.update(estagioEncontrado);

            for (Aprovacao aprovacao : aprovacoes) {

                String conteudo =
                        "<html>\n"
                        + "    <head>\n"
                        + "        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n"
                        + "    </head>\n"
                        + "    <body>\n"
                        + "        <h2>Caro(a) " + aprovacao.getNomeAprovador() + ", </h2><br/>"
                        + "        <p>Um pedido de avaliação de Relatório Final de Estágio foi encaminhado por <b>" + estagioEncontrado.getAluno().getUsuario().getNome() + "</b>:</p><br/>"
                        + "        <form id=\"formEnvio\" name=\"formEnvio\" method=\"post\" action=\"http://localhost:8080/Projeto/estagios/relatorioFinal/confirm\">\n"
                        + "            <input type=\"hidden\" name=\"estagio.id\" value=\"" + estagioEncontrado.getId() + "\"/>\n"
                        + "            <input type=\"hidden\" name=\"usuario.email\" value=\"" + aprovacao.getEmailAprovador() + "\"/>\n"
                        + "            <button id=\"acessar\" type=\"submit\">Acesse Aqui.</a>\n"
                        + "        </form>\n"
                        + "    </body>\n"
                        + "</html>";


                this.notificador.enviarEmail(aprovacao.getEmailAprovador(), conteudo, "[Sistema Atividades Extracurriculares] Análise de Relatório Final de Estágio");
//                this.notificador.notifica(aprovacao.getEmailAprovador(), conteudo, "[Sistema Atividades Extracurriculares] Análise de Relatório Final de Estágio", "Um pedido de avaliação de Relatório Final de Estágio foi encaminhado", Boolean.TRUE);

            }

            this.result.include("success", "Relatório Final");
        }

        this.result.redirectTo(EstagioController.class).show(estagio.getId());
    }

    @Put("estagios/relatorio/analise")
    public void analiseRelatorio(Estagio estagio, String analise, Usuario usuario) {
        Estagio estagioEncontrado = this.estagioDAO.findById(estagio.getId());

        if (estagioEncontrado == null) {
            this.validator.add(new ValidationMessage("Desculpe! Não foi encontrado o estágio.", "estagio.id"));
            this.result.include("estagio", estagioEncontrado);
        }

        this.validator.onErrorForwardTo(EstagioController.class).show(estagio.getId());

        List<Aprovacao> aprovacoes = estagioEncontrado.getRelatorioFinal().getAprovacoes();

        Aprovacao aprovacaoAux = null;

        for (Aprovacao aprovacao : aprovacoes) {
            if (aprovacao.getEmailAprovador().equals(usuario.getEmail())) {
                aprovacaoAux = aprovacao;
            }
        }

        if (aprovacaoAux == null) {
            this.validator.add(new ValidationMessage("Desculpe! Você não está cadastrado como aprovador.", "estagio.id"));
            this.result.include("estagio", estagioEncontrado);
        }

        this.validator.onErrorForwardTo(EstagioController.class).show(estagio.getId());

        if (analise.equals("aprovado")) {
            aprovacaoAux.setAprovou(true);
        } else if (analise.equals("reprovado")) {
            aprovacaoAux.setAprovou(false);
        }

        this.aprovacaoDAO.update(aprovacaoAux);

        this.result.include("success", analise);
    }

    @Get("/consulta/estagio")
    public void consulta(Long idPeriodo, Long idCurso, Long idAluno) {
        if (idCurso == null) {
            idCurso = 0L;
        }

        if (idAluno == null) {
            idAluno = 0L;
        }

        Usuario usuario = sessionData.getUsuario();

        if (usuario.getRole() == Perfil.COORDENADOR) {
            CoordenadorCurso coordenadorCurso = this.coordenadorCursoDAO.findByUsuario(usuario.getId());

            idCurso = coordenadorCurso.getCurso().getId();

            List<Estagio> estagios = estagioDAO.search(idCurso, idPeriodo, idAluno);

            List<Aluno> alunos = this.alunoDAO.findByCursoId(coordenadorCurso.getCurso().getId());

            this.result.include("alunoList", alunos);
            this.result.include("estagioList", estagios);

        } else if (usuario.getRole() == Perfil.SECRETARIA) {
            Secretaria secretaria = this.secretariaDAO.findByUsuario(usuario.getId());

            List<Curso> cursos = secretaria.getCursos();

            List<Curso> cursosAnalisaveis = new ArrayList<Curso>();

            if (idCurso == 0) {
                cursosAnalisaveis.addAll(cursos);
            } else {
                for (Curso curso : cursos) {
                    if (curso.getId().equals(idCurso)) {
                        cursosAnalisaveis.add(curso);
                        break;
                    }
                }
            }

            List<Estagio> estagios = estagioDAO.findByCursoOrPeriodoOrAluno(cursosAnalisaveis, idPeriodo, idAluno);

            List<Aluno> alunos = this.alunoDAO.findByCurso(cursos);

            this.result.include("estagioList", estagios);
            this.result.include("cursoList", cursos);
            this.result.include("alunoList", alunos);
        }

        List<PeriodoLetivo> periodosLetivos = periodoLetivoDAO.listLetivoAnterior();

        periodosLetivos.add(0, sessionData.getLetivoAtual());       

        result.include("periodoLetivoList", periodosLetivos);
        this.result.include("idAluno", idAluno);
        this.result.include("idCurso", idCurso);
        this.result.include("idPeriodo", idPeriodo);
    }

    @Get("/consulta/estagio/{id}/show")
    public Estagio detalha(Long id) {
        PeriodoLetivo letivo = sessionData.getLetivoAtual();
        Estagio estagio = this.estagioDAO.findById(id);

        String mes[] = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};

        int mes_inicio = letivo.getDtInicio().getMonth();
        int mes_fim = letivo.getDtFim().getMonth();

        List<FrequenciaMensalEstagio> frequencias = estagio.getFrequenciasMensais();
        List<FrequenciaMensalEstagio> resultado = new ArrayList<FrequenciaMensalEstagio>();

        for (int i = mes_inicio; i <= mes_fim; i++) {
            FrequenciaMensalEstagio freq = findByMesEstagio(i, frequencias);
            if (freq == null) {
                freq = new FrequenciaMensalEstagio();
                freq.setMes(i);
                freq.setStatus(Status.VAZIA);
            } else {
                if (freq.getStatus() == Status.NOVA) {
                    freq.setStatus(Status.PENDENTE);
                }
            }
            resultado.add(freq);
        }

        this.result.include("freqAnalisadas", resultado);
        this.result.include("inicioPeriodo", letivo.getDtInicio());
        this.result.include("fimPeriodo", letivo.getDtFim());

        return estagio;
    }

    private FrequenciaMensalEstagio findByMesEstagio(int mes, List<FrequenciaMensalEstagio> frequencias) {
        for (FrequenciaMensalEstagio frequenciaMensal : frequencias) {
            if (frequenciaMensal.getMes() == mes) {
                return frequenciaMensal;
            }
        }
        return null;
    }

    @Get("/consulta/estagio/{idPeriodo}/{idCurso}/{idAluno}/search")
    public void search(Long idPeriodo, Long idCurso, Long idAluno) {
        this.result.redirectTo(this).consulta(idPeriodo, idCurso, idAluno);
    }
}