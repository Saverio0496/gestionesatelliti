package it.prova.gestionesatelliti.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.gestionesatelliti.model.Satellite;
import it.prova.gestionesatelliti.repository.SatelliteRepository;

@Service
public class SatelliteServiceImpl implements SatelliteService {

	@Autowired
	private SatelliteRepository repository;

	@Override
	@Transactional(readOnly = true)
	public List<Satellite> listAllElements() {
		return (List<Satellite>) repository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Satellite caricaSingoloElemento(Long id) {
		return repository.findById(id).orElse(null);
	}

	public void aggiorna(Satellite satelliteInstance) {
	}

	@Override
	@Transactional
	public void inserisciNuovo(Satellite satelliteInstance) {
		repository.save(satelliteInstance);
	}

	@Override
	@Transactional
	public void rimuoviPerId(Long idSatellite) {
		repository.deleteById(idSatellite);
	}

	public List<Satellite> findByExample(Satellite example) {
		return null;
	}

}
