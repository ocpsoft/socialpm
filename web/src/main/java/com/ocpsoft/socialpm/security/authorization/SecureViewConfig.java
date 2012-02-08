package com.ocpsoft.socialpm.security.authorization;

import org.jboss.seam.faces.rewrite.FacesRedirect;
import org.jboss.seam.faces.rewrite.UrlMapping;
import org.jboss.seam.faces.security.AccessDeniedView;
import org.jboss.seam.faces.security.LoginView;
import org.jboss.seam.faces.view.config.ViewConfig;
import org.jboss.seam.faces.view.config.ViewPattern;

/**
* @author <a href="mailto:bleathem@gmail.com">Brian Leathem</a>
* @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
*/
@ViewConfig
public interface SecureViewConfig {

    static enum Pages {

        @FacesRedirect
        @ViewPattern("/pages/project/view.xhtml")
        @Owner
        ADMIN,

        @FacesRedirect
        @ViewPattern("/*")
        @AccessDeniedView("/pages/404.xhtml")
        @LoginView("/pages/login.xhtml")
        ALL;

    }
}