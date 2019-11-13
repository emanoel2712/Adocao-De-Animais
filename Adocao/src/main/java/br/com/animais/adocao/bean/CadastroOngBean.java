package br.com.animais.adocao.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import br.com.animais.adocao.dao.OngDao;
import br.com.animais.adocao.model.Endereco;
import br.com.animais.adocao.model.Ong;
import br.com.animais.adocao.model.Usuario;
import br.com.animais.adocao.util.Constantes;
import br.com.animais.adocao.util.FacesUtil;

@ManagedBean
@ViewScoped
public class CadastroOngBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private Ong ong;
	private OngDao ongDao;
	private List<Ong> listaOngs;

	private List<String> listaEstados;

	public void inicializar() {
		ong = new Ong();
		ong.setEndereco(new Endereco());
		ong.setUsuario(new Usuario());
		ongDao = new OngDao();
		listaOngs = new ArrayList<Ong>();
		popularListaEstado();

	}

	private void popularListaEstado() {
		listaEstados = Constantes.listaEstados();

	}

	public void salvar() {
		ong.getUsuario().setLogin(ong.getEmail());
		ongDao.salvar(ong);
		listaOngs.add(ong);
		FacesUtil.addInfoMessage("Cadastrado com sucesso");
		limpar();
	}

	private void limpar() {
		ong = new Ong();
	}

	public Ong getOng() {
		return ong;
	}

	public void setOng(Ong ong) {
		this.ong = ong;
	}

	public OngDao getOngDao() {
		return ongDao;
	}

	public void setOngDao(OngDao ongDao) {
		this.ongDao = ongDao;
	}

	public List<Ong> getListaOngs() {
		return listaOngs;
	}

	public void setListaOngs(List<Ong> listaOngs) {
		this.listaOngs = listaOngs;
	}

	public List<String> getListaEstados() {
		return listaEstados;
	}

	public void setListaEstados(List<String> listaEstados) {
		this.listaEstados = listaEstados;
	}
}
