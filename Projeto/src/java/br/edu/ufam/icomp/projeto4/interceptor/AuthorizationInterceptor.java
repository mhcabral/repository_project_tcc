package br.edu.ufam.icomp.projeto4.interceptor;

import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.core.InterceptorStack;
import br.com.caelum.vraptor.interceptor.Interceptor;
import br.com.caelum.vraptor.resource.ResourceMethod;
import br.edu.ufam.icomp.projeto4.IndexController;
import br.edu.ufam.icomp.projeto4.SessionData;
import br.edu.ufam.icomp.projeto4.controllers.UsuariosController;
import java.util.Arrays;
import java.util.Collection;


/**
 * @author Janderson
 * Interceptor to check if the user is in the session.
 */
@Intercepts
public class AuthorizationInterceptor implements Interceptor {

    private final Result result;
    private final SessionData info;

    public AuthorizationInterceptor(SessionData info, Result result) {
        this.info = info;
        this.result = result;
    }

    @Override
    public boolean accepts(ResourceMethod method) {
        return !(method.getMethod().isAnnotationPresent(Public.class)
                || method.getResource().getType().isAnnotationPresent(Public.class));
    }

    @Override
    public void intercept(InterceptorStack stack, ResourceMethod method, Object resource) {
        Permission methodPermission = method.getMethod().getAnnotation(Permission.class);
        Permission controllerPermission = method.getResource().getType().getAnnotation(Permission.class);

        if (this.hasAccess(methodPermission) && this.hasAccess(controllerPermission)) {
            stack.next(method, resource);
        } else {
            if (info.getLogado()) {
                result.redirectTo(IndexController.class).index();
            } else {
                result.redirectTo(UsuariosController.class).loginForm();
            }
        }
    }

    private boolean hasAccess(Permission permission) {
        if (permission == null) {
            return true;
        }

        Collection<Perfil> perfilList = Arrays.asList(permission.value());

        if (info.getLogado()) {

            return perfilList.contains(info.getUsuario().getRole());

        } else {

            return false;
        }
    }
}
