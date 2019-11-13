package br.com.animais.adocao.animalDaoInterface;

import java.util.List;

import br.com.animais.adocao.model.Animal;

public interface AnimalDaoInterface<T> {

	public T salvar(Animal animal);

	public List<T> buscarTodos();

	public List<T> buscarAnimaisOng(Long id);

	public List<T> buscarAnimaisPessoa(Long id);
	
	public T buscarPorId(Long id);

	public List<T> buscarAnimalPorStatus(String status);

	public List<T> buscarAnimalPorTipo(String tipo);

	public List<T> buscarAnimaisTodos(String tipo, String raca, String estado);

	public List<T> buscarAnimaisTipoRaca(String tipo, String raca);

	public List<T> buscarAnimaisTipoEstado(String tipo, String estado);

	public List<T> buscarAnimaisRacaEstado(String raca, String estado);

	public List<T> buscarAnimaisSomenteTipo(String tipo);

	public List<T> buscarAnimaisSomenteEstado(String estado);
}
