package br.com.animais.adocao.bean;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import br.com.animais.adocao.dao.FotoDao;
import br.com.animais.adocao.model.Animal;
import br.com.animais.adocao.model.Foto;

@ManagedBean
@ViewScoped
public class FotoAnimalBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private Foto foto = new Foto();
	private List<Foto> listFotos = new ArrayList<Foto>();
	private FotoDao fotoDao;

	private UploadedFile arquivo;

	private StreamedContent file;
	private Animal animal = new Animal();

	public StreamedContent getFile() throws FileNotFoundException {
		fotoDao = new FotoDao();
		Long id = foto.getId();
		foto = fotoDao.buscarPorId(id);
		String caminho = foto.getCaminho();
//	        String caminho = "\\arquivos\\"+ arquivo.getNome();
//	        FileInputStream stream = new FileInputStream("arquivos\\"+ arquivo.getNome());
		InputStream stream = new FileInputStream(caminho);
		file = new DefaultStreamedContent(stream, "", foto.getCaminho());
//	        file = new DefaultStreamedContent(stream, caminho, arquivo.getNome());
		return file;
	}

	public void handleFileUpload(FileUploadEvent event) {
		FacesMessage msg = new FacesMessage("Upload Concluido Com Sucesso!",
				event.getFile().getFileName() + " upload concluido.");
		FacesContext.getCurrentInstance().addMessage(null, msg);

		try {
//		    String realPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/Adocao/src/main/webapp/resources/imagens");

			// Aqui cria o diretorio caso não exista
//			File file = new File(realPath + "/imagens/");
//			file.mkdirs();
			file = new DefaultStreamedContent(event.getFile().getInputstream());
			byte[] arquivo = event.getFile().getContents();

			String caminho = "C:\\Users\\Bruno\\Downloads\\Faculdade\\Adocao\\src\\main\\webapp\\resources\\imagens"
					+ event.getFile().getFileName();
//			String caminho = realPath + "/imagens/" + event.getFile().getFileName();

			// esse trecho grava o arquivo no diretório
			FileOutputStream fos = new FileOutputStream(caminho);
			fos.write(arquivo);
			fos.close();

//			pathImage = caminho;
			System.out.println("caminho da imagem salva é  = " + caminho);

			fotoDao = new FotoDao();
			Foto foto = new Foto();
			foto.setCaminho(caminho);
			foto.setAnimal(animal);
//			animal.setListFotos(listFotos);
			listFotos.add(foto);
//			fotoDao.salvar(foto);

//			for (Foto ft : listFotos) {
//				animal.getListFotos().add(foto);

		} catch (Exception e) {
			e.printStackTrace();
//			 FacesContext context = FacesContext.getCurrentInstance();
//			 context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!", " " + e));
		}

	}

	public Foto getFoto() {
		return foto;
	}

	public UploadedFile cadastroAdocaoBean() {
		return arquivo;
	}

	public void setArquivo(UploadedFile arquivo) {
		this.arquivo = arquivo;
	}

	public void setFoto(Foto foto) {
		this.foto = foto;
	}

	public FotoDao getFotoDao() {
		return fotoDao;
	}

	public void setFotoDao(FotoDao fotoDao) {
		this.fotoDao = fotoDao;
	}

	public List<Foto> getListFotos() {
		return listFotos;
	}

	public void setListFotos(List<Foto> listFotos) {
		this.listFotos = listFotos;
	}

	public void setFile(StreamedContent file) {
		this.file = file;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
