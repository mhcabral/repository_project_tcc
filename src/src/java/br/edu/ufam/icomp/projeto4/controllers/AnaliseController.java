package br.edu.ufam.icomp.projeto4.controllers;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Put;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.ValidationMessage;
import br.edu.ufam.icomp.projeto4.SessionData;
import br.edu.ufam.icomp.projeto4.dao.AlunoDAO;
import br.edu.ufam.icomp.projeto4.dao.AtividadeDAO;
import br.edu.ufam.icomp.projeto4.dao.CoordenadorCursoDAO;
import br.edu.ufam.icomp.projeto4.dao.CursoDAO;
import br.edu.ufam.icomp.projeto4.dao.PeriodoLetivoDAO;
import br.edu.ufam.icomp.projeto4.dao.ProfessorDAO;
import br.edu.ufam.icomp.projeto4.dao.RegraDAO;
import br.edu.ufam.icomp.projeto4.dao.RegraGrupoDAO;
import br.edu.ufam.icomp.projeto4.dao.SecretariaDAO;
import br.edu.ufam.icomp.projeto4.dao.SolicitacaoDAO;
import br.edu.ufam.icomp.projeto4.interceptor.Perfil;
import br.edu.ufam.icomp.projeto4.interceptor.Permission;
import br.edu.ufam.icomp.projeto4.model.Aluno;
import br.edu.ufam.icomp.projeto4.model.Anexo;
import br.edu.ufam.icomp.projeto4.model.Atividade;
import br.edu.ufam.icomp.projeto4.model.CoordenadorCurso;
import br.edu.ufam.icomp.projeto4.model.Curso;
import br.edu.ufam.icomp.projeto4.model.Mudanca;
import br.edu.ufam.icomp.projeto4.model.RegraGrupo;
import br.edu.ufam.icomp.projeto4.model.Secretaria;
import br.edu.ufam.icomp.projeto4.model.Solicitacao;
import br.edu.ufam.icomp.projeto4.model.Status;
import br.edu.ufam.icomp.projeto4.model.Usuario;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Bruna
 */
@Resource
public class AnaliseController {

    private SessionData session;
    private Usuario usuario;
    private final SolicitacaoDAO solicitacaoDAO;
    private final Result result;
    private final Validator validator;
    private AtividadeDAO atividadeDAO;
    private AlunoDAO alunoDAO;
    private Anexo pastaDeAnexos;
    private MudancaController mudancaController;
    private RegraDAO regraDAO;
    private ProfessorDAO professorDAO;
    private SecretariaDAO secretariaDAO;
    private CursoDAO cursoDAO;
    private CoordenadorCursoDAO coordenadorCursoDAO;
    private PeriodoLetivoDAO periodoLetivoDAO;
    private RegraGrupoDAO regraGrupoDAO;

    public AnaliseController(SolicitacaoDAO solicitacaoDAO, Result result, Validator validator, AtividadeDAO atividadeDAO,
            MudancaController mudancaController, AlunoDAO alunoDAO, Anexo pastaDeAnexos, RegraDAO regraDAO, ProfessorDAO professorDAO,
            SecretariaDAO secretariaDAO, CursoDAO cursoDAO, SessionData session, CoordenadorCursoDAO coordenadorCursoDAO, 
            PeriodoLetivoDAO periodoLetivoDAO, RegraGrupoDAO regraGrupoDAO) {
        this.solicitacaoDAO = solicitacaoDAO;
        this.atividadeDAO = atividadeDAO;
        this.result = result;
        this.validator = validator;
        this.mudancaController = mudancaController;
        this.alunoDAO = alunoDAO;
        this.pastaDeAnexos = pastaDeAnexos;
        this.regraDAO = regraDAO;
        this.professorDAO = professorDAO;
        this.secretariaDAO = secretariaDAO;
        this.cursoDAO = cursoDAO;
        this.session = session;
        this.usuario = session.getUsuario();
        this.coordenadorCursoDAO = coordenadorCursoDAO;
        this.periodoLetivoDAO = periodoLetivoDAO;
        this.regraGrupoDAO = regraGrupoDAO;
    }

