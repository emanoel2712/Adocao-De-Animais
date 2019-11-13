
package br.com.animais.adocao.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.com.animais.adocao.dao.AnimalDao;
import br.com.animais.adocao.dao.AnimalPerdidoDao;
import br.com.animais.adocao.dao.OngDao;
import br.com.animais.adocao.dao.PessoaDao;
import br.com.animais.adocao.model.Animal;
import br.com.animais.adocao.model.Ong;
import br.com.animais.adocao.model.Pessoa;
import br.com.animais.adocao.model.Usuario;

@ManagedBean
@ViewScoped
public class MeusAnunciosBean {

	private Usuario usuario;
	private Animal animal;

	private List<Animal> listAnimal;
	private List<Animal> listAnimalPerdidos;
	private List<Animal> listAnimalAdocao;
	private List<Animal> listAnimalExcluido;

	private OngDao ongDao;
	private PessoaDao pessoaDao;
	private AnimalDao animalDao;
	private AnimalPerdidoDao animalPerdidoDao;

	private int numeroPublicacoes;
	private int numeroPerdidos;
	private int numeroAdocao;
	private int numeroExcluido;

	// ---------------------------------------------------

	public void inicializar() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		usuario = (Usuario) session.getAttribute("UsuarioLogado");
		animal = new Animal();
		listAnimal = new ArrayList<Animal>();
		listAnimalPerdidos = new ArrayList<Animal>();
		listAnimalAdocao = new ArrayList<Animal>();
		listAnimalExcluido = new ArrayList<Animal>();

		ongDao = new OngDao();
		pessoaDao = new PessoaDao();
		animalDao = new AnimalDao();
		animalPerdidoDao = new AnimalPerdidoDao();

		dadosUsuario();
	}

	public void dadosUsuario() {
		if (ongDao.ongUsuario(usuario.getId()) != null) {
			Ong ong = ongDao.ongUsuario(usuario.getId());
			animal.setOng(ong);
			listAnimal = animalDao.buscarAnimaisOngAdocao(ong.getId());
			listAnimalPerdidos = animalPerdidoDao.buscarAnimaisOngPerdido(ong.getId());
			listAnimalAdocao = animalDao.buscarAnimaisOngProcessoAdocao(ong.getId());
			listAnimalExcluido = animalDao.buscarAnimaisOngExcluido(ong.getId());

		} else if (pessoaDao.pessoaUsuario(usuario.getId()) != null) {
			Pessoa pessoa = pessoaDao.pessoaUsuario(usuario.getId());
			animal.setPessoa(pessoa);
			listAnimal = animalDao.buscarAnimaisPessoaAdocao(pessoa.getId());
			listAnimalPerdidos = animalPerdidoDao.buscarAnimaisPessoaPerdido(pessoa.getId());
			listAnimalAdocao = animalDao.buscarAnimaisPessoaProcessoAdocao(pessoa.getId());
			listAnimalExcluido = animalDao.buscarAnimaisPessoaExcluido(pessoa.getId());

		} else {
			return;
		}
		numeroPerdidos();
		numeroPublicados();
		numeroAdocao();
		numeroExcluidos();
	}

	public void numeroPublicados() {
		numeroPublicacoes = listAnimal.size();
	}

	public void numeroPerdidos() {
		numeroPerdidos = listAnimalPerdidos.size();
	}

	public void numeroAdocao() {
		numeroAdocao = listAnimalAdocao.size();
	}

	public void numeroExcluidos() {
		numeroExcluido = listAnimalExcluido.size();
	}

	public void confirmarAdocao() {
		animal.setStatus("Adotado");
		animalDao.salvar(animal);
		dadosUsuario();
	}

	public void cancelarAdocao() {
		animal.setStatus("adocao");
		animalDao.salvar(animal);
		dadosUsuario();
	}

	public void adicionarListExcluidos() {
		animal.setStatus("excluido");
		animalDao.salvar(animal);
		dadosUsuario();
	}

	public void excluir() {
		animalDao.excluir(animal);
		dadosUsuario();
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Animal> getListAnimal() {
		return listAnimal;
	}

	public void setListAnimal(List<Animal> listAnimal) {
		this.listAnimal = listAnimal;
	}

	public List<Animal> getListAnimalPerdidos() {
		return listAnimalPerdidos;
	}

	public void setListAnimalPerdidos(List<Animal> listAnimalPerdidos) {
		this.listAnimalPerdidos = listAnimalPerdidos;
	}

	public int getNumeroPublicacoes() {
		return numeroPublicacoes;
	}

	public void setNumeroPublicacoes(int numeroPublicacoes) {
		this.numeroPublicacoes = numeroPublicacoes;
	}

	public int getNumeroPerdidos() {
		return numeroPerdidos;
	}

	public void setNumeroPerdidos(int numeroPerdidos) {
		this.numeroPerdidos = numeroPerdidos;
	}

	public int getNumeroAdocao() {
		return numeroAdocao;
	}

	public void setNumeroAdocao(int numeroAdocao) {
		this.numeroAdocao = numeroAdocao;
	}

	public List<Animal> getListAnimalAdocao() {
		return listAnimalAdocao;
	}

	public void setListAnimalAdocao(List<Animal> listAnimalAdocao) {
		this.listAnimalAdocao = listAnimalAdocao;
	}

	public List<Animal> getListAnimalExcluido() {
		return listAnimalExcluido;
	}

	public void setListAnimalExcluido(List<Animal> listAnimalExcluido) {
		this.listAnimalExcluido = listAnimalExcluido;
	}

	public int getNumeroExcluido() {
		return numeroExcluido;
	}

	public void setNumeroExcluido(int numeroExcluido) {
		this.numeroExcluido = numeroExcluido;
	}

	public Animal getAnimal() {
		return animal;
	}

	public void setAnimal(Animal animal) {
		this.animal = animal;
	}
}