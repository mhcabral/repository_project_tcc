package br.edu.ufam.icomp.projeto4.controllers;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.ValidationMessage;
import br.edu.ufam.icomp.projeto4.SessionData;
import br.edu.ufam.icomp.projeto4.dao.CoordenadorAcademicoDAO;
import br.edu.ufam.icomp.projeto4.dao.CursoDAO;
import br.edu.ufam.icomp.projeto4.dao.DisciplinaDAO;
import br.edu.ufam.icomp.projeto4.dao.InscricaoMonitoriaDAO;
import br.edu.ufam.icomp.projeto4.interceptor.Perfil;
import br.edu.ufam.icomp.projeto4.interceptor.Permission;
import br.edu.ufam.icomp.projeto4.model.Anexo;
import br.edu.ufam.icomp.projeto4.model.CoordenadorAcademico;
import br.edu.ufam.icomp.projeto4.model.InscricaoMonitoria;
import br.edu.ufam.icomp.projeto4.model.Monitoria;
import br.edu.ufam.icomp.projeto4.model.Status;
import br.edu.ufam.icomp.projeto4.model.Usuario;
import java.io.File;
import java.util.List;

/**
 *
 * @author Janderson
 */
@Resource
public class AnaliseInscricaoController {

    private SessionData session;
    private Result result;
    private Validator validator;
    private InscricaoMonitoriaDAO inscricaoDAO;
    private CursoDAO cursoDAO;
    private DisciplinaDAO disciplinaDAO;
    private Anexo pastaDeAnexos;
    private CoordenadorAcademicoDAO coordenadorAcademicoDAO;
    private Usuario usuario;

    public AnaliseInscricaoController(SessionData session, Result result, Validator validator, InscricaoMonitoriaDAO inscricaoDAO, CursoDAO cursoDAO, DisciplinaDAO disciplinaDAO, Anexo pastaDeAnexos, CoordenadorAcademicoDAO coordenadorAcademicoDAO) {
        this.session = session;
        this.result = result;
        this.validator = validator;
        this.inscricaoDAO = inscricaoDAO;
        this.cursoDAO = cursoDAO;
        this.disciplinaDAO = disciplinaDAO;
        this.pastaDeAnexos = pastaDeAnexos;
        this.coordenadorAcademicoDAO = coordenadorAcademicoDAO;
        this.usuario = this.session.getUsuario();
    }

    @Permission(Perfil.COORDENADORACAD)
    @Get("/analiseInscricao")
    public List<InscricaoMonitoria> index(Long idCurso, Long idDisciplina) {

        CoordenadorAcademico coordenadorAcademico = this.coordenadorAcademicoDAO.findByUsuario(this.usuario.getId());

        result.include("cursoList", coordenadorAcademico.getCursos());
        result.include("disciplinaList", this.disciplinaDAO.findAll());

        result.include("idCurso", idCurso);
        result.include("idDisciplina", idDisciplina);

        if (idCurso == null) {

            idCurso = 0L;
        }

        if (idDisciplina == null) {

            idDisciplina = 0L;
        }

        List<InscricaoMonitoria> listInscricao = inscricaoDAO.search(idCurso, idDisciplina);

        if ((idCurso > 0) && (idDisciplina > 0)) {

            if (!listInscricao.isEmpty()) {

                InscricaoMonitoria umaInscricao = listInscricao.get(0);
                Monitoria monitoria = umaInscricao.getMonitoria();
                result.include("monitoria", monitoria);
            }
        }

        return listInscricao;
    }

    @Permission(Perfil.COORDENADORACAD)
    @Get("/analiseInscricao/{idCurso}/{idDisciplina}/search")
    public void search(Long idCurso, Long idDisciplina) {

        this.result.forwardTo(this).index(idCurso, idDisciplina);

    }

    @Permission(Perfil.COORDENADORACAD)
    @Get("/analiseInscricao/{id}/show")
    public InscricaoMonitoria show(Long id) {

        return inscricaoDAO.findById(id);
    }

    @Permission(Perfil.COORDENADORACAD)
    @Get("/analiseInscricao/{id}/{bolsista}/defere")
    public void defere(Long id, Boolean bolsista) {
        InscricaoMonitoria inscricao = inscricaoDAO.findById(id);

        inscricao.setBolsista(bolsista);

        if (inscricao == null) {
            validator.add(new ValidationMessage("Inscrição não encontrada!", "inscricao.id"));
        }

        validator.validate(inscricao);
        validator.onErrorForwardTo(this).index(null, null);

        inscricao.setStatusAtual(Status.DEFERIDA);

        inscricaoDAO.update(inscricao);

        this.result.include("success", "deferida");

        this.result.redirectTo(this).index(null, null);
    }

    @Permission(Perfil.COORDENADORACAD)
    @Get("/analiseInscricao/{id}/indefere")
    public void indefere(Long id) {

        InscricaoMonitoria inscricao = inscricaoDAO.findById(id);

        if (inscricao == null) {

            validator.add(new ValidationMessage("Inscrição não encontrada!", "inscricao.id"));

        }

        validator.validate(inscricao);
        validator.onErrorForwardTo(this).index(null, null);

        inscricao.setStatusAtual(Status.INDEFERIDA);

        inscricaoDAO.update(inscricao);

        this.result.include("success", "indeferida");

        this.result.redirectTo(this).index(null, null);

    }

    @Get("/analiseInscricao/download/{anexo}")
    public File download(String anexo) {

        File file = pastaDeAnexos.mostrar(anexo);

        return file;
    }
}