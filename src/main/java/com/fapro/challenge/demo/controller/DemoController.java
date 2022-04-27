package com.fapro.challenge.demo.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fapro.challenge.demo.service.ParserWeb;

@RestController
@RequestMapping("/parserweb")
public class DemoController {
	
	private File input = new File("./src/main/java/com/fapro/challenge/demo/service/copia.html");
	private String Url = "https://www.sii.cl/servicios_online/1047-nomina_inst_financieras-1714.html";
	private Map<String, String> titulo = new JSONObject();
	private Map<String, String> descripcion = new JSONObject();;
	private Map<String, String> cabezatabla = new JSONObject();;
	private List<String> cuerpotabla = new ArrayList();
	
	 
   @GetMapping("/titulo")
   @Produces(MediaType.APPLICATION_JSON)
   public String parsertitle() {
 
		    try {
				titulo = new ParserWeb().parserHtmlbasicolocal(input, "h2");
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		    
			    Map<String, String>  cadenaresp = titulo;
		return cadenaresp.toString();
		 
	 }
	 
	@GetMapping("/descripcion")
	@Produces(MediaType.APPLICATION_JSON)
	public String parserdescrip() {
	    	
	    	try {
				descripcion = new ParserWeb().parserHtmlsencillo(Url, "div.contenido", "p");
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			return descripcion.toString();
		 
	 }
	    
	@GetMapping("/tablacabeza")
	@Produces(MediaType.APPLICATION_JSON)
	public String parsertable() {
	    	
	    	try {
				cabezatabla = new ParserWeb().parserHtmlbasicolocal(input, "th");;
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			
			return cabezatabla.toString();
	 
	 }
	@GetMapping("/tablacuerpo")
	@Produces(MediaType.APPLICATION_JSON)
    public List<String> parsertablacuerpo() {
	    
			try {
				cuerpotabla = new ParserWeb().parserHtmlcompuestoLocal(input, "tbody > tr", "td");
				
			} catch (IOException e) {
				
				e.printStackTrace();
			}
	  return cuerpotabla;
	 
	 }
	
	@Produces(MediaType.APPLICATION_JSON)   
	@GetMapping("/resultado")
    public List<String> todo() {
		List<String> tdo = new JSONArray();
				tdo.add(parsertitle());
				tdo.add(parserdescrip());
				tdo.add(parsertable());
				tdo.addAll(parsertablacuerpo());
	  return tdo;
	 }

}
