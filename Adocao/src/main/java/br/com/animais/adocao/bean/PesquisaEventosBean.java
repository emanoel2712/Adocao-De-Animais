package br.com.animais.adocao.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.animais.adocao.dao.EventoDao;
import br.com.animais.adocao.model.Evento;
import br.com.animais.adocao.model.Foto;

@ManagedBean
@ViewScoped
public class PesquisaEventosBean {

	private String nomeEvento;
	private List<Evento> listaEventos;
	private EventoDao eDao;

	private List<Foto> listaFotos;

	public void inicializar() {
		eDao = new EventoDao();
		listaEventos = new ArrayList<Evento>();
		listaFotos = new ArrayList<Foto>();

		listar();
	}

	public void listar() {
		listaEventos = eDao.buscarTodos();

	}

	public void pesquisaEventoNome() {
		listaEventos = eDao.buscarEventoNome(nomeEvento);

	}

	public List<Evento> getListaEventos() {
		return listaEventos;
	}

	public void setListaEventos(List<Evento> listaEventos) {
		this.listaEventos = listaEventos;
	}

	public List<Foto> getListaFotos() {
		return listaFotos;
	}

	public void setListaFotos(List<Foto> listaFotos) {
		this.listaFotos = listaFotos;
	}

	public String getNomeEvento() {
		return nomeEvento;
	}

	public void setNomeEvento(String nomeEvento) {
		this.nomeEvento = nomeEvento;
	}

}
