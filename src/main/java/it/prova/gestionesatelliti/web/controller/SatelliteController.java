package it.prova.gestionesatelliti.web.controller;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.prova.gestionesatelliti.model.Satellite;
import it.prova.gestionesatelliti.model.StatoSatellite;
import it.prova.gestionesatelliti.service.SatelliteService;
import it.prova.gestionesatelliti.validator.SatelliteValidator;

@Controller
@RequestMapping(value = "/satellite")
public class SatelliteController {

	@Autowired
	private SatelliteService satelliteService;

	@Autowired
	private SatelliteValidator satelliteValidator;

	@GetMapping
	public ModelAndView listAll() {
		ModelAndView mv = new ModelAndView();
		List<Satellite> results = satelliteService.listAllElements();
		mv.addObject("satellite_list_attribute", results);
		mv.setViewName("satellite/list");
		return mv;
	}

	@GetMapping("/insert")
	public String create(Model model) {
		model.addAttribute("insert_satellite_attr", new Satellite());
		return "satellite/insert";
	}

	@PostMapping("/save")
	public String save(@Valid @ModelAttribute("insert_satellite_attr") Satellite satellite, BindingResult result,
			RedirectAttributes redirectAttrs) {

		satelliteValidator.validate(satellite, result);

		if (result.hasErrors())
			return "satellite/insert";

		satelliteService.inserisciNuovo(satellite);

		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/satellite";
	}

	@GetMapping("/show/{idSatellite}")
	public String show(@PathVariable(required = true) Long idSatellite, Model model) {
		model.addAttribute("show_satellite_attr", satelliteService.caricaSingoloElemento(idSatellite));
		return "satellite/show";
	}

	@GetMapping("/delete/{idSatellite}")
	public String delete(@PathVariable(required = true) Long idSatellite, Model model) {

		model.addAttribute("delete_satellite_attr", satelliteService.caricaSingoloElemento(idSatellite));
		return "satellite/delete";
	}

	@PostMapping("/remove")
	public String remove(@RequestParam(required = true) Long idSatellite, RedirectAttributes redirectAttrs) {

		if (satelliteValidator.validateDelete(satelliteService.caricaSingoloElemento(idSatellite))) {

			satelliteService.rimuoviPerId(idSatellite);
			redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");

		} else {

			redirectAttrs.addFlashAttribute("errorMessage",
					"Operazione fallita, non è possibile eliminare questo satellite!");

		}
		return "redirect:/satellite";
	}

	@GetMapping("/search")
	public String search() {
		return "satellite/search";
	}

	@PostMapping("/list")
	public String listByExample(Satellite example, ModelMap model) {
		List<Satellite> results = satelliteService.findByExample(example);
		model.addAttribute("satellite_list_attribute", results);
		return "satellite/list";
	}

	@GetMapping("/edit/{idSatellite}")
	public String edit(@PathVariable(required = true) Long idSatellite, Model model) {
		model.addAttribute("update_satellite_attr", satelliteService.caricaSingoloElemento(idSatellite));
		return "satellite/edit";
	}

	@PostMapping("/update")
	public String update(@Valid @ModelAttribute("update_satellite_attr") Satellite satellite, BindingResult result,
			RedirectAttributes redirectAttrs) {

		satelliteValidator.validate(satellite, result);

		if (result.hasErrors())
			return "satellite/edit";

		satelliteService.aggiorna(satellite);

		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/satellite";

	}

	@GetMapping("/launch/{idSatellite}")
	public String launch(@PathVariable(required = true) Long idSatellite, RedirectAttributes redirectAttrs) {
		Satellite satelliteDaLanciare = satelliteService.caricaSingoloElemento(idSatellite);
		satelliteDaLanciare.setDataLancio(new Date());
		satelliteDaLanciare.setStato(StatoSatellite.IN_MOVIMENTO);
		satelliteService.aggiorna(satelliteDaLanciare);

		redirectAttrs.addFlashAttribute("successMessage", "Lancio eseguito perfettamente!");
		return "redirect:/satellite";
	}
	
	@GetMapping("/comeback/{idSatellite}")
	public String comeback(@PathVariable(required = true) Long idSatellite, RedirectAttributes redirectAttrs) {
		Satellite satelliteDaFarRientrare = satelliteService.caricaSingoloElemento(idSatellite);
		satelliteDaFarRientrare.setDataRientro(new Date());
		satelliteDaFarRientrare.setStato(StatoSatellite.DISATTIVATO);
		satelliteService.aggiorna(satelliteDaFarRientrare);

		redirectAttrs.addFlashAttribute("successMessage", "Ricevuto! Rientriamo alla base!");
		return "redirect:/satellite";
	}
	
	@GetMapping("/launched2yearsago")
	public ModelAndView listAllSatellitiLanciatiDaPiuDi2AnniENonDisattivati() {
		ModelAndView mv = new ModelAndView();
		List<Satellite> results = satelliteService.cercaTuttiISatellitiLanciatiDaPiuDi2AnniENonDisattivati();
		mv.addObject("satellite_list_attribute", results);
		mv.setViewName("satellite/list");
		return mv;
	}
	
}
