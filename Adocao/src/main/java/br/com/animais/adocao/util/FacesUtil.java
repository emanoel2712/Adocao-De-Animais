package br.com.animais.adocao.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class FacesUtil {

	public static void addInfoMessage(String msg) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação !!", msg);
		FacesContext.getCurrentInstance().addMessage(null, message);

	}

	public static void addErrorMessage(String msg) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro !!", msg);
		FacesContext.getCurrentInstance().addMessage(null, message);

	}

	public static void addWarningMessages(String msg) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso !!", msg);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

}
