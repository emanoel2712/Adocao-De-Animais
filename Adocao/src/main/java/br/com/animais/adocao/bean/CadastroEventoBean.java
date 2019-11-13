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
import br.com.animais.adocao.model.Foto;
import br.com.animais.adocao.model.Usuario;
import br.com.animais.adocao.util.Constantes;
import br.com.animais.adocao.util.FacesUtil;

@ManagedBean
@ViewScoped
public class CadastroEventoBean {

	private Evento evento;
	private EventoDao eventoDao;
	private List<Evento> eventos;

	private List<String> listaEstados;

	private String caminho;
	private Foto foto;

	private OngDao ongDao;
	private PessoaDao pessoaDao;

	public void inicializar() {
		if (evento == null) {
			evento = new Evento();

		}

		foto = new Foto();
		eventos = new ArrayList<Evento>();

		eventoDao = new EventoDao();
		ongDao = new OngDao();
		pessoaDao = new PessoaDao();
		popularEstados();

	}

	public void editar() {

		if (!evento.compararDatas()) {
			FacesUtil.addWarningMessages("Cadastre o evento com a Data correta!!");
			return;
		}
		if (caminho == null) {
			eventoDao.salvar(evento);
			FacesUtil.addInfoMessage("Evento salvo com sucesso !!");
		} else {
			evento.getFoto().setCaminho(caminho);
			eventoDao.salvar(evento);
			FacesUtil.addInfoMessage("Evento salvo com sucesso !!");

		}
		limpar();

	}

	public void salvar() {

		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Usuario usuario = (Usuario) session.getAttribute("UsuarioLogado");

		if (ongDao.ongUsuario(usuario.getId()) != null) {
			evento.setOng(ongDao.ongUsuario(usuario.getId()));

		} else if (pessoaDao.pessoaUsuario(usuario.getId()) != null) {
			evento.setPessoa(pessoaDao.pessoaUsuario(usuario.getId()));
		} else {
			return;
		}

		if (!evento.compararDatas()) {
			FacesUtil.addWarningMessages("Cadastre o evento com a Data correta!!");
			return;
		}
		foto.setCaminho(caminho);
		evento.setFoto(foto);
		eventoDao.salvar(evento);

		FacesUtil.addInfoMessage("Evento salvo com sucesso !!");
		limpar();

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
		listaEstados = Constantes.listaEstados();
	}

	public void limpar() {
		this.evento = new Evento();
	}

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	public List<Evento> getEventos() {
		return eventos;
	}

	public void setEventos(List<Evento> eventos) {
		this.eventos = eventos;
	}

	public Foto getFoto() {
		return foto;
	}

	public void setFoto(Foto foto) {
		this.foto = foto;
	}

	public String getCaminho() {
		return caminho;
	}

	public void setCaminho(String caminho) {
		this.caminho = caminho;
	}

	public List<String> getListaEstados() {
		return listaEstados;
	}

	public void setListaEstados(List<String> listaEstados) {
		this.listaEstados = listaEstados;
	}
}
