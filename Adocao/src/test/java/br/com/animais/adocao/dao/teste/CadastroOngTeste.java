package br.com.animais.adocao.dao.teste;

import java.util.List;

import org.junit.jupiter.api.Test;

import br.com.animais.adocao.dao.OngDao;
import br.com.animais.adocao.model.Endereco;
import br.com.animais.adocao.model.Ong;
import br.com.animais.adocao.model.Usuario;

class CadastroOngTeste {

	private Ong ong;
	private OngDao ongDao;

	public CadastroOngTeste() {
		ongDao = new OngDao();
	}

	@Test
	void testCadastroOng() {
		ong = new Ong();
		Usuario usu = new Usuario();
		usu.setLogin("login");
		usu.setSenha("senha");
		ong.setUsuario(usu);

		Endereco end = new Endereco();
		end.setLogradouro("log");
		end.setNumero("123");
		end.setBairro("Barra");
		end.setCep("89212-128");
		end.setCidade("Floria");
		end.setEstado("SC");

		ong.setEndereco(end);

		ong.setNome("Ação animal");
		ong.setTelefone("(99) 99999-9999");
		ong.setEmail("@@gmail");
		ong.setDescricao("Animais de rua");
		ong.setCnpj("99.999.999/9999-99");
		ongDao.salvar(ong);
	}

	@Test
	void testDeleteOng() {
		List<Ong> ongs = ongDao.buscarTodos();
		for (Ong ong : ongs) {
			System.out.println(ong.getNome());
		}
	}
}
