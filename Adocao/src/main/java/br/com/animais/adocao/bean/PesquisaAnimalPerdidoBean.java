package br.com.animais.adocao.bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import br.com.animais.adocao.dao.AnimalPerdidoDao;
import br.com.animais.adocao.dao.FotoDao;
import br.com.animais.adocao.dao.RacaDao;
import br.com.animais.adocao.model.Animal;
import br.com.animais.adocao.model.Foto;
import br.com.animais.adocao.model.Raca;
import br.com.animais.adocao.util.Constantes;

@ManagedBean
@ViewScoped
public class PesquisaAnimalPerdidoBean {

	private List<Animal> listaAnimal;
	private List<Foto> listaFotos;
	private List<String> listaEstado;
	private AnimalPerdidoDao animalDao;
	private FotoDao fDao;

	// ---------------------------------------
	private Animal animal;
	private Animal animalSelecionado;
	private String nomeUsuario;
	private RacaDao racaDao;
	private List<Raca> racas;
	private List<String> listaTipo;
	private List<String> selectRacas;

	private String telefone;

	public void inicializar() {
		fDao = new FotoDao();
		animalDao = new AnimalPerdidoDao();
		racaDao = new RacaDao();
		animalSelecionado = new Animal();
		animal = new Animal();
		animal.setRaca(new Raca());
		selectRacas = new ArrayList<String>();
		racas = new ArrayList<Raca>();
		listaAnimal = new ArrayList<Animal>();
		listaFotos = new ArrayList<Foto>();

		popularListaEstado();
		popularListaTipo();
		listarAnimais();
		informacaoAnimalOngPessoa();
	}

	private void popularListaEstado() {
		listaEstado = Constantes.listaEstados();

	}

	private void popularListaTipo() {
		listaTipo = new ArrayList<String>();
		listaTipo.add("Cachorro");
		listaTipo.add("Gato");
	}

	public void informacaoAnimalOngPessoa() {
		for (Animal animal : listaAnimal) {

			if (animal.getOng() != null) {
				nomeUsuario = "ONG - " + animal.getOng().getNome();
			} else if (animal.getPessoa() != null) {
				nomeUsuario = "Pessoa física - " + animal.getPessoa().getNome();
			}

		}
	}

	public void configRacas() {
		racas = racaDao.buscarRacaEspecifica(this.animal.getTipoAnimal());
		selectRacas.clear();
		for (Raca raca : racas) {
			selectRacas.add(raca.getNome());
		}
	}

	public void pesquisarAnimais() {

		if (!animal.getTipoAnimal().isEmpty() && !animal.getRaca().getNome().isEmpty()
				&& !animal.getEstado().isEmpty()) {
			listaAnimal = animalDao.buscarAnimaisTodos(animal.getTipoAnimal(), animal.getRaca().getNome(),
					animal.getEstado());

		} else if (!animal.getTipoAnimal().isEmpty() && animal.getRaca().getNome().isEmpty()
				&& animal.getEstado().isEmpty()) {

			listaAnimal = animalDao.buscarAnimaisSomenteTipo(animal.getTipoAnimal());

		} else if (animal.getTipoAnimal().isEmpty() && !animal.getEstado().isEmpty()) {
			listaAnimal = animalDao.buscarAnimaisSomenteEstado(animal.getEstado());

		} else if (!animal.getTipoAnimal().isEmpty() && !animal.getRaca().getNome().isEmpty()
				&& animal.getEstado().isEmpty()) {
			listaAnimal = animalDao.buscarAnimaisTipoRaca(animal.getTipoAnimal(), animal.getRaca().getNome());

		} else if (!animal.getTipoAnimal().isEmpty() && animal.getRaca().getNome().isEmpty()
				&& !animal.getEstado().isEmpty()) {

			listaAnimal = animalDao.buscarAnimaisTipoEstado(animal.getTipoAnimal(), animal.getEstado());

		} else {
			listarAnimais();
			return;
		}

	}

	public void telefoneUsuario() {
		if (animalSelecionado.getOng() != null) {
			telefone = animalSelecionado.getOng().getTelefone();
		} else if (animalSelecionado.getPessoa() != null) {
			telefone = animalSelecionado.getPessoa().getTelefone();
		}

		telefone = telefone.replaceAll("([^0-9])", "");
		telefone = "https://wa.me/55" + telefone;
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		try {
			externalContext.redirect(telefone);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void listarAnimais() {
		listaAnimal = animalDao.buscarAnimalPorStatus("perdido");

		List<Foto> lista = new ArrayList<Foto>();
		for (int i = 0; i < listaAnimal.size(); i++) {

			lista = fDao.buscarListaFotos(listaAnimal.get(i).getId());
			listaAnimal.get(i).setListFotos(lista);
		}

	}

	public List<Animal> getListaAnimal() {
		return listaAnimal;
	}

	public void setListaAnimal(List<Animal> listaAnimal) {
		this.listaAnimal = listaAnimal;
	}

	public List<Foto> getListaFotos() {
		return listaFotos;
	}

	public void setListaFotos(List<Foto> listaFotos) {
		this.listaFotos = listaFotos;
	}

	public List<String> getListaEstado() {
		return listaEstado;
	}

	public void setListaEstado(List<String> listaEstado) {
		this.listaEstado = listaEstado;
	}

	public Animal getAnimal() {
		return animal;
	}

	public void setAnimal(Animal animal) {
		this.animal = animal;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public List<Raca> getRacas() {
		return racas;
	}

	public void setRacas(List<Raca> racas) {
		this.racas = racas;
	}

	public List<String> getListaTipo() {
		return listaTipo;
	}

	public void setListaTipo(List<String> listaTipo) {
		this.listaTipo = listaTipo;
	}

	public List<String> getSelectRacas() {
		return selectRacas;
	}

	public void setSelectRacas(List<String> selectRacas) {
		this.selectRacas = selectRacas;
	}

	public Animal getAnimalSelecionado() {
		return animalSelecionado;
	}

	public void setAnimalSelecionado(Animal animalSelecionado) {
		this.animalSelecionado = animalSelecionado;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
}
