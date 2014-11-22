/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ufam.icomp.projeto4.interceptor;

import br.com.caelum.vraptor.interceptor.multipart.DefaultMultipartConfig;
import br.com.caelum.vraptor.ioc.ApplicationScoped;
import br.com.caelum.vraptor.ioc.Component;

/**
 *
 * @author Thiago Santos
 */
@Component
@ApplicationScoped
public class CustomMultipartConfig extends DefaultMultipartConfig {

    @Override
    public long getSizeLimit() {
        return 10 * 1024 * 1024;
    }       
}
