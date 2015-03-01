/***
 * Copyright (c) 2009 Caelum - www.caelum.com.br/opensource
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package br.edu.ufam.icomp.projeto4;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.edu.ufam.icomp.projeto4.interceptor.Perfil;
import br.edu.ufam.icomp.projeto4.interceptor.Permission;

@Resource
public class IndexController {

    private final Result result;        
    private final SessionData session;        
    private final Notificador notificador;

    public IndexController(Result result, SessionData session, Notificador notificador) {
        this.result = result;
        this.session = session;
        this.notificador = notificador;
    }
    
    @Get("/")
    @Permission({ Perfil.ALUNO, Perfil.COORDENADOR, Perfil.PROFESSOR, Perfil.SECRETARIA, Perfil.ROOT, Perfil.COORDENADORACAD})
    public void index() {
        result.include("notificacaoList", notificador.getNotificacaoList());
    }		
    
    /**
     *
     * @param id
     */
    @Post("/{id}/rticket")
    @Permission({ Perfil.ALUNO, Perfil.COORDENADOR, Perfil.SECRETARIA, Perfil.ROOT, Perfil.COORDENADORACAD})
    public void removeNotificacao (Long id){        
        
        notificador.removeNotificacao(id);
        result.forwardTo(this).index();
                
      
    }    
    
}