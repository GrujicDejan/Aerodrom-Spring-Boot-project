package com.example.demo.controller;

import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.repository.AerodromRepository;
import com.example.demo.repository.KartaRepository;
import com.example.demo.repository.KompanijaRepository;
import com.example.demo.repository.LetRepository;
import com.example.demo.repository.MestoRepository;
import com.example.demo.repository.PlatnaKarticaRepository;
import com.example.demo.repository.PutnikRepository;
import com.example.demo.repository.RecenzijaRepository;

import model.Aerodrom;
import model.Karta;
import model.Kompanija;
import model.Let;
import model.Mesto;
import model.Putnik;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Controller
@RequestMapping(value = "/admin")
public class AdministratorController {
	
	@PersistenceContext
	EntityManager em;
	
	@Autowired
	LetRepository lr;
	
	@Autowired
	PutnikRepository pr;
	
	@Autowired
	PlatnaKarticaRepository pkr;
	
	@Autowired
	MestoRepository mr;
	
	@Autowired
	KartaRepository kr;
	
	@Autowired
	RecenzijaRepository rr;
	
	@Autowired
	KompanijaRepository kmpr;
	
	@Autowired 
	AerodromRepository ar;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
	}
	
	@RequestMapping(value = "unosLeta", method = RequestMethod.GET)
	public String unosLeta(HttpServletRequest request) {
		List<Aerodrom> aerodromi = ar.findAll();
		List<Kompanija> kompanije = kmpr.findAll();
		
		request.setAttribute("aerodromi", aerodromi);
		request.setAttribute("kompanije", kompanije);
		return "/unos/UnosLeta";
	}
	
	
	@RequestMapping(value = "sacuvajLet", method = RequestMethod.POST)
	public String sacuvajLet(Integer dolazniA, Integer odlazniA, String cena, String trajanjeLeta,
			String vremePolaska, Date datumPolaska, Date datumDolaska, Integer idKompanija, HttpServletRequest request) {
		Aerodrom a1 = ar.getById(odlazniA);
		Aerodrom a2 = ar.getById(dolazniA);
		Kompanija k = kmpr.findById(idKompanija).get();
		
		String poruka = "";
		boolean ok = true;
		double c = 0;
		
		if (dolazniA != null && odlazniA != null && idKompanija != null && trajanjeLeta != null && !trajanjeLeta.isEmpty() && 
				vremePolaska != null && !vremePolaska.isEmpty() && datumPolaska != null && datumDolaska != null && cena != null && !cena.isEmpty()) {
			
			try {
				c = Double.parseDouble(cena);
			} catch (Exception e) {
				poruka += "Cena leta nije ispravno uneto.";
				ok = false;
			}
			
			for (int i = 0; i < trajanjeLeta.length() - 1; i++) {
				if (!Character.isDigit(trajanjeLeta.charAt(i))) {
					poruka += "Trajanje leta nije dobro uneto.\n";
			    	ok = false;
			    	break;
			    }
			}
			
		} else {
			poruka = "Sva polja moraju biti popunjena.";
			ok = false;
		}
		
		if (ok) {
			Let l = new Let();
			l.setAerodrom1(a1);
			l.setAerodrom2(a2);
			l.setCena(c);
			l.setTrajanjeLeta(trajanjeLeta);
			l.setVremePolaska(vremePolaska);
			l.setDatumPolaska(datumPolaska);
			l.setDatumDolaska(datumDolaska);
			l.setKompanija(k);
			
			l = lr.save(l);
			request.getSession().setAttribute("sacuvanLet", true);
			String let = l.getAerodrom1().getNaziv() + " - " + l.getAerodrom2().getNaziv();
			request.getSession().setAttribute("idLetSave", l.getIdLet());
			request.getSession().setAttribute("letSave", let);
		} else {
			request.getSession().setAttribute("sacuvanLet", false);
		}
		
		request.getSession().setAttribute("porukaLet", poruka);
		
		List<Aerodrom> aerodromi = ar.findAll();
		List<Kompanija> kompanije = kmpr.findAll();
		request.getSession().setAttribute("kompanije", kompanije);
		request.getSession().setAttribute("aerodromi", aerodromi);
		return "/unos/UnosPresedanja";
	}
	
	
	@RequestMapping(value = "sacuvajAerodrom", method = RequestMethod.POST)
	public String sacuvajAerodrom(String naziv, String mesto, String adresa, HttpServletRequest request) {
		
		boolean ok = true;
		String poruka = "";
		
		if (naziv != null && !naziv.isEmpty() && mesto != null && !mesto.isEmpty() && adresa != null && !adresa.isEmpty()) {
			
			for (int i = 0; i < naziv.length(); i++) {
				if (Character.isDigit(naziv.charAt(i))) {
					poruka += "Naziv nije dobro unet.\n";
			    	ok = false;
			    	break;
			    }
			}
			
			for (int i = 0; i < mesto.length(); i++) {
				if (Character.isDigit(mesto.charAt(i))) {
					poruka += "Mesto nije dobro uneto.\n";
			    	ok = false;
			    	break;
			    }
			}
			
			if (ok) {
			
				Aerodrom a = new Aerodrom();
				a.setAdresa(adresa);
				a.setMesto(mesto);
				a.setNaziv(naziv);
				a = ar.save(a);
				
			}
			
		} else {
			poruka = "Sva polja moraju biti popunjena.\n";
			ok = false;
		}
		
		request.setAttribute("porukaAerodrom", poruka);
		request.setAttribute("sacuvanA", ok);
		
		List<Aerodrom> aerodromi = ar.findAll();
		request.setAttribute("aerodromi", aerodromi);
		List<Kompanija> kompanije = kmpr.findAll();
		request.setAttribute("kompanije", kompanije);
		
		return "/unos/UnosLeta";
	}
	
	
	@RequestMapping(value = "dodajPresedanje", method = RequestMethod.POST)
	public String dodajPresedanje(Integer idAerodrom ,HttpServletRequest request) {
		Aerodrom a = ar.getById(idAerodrom);
		Integer idLet = (Integer) request.getSession().getAttribute("idLetSave");
		Let l = lr.getById(idLet);
		
		l.getAerodroms().add(a);
		lr.save(l);
		
		boolean sacuvanoP = (a == null)? false : true;
		request.setAttribute("sacuvanoP", sacuvanoP);
		return "/unos/UnosPresedanja";
	}
	
	
	@RequestMapping(value = "prikazLetovaAdministrator", method = RequestMethod.GET)
	public String prikazLetovaAdministrator(HttpServletRequest request) {
		List<Let> letovi = lr.findAll();
		request.getSession().setAttribute("letovi", letovi);

		return "/PrikazLetovaAdministrator";
	}
	
	
	@RequestMapping(value = "filtrirajLetoveAdministrator", method = RequestMethod.GET)
	public String filtrirajLetoveAdministrator(HttpServletRequest request, Date datumPolaska, Date datumDolaska, String destinacija) {
		List<Let> letovi = null;
		if (datumDolaska != null && datumPolaska != null && !destinacija.isEmpty()) {
			letovi = lr.filtrirajPoDatumuIDestinaciji(datumDolaska, datumPolaska, destinacija);
		} else if (datumDolaska != null && datumPolaska != null) {
			letovi = lr.filtrirajPoDatumu(datumDolaska, datumPolaska);
		} else if (destinacija != null) {
			letovi = lr.filtrirajPoDestinaciji(destinacija);
		}
		
		boolean primenjenFilter = true;
		
		request.getSession().setAttribute("letovi", letovi);
		request.setAttribute("primenjenFilter", primenjenFilter);
		return "/PrikazLetovaAdministrator";
	}
	
	@RequestMapping(value = "getLetInfoAdministrator", method = RequestMethod.GET)
	public String getLetInfoAdministrator(Integer idLet, HttpServletRequest request) {
		Let let = lr.findById(idLet).get();
		List<Aerodrom> presedanja = let.getAerodroms();
		request.getSession().setAttribute("letForInfo", let);
		if (presedanja != null) {
			request.setAttribute("presedanja", presedanja);
		}
		return "/InformacijeOLetuAdministrator";
	}
	
	@RequestMapping(value = "dodajMesto", method = RequestMethod.GET)
	public String dodajMesto(Integer idLet, HttpServletRequest request) {
		
		return "/unos/dodajMesto";
	}
	
	@RequestMapping(value = "sacuvajMesto", method = RequestMethod.POST)
	public String sacuvajMesto(String red, String broj, String klasa, HttpServletRequest request) {
		
		boolean ok = true;
		String poruka = "";
		
		if (red != null && !red.isEmpty() && broj != null && !broj.isEmpty() && klasa != null && !klasa.isEmpty()) {
			for (int i = 0; i < red.length(); i++) {
				if (!Character.isDigit(red.charAt(i))) {
			    	poruka += "Red nije ispravno unet.\n";
			    	ok = false;
			    	break;
			    }
			}
			for (int i = 0; i < broj.length(); i++) {
				if (!Character.isDigit(broj.charAt(i))) {
			    	poruka += "Broj mesta nije ispravno unet.\n";
			    	ok = false;
			    	break;
			    }
			}
			if (!klasa.strip().equalsIgnoreCase("biznis") && !klasa.strip().equalsIgnoreCase("ekonomska")) {
				poruka += "Klasa sedista nije ispravno uneta.\n";
		    	ok = false;
			}
		} else {
			ok = false;
			poruka = "Sva polja moraju biti popunjena.\n";
		}
		if (ok) {
			Let let = (Let) request.getSession().getAttribute("letForInfo");
			Mesto m = new Mesto();
			m.setLet(let);
			m.setBroj(broj);
			m.setRed(red);
			m.setKlasa(klasa);
			m = mr.save(m);
			request.setAttribute("mesto", m);
		} else {
			request.setAttribute("poruka", poruka);
		}
		
		return "/unos/dodajMesto";
	}
	
	@RequestMapping(value = "sacuvajPromeneZaLet", method = RequestMethod.POST)
	public String sacuvajPromeneZaLet(Integer idLet, String cena, Date datumPolaska, Date datumDolaska, String vremePolaska, HttpServletRequest request) {
		boolean promena = false;
		boolean ok = true;
		
		String poruka = "";
		
		Let l = lr.getById(idLet);
		if (datumDolaska != null) {
			l.setDatumDolaska(datumDolaska);
			promena = true;
		}
		if (datumPolaska != null) {
			l.setDatumPolaska(datumPolaska);
			promena = true;
		} 
		if (vremePolaska != null && !vremePolaska.isEmpty()) {
			if (!vremePolaska.contains(":")) {
				poruka += "Vreme polaska nije ispravno uneto.";
				ok = false;
			} else {
				l.setVremePolaska(vremePolaska);
				promena = true;
			}
		} 
		if (cena != null && !cena.isEmpty()) {
			
			try {
				double c = Double.parseDouble(cena);
				l.setCena(c);
				promena = true;
			} catch (Exception e) {
				poruka += "Cena polaska nije ispravno uneto.";
				ok = false;
			}

		} 
		
		if (promena && ok) {
			l = lr.save(l);
		} else {
			promena = false;
		}
		
		request.setAttribute("letForInfo", l);
		request.setAttribute("promena", promena);
		request.setAttribute("poruka", poruka);
		return "/InformacijeOLetuAdministrator";
	}
	
	@RequestMapping(value = "izbrisiLet", method = RequestMethod.GET)
	public String izbrisiLet(Integer idLet, HttpServletRequest request) {
		Let l = lr.getById(idLet);
		List<Karta> karte = kr.findByLet(l);
		List<Mesto> mesta = mr.findByLet(l);
		for (Karta k : karte) {
			kr.delete(k);
		}
		for (Mesto m : mesta) {
			mr.delete(m);
		}
		lr.delete(l);
		
		List<Let> letovi = lr.findAll();
		request.getSession().setAttribute("letovi", letovi);
		request.setAttribute("izbrisanLet", true);
		return "/PrikazLetovaAdministrator";
	}
	
	@RequestMapping(value = "prikazRezervacija", method = RequestMethod.GET)
	public String prikazRezervacija(HttpServletRequest request) {
		List<Karta> karte = kr.findAll(); 
 		
		request.getSession().setAttribute("karte", karte);
		return "/PrikazRezervacija";
	}
	
	@RequestMapping(value = "izbrisiRezervaciju", method = RequestMethod.GET)
	public String izbrisiRezervaciju(Integer idKarta, HttpServletRequest request) {
		Karta k = kr.getById(idKarta);
		kr.delete(k);
 		
		List<Karta> karte = kr.findAll(); 
		request.getSession().setAttribute("karte", karte);
		request.setAttribute("izbrisanaRezervacija", true);
		return "/PrikazRezervacija";
	}
	
	
	@RequestMapping(value = "getInfoPutnik", method = RequestMethod.GET)
	public String getInfoPutnik(Integer idPutnik, HttpServletRequest request) {
		Putnik p = pr.getById(idPutnik);
				
		request.setAttribute("putnik", p);
		return "/InformacijeOPutniku";
	}
	
	@RequestMapping(value = "filtrirajRezervacije", method = RequestMethod.GET)
	public String filtrirajRezervacije(String ime, String prezime, String destinacija, Date datum, HttpServletRequest request) {
		List<Karta> karte = null;
		if (!ime.isEmpty() && !prezime.isEmpty() && !destinacija.isEmpty() && datum != null) {
			karte = kr.filtrirajZaImePrezimeDestinacijaDatum(ime, prezime, destinacija, datum);
		} else if (!ime.isEmpty() && !prezime.isEmpty() && !destinacija.isEmpty()) {
			karte = kr.filtrirajZaImePrezimeDestinacija(ime, prezime, destinacija);
		} else if (!ime.isEmpty() && !prezime.isEmpty() && datum != null) {
			karte = kr.filtrirajZaImePrezimeDatum(ime, prezime, datum);
		} else if (!ime.isEmpty() && !destinacija.isEmpty() && datum != null) {
			karte = kr.filtrirajZaImeDestinacijaDatum(ime, destinacija, datum);
		} else if (!prezime.isEmpty() && !destinacija.isEmpty() && datum != null) {
			karte = kr.filtrirajZaPrezimeDestinacijaDatum(prezime, destinacija, datum);
		} else if (!ime.isEmpty() && !prezime.isEmpty()) {
			karte = kr.filtrirajZaImePrezime(ime, prezime);
		} else if (!ime.isEmpty() && !destinacija.isEmpty()) {
			karte = kr.filtrirajZaImeDestinacija(ime, destinacija);
		} else if (!ime.isEmpty() && datum != null) {
			karte = kr.filtrirajZaImeDatum(ime, datum);
		} else if (!prezime.isEmpty() && !destinacija.isEmpty()) {
			karte = kr.filtrirajZaPrezimeDestinacija(prezime, destinacija);
		} else if (!prezime.isEmpty() && datum != null) {
			karte = kr.filtrirajZaPrezimeDatum(prezime, datum);
		} else if (!destinacija.isEmpty() && datum != null) {
			karte = kr.filtrirajZaDestinacijaDatum(destinacija, datum);
		} else if (!ime.isEmpty()) {
			karte = kr.filtrirajZaIme(ime);
		} else if (!prezime.isEmpty()) {
			karte = kr.filtrirajZaPrezime(prezime);
		} else if (!destinacija.isEmpty()) {
			karte = kr.filtrirajZaDestinacija(destinacija);
		} else if (datum != null) {
			karte = kr.filtrirajZaDatum(datum);
		}
		
		boolean primenjenFilter = true;
		
		request.setAttribute("karte", karte);
		request.setAttribute("primenjenFilter", primenjenFilter);
		return "/PrikazRezervacija";
	}
	
	
	@RequestMapping(value = "/generisiIzvestaj", method = RequestMethod.GET)
	public void generisiIzvestaj(HttpServletResponse response) throws Exception {
		LocalDate ld = LocalDate.now();
		String d = ld.getDayOfMonth() + "/" + ld.getMonthValue() + "/" + ld.getYear();
		Date datum = new SimpleDateFormat("dd/MM/yyyy").parse(d);
		List<Karta> karte = kr.getAkutelne(datum);
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(karte);

		InputStream inputStream = getClass().getResourceAsStream("/jasperreports/IzvestajRezervacije.jrxml");
		JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
		
		Map<String, Object> params = new HashMap<>();

		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);
		inputStream.close();
		response.setContentType("application/x-download");
		response.addHeader("Content-disposition", "attachment; filename=Rezervacije.pdf");
		OutputStream out = response.getOutputStream();
		JasperExportManager.exportReportToPdfStream(jasperPrint, out);
	}

}
