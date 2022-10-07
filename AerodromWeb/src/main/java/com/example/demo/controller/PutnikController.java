package com.example.demo.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.repository.KartaRepository;
import com.example.demo.repository.KompanijaRepository;
import com.example.demo.repository.KorisnikRepository;
import com.example.demo.repository.LetRepository;
import com.example.demo.repository.MestoRepository;
import com.example.demo.repository.PlatnaKarticaRepository;
import com.example.demo.repository.PutnikRepository;
import com.example.demo.repository.RecenzijaRepository;

import model.Aerodrom;
import model.Karta;
import model.Kompanija;
import model.Korisnik;
import model.Let;
import model.Mesto;
import model.Platnakartica;
import model.Putnik;
import model.Recenzija;

@Controller
@RequestMapping(value = "/putnik")
public class PutnikController {
	
	@Autowired
	KorisnikRepository ur;
	
	@Autowired
	PutnikRepository pr;
	
	@Autowired
	MestoRepository mr;
	
	@Autowired
	PlatnaKarticaRepository pkr;
	
	@Autowired
	LetRepository lr;
	
	@Autowired
	KartaRepository kr;
	
	@Autowired
	KompanijaRepository kmpr;
	
	@Autowired
	RecenzijaRepository rp;
	
	@RequestMapping(value = "unesiPodatke", method = RequestMethod.GET)
	public String unesiPodatke(HttpServletRequest request) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((UserDetails)principal).getUsername();
		Korisnik k = ur.findByKorisnickoIme(username);
		request.setAttribute("registrovan", k.getPutnik());
		return "/unos/unosPodotakaPutnik";
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
	}
	
	@RequestMapping(value = "sacuvajPodatke", method = RequestMethod.POST)
	public String sacuvajPodatke(String ime, String prezime, String brojTelefona, String email,
			String adresa, String grad, Date datumRodjenja, String brojKartice, Integer cvv, String datumIsteka,
			HttpServletRequest request) {
		
		String poruka = "";
		
		if (ime == null || prezime == null || brojTelefona == null || email == null || adresa == null || grad == null || datumRodjenja == null ||
				brojKartice == null || cvv == null || datumIsteka == null || ime.isEmpty() || prezime.isEmpty() || brojTelefona.isEmpty() ||
				email.isEmpty() || adresa.isEmpty() || grad.isEmpty() || brojKartice.isEmpty() || datumIsteka.isEmpty()) {
			poruka += "Sva polja moraju biti popunjena.\n";
		} else {
		
			for (int i = 0; i < ime.length(); i++) {
			    if (Character.isDigit(ime.charAt(i))) {
			    	poruka += "Ime nije ispravno uneto.\n";
			    	break;
			    }
			}
			for (int i = 0; i < prezime.length(); i++) {
			    if (Character.isDigit(prezime.charAt(i))) {
			    	poruka += "Prezime nije ispravno uneto.\n";
			    	break;
			    }
			}
			for (int i = 0; i < brojTelefona.length(); i++) {
			    if (!Character.isDigit(brojTelefona.charAt(i))) {
			    	poruka += "Broj telefona nije ispravno unet.\n";
			    	break;
			    }
			}
			if (!email.contains("@")) {
				poruka += "Email nije ispravno unet.\n";
			}
			for (int i = 0; i < grad.length(); i++) {
			    if (Character.isDigit(grad.charAt(i))) {
			    	poruka += "Grad nije ispravno unet.\n";
			    	break;
			    }
			}
			for (int i = 0; i < brojKartice.length(); i++) {
			    if (!Character.isDigit(brojKartice.charAt(i))) {
			    	poruka += "Broj kartice nije ispravno unet.\n";
			    	break;
			    }
			}
			if (brojKartice.length() != 9) {
				poruka += "Broj kartice mora imati 9 cifara.\n";
			}
//			if (Character.isDigit(datumIsteka.charAt(0)) && Character.isDigit(datumIsteka.charAt(1)) &&
//					Character.isDigit(datumIsteka.charAt(3)) && Character.isDigit(datumIsteka.charAt(4)) && datumIsteka.charAt(2) == '/') {
//				poruka += "Datum isteka kartice nije ispravno unet.\n";
//			}
		} 
		
		if (poruka.equals("")) {
		
			Platnakartica kartica = new Platnakartica();
			kartica.setBrojKartice(brojKartice);
			kartica.setCvv(cvv);
			kartica.setDatumIsteka(datumIsteka);
			
			Putnik p = new Putnik();
			p.setIme(ime);
			p.setPrezime(prezime);
			p.setBrojTelefona(brojTelefona);
			p.setEmail(email);
			p.setAdresa(adresa);
			p.setGrad(grad);
			p.setDatumRodjenja(datumRodjenja);
			
			kartica.setPutnik(p);
			
			pr.save(p);
			pkr.save(kartica);
			
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String username = ((UserDetails)principal).getUsername();
			Korisnik k = ur.findByKorisnickoIme(username);
			k.setPutnik(p);
			
			ur.save(k);
			request.setAttribute("registrovan", k);
			request.setAttribute("uspesno", true);
		} else {
			request.setAttribute("poruka", poruka);
			request.setAttribute("uspesno", false);
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String username = ((UserDetails)principal).getUsername();
			Korisnik k = ur.findByKorisnickoIme(username);
			request.setAttribute("registrovan", k.getPutnik());
		}
		
		return "/unos/unosPodotakaPutnik";
	}
	
	@RequestMapping(value = "azurirajPodatke", method = RequestMethod.GET)
	public String azurirajPodatke(HttpServletRequest request) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((UserDetails)principal).getUsername();
		Korisnik k = ur.findByKorisnickoIme(username);
		
		Platnakartica kartica = pkr.findByPutnik(k.getPutnik());

		request.setAttribute("putnik", k.getPutnik());
		request.setAttribute("kartica", kartica);
		return "/izmenaPodatakaPutnik";
	}
	
	@RequestMapping(value = "azuriraj", method = RequestMethod.POST)
	public String azuriraj(Integer idP, Integer idK, String ime, String prezime, String brojTelefona, String email,
			String adresa, String grad, String brojKartice, Integer cvv, String datumIsteka,
			HttpServletRequest request) {
		
		Platnakartica kartica = pkr.findById(idK).get();
		Putnik p = pr.findById(idP).get();

		String poruka = "";
		boolean azurirano = false;
		boolean allIsOk = true;
		
		if (cvv != null && brojKartice != null && datumIsteka != null
				&& !brojKartice.isEmpty() && !datumIsteka.isEmpty()) {
			
			boolean ok = true;
			
			for (int i = 0; i < brojKartice.length(); i++) {
			    if (!Character.isDigit(brojKartice.charAt(i))) {
			    	ok = false;
			    	allIsOk = false;
			    	poruka += "Broj kartice nije ispravno unet.\n";
			    	break;
			    }
			}
			
			if (brojKartice.length() != 9) {
				ok = false;
				allIsOk = false;
				poruka += "Broj kartice mora imati 9 cifara.\n";
			}
			
			if (ok) {
				kartica.setBrojKartice(brojKartice);
				kartica.setCvv(cvv);
				kartica.setDatumIsteka(datumIsteka);
				azurirano = true;
			}
		}
		
		
		
		if (ime != null && !ime.isEmpty()) {
			
			boolean ok =true;
			for (int i = 0; i < ime.length(); i++) {
			    if (Character.isDigit(ime.charAt(i))) {
			    	poruka += "Ime nije ispravno uneto.\n";
			    	ok = false;
			    	allIsOk = false;
			    	break;
			    }
			}
			if (ok) {
				p.setIme(ime);
				azurirano = true;
			}
		} 
		if (prezime != null && !prezime.isEmpty()) {
			
			boolean ok = true;
			
			for (int i = 0; i < prezime.length(); i++) {
		    if (Character.isDigit(prezime.charAt(i))) {
			    	poruka += "Prezime nije ispravno uneto.\n";
			    	ok = false;
			    	allIsOk = false;
			    	break;
		    	}
			}
			
			if (ok) {
				p.setPrezime(prezime);
				azurirano = true;
			}
			
		}
		if (brojTelefona != null && !brojTelefona.isEmpty()) {
			
			boolean ok = true;
			
			for (int i = 0; i < brojTelefona.length(); i++) {
				if (!Character.isDigit(brojTelefona.charAt(i))) {
			    	poruka += "Broj telefona nije ispravno unet.\n";
			    	ok = false;
			    	allIsOk = false;
			    	break;
			    }
			}
			
			if (ok) {
				p.setBrojTelefona(brojTelefona);
				azurirano = true;
			}
		}
		if (email != null && !email.isEmpty()) {
			boolean ok = true;
			
			if (!email.contains("@")) {
				ok = false;
				poruka += "Email nije ispravno unet.\n";
				allIsOk = false;
			}
			
			if (ok) {
				p.setEmail(email);
				azurirano = true;
			}
		}
		if (adresa != null && !adresa.isEmpty()) {
			p.setAdresa(adresa);
		} 
		if (grad != null && !grad.isEmpty()) {
			boolean ok = true;
			
			for (int i = 0; i < grad.length(); i++) {
			    if (Character.isDigit(grad.charAt(i))) {
			    	poruka += "Grad nije ispravno unet.\n";
			    	ok = false;
			    	allIsOk = false;
			    	break;
			    }
			}
			
			if (ok) {
				p.setGrad(grad);
				azurirano = true;
			}
			
			
		}
		
//		boolean azurirano = false;
//		
//		if (ime != null && !ime.isEmpty() || prezime != null && !prezime.isEmpty() || brojTelefona != null && !brojTelefona.isEmpty() ||
//				email != null && !email.isEmpty() || adresa != null && !adresa.isEmpty() || grad != null && !grad.isEmpty() || 
//				(cvv != null && brojKartice != null && datumIsteka != null && !brojKartice.isEmpty() && !datumIsteka.isEmpty())) {
//			azurirano = true;
//		}
		
		if (azurirano && allIsOk) {
			pr.save(p);
			pkr.save(kartica);
			request.setAttribute("azurirano", azurirano);
		} else {
			request.setAttribute("azurirano", false);
		}
		
		Putnik putnik = pr.findById(idP).get();
		request.setAttribute("putnik", putnik);
		request.setAttribute("poruka", poruka);
		request.setAttribute("kartica", kartica);
		
		
		return "/izmenaPodatakaPutnik";
	}
	
	
	@RequestMapping(value = "prikazLetova", method = RequestMethod.GET)
	public String prikazLetova(HttpServletRequest request) {
		List<Let> letovi = lr.findAll();
		request.getSession().setAttribute("letovi", letovi);
		return "/PrikazLetovaPutnik";
	}
	
	
	@RequestMapping(value = "filtrirajLetove", method = RequestMethod.GET)
	public String filtrirajLetove(HttpServletRequest request, Date datumPolaska, Date datumDolaska, String destinacija) {
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
		return "/PrikazLetovaPutnik";
	}
	
	@RequestMapping(value = "getLetInfo", method = RequestMethod.GET)
	public String getLetInfo(Integer idLet, HttpServletRequest request) {
		Let let = lr.findById(idLet).get();
		List<Aerodrom> presedanja = let.getAerodroms();
		request.setAttribute("letForInfo", let);
		if (presedanja != null) {
			request.setAttribute("presedanja", presedanja);
		}
		return "/InformacijeOLetuPutnik";
	}
	
	@RequestMapping(value = "rezervacija", method = RequestMethod.GET)
	public String rezervacija(Integer idLet, HttpServletRequest request) {
		List<Mesto> mesta = mr.getSlobodnaMestaZaLet(idLet);
		request.getSession().setAttribute("idLetRezervacija", idLet);
		request.setAttribute("mesta", mesta);
		return "/rezervacijaPutnik";
	}
	
	@RequestMapping(value = "sacuvajRezervaciju", method = RequestMethod.POST)
	public String sacuvajRezervaciju(Integer mesto, HttpServletRequest request) {
		Mesto m = mr.getById(mesto);
		Integer lId = (Integer)request.getSession().getAttribute("idLetRezervacija");
		Let l = lr.getById(lId);
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((UserDetails)principal).getUsername();
		Korisnik u = ur.findByKorisnickoIme(username);
		
		
		Karta k = new Karta();
		if (m.getKlasa().equalsIgnoreCase("Biznis")) {
			k.setCena(l.getCena() * 1.3);
			k.setOpis("Biznis karta");
		} else {
			k.setOpis("Ekonomska karta");
			k.setCena(l.getCena());
		}
		
		k.setLet(l);
		k.setMesto(m);
		k.setPutnik(u.getPutnik());
		
		LocalDate ld = LocalDate.now();
		String d = ld.getDayOfMonth() + "/" + ld.getMonthValue() + "/" + ld.getYear();
		@SuppressWarnings("deprecation")
		Date datum = new Date(d);
		k.setDatum(datum);
		
		
		Karta sacuvanaRezervacija = kr.save(k);
		request.setAttribute("sacuvanaRezervacija", sacuvanaRezervacija);
		List<Karta> karte = kr.findAllByPutnik(u.getPutnik()); 
		request.getSession().setAttribute("karte", karte);
		
		return "/PrikazRezervacijaPutnik";
	}
	
	
	@RequestMapping(value = "prikazRezervacija", method = RequestMethod.GET)
	public String prikazRezervacija(HttpServletRequest request) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((UserDetails)principal).getUsername();
		Korisnik u = ur.findByKorisnickoIme(username);
		List<Karta> karte = kr.findAllByPutnik(u.getPutnik()); 
		
		LocalDate ld = LocalDate.now();
		String d = ld.getDayOfMonth() + "/" + ld.getMonthValue() + "/" + ld.getYear();
		@SuppressWarnings("deprecation")
		Date datum = new Date(d);
		request.getSession().setAttribute("datumNow", datum);
 		
		request.getSession().setAttribute("karte", karte);
		return "/PrikazRezervacijaPutnik";
	}
	
	@RequestMapping(value = "izbrisiRezervaciju", method = RequestMethod.GET)
	public String izbrisiRezervaciju(Integer idKarta, HttpServletRequest request) {
		Karta k = kr.getById(idKarta);
		kr.delete(k);
 		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((UserDetails)principal).getUsername();
		Korisnik u = ur.findByKorisnickoIme(username);
		List<Karta> karte = kr.findAllByPutnik(u.getPutnik()); 
		request.getSession().setAttribute("karte", karte);
		request.setAttribute("izbrisanaRezervacija", true);
		
		return "/PrikazRezervacijaPutnik";
	}
	
	@RequestMapping(value = "filtrirajRezervacije", method = RequestMethod.GET)
	public String filtrirajRezervacije(String destinacija, Date datum, HttpServletRequest request) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((UserDetails)principal).getUsername();
		Korisnik u = ur.findByKorisnickoIme(username);
		
		List<Karta> karte = null;
		if (!destinacija.isEmpty() && datum != null) {
			karte = kr.filtrirajZaDestinacijaDatumPutnik(u.getPutnik().getIdPutnik(), destinacija, datum);
		} else if (!destinacija.isEmpty()) {
			karte = kr.filtrirajZaDestinacijaPutnik(u.getPutnik().getIdPutnik(), destinacija);
		} else if (datum != null) {
			karte = kr.filtrirajZaDatumPutnik(u.getPutnik().getIdPutnik(), datum);
		}
		
		boolean primenjenFilter = true;
		
		request.setAttribute("karte", karte);
		request.setAttribute("primenjenFilter", primenjenFilter);
		return "/PrikazRezervacijaPutnik";
	}
	
	@RequestMapping(value = "dodajRecenziju", method = RequestMethod.GET)
	public String dodajRecenziju(Integer idKompanija, HttpServletRequest request) {
		Kompanija k = kmpr.getById(idKompanija);
		request.getSession().setAttribute("kompanija", k);
		return "/unos/dodajRecenziju";
	}
	
	@RequestMapping(value = "sacuvajRecenziju", method = RequestMethod.POST)
	public String sacuvajRecenziju(Integer ocena, String sadrzaj, HttpServletRequest request) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((UserDetails)principal).getUsername();
		Korisnik u = ur.findByKorisnickoIme(username);
		Putnik p = u.getPutnik();
		
		Kompanija k = (Kompanija) request.getSession().getAttribute("kompanija");
		
		if (ocena != null && sadrzaj != null && !sadrzaj.isEmpty()) {
			Recenzija r = new Recenzija();
			r.setKompanija(k);
			r.setPutnik(p);
			LocalDate ld = LocalDate.now();
			String d = ld.getDayOfMonth() + "/" + ld.getMonthValue() + "/" + ld.getYear();
			@SuppressWarnings("deprecation")
			Date datum = new Date(d);
			r.setDatum(datum);
			r.setOcena(ocena);
			r.setSadrzaj(sadrzaj);
			
			rp.save(r);
		} else {
			request.setAttribute("poruka", "Recenzija nije sacuvana. Neophodno je popuniti sva polja!");
		}
		
		return "/pocetna";
	}

}
