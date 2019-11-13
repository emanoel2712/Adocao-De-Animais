package br.com.animais.adocao.bean;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.animais.adocao.dao.PessoaDao;
import br.com.animais.adocao.model.Pessoa;
import br.com.animais.adocao.model.Usuario;
import br.com.animais.adocao.util.FacesUtil;

@ManagedBean
@ViewScoped
public class CadastroPessoaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Pessoa pessoa;
	private PessoaDao dao;

	public void inicializar() {
		pessoa = new Pessoa();
		pessoa.setUsuario(new Usuario());
		dao = new PessoaDao();

	}

	public void limpar() {
		pessoa = new Pessoa();

	}

	public void salvar() {
		pessoa.getUsuario().setLogin(pessoa.getEmail());
		dao.salvar(pessoa);

		FacesUtil.addInfoMessage("Cadastrado com sucesso");
		limpar();

	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public PessoaDao getDao() {
		return dao;
	}

	public void setDao(PessoaDao dao) {
		this.dao = dao;
	}

}
