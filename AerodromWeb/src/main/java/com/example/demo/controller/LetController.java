package com.example.demo.controller;

import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import model.Platnakartica;
import model.Putnik;
import model.Recenzija;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Controller
@RequestMapping(value = "/letovi")
public class LetController {
	
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
	
	@RequestMapping(value = "prikazLetova", method = RequestMethod.GET)
	public String prikazLetova(HttpServletRequest request) {
		List<Let> letovi = lr.findAll();
		request.getSession().setAttribute("letovi", letovi);
		return "/PrikazLetova";
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
		return "/PrikazLetova";
	}
	
	@RequestMapping(value = "getLetInfo", method = RequestMethod.GET)
	public String getLetInfo(Integer idLet, HttpServletRequest request) {
		Let let = lr.findById(idLet).get();
		List<Aerodrom> presedanja = let.getAerodroms();
		request.setAttribute("letForInfo", let);
		if (presedanja != null) {
			request.setAttribute("presedanja", presedanja);
		}
		return "/InformacijeOLetu";
	}
	
	@RequestMapping(value = "rezervacija", method = RequestMethod.GET)
	public String rezervacija(Integer idLet, HttpServletRequest request) {
		List<Mesto> mesta = mr.getSlobodnaMestaZaLet(idLet);
		request.getSession().setAttribute("idLetRezervacija", idLet);
		request.getSession().setAttribute("mestaR", mesta);
		return "/unos/UnosPodatakaOPutniku";
	}
	
	
	@RequestMapping(value = "getRecenzije", method = RequestMethod.GET)
	public String getRecenzije(Integer idKompanija, HttpServletRequest request) {
		Kompanija k = kmpr.getById(idKompanija);
		List<Recenzija> recenzije = rr.findByKompanijaOrderByOcena(k);
		double prosecnaOcena = 0;
		int i = 0;
		for (Recenzija r : recenzije) {
			prosecnaOcena += r.getOcena();
			i++;
		}
		prosecnaOcena /= i;
		
		request.setAttribute("recenzije", recenzije);
		request.setAttribute("prosecnaOcena", prosecnaOcena);
		request.setAttribute("kompanija", k.getNaziv());
		return "PrikazRecenzija";
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
	}
	
	@RequestMapping(value = "SacuvajRezervaciju", method = RequestMethod.POST)
	public String SacuvajRezervaciju(String ime, String prezime, String brojTelefona, String email,
			String adresa, String grad, Date datumRodjenja, String brojKartice, String cvv, String datumIsteka,
			Integer mesto, HttpServletRequest request) {
		
		String poruka = "";
		
		if (ime == null || prezime == null || brojTelefona == null || email == null || adresa == null || grad == null || datumRodjenja == null ||
				brojKartice == null || cvv == null || datumIsteka == null || ime.isEmpty() || prezime.isEmpty() || brojTelefona.isEmpty() ||
				email.isEmpty() || adresa.isEmpty() || grad.isEmpty() || brojKartice.isEmpty() || datumIsteka.isEmpty() || mesto == null) {
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
			int cvv2 = Integer.parseInt(cvv);
		
			Integer idLet = (Integer) request.getSession().getAttribute("idLetRezervacija");
			Let l = lr.findById(idLet).get();
			
			Platnakartica kartica = new Platnakartica();
			kartica.setBrojKartice(brojKartice);
			kartica.setCvv(cvv2);
			kartica.setDatumIsteka(datumIsteka);
			
			Putnik p = new Putnik();
			p.setIme(ime);
			p.setPrezime(prezime);
			p.setBrojTelefona(brojTelefona);
			p.setEmail(email);
			p.setAdresa(adresa);
			p.setGrad(grad);
			p.setDatumRodjenja(datumRodjenja);
			
			Mesto m = mr.getById(mesto);
			kartica.setPutnik(p);
			
			pr.save(p);
			pkr.save(kartica);
			
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
			k.setPutnik(p);
			
			LocalDate ld = LocalDate.now();
			String d = ld.getDayOfMonth() + "/" + ld.getMonthValue() + "/" + ld.getYear();
			@SuppressWarnings("deprecation")
			Date datum = new Date(d);
			k.setDatum(datum);
			
			
			Karta sacuvanaRezervacija = kr.save(k);
			request.setAttribute("sacuvanaRezervacija", sacuvanaRezervacija);
		} else {
			request.setAttribute("poruka", poruka);
			request.setAttribute("sacuvanaRezervacija", null);
		}
		
		return "/unos/UnosPodatakaOPutniku";
	}
	
	@RequestMapping(value = "/kreirajKartu", method = RequestMethod.GET)
	public void kreirajKartu(Integer idKarta, HttpServletResponse response) throws Exception {
		Karta k = kr.findById(idKarta).get();
		List<Karta> kkl = new ArrayList<Karta>();
		kkl.add(new Karta());
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(kkl);

		InputStream inputStream = getClass().getResourceAsStream("/jasperreports/Karta.jrxml");
		JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
		
		Map<String, Object> params = new HashMap<>();
		params.put("putnik", k.getPutnik().getIme() + " " + k.getPutnik().getPrezime());
		params.put("let", k.getLet().getAerodrom1().getMesto() + " - " + k.getLet().getAerodrom2().getMesto());
		params.put("aerodromOdlazni", k.getLet().getAerodrom1().getNaziv());
		params.put("aerodromDolazni", k.getLet().getAerodrom2().getNaziv());
		params.put("vremePolaska", k.getLet().getVremePolaska());
		@SuppressWarnings("deprecation")
		String datumPolaska = k.getLet().getDatumPolaska().getDay() + "." +  k.getLet().getDatumPolaska().getMonth() + ".2022.";
		@SuppressWarnings("deprecation")
		String datumDolaska = k.getLet().getDatumDolaska().getDay() + "." +  k.getLet().getDatumDolaska().getMonth() + ".2022.";
		params.put("datumPolaska", datumPolaska);
		params.put("datumDolaska", datumDolaska);
		params.put("cena", k.getCena());
		params.put("opis", k.getOpis());
		params.put("mesto", "Red: " + k.getMesto().getRed() + ", br: " + k.getMesto().getBroj());
		params.put("kompanija", k.getLet().getKompanija().getNaziv());
		List<Aerodrom> presedanjaList = k.getLet().getAerodroms();
		String presedanja = "";
		if (presedanjaList.isEmpty()) {
			presedanja = "Nema";
		} else {
			for (Aerodrom a : presedanjaList) {
				presedanja += a.getMesto() + ", ";
			}
			presedanja = presedanja.substring(0, presedanja.length() - 2);
		}
		params.put("presedanja", presedanja);
		params.put("datumKreiranja", k.getDatum());

		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);
		inputStream.close();
		response.setContentType("application/x-download");
		response.addHeader("Content-disposition", "attachment; filename=Karta.pdf");
		OutputStream out = response.getOutputStream();
		JasperExportManager.exportReportToPdfStream(jasperPrint, out);

	}
	
}