    @Permission({Perfil.COORDENADOR, Perfil.SECRETARIA})
    @Get("/analise")
    public void index(Long idAtividadeAnalisavel, Long idCursoAnalisavel,  Long idAlunoAnalisavel) {       
        if (idCursoAnalisavel == null) {
            idCursoAnalisavel = 0L;
        }

        if (idAtividadeAnalisavel == null) {
            idAtividadeAnalisavel = 0L;
        }

        if (idAlunoAnalisavel == null) {
            idAlunoAnalisavel = 0L;
        }

        if (this.usuario.getRole() == Perfil.COORDENADOR) {
            CoordenadorCurso coordenadorCurso = this.coordenadorCursoDAO.findByUsuario(this.usuario.getId());

            List<Status> statusAnalisaveis = new ArrayList<Status>();
            //statusAnalisaveis.add(Status.NOVA);
            //statusAnalisaveis.add(Status.REENVIADA);
            statusAnalisaveis.add(Status.PREAPROVADA);

            List<Curso> cursosAnalisaveis = new ArrayList<Curso>();            
            
            cursosAnalisaveis.add(coordenadorCurso.getCurso());

            List<Solicitacao> solicitacoesAnalisaveis = this.solicitacaoDAO.findByCursoOrPeriodoOrAtividadeOrAlunoAndStatus(cursosAnalisaveis, 0L, idAtividadeAnalisavel, idAlunoAnalisavel, statusAnalisaveis);
            
            List<Atividade> atividades = this.regraDAO.findAtividadeFromCurso(coordenadorCurso.getCurso().getId());

            List<Aluno> alunos = this.alunoDAO.findByCursoId(coordenadorCurso.getCurso().getId());

            this.result.include("atividadeList", atividades);
            this.result.include("alunoList", alunos);
            this.result.include("solicitacaoAnalisavelList", solicitacoesAnalisaveis);

        } else if (this.usuario.getRole() == Perfil.SECRETARIA) {
            Secretaria secretaria = this.secretariaDAO.findByUsuario(this.usuario.getId());

            List<Status> statusAnalisaveis = new ArrayList<Status>();
            statusAnalisaveis.add(Status.NOVA);
            statusAnalisaveis.add(Status.REENVIADA);

            List<Curso> cursos = secretaria.getCursos();

            List<Curso> cursosAnalisaveis = new ArrayList<Curso>();            

            if (idCursoAnalisavel == 0) {
                cursosAnalisaveis.addAll(cursos);
            } else {
                for (Curso curso : cursos) {
                    if (curso.getId().equals(idCursoAnalisavel)) {
                        cursosAnalisaveis.add(curso);
                        break;
                    }
                }
            }

            List<Solicitacao> solicitacoesAnalisaveis = this.solicitacaoDAO.findByCursoOrPeriodoOrAtividadeOrAlunoAndStatus(cursosAnalisaveis, 0L, idAtividadeAnalisavel, idAlunoAnalisavel, statusAnalisaveis);                       

            List<Atividade> atividades = new ArrayList<Atividade>();

            for (Curso curso : cursos) {
                List<Atividade> atividadesDoCurso = this.regraDAO.findAtividadeFromCurso(curso.getId());

                for (Atividade atividade : atividadesDoCurso) {
                    if (!atividades.contains(atividade)) {
                        atividades.add(atividade);
                    }
                }
            }

            List<Aluno> alunos = this.alunoDAO.findByCurso(cursos);

            this.result.include("atividadeList", atividades);
            this.result.include("cursoList", cursos);
            this.result.include("alunoList", alunos);

            this.result.include("atividadeListAnalisavel", atividades);
            this.result.include("cursoListAnalisavel", cursos);
            this.result.include("alunoListAnalisavel", alunos);

            this.result.include("solicitacaoAnalisavelList", solicitacoesAnalisaveis);            
        }
        
        this.result.include("idAlunoAnalisavel", idAlunoAnalisavel);
        this.result.include("idCursoAnalisavel", idCursoAnalisavel);
        this.result.include("idAtividadeAnalisavel", idAtividadeAnalisavel);       
    }

    @Get("/analise/{idAtividadeAnalisavel}/{idCursoAnalisavel}/{idAlunoAnalisavel}/search")
    public void search(Long idAtividadeAnalisavel, Long idCursoAnalisavel, Long idAlunoAnalisavel) {
        this.result.redirectTo(AnaliseController.class).index(idAtividadeAnalisavel, idCursoAnalisavel, idAlunoAnalisavel);
    }
    
    @Get("/analise/historico/{idAtividade}/{idPeriodoLetivo}/{idCurso}/{idAluno}/search")
    public void search(Long idAtividade, Long idPeriodoLetivo, Long idCurso, Long idAluno){
        this.result.redirectTo(AnaliseController.class).historico(idAtividade, idPeriodoLetivo, idCurso, idAluno);
    }

