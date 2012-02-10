package com.ocpsoft.socialpm.gwt.client.local.history;

import com.google.gwt.user.client.impl.HistoryImpl;

/**
 * History implementation based on pushState
 */
public class HistoryStateImpl extends HistoryImpl {

	public native boolean init() /*-{
		var token = '';
	
		var path = $wnd.location.pathname;
		if (path.length > 0) {
			token = this.@com.google.gwt.user.client.impl.HistoryImpl::decodeFragment(Ljava/lang/String;)(path);
		}
	
		@com.google.gwt.user.client.impl.HistoryImpl::setToken(Ljava/lang/String;)(token);
	
		var historyImpl = this;
	
		var oldHandler = $wnd.history.onpopstate;
	
		$wnd.onpopstate = $entry(function() {
			var token = '';
			var path = $wnd.location.pathname;
			if (path.length > 0) {
				token = historyImpl.@com.google.gwt.user.client.impl.HistoryImpl::decodeFragment(Ljava/lang/String;)(path);
			}
	
			historyImpl.@com.google.gwt.user.client.impl.HistoryImpl::newItemOnEvent(Ljava/lang/String;)(token);
		
			if (oldHandler) {
				oldHandler();
			}
		});
	
		return true;
	}-*/;

	protected native void nativeUpdate(String historyToken) /*-{
		var encodedToken =
		this.@com.google.gwt.user.client.impl.HistoryImpl::encodeFragment(Ljava/lang/String;)(historyToken);
		$wnd.history.pushState(encodedToken, $wnd.document.title, encodedToken);
	}-*/;

}