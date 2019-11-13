package br.com.animais.adocao.util;

import javax.persistence.Persistence;

public class GeradorDeTabelas {

	public static void main(String[] args) {
		Persistence.createEntityManagerFactory("AdocaoAnimaisPU");
	}

}
