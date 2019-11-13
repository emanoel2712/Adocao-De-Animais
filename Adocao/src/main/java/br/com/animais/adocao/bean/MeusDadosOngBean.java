
package br.com.animais.adocao.bean;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.com.animais.adocao.dao.OngDao;
import br.com.animais.adocao.dao.UsuarioDao;
import br.com.animais.adocao.model.Ong;
import br.com.animais.adocao.model.Usuario;
import br.com.animais.adocao.util.Constantes;
import br.com.animais.adocao.util.EnviarEmail;
import br.com.animais.adocao.util.FacesUtil;

@ManagedBean
@ViewScoped
public class MeusDadosOngBean {

	private Usuario usuario;
	private Ong ong;

	private OngDao ongDao;
	private UsuarioDao usuarioDao;

	private List<String> listaEstados;

	private String senhaAtual;
	private String novaSenha;

	public void inicializar() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		usuario = (Usuario) session.getAttribute("UsuarioLogado");

		usuarioDao = new UsuarioDao();
		ongDao = new OngDao();

		ongLogada();
		popularEstados();
	}

	public void ongLogada() {
		ong = ongDao.ongUsuario(usuario.getId());
	}

	public void alterarDadosOng() {
		ongDao.salvar(ong);
		usuario.setLogin(ong.getEmail());
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
		EnviarEmail.enviarEmailPessoa(ong);
		FacesUtil.addInfoMessage("Email enviado ao destino: " + ong.getEmail());

	}

	public void popularEstados() {
		listaEstados = Constantes.listaEstados();
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

	public Ong getOng() {
		return ong;
	}

	public void setOng(Ong ong) {
		this.ong = ong;
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

	public List<String> getListaEstados() {
		return listaEstados;
	}

	public void setListaEstados(List<String> listaEstados) {
		this.listaEstados = listaEstados;
	}
}