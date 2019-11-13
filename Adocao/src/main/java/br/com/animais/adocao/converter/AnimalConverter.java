package br.com.animais.adocao.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.animais.adocao.dao.AnimalDao;
import br.com.animais.adocao.model.Animal;

@FacesConverter(forClass = Animal.class)
public class AnimalConverter implements Converter {

	private AnimalDao dao;

	public AnimalConverter() {
		dao = new AnimalDao();
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {

		if (value.isEmpty() || value == null) {
			return null;
		}
		return dao.buscarPorId(Long.parseLong(value));
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value == null) {
			return null;
		}
		Animal animal = (Animal) value;

		if (animal.getId() == null) {
			return null;
		}
		return animal.getId().toString();
	}

}
