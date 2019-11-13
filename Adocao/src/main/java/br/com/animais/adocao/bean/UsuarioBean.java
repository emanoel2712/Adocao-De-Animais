package br.com.animais.adocao.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.com.animais.adocao.dao.OngDao;
import br.com.animais.adocao.dao.PessoaDao;
import br.com.animais.adocao.dao.UsuarioDao;
import br.com.animais.adocao.model.Ong;
import br.com.animais.adocao.model.Pessoa;
import br.com.animais.adocao.model.Usuario;
import br.com.animais.adocao.util.EnviarEmail;
import br.com.animais.adocao.util.FacesUtil;

@ManagedBean
@SessionScoped
public class UsuarioBean {

	private String login;

	private String senha;

	private Usuario usuario;
	private UsuarioDao usuarioDao;

	private String email;

	private String nomeUsuario;
	private String meuCadastro;

	private PessoaDao pessoaDao;
	private OngDao ongDao;

	public UsuarioBean() {
		usuarioDao = new UsuarioDao();
		pessoaDao = new PessoaDao();
		ongDao = new OngDao();
	}

	public String login() {
		usuario = usuarioDao.verificaUsuario(login, senha);
		if (usuario == null) {
			FacesUtil.addErrorMessage("Usuário ou senha invalidos");
			return "";
		} else {
			// criando uma sessao e setando o usuario na sessao
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext()
					.getSession(false);
			session.setAttribute("UsuarioLogado", usuario);
			nomeUsuario();
			login = "";
			senha = "";
			return "/cadastro-adocao?faces-redirect=true";
		}
	}

	public String logout() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

		session.invalidate();
		return "/animais-adocao?faces-redirect=true";

	}

	public String nomeUsuario() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Usuario usuario = (Usuario) session.getAttribute("UsuarioLogado");

		if (ongDao.ongUsuario(usuario.getId()) != null) {
			Ong ong = ongDao.ongUsuario(usuario.getId());
			nomeUsuario = ong.getNome();
			meuCadastro = "/cadastroOngs/dados-cadastro-ong.xhtml";
		} else if (pessoaDao.pessoaUsuario(usuario.getId()) != null) {
			Pessoa pessoa = pessoaDao.pessoaUsuario(usuario.getId());
			nomeUsuario = pessoa.getNome();
			meuCadastro = "/cadastroUsuario/dados-cadastro-pessoa.xhtml";
		}

		return nomeUsuario;
	}

	public void enviarEmail() {
		if (email == "") {
			FacesUtil.addWarningMessages("Digite um email válido !!");
			return;
		}
		Ong ong = ongDao.ongEmail(email);
		Pessoa pessoa = pessoaDao.pessoaEmail(email);

		if (ong != null) {
			EnviarEmail.enviarEmailPessoa(ong);
			FacesUtil.addInfoMessage("Email enviado ao destino: " + email);
		} else if (pessoa != null) {
			EnviarEmail.enviarEmailPessoa(pessoa);
			FacesUtil.addInfoMessage("Email enviado ao destino: " + email);
		} else {
			FacesUtil.addWarningMessages("Não há este email cadastrado no sistema: " + email);
		}
		email = "";

	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getMeuCadastro() {
		return meuCadastro;
	}

	public void setMeuCadastro(String meuCadastro) {
		this.meuCadastro = meuCadastro;
	}

}
