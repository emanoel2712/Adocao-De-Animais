
package br.com.animais.adocao.bean;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.event.FileUploadEvent;
import br.com.animais.adocao.dao.EventoDao;
import br.com.animais.adocao.dao.OngDao;
import br.com.animais.adocao.dao.PessoaDao;
import br.com.animais.adocao.model.Evento;
import br.com.animais.adocao.model.Ong;
import br.com.animais.adocao.model.Pessoa;
import br.com.animais.adocao.model.Usuario;
import br.com.animais.adocao.util.Constantes;
import br.com.animais.adocao.util.FacesUtil;

@ManagedBean
@ViewScoped
public class MeusEventosBean {
	private Usuario usuario;

	private List<Evento> lista;
	private List<Evento> listEventos;
	private List<Evento> listEventoExpirado;

	private EventoDao eventoDao;
	private PessoaDao pessoaDao;
	private OngDao ongDao;

	private int numeroPublicacoes;
	private int numeroExpirado;

	// ----

	private Evento evento;

	private List<String> listEstados;
	private String caminho;

	public void inicializar() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		usuario = (Usuario) session.getAttribute("UsuarioLogado");
		evento = new Evento();
		listEventos = new ArrayList<Evento>();
		listEventoExpirado = new ArrayList<Evento>();
		lista = new ArrayList<Evento>();
		eventoDao = new EventoDao();
		ongDao = new OngDao();
		pessoaDao = new PessoaDao();

		dadosUsuario();
		popularEstados();
	}

	public void handleFileUpload(FileUploadEvent event) {
		try {
			byte[] arquivo = event.getFile().getContents();

			// pra salvar a foto com o nome do caminho
			caminho = "C:\\Users\\Bruno\\Downloads\\Faculdade\\Adocao\\src\\main\\webapp\\resources\\imagens\\"
					+ event.getFile().getFileName();

			// esse trecho grava o arquivo no diretório
			FileOutputStream fos = new FileOutputStream(caminho);
			fos.write(arquivo);
			fos.close();

			caminho = event.getFile().getFileName();
			System.out.println("caminho da imagem salva é  = " + caminho);

		} catch (Exception e) {
			e.printStackTrace();
		}
		FacesMessage msg = new FacesMessage("Upload Concluido com Sucesso!");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void popularEstados() {
		listEstados = Constantes.listaEstados();
	}

	public void dadosUsuario() {
		limparLista();
		if (ongDao.ongUsuario(usuario.getId()) != null) {
			Ong ong = ongDao.ongUsuario(usuario.getId());
			evento.setOng(ong);
			lista = eventoDao.buscarEventosOng(ong.getId());

		} else if (pessoaDao.pessoaUsuario(usuario.getId()) != null) {
			Pessoa pessoa = pessoaDao.pessoaUsuario(usuario.getId());
			evento.setPessoa(pessoa);
			lista = eventoDao.buscarEventosPessoa(pessoa.getId());

		} else {
			return;
		}

		for (Evento evento : lista) {
			if (!evento.compararDatas()) {
				listEventoExpirado.add(evento);
			} else {
				listEventos.add(evento);
			}
		}

		numeroPublicacoes = listEventos.size();
		numeroExpirado = listEventoExpirado.size();
	}

	public void excluir() {
		eventoDao.excluir(evento);
		FacesUtil.addInfoMessage("Excluido com sucesso !!");
		evento = new Evento();
		dadosUsuario();
	}

	private void limparLista() {
		listEventos.clear();
		listEventoExpirado.clear();
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Evento> getListEventos() {
		return listEventos;
	}

	public void setListEventos(List<Evento> listEventos) {
		this.listEventos = listEventos;
	}

	public List<Evento> getListEventoExpirado() {
		return listEventoExpirado;
	}

	public void setListEventoExpirado(List<Evento> listEventoExpirado) {
		this.listEventoExpirado = listEventoExpirado;
	}

	public int getNumeroPublicacoes() {
		return numeroPublicacoes;
	}

	public void setNumeroPublicacoes(int numeroPublicacoes) {
		this.numeroPublicacoes = numeroPublicacoes;
	}

	public int getNumeroExpirado() {
		return numeroExpirado;
	}

	public void setNumeroExpirado(int numeroExpirado) {
		this.numeroExpirado = numeroExpirado;
	}

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	public List<String> getListEstados() {
		return listEstados;
	}

	public void setListEstados(List<String> listEstados) {
		this.listEstados = listEstados;
	}

	public String getCaminho() {
		return caminho;
	}

	public void setCaminho(String caminho) {
		this.caminho = caminho;
	}

	public List<Evento> getLista() {
		return lista;
	}

	public void setLista(List<Evento> lista) {
		this.lista = lista;
	}

}