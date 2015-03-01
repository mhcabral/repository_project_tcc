package br.edu.ufam.icomp.projeto4.controllers;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
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
import br.edu.ufam.icomp.projeto4.dao.InscricaoMonitoriaDAO;
import br.edu.ufam.icomp.projeto4.dao.RelatorioFinalDAO;
import br.edu.ufam.icomp.projeto4.dao.UsuariosDao;
import br.edu.ufam.icomp.projeto4.interceptor.Perfil;
import br.edu.ufam.icomp.projeto4.interceptor.Permission;
import br.edu.ufam.icomp.projeto4.model.Aluno;
import br.edu.ufam.icomp.projeto4.model.Anexo;
import br.edu.ufam.icomp.projeto4.model.Aprovacao;
import br.edu.ufam.icomp.projeto4.model.CoordenadorCurso;
import br.edu.ufam.icomp.projeto4.model.InscricaoMonitoria;
import br.edu.ufam.icomp.projeto4.model.PeriodoLetivo;
import br.edu.ufam.icomp.projeto4.model.RelatorioFinal;
import br.edu.ufam.icomp.projeto4.model.Usuario;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Janderson
 */
@Resource
public class RelatorioFinalController {

    private SessionData session;
    private Result result;
    private Validator validator;
    private InscricaoMonitoriaDAO inscricaoDAO;
    private Anexo pastaDeAnexos;
    private RelatorioFinalDAO relatorioFinalDAO;
    private AprovacaoDAO aprovacaoDAO;
    private CoordenadorCursoDAO coordenadorCursoDAO;
    private AlunoDAO alunoDAO;
    private Notificador notificador;
    private UsuariosDao usuarioDAO;

    public RelatorioFinalController(SessionData session, Result result, Validator validator, InscricaoMonitoriaDAO inscricaoDAO, Anexo pastaDeAnexos, RelatorioFinalDAO relatorioFinalDAO,
            AprovacaoDAO aprovacaoDAO, CoordenadorCursoDAO coordenadorCursoDAO, AlunoDAO alunoDAO, Notificador notificador, UsuariosDao UsuarioDAO) {
        this.session = session;
        this.result = result;
        this.validator = validator;
        this.inscricaoDAO = inscricaoDAO;
        this.pastaDeAnexos = pastaDeAnexos;
        this.relatorioFinalDAO = relatorioFinalDAO;
        this.aprovacaoDAO = aprovacaoDAO;
        this.coordenadorCursoDAO = coordenadorCursoDAO;
        this.alunoDAO = alunoDAO;
        this.notificador = notificador;
        this.usuarioDAO = UsuarioDAO;
    }

    @Permission(Perfil.ALUNO)
    @Get("/inscricoes/relatorioFinal")
    public List<InscricaoMonitoria> index() {

        return inscricaoDAO.search(session.getUsuario().getId());

    }

    @Permission(Perfil.ALUNO)
    @Get("/inscricoes/{id}/relatorioFinal")
    public InscricaoMonitoria show(Long id) {

        PeriodoLetivo periodoAtual = session.getLetivoAtual();

        Date atual = new Date();

        if (periodoAtual.getDtFim().getMonth() != atual.getMonth() || atual.getDate() < 25) {
            this.result.include("bloqueioRF", true);
        }


        return inscricaoDAO.findById(id);
    }

    @Get("/inscricoes/relatorioFinal/{anexo}/download")
    public File download(String anexo) {

        File file = pastaDeAnexos.mostrar(anexo);

        return file;
    }

