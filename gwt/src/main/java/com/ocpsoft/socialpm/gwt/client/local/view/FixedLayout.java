package com.ocpsoft.socialpm.gwt.client.local.view;

import com.google.gwt.user.client.ui.IsWidget;
import com.ocpsoft.socialpm.gwt.client.local.view.component.TopNav;
import com.ocpsoft.socialpm.gwt.client.local.view.presenter.AuthenticationAware;

/**
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 */
public interface FixedLayout extends IsWidget, AuthenticationAware
{
   public TopNav getTopNav();
}
