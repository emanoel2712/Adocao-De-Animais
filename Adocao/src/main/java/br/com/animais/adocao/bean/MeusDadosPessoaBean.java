
package br.com.animais.adocao.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.com.animais.adocao.dao.PessoaDao;
import br.com.animais.adocao.dao.UsuarioDao;
import br.com.animais.adocao.model.Pessoa;
import br.com.animais.adocao.model.Usuario;
import br.com.animais.adocao.util.EnviarEmail;
import br.com.animais.adocao.util.FacesUtil;

@ManagedBean
@ViewScoped
public class MeusDadosPessoaBean {

	private Usuario usuario;
	private Pessoa pessoa;

	private PessoaDao pessoaDao;
	private UsuarioDao usuarioDao;

	private String senhaAtual;
	private String novaSenha;

	public void inicializar() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		usuario = (Usuario) session.getAttribute("UsuarioLogado");

		usuarioDao = new UsuarioDao();
		pessoaDao = new PessoaDao();

		pessoaLogada();
	}

	public void pessoaLogada() {
		pessoa = pessoaDao.pessoaUsuario(usuario.getId());
	}

	public void alterarDadosPessoa() {
		pessoaDao.salvar(pessoa);
		usuario.setLogin(pessoa.getEmail());
		usuarioDao.salvar(usuario);

		FacesUtil.addInfoMessage("Alterado com sucesso !!");
	}

	public void redefinirSenha() {
		if (senhaAtual.equals(usuario.getSenha())) {
			usuario.setSenha(novaSenha);
			usuarioDao.salvar(usuario);
			FacesUtil.addInfoMessage("Alterado com sucesso !!");
			limparCampos();
		} else {
			FacesUtil.addErrorMessage("Digite corretamente sua senha atual !!");
			limparCampos();
			return;
		}
	}

	public void enviarEmail() {
		EnviarEmail.enviarEmailPessoa(pessoa);
		FacesUtil.addInfoMessage("Email enviado ao destino: " + pessoa.getEmail());

	}

	public void limparCampos() {
		senhaAtual = "";
		novaSenha = "";
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public String getNovaSenha() {
		return novaSenha;
	}

	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}

	public String getSenhaAtual() {
		return senhaAtual;
	}

	public void setSenhaAtual(String senhaAtual) {
		this.senhaAtual = senhaAtual;
	}
}