    @Post("/inscricoes/relatorioFinal/upload")
    public void upload(InscricaoMonitoria inscricaoMonitoria, UploadedFile relatorio) {

        if (!relatorio.getContentType().equals("application/pdf") && !relatorio.getContentType().equals("application/msword")
                && !relatorio.getContentType().equals("application/vnd.oasis.opendocument.text") && !relatorio.getContentType().equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document")
                && !relatorio.getContentType().equals("image/png") && !relatorio.getContentType().equals("image/jpeg")) {
            this.validator.add(new ValidationMessage("A extensão do arquivo [" + relatorio.getFileName() + "] não é aceita", "anexos[]", ""));
        } else {                        
            String nomeAleatorio = pastaDeAnexos.nomeAleatorio();
            pastaDeAnexos.salva(relatorio, nomeAleatorio);

            InscricaoMonitoria inscricaoEncontrada = this.inscricaoDAO.findById(inscricaoMonitoria.getId());

            RelatorioFinal relatorioFinal = new RelatorioFinal();
            relatorioFinal.setRelatorioFinal(nomeAleatorio);

            Aprovacao aprovacao1 = new Aprovacao();
            aprovacao1.setEmailAprovador(inscricaoEncontrada.getMonitoria().getProfessor().getUsuario().getEmail());
            aprovacao1.setNomeAprovador(inscricaoEncontrada.getMonitoria().getProfessor().getUsuario().getNome());

            Aprovacao aprovacao2 = new Aprovacao();
            Aluno aluno = this.alunoDAO.findByIdUsuario(this.session.getUsuario().getId());
            CoordenadorCurso coordenadorCurso = this.coordenadorCursoDAO.findByCurso(aluno.getCurso().getId());
            aprovacao2.setEmailAprovador(coordenadorCurso.getProfessor().getUsuario().getEmail());
            aprovacao2.setNomeAprovador(coordenadorCurso.getProfessor().getUsuario().getNome());

            this.aprovacaoDAO.create(aprovacao1);
            this.aprovacaoDAO.create(aprovacao2);

            List<Aprovacao> aprovacoes = new ArrayList<Aprovacao>();
            aprovacoes.add(aprovacao1);
            aprovacoes.add(aprovacao2);

            relatorioFinal.setAprovacoes(aprovacoes);

            this.relatorioFinalDAO.create(relatorioFinal);

            inscricaoEncontrada.setRelatorioFinal(relatorioFinal);

            this.inscricaoDAO.update(inscricaoEncontrada);

            for (Aprovacao aprovacao : aprovacoes) {

                String conteudo =
                        "<html>\n"
                        + "    <head>\n"
                        + "        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n"
                        + "    </head>\n"
                        + "    <body>\n"
                        + "        <h2>Caro(a) " + aprovacao.getNomeAprovador() + ", </h2><br/>"
                        + "        <p>Um pedido de avaliação de Relatório Final de Monitoria foi encaminhado por <b>" + inscricaoEncontrada.getInscrito().getUsuario().getNome() + "</b>:</p><br/>"
                        + "        <form id=\"formEnvio\" name=\"formEnvio\" method=\"post\" action=\"http://localhost:8080/Projeto/analise/relatorio/aluno\">\n"
                        + "            <input type=\"hidden\" name=\"inscricaoMonitoria.id\" value=\"" + inscricaoEncontrada.getId() + "\"/>\n"
                        + "            <input type=\"hidden\" name=\"usuario.email\" value=\"" + aprovacao.getEmailAprovador() + "\"/>\n"
                        + "            <button id=\"acessar\" type=\"submit\">Acesse Aqui.</a>\n"
                        + "        </form>\n"
                        + "    </body>\n"
                        + "</html>";

                this.notificador.enviarEmail(aprovacao.getEmailAprovador(), conteudo, "[Sistema Atividades Extracurriculares] Análise de Relatório Final de Monitoria");

            }

            this.validator.onErrorRedirectTo(this).show(inscricaoMonitoria.getId());

            this.result.include("success", "Relatório Final");

            this.result.forwardTo(RelatorioFinalController.class).index();
        }
    }

    @Post("/inscricoes/relatorioFinal/analiseAprovador")
    public void analiseRelatorioFinal(InscricaoMonitoria inscricaoMonitoria, Usuario usuario, String analise) {


        System.out.println(inscricaoMonitoria.getId() + " " + usuario.getEmail() + " " + analise);

        InscricaoMonitoria inscricaoMonitoriaEncontrada = this.inscricaoDAO.findById(inscricaoMonitoria.getId());

        List<Aprovacao> aprovacoes = inscricaoMonitoriaEncontrada.getRelatorioFinal().getAprovacoes();

        Boolean aprovou = null;

        if (analise.equals("aprova")) {
            aprovou = true;
        } else if (analise.equals("reprova")) {
            aprovou = false;
        }

        Aprovacao ap = null;

        for (Aprovacao aprovacao : aprovacoes) {

            if (aprovacao.getEmailAprovador().equals(usuario.getEmail())) {
                aprovacao.setAprovou(aprovou);

                ap = aprovacao;
                break;
            }
        }

        if (ap == null) {
            this.validator.add(new ValidationMessage("Desculpe! Você não está cadastro no fluxo de aprovação deste relatório final.", "inscricao.id"));
        } else {
            this.aprovacaoDAO.update(ap);
        }

        this.validator.onErrorRedirectTo(this).index();

        this.result.redirectTo(this).show(inscricaoMonitoria.getId());
    }

    @Post("/analise/relatorio/aluno")
    public void confirm(InscricaoMonitoria inscricaoMonitoria, Usuario usuario) {

        InscricaoMonitoria inscricaoEncontrada = this.inscricaoDAO.findById(inscricaoMonitoria.getId());

        if (inscricaoEncontrada == null) {
            this.validator.add(new ValidationMessage("Desculpe! Não foi encontrado um pedido para análise do relatório final.", "inscricaoMonitoria.id"));

        } else {
            List<Aprovacao> aprovacoes = inscricaoEncontrada.getRelatorioFinal().getAprovacoes();

            boolean achou = false;

            for (Aprovacao aprovacao : aprovacoes) {
                if (aprovacao.getEmailAprovador().equals(usuario.getEmail())) {
                    achou = true;

                    if (aprovacao.getAprovou() != null) {
                        this.validator.add(new ValidationMessage("Desculpe! Você já analisou este relatório final.", "inscricaoMonitoria.id"));
                    }
                    break;
                }
            }

            if (!achou) {
                this.validator.add(new ValidationMessage("Desculpe! Você não está registrado como um aprovador.", "inscricaoMonitoria.id"));
            }
        }
        this.result.include("usuario", usuario);

        this.result.include("inscricaoMonitoria", inscricaoEncontrada);

        this.validator.onErrorForwardTo(RelatorioFinalController.class).analiseRelatorio();
    }

    public void analiseRelatorio() {
    }
}
