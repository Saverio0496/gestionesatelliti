package it.prova.gestionesatelliti.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.prova.gestionesatelliti.model.Satellite;
import it.prova.gestionesatelliti.model.StatoSatellite;

@Component
public class SatelliteValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Satellite.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Satellite satellite = (Satellite) target;

		if (satellite.getDataLancio() != null && satellite.getDataRientro() != null
				&& satellite.getDataLancio().after(satellite.getDataRientro())) {

			errors.rejectValue("dataLancio", null,
					"Errore! Bisogna modificare, la data di lancio deve precedere quella di rientro!");

		}

		if (satellite.getDataLancio() == null && satellite.getDataRientro() != null) {

			errors.rejectValue("dataLancio", null,
					"Errore! Bisogna inserire anche la data di lancio oppure eliminare la data di rientro");

		}

		if ((satellite.getStato() == StatoSatellite.FISSO || satellite.getStato() == StatoSatellite.IN_MOVIMENTO)
				&& satellite.getDataRientro() != null) {

			errors.rejectValue("stato", null,
					"Errore! Un satellite FISSO o IN_MOVIMENTO non può avere una data di rientro");

		}

		if (satellite.getStato() == StatoSatellite.DISATTIVATO && satellite.getDataRientro() == null) {

			errors.rejectValue("stato", null, "Errore! Un satellite disattivato deve avere una data di rientro");

		}

	}

	public boolean validateDelete(Object target) {
		Satellite satellite = (Satellite) target;

		if (satellite.getDataLancio() == null) {
			return true;
		}

		if (satellite.getDataRientro() != null && satellite.getStato() == StatoSatellite.DISATTIVATO) {
			return true;
		}

		return false;
	}

}
