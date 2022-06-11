package it.prova.gestionesatelliti.service;

import java.util.List;

import it.prova.gestionesatelliti.model.Satellite;

public interface SatelliteService {
	public List<Satellite> listAllElements();

	public Satellite caricaSingoloElemento(Long id);

	public void aggiorna(Satellite satelliteInstance);

	public void inserisciNuovo(Satellite satelliteInstance);

	public void rimuoviPerId(Long idSatellite);

	public List<Satellite> findByExample(Satellite example);

	public List<Satellite> cercaTuttiISatellitiLanciatiDaPiuDi2AnniENonDisattivati();

	public List<Satellite> cercaSatellitiDisattivatiMaNonAncoraRientrati();

	public List<Satellite> cercaSatellitiInOrbitaDaPiuDi10AnniEFissi();
}