    @Permission({Perfil.COORDENADOR, Perfil.SECRETARIA})
    @Get("/analise/{id}/show")
    public Solicitacao show(Long id) {
        if (solicitacaoDAO.findById(id) == null) {
            this.result.redirectTo(AnaliseController.class).index(null, null, null);
        }

        Solicitacao solicitacao = this.solicitacaoDAO.findById(id);

        if (!solicitacao.getMudancas().isEmpty()) {
            this.result.include("mudanca", solicitacao.getMudancas().get(solicitacao.getMudancas().size() - 1));
        }

        if (usuario.getPerfil() == Perfil.COORDENADOR.ordinal()) {
            this.result.include("operacao", "Deferir");
            this.result.include("perfil", "COORDENADOR");
        } else {
            this.result.include("operacao", "Aprovar");
            this.result.include("perfil", "SECRETARIA");
        }

        return solicitacao;
    }
    
    @Get("/analise/historico/{id}/show")
    public void analiseHistorico(Long id) {                
        
        if (solicitacaoDAO.findById(id) == null) {
            this.result.redirectTo(AnaliseController.class).historico(null, null, null, null);
        }

        Solicitacao solicitacao = this.solicitacaoDAO.findById(id);

        if (!solicitacao.getMudancas().isEmpty()) {
            this.result.include("mudanca", solicitacao.getMudancas().get(solicitacao.getMudancas().size() - 1));
        }

        if (usuario.getPerfil() == Perfil.COORDENADOR.ordinal()) {
            this.result.include("operacao", "Deferir");
            this.result.include("perfil", "COORDENADOR");
        } else {
            this.result.include("operacao", "Aprovar");
            this.result.include("perfil", "SECRETARIA");
        }

        this.result.include("solicitacao", solicitacao);                                
    }

    @Permission({Perfil.COORDENADOR, Perfil.SECRETARIA})
    @Get("/analise/{id}/defere")
    public void defere(Long id) {
        Solicitacao solicitacao = this.solicitacaoDAO.findById(id);

        Mudanca mudanca;

        if (usuario.getPerfil() == Perfil.COORDENADOR.ordinal()) {
            mudanca = mudancaController.create(usuario, Status.DEFERIDA, "");
            solicitacao.setStatusAtual(Status.DEFERIDA);
        } else {
            mudanca = mudancaController.create(usuario, Status.PREAPROVADA, "");
            solicitacao.setStatusAtual(Status.PREAPROVADA);
        }

        solicitacao.adicionaMudanca(mudanca);

        solicitacao.setHorasComputadas(getHorasComputadas(solicitacao));

        this.solicitacaoDAO.update(solicitacao);

        if (usuario.getPerfil() == Perfil.COORDENADOR.ordinal()) {
            this.result.include("success", "deferida");
        } else {
            this.result.include("success", "aprovada");
        }

        this.result.redirectTo(this).index(null, null, null);
    }        

    @Permission({Perfil.COORDENADOR, Perfil.SECRETARIA})
    @Post("/analise/{id}/indeferir")
    public void indeferir(Long id, Mudanca mudanca) {
        Solicitacao solicitacao = this.solicitacaoDAO.findById(id);

        Mudanca novaMudanca = mudancaController.create(usuario, Status.INDEFERIDA, mudanca.getObservacao());

        solicitacao.adicionaMudanca(novaMudanca);
        solicitacao.setStatusAtual(Status.INDEFERIDA);

        this.solicitacaoDAO.update(solicitacao);

        result.include("success", "indeferida");

        this.result.redirectTo(this).index(null, null, null);
    }

