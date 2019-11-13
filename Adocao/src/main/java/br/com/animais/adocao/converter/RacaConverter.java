package br.com.animais.adocao.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.animais.adocao.dao.RacaDao;

import br.com.animais.adocao.model.Raca;

@FacesConverter(forClass = Raca.class)
public class RacaConverter implements Converter {

	private RacaDao dao;

	public RacaConverter() {
		dao = new RacaDao();
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
		Raca raca = (Raca) value;

		if (raca.getId() == null) {
			return null;
		}
		return raca.getId().toString();
	}

}
