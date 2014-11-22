package br.edu.ufam.icomp.projeto4;

import br.com.caelum.vraptor.Convert;
import br.com.caelum.vraptor.Converter;
import br.com.caelum.vraptor.interceptor.multipart.UploadedFile;
import java.util.ResourceBundle;
import javax.servlet.http.HttpServletRequest;

@Convert(UploadedFile.class)
public class UploadedFileConverter implements Converter {

    private final HttpServletRequest request;

    public UploadedFileConverter(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public UploadedFile convert(String value, Class type, ResourceBundle bundle) {
        Object upload = request.getAttribute(value);
        return (upload == null ? null : (UploadedFile) type.cast(upload));
    }
}