    @Permission({Perfil.COORDENADOR, Perfil.SECRETARIA})
    @Path("/analise/{id}/altera")
    public Solicitacao edit(Long id) {
        Solicitacao solicitacao = this.solicitacaoDAO.findById(id);

        if (solicitacao == null) {
            this.result.redirectTo(SolicitacaoController.class).index(null, null);
        } else if (!(solicitacao.getStatusAtual() == Status.NOVA || solicitacao.getStatusAtual() == Status.REENVIADA || solicitacao.getStatusAtual() == Status.PREAPROVADA)) {
            validator.add(new ValidationMessage("Desculpe! Solicitação não pode ser editada, já foi analisada", "solicitacao.id"));
            result.include("solicitacao", solicitacao);

            this.validator.validate(solicitacao);

            this.validator.onErrorRedirectTo(this).show(id);
        } else {
            Aluno aluno = alunoDAO.findById(solicitacao.getSolicitante().getId());
            Mudanca mudanca = new Mudanca();

            this.result.include("atividadeList", this.regraDAO.findAtividadeFromCurso(solicitacao.getSolicitante().getCurso().getId()));
            this.result.include("aluno", aluno);
            this.result.include("mudanca", mudanca);
            this.result.include("horaMaxima", this.regraDAO.findByAtividadeCurso(solicitacao.getAtividade().getId(), aluno.getCurso().getId()).getMaximoHoras());

            if (usuario.getPerfil() == Perfil.COORDENADOR.ordinal()) {
                this.result.include("operacao", "Deferir");
            } else {
                this.result.include("operacao", "Aprovar");
            }
        }
        return solicitacao;
    }

    @Put("/analise")
    public void altera(Solicitacao solicitacao, Mudanca mudanca) {
        Solicitacao editada = this.solicitacaoDAO.findById(solicitacao.getId());
        editada.setAtividade(atividadeDAO.findById(solicitacao.getAtividade().getId()));
        editada.setQntd_horas(solicitacao.getQntd_horas());

//        Mudanca novaMudanca = mudancaController.create(usuario, Status.INDEFERIDA, mudanca.getObservacao());
//
//        editada.adicionaMudanca(novaMudanca);
//        editada.setStatusAtual(Status.INDEFERIDA);


        if (editada.getAtividade().getId() == null) {
            this.validator.add(new ValidationMessage("Selecione uma Atividade", "solicitacao.atividade.id"));
        }

        this.validator.validate(editada);

        this.validator.onErrorRedirectTo(this).edit(editada.getId());

        this.solicitacaoDAO.update(editada);

        result.include("success", "Solicitação editada com sucesso");

        this.result.redirectTo(this).show(editada.getId());
    }

    private List<Solicitacao> getSolicitacoesAnalisaveis(List<Solicitacao> solicitacoes) {
        List<Solicitacao> analisaveis = new ArrayList<Solicitacao>();
        for (Solicitacao solicitacao : solicitacoes) {
            if (solicitacao.getStatusAtual() == Status.NOVA || solicitacao.getStatusAtual() == Status.REENVIADA) {
                analisaveis.add(solicitacao);
            } else if (solicitacao.getStatusAtual() == Status.PREAPROVADA && usuario.getPerfil() == Perfil.COORDENADOR.ordinal()) {
                analisaveis.add(solicitacao);
            }
        }

        return analisaveis;
    }

    private List<Solicitacao> getSolicitacoesNaoAnalisaveis(List<Solicitacao> solicitacoes) {
        List<Solicitacao> naoAnalisaveis = new ArrayList<Solicitacao>();
        for (Solicitacao solicitacao : solicitacoes) {
            if (solicitacao.getStatusAtual() == Status.INDEFERIDA || solicitacao.getStatusAtual() == Status.DEFERIDA) {
                naoAnalisaveis.add(solicitacao);
            } else if (solicitacao.getStatusAtual() == Status.PREAPROVADA && usuario.getPerfil() == Perfil.SECRETARIA.ordinal()) {
                naoAnalisaveis.add(solicitacao);
            }
        }

        return naoAnalisaveis;
    }

    private int getHorasComputadas(Solicitacao solicitacao) {
        Curso curso = cursoDAO.findById(solicitacao.getSolicitante().getCurso().getId());
        int horasComputadas = (int) solicitacaoDAO.countBySolicitante(solicitacao.getSolicitante().getId(), Status.DEFERIDA);
        RegraGrupo regra = regraGrupoDAO.findByCursoAndGrupo(curso, solicitacao.getAtividade().getGrupo());
        int maxGrupo = regra.getMaximoHoras();
        int countGrupo = (int) solicitacaoDAO.countByGrupo(solicitacao.getSolicitante().getId(), solicitacao.getAtividade().getGrupo().getId(), Status.DEFERIDA);

        int diferenca = maxGrupo - countGrupo;

        int horasGrupo = 0;

        if (solicitacao.getQntd_horas() > diferenca) {
            horasGrupo = diferenca;
        } else {
            horasGrupo = solicitacao.getQntd_horas();
        }

        int diferenca2 = curso.getQntd_horas() - horasComputadas;

        if (horasGrupo <= diferenca2) {
            return horasGrupo;
        } else {
            return diferenca2;
        }
    }

