package it.prova.gestionesatelliti.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.prova.gestionesatelliti.model.Satellite;
import it.prova.gestionesatelliti.repository.SatelliteRepository;

@Service
public class SatelliteServiceImpl implements SatelliteService {

	@Autowired
	private SatelliteRepository repository;

	public List<Satellite> listAllElements() {
		return null;
	}

	public Satellite caricaSingoloElemento(Long id) {
		return null;
	}

	public void aggiorna(Satellite satelliteInstance) {
	}

	public void inserisciNuovo(Satellite satelliteInstance) {
	}

	public void rimuoviPerId(Long idSatellite) {
	}

	public List<Satellite> findByExample(Satellite example) {
		return null;
	}

}
