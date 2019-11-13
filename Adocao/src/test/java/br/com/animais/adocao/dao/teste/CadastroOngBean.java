package br.com.animais.adocao.dao.teste;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import junit.framework.Assert;

class CadastroOngBean {

	private br.com.animais.adocao.bean.CadastroOngBean ongBean; 
	
	@Test
	void testOngBean() {
		ongBean = new br.com.animais.adocao.bean.CadastroOngBean();
		ongBean.inicializar();
		
	}

}
