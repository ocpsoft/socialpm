package com.ocpsoft.socialpm.gwt.client.local.history;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.impl.HistoryImpl;

/**
 * History implementation based on pushState
 */
public class HistoryStateImpl extends HistoryImpl
{
   public static String getContextPath()
   {
      return GWT.getHostPageBaseURL().replaceFirst("[^/]+://[^/]+/", "/");
   }

   @Override
   public boolean init()
   {
      return initNative();
   }

   public native boolean initNative() /*-{
		var token = '';
		var historyImpl = this;

		var path = $wnd.location.pathname;
		if (path.length > 0) {
			token = historyImpl.@com.google.gwt.user.client.impl.HistoryImpl::decodeFragment(Ljava/lang/String;)(path);
		}

		@com.google.gwt.user.client.impl.HistoryImpl::setToken(Ljava/lang/String;)(token);

		var oldHandler = $wnd.history.onpopstate;

		$wnd.onpopstate = $entry(function() {
			var token = '';
			var path = $wnd.location.pathname;
			if (path.length > 0) {
				token = historyImpl.@com.google.gwt.user.client.impl.HistoryImpl::decodeFragment(Ljava/lang/String;)(path);
				token = historyImpl.@com.ocpsoft.socialpm.gwt.client.local.history.HistoryStateImpl::cleanToken(Ljava/lang/String;)(token);
			}

			historyImpl.@com.google.gwt.user.client.impl.HistoryImpl::newItemOnEvent(Ljava/lang/String;)(token);

			if (oldHandler) {
				oldHandler();
			}
		});

		return true;
   }-*/;

   protected String cleanToken(String historyToken)
   {
      String contextPath = HistoryStateImpl.getContextPath();
      if (!contextPath.equals(historyToken) && historyToken.startsWith(contextPath))
         historyToken = historyToken.substring(contextPath.length());
      return historyToken;
   }

   @Override
   protected void nativeUpdate(String historyToken)
   {
      String contextPath = HistoryStateImpl.getContextPath();
      if (!contextPath.equals(historyToken))
         historyToken = contextPath + historyToken;
      update(historyToken);
   }

   protected native void update(String historyToken) /*-{
		var encodedToken = this.@com.google.gwt.user.client.impl.HistoryImpl::encodeFragment(Ljava/lang/String;)(historyToken);
		$wnd.history.pushState(encodedToken, $wnd.document.title, encodedToken);
   }-*/;

}