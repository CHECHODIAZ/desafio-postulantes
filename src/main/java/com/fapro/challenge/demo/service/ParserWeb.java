package com.fapro.challenge.demo.service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.json.simple.JSONArray;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

@Service
public class ParserWeb {
	
static String Url = "https://www.sii.cl/servicios_online/1047-nomina_inst_financieras-1714.html";
static File input = new File("./src/main/java/com/fapro/challenge/demo/service/copia.html");
static Document html = null;

@Produces(MediaType.APPLICATION_JSON)
public static Document getHTML(String url) {

try {
	html = Jsoup.connect(url).timeout(800000).get();
} catch (IOException e) {
	System.out.println("No se cargo el codigo html");
	e.printStackTrace();
}
return html;

}

@Produces(MediaType.APPLICATION_JSON)
public static Document getHTMLlocal(File input) {

try {
	html = Jsoup.parse(input, "UTF-8");
} catch (IOException e) {
	System.out.println("No se cargo el codigo html");
	e.printStackTrace();
}
return html;

}


@Produces(MediaType.APPLICATION_JSON)
public Map<String,String> parserHtmlsencillo(String url, String seccion, String sec ) throws IOException {
   Map<String,String> re = new HashMap<String,String>();
   Document doc = getHTML(Url);
   Elements body = doc.select(seccion);
   String sencillo = body.select(sec).text();
   System.out.println(sencillo);
   re.put("descripcion", sencillo);
return re;

}


@Produces(MediaType.APPLICATION_JSON)
public Map<String,String> parserHtmlbasicolocal(File input, String seccion) throws IOException {
	Map<String,String> re = new HashMap<String,String>();
   Document doc = getHTMLlocal(input);
   Elements body = doc.select(seccion);
   String basico = body.text();
   System.out.println(basico);
   re.put("titulo", basico);
return re ;

}

public JSONArray parserHtmlcompuestoLocal(File input, String seccion, String sec) throws IOException {
   
   JSONArray elemento = new JSONArray();
   Document doc = getHTMLlocal(input);
   Elements body = doc.select(seccion);

   for(Element b : body ) {
     elemento.add(b.select(sec).text());
     System.out.println(elemento);

    }
return elemento;
}


}
