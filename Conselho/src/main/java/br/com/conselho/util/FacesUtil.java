package br.com.conselho.util;

import org.primefaces.context.RequestContext;

public class FacesUtil {
	public static RequestContext getRequestContext() {
		return RequestContext.getCurrentInstance();
	}
}
