package br.com.animais.adocao.dao.teste;

import org.junit.jupiter.api.Test;

import br.com.animais.adocao.dao.PessoaDao;
import br.com.animais.adocao.model.Animal;
import br.com.animais.adocao.model.Ong;
import br.com.animais.adocao.model.Pessoa;
import br.com.animais.adocao.model.Usuario;
import br.com.animais.adocao.util.EnviarEmail;

class PessoaDaoTeste {

	private Pessoa pessoa;
	private Animal animal;
	private PessoaDao pessoaDao;

	public PessoaDaoTeste() {
		pessoaDao = new PessoaDao();
	}

	@Test
	public void testeSalvar() {
		/*
		 * pessoa = new Pessoa(); pessoa.setUsuario(new Usuario("bru", "123"));
		 * pessoa.setNome("Ling Young"); pessoa.setTelefone("48 9 9941 1001");
		 * pessoa.setEmail("brunow.gamer@gmail.com");
		 * EnviarEmail.enviarEmailPessoa(pessoa);
		 */
		Ong ong = new Ong("Jogo", "dewde", "24324324", "brunow.gamer@gmail.com", "21323");
		ong.setUsuario(new Usuario("ong@ong.com", "ongong"));
		EnviarEmail.enviarEmailPessoa(ong);

	}

}
