/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ufam.icomp.projeto4.model;

import br.com.caelum.vraptor.interceptor.multipart.UploadedFile;
import br.com.caelum.vraptor.ioc.Component;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import javax.servlet.ServletContext;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author Thiago Santos
 */
@Component
public class Anexo {

    private File pastaImagens;

    public Anexo(ServletContext context) {
        String caminhoImagens = context.getRealPath("/anexos");
        pastaImagens = new File(caminhoImagens);
        pastaImagens.mkdir();
    }

    public void salva(UploadedFile anexo, String nomeAnexo) {

        File destino = new File(pastaImagens, nomeAnexo);

        try {
            IOUtils.copy(anexo.getFile(), new FileOutputStream(destino));
        } catch (IOException e) {
            throw new RuntimeException("Erro ao copiar imagem", e);
        }
    }

    public File mostrar(String fileName) {
        return new File(pastaImagens + "/" + fileName);
    }

    public String nomeAleatorio() {
        Random random = new Random();

        String nome = "";

        int numeroAleatorio = random.nextInt(999999999);

        nome += numeroAleatorio + "_";

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd_MM_yyyy");

        nome += simpleDateFormat.format(new Date());
        
        return nome;
    }
}
