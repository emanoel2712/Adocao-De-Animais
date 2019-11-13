package br.com.animais.adocao.dao.teste;

import java.util.Date;

import org.junit.jupiter.api.Test;

import br.com.animais.adocao.dao.OngDao;
import br.com.animais.adocao.model.Endereco;
import br.com.animais.adocao.model.Ong;
import br.com.animais.adocao.model.Usuario;

class TesteCadastroOng {

	@Test
	void testCadastroOng() {
		Ong ong = new Ong();
		Usuario usuario = new Usuario();
		Endereco endereco = new Endereco();

		usuario.setSenha("123");

		endereco.setLogradouro("ed");
		endereco.setBairro("d");
		endereco.setCep("323");
		endereco.setCidade("pa");
		endereco.setEstado("esr");
		endereco.setNumero("232");

		ong.setUsuario(usuario);
		ong.setEndereco(endereco);

		ong.setCnpj("99.999.999/9999-99");
		ong.setDescricao("de");
		ong.setEmail("bru@gmail");
		usuario.setLogin(ong.getEmail());
		ong.setNome("ONG");
		ong.setTelefone("(99) 99999-9999");

		OngDao dao = new OngDao();
		dao.salvar(ong);
	}

}
