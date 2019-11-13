package br.com.animais.adocao.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.animais.adocao.dao.EventoDao;
import br.com.animais.adocao.model.Evento;

@FacesConverter(forClass = Evento.class)
public class EventoConverter implements Converter {

	private EventoDao dao;

	public EventoConverter() {
		dao = new EventoDao();
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
		Evento evento = (Evento) value;

		if (evento.getId() == null) {
			return null;
		}
		return evento.getId().toString();
	}

}
