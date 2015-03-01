package br.edu.ufam.icomp.projeto4.interceptor;

import br.com.caelum.vraptor.InterceptionException;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.core.InterceptorStack;
import br.com.caelum.vraptor.interceptor.Interceptor;
import br.com.caelum.vraptor.ioc.RequestScoped;
import br.com.caelum.vraptor.resource.ResourceMethod;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Janderson
 * Interceptor to check if the user is in the session.
 */
@Intercepts
@RequestScoped
public class NoCacheInterceptor implements Interceptor {


	 private final HttpServletResponse response;

	    public NoCacheInterceptor(HttpServletResponse response) {
	        this.response = response;
	    }

	    public boolean accepts(ResourceMethod method) {
	    	return method.containsAnnotation(NoCache.class);
	    }

	    public void intercept(InterceptorStack stack, ResourceMethod method, 
	                Object resourceInstance)
	        throws InterceptionException {
	        // set the expires to past
	        response.setHeader("Expires", "Wed, 31 Dec 1969 21:00:00 GMT");

	        // no-cache headers for HTTP/1.1
	        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");

	        // no-cache headers for HTTP/1.1 (IE)
	        response.addHeader("Cache-Control", "post-check=0, pre-check=0");

	        // no-cache headers for HTTP/1.0
	        response.setHeader("Pragma", "no-cache");

	        stack.next(method, resourceInstance);
	    }
	}
