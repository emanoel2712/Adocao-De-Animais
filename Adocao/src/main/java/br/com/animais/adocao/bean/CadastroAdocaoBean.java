package br.com.animais.adocao.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import org.primefaces.event.FileUploadEvent;

import br.com.animais.adocao.dao.AnimalDao;
import br.com.animais.adocao.dao.OngDao;
import br.com.animais.adocao.dao.PessoaDao;
import br.com.animais.adocao.dao.RacaDao;
import br.com.animais.adocao.model.Animal;
import br.com.animais.adocao.model.Foto;
import br.com.animais.adocao.model.Raca;
import br.com.animais.adocao.model.Usuario;
import br.com.animais.adocao.util.Constantes;
import br.com.animais.adocao.util.FacesUtil;

import java.io.FileOutputStream;

@ManagedBean
@ViewScoped
public class CadastroAdocaoBean {

	private Animal animal;
	private Raca raca;
	private Foto foto;

	private AnimalDao animalDao;
	private RacaDao racaDao;
	private OngDao oDao;
	private PessoaDao pessoaDao;

	private String nomeArquivoSaida;

	private List<Raca> racas;
	private List<SelectItem> selectItemRacas;
	private List<Foto> listFotos;
	private List<String> listaAnimais;
	private List<String> listaEstados;
	private List<String> tipoSexo;
	private String tipoAnimal;

	public void inicializar() {
		if (animal == null) {
			animal = new Animal();
			foto = new Foto();

		}

		raca = new Raca();
		animalDao = new AnimalDao();
		racaDao = new RacaDao();
		oDao = new OngDao();
		pessoaDao = new PessoaDao();

		listFotos = new ArrayList<>();
		racas = new ArrayList<>();

		popularAnimalTipo();
		addTipoSexo();
		popularEstados();

	}

	public void limpar() {
		animal = new Animal();
		listFotos.clear();
	}

	public void popularEstados() {
		listaEstados = Constantes.listaEstados();
	}

	public void addTipoSexo() {
		tipoSexo = new ArrayList<String>();
		tipoSexo.add("Macho");
		tipoSexo.add("Fêmea");
	}

	public void popularAnimalTipo() {
		listaAnimais = new ArrayList<String>();
		listaAnimais.add("Cachorro");
		listaAnimais.add("Gato");
	}

	public void configRacas() {
		racas = racaDao.buscarRacaEspecifica(tipoAnimal);

		selectItemRacas = new ArrayList<SelectItem>();
		for (Raca raca : racas) {
			selectItemRacas.add(new SelectItem(raca.getId(), raca.getNome()));
		}
	}

	public void editar() {
		animal.setRaca(raca);
		animal.setTipoAnimal(tipoAnimal);
		animalDao.salvar(animal);
		FacesUtil.addInfoMessage("Editado com sucesso !!");
		limpar();

	}

	public void salvarAnimal() {

		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Usuario usuario = (Usuario) session.getAttribute("UsuarioLogado");

		if (oDao.ongUsuario(usuario.getId()) != null) {
			animal.setOng(oDao.ongUsuario(usuario.getId()));

		} else if (pessoaDao.pessoaUsuario(usuario.getId()) != null) {
			animal.setPessoa(pessoaDao.pessoaUsuario(usuario.getId()));
		} else {
			return;
		}
		animal.setRaca(raca);
		animal.setTipoAnimal(tipoAnimal);
		animal.setListFotos(listFotos);
		for (Foto foto : listFotos) {
			foto.setAnimal(animal);
		}
		animalDao.salvar(animal);
		FacesUtil.addInfoMessage("Animal salvo com sucesso");
		limpar();

	}

	public void handleFileUpload(FileUploadEvent event) {
		try {
			byte[] arquivo = event.getFile().getContents();

			// pra salvar a foto com o nome do caminho
			nomeArquivoSaida = "C:\\Users\\Bruno\\Downloads\\Faculdade\\Adocao\\src\\main\\webapp\\resources\\imagens\\"
					+ event.getFile().getFileName();

			// esse trecho grava o arquivo no diretório
			FileOutputStream fos = new FileOutputStream(nomeArquivoSaida);
			fos.write(arquivo);
			fos.close();

			nomeArquivoSaida = event.getFile().getFileName();
			foto = new Foto();
			foto.setCaminho(nomeArquivoSaida);
			listFotos.add(foto);

			System.out.println("caminho da imagem salva é  = " + nomeArquivoSaida);

		} catch (Exception e) {
			e.printStackTrace();
		}
		FacesMessage msg = new FacesMessage("Upload Concluido com Sucesso!");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public Animal getAnimal() {
		return animal;
	}

	public void setAnimal(Animal animal) {
		this.animal = animal;
	}

	public Raca getRaca() {
		return raca;
	}

	public void setRaca(Raca raca) {
		this.raca = raca;
	}

	public Foto getFoto() {
		return foto;
	}

	public void setFoto(Foto foto) {
		this.foto = foto;
	}

	public String getNomeArquivoSaida() {
		return nomeArquivoSaida;
	}

	public void setNomeArquivoSaida(String nomeArquivoSaida) {
		this.nomeArquivoSaida = nomeArquivoSaida;
	}

	public List<Raca> getRacas() {
		return racas;
	}

	public void setRacas(List<Raca> racas) {
		this.racas = racas;
	}

	public List<SelectItem> getSelectItemRacas() {
		return selectItemRacas;
	}

	public void setSelectItemRacas(List<SelectItem> selectItemRacas) {
		this.selectItemRacas = selectItemRacas;
	}

	public List<Foto> getListFotos() {
		return listFotos;
	}

	public void setListFotos(List<Foto> listFotos) {
		this.listFotos = listFotos;
	}

	public List<String> getListaAnimais() {
		return listaAnimais;
	}

	public void setListaAnimais(List<String> listaAnimais) {
		this.listaAnimais = listaAnimais;
	}

	public List<String> getTipoSexo() {
		return tipoSexo;
	}

	public void setTipoSexo(List<String> tipoSexo) {
		this.tipoSexo = tipoSexo;
	}

	public String getTipoAnimal() {
		return tipoAnimal;
	}

	public void setTipoAnimal(String tipoAnimal) {
		this.tipoAnimal = tipoAnimal;
	}

	public List<String> getListaEstados() {
		return listaEstados;
	}

	public void setListaEstados(List<String> listaEstados) {
		this.listaEstados = listaEstados;
	}

}