    @Get("/analise/download/{anexo}")
    public File download(String anexo) {

        File file = pastaDeAnexos.mostrar(anexo);

        return file;
    }
    
    @Permission({Perfil.COORDENADOR, Perfil.SECRETARIA})
    @Get("/analise/historico")
    public void historico(Long idAtividade, Long idPeriodoLetivo, Long idCurso, Long idAluno){                                       
        
        if (idAtividade == null) {
            idAtividade = 0L;
        }

        if (idCurso == null) {
            idCurso = 0L;
        }

        if (idAluno == null) {
            idAluno = 0L;
        }

        if (idPeriodoLetivo == null) {
            idPeriodoLetivo = 0L;
        }       

        if (this.usuario.getRole() == Perfil.COORDENADOR) {
            CoordenadorCurso coordenadorCurso = this.coordenadorCursoDAO.findByUsuario(this.usuario.getId());            
            
            List<Curso> cursosNaoAnalisaveis = new ArrayList<Curso>();
            cursosNaoAnalisaveis.add(coordenadorCurso.getCurso());            

            List<Status> statusNaoAnalisaveis = new ArrayList<Status>();
            statusNaoAnalisaveis.add(Status.INDEFERIDA);
            statusNaoAnalisaveis.add(Status.DEFERIDA);
            statusNaoAnalisaveis.add(Status.PREAPROVADA);

            List<Solicitacao> solicitacoesNaoAnalisaveis = this.solicitacaoDAO.findByCursoOrPeriodoOrAtividadeOrAlunoAndStatus(cursosNaoAnalisaveis, idPeriodoLetivo, idAtividade, idAluno, statusNaoAnalisaveis);

            List<Atividade> atividades = this.regraDAO.findAtividadeFromCurso(coordenadorCurso.getCurso().getId());

            List<Aluno> alunos = this.alunoDAO.findByCursoId(coordenadorCurso.getCurso().getId());

            this.result.include("atividadeList", atividades);
            this.result.include("alunoList", alunos);                                                  
            this.result.include("solicitacaoNaoAnalisavelList", solicitacoesNaoAnalisaveis);

        } else if (this.usuario.getRole() == Perfil.SECRETARIA) {
            Secretaria secretaria = this.secretariaDAO.findByUsuario(this.usuario.getId());

            List<Curso> cursos = secretaria.getCursos();
            
            List<Curso> cursosNaoAnalisaveis = new ArrayList<Curso>();

            if (idCurso == 0) {
                cursosNaoAnalisaveis.addAll(cursos);
            } else {
                for (Curso curso : cursos) {
                    if (curso.getId().equals(idCurso)) {
                        cursosNaoAnalisaveis.add(curso);
                        break;
                    }
                }
            }                      

            List<Status> statusNaoAnalisaveis = new ArrayList<Status>();
            statusNaoAnalisaveis.add(Status.INDEFERIDA);
            statusNaoAnalisaveis.add(Status.DEFERIDA);
            statusNaoAnalisaveis.add(Status.PREAPROVADA);

            List<Solicitacao> solicitacoesNaoAnalisaveis = this.solicitacaoDAO.findByCursoOrPeriodoOrAtividadeOrAlunoAndStatus(cursosNaoAnalisaveis, idPeriodoLetivo, idAtividade, idAluno, statusNaoAnalisaveis);

            List<Atividade> atividades = new ArrayList<Atividade>();

            for (Curso curso : cursos) {
                List<Atividade> atividadesDoCurso = this.regraDAO.findAtividadeFromCurso(curso.getId());

                for (Atividade atividade : atividadesDoCurso) {
                    if (!atividades.contains(atividade)) {
                        atividades.add(atividade);
                    }
                }
            }

            List<Aluno> alunos = this.alunoDAO.findByCurso(cursos);

            this.result.include("atividadeList", atividades);
            this.result.include("cursoList", cursos);
            this.result.include("alunoList", alunos);           
            
            this.result.include("solicitacaoNaoAnalisavelList", solicitacoesNaoAnalisaveis);
        }

        this.result.include("idAtividade", idAtividade);
        this.result.include("idPeriodoLetivo", idPeriodoLetivo);
        this.result.include("idCurso", idCurso);
        this.result.include("idAluno", idAluno);
        this.result.include("periodoLetivoList", this.periodoLetivoDAO.findAll());        
    }
}