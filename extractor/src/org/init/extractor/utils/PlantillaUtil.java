package org.init.extractor.utils;

import java.io.StringWriter;
import java.util.ArrayList;

import org.eclipse.mylyn.wikitext.core.parser.MarkupParser;
import org.eclipse.mylyn.wikitext.core.parser.builder.HtmlDocumentBuilder;
import org.eclipse.mylyn.wikitext.core.parser.markup.MarkupLanguage;
import org.eclipse.mylyn.wikitext.core.util.ServiceLocator;
import org.init.extractor.wikipedia.Pagina;
import org.init.extractor.wikipedia.Plantilla;
import org.init.extractor.wikipedia.eventos.AtributoEventoMemoria;
import org.init.extractor.wikipedia.eventos.EventoMemoria;

import net.sf.json.JSONObject;

/**
 * Clase de utilidades para trabajar con las plantillas de Wikipedia
 * 
 * @author fbobbio
 *
 */
public class PlantillaUtil {	
	
	/**
	 * Método que recoge un atributo del infobox de la plantilla y devuelve su
	 * valor
	 * 
	 * @param completo
	 *            el string completo del contenido de wikipedia
	 * @param att
	 *            el nombre del atributo que se desea obtener
	 * @return el valor del atributo buscado
	 */
	public static String getAtributoPlantilla(String completo, String att) {
		try {
			int index = completo.indexOf(att);
			String aux = completo.substring(index, completo.length());
			String ret = aux.substring(aux.indexOf("=") + 1, aux.indexOf("\n")).trim();
			return ret;
		} catch (StringIndexOutOfBoundsException e) {
			return "sin datos";
		}
	}
	
	public static ArrayList<JSONObject> convertirPlantillaAJSON(Plantilla plantilla)
	{
		// Creo el jsonArray a devolver 
		ArrayList<JSONObject> array = new ArrayList<JSONObject>();

		// Itero el array y creo un nuevo json
		for (Pagina p : plantilla.getPaginasExitosas()) {
			JSONObject aux = new JSONObject();
			EventoMemoria e = p.getEvento();
			
			for (AtributoEventoMemoria att : e.getAtributos())
			{
				aux.put(att.getNombre(), att.getValorFinal());
			}
			array.add(aux);
		}		
		return array;		
	}

	/**
	 * Método que parsea un string según un markupLanguaje a HTML
	 * 
	 * @param wikiText
	 * @return
	 */
	public static String parseWikiTextByLanguageToHTML(String wikiText) {
		MarkupLanguage language = ServiceLocator.getInstance()
				.getMarkupLanguage("MediaWiki");
		StringWriter writer = new StringWriter();
		HtmlDocumentBuilder builder = new HtmlDocumentBuilder(writer);
		MarkupParser parser = new MarkupParser(language, builder);
		parser.parse(wikiText);
		return writer.toString();
	}

	/** 
	 * Método que limpia los tags HTML del string parseado desde el formato wikimedia
	 * @param descripcionBreve
	 * @return
	 */
	private static String limpiarHTML(String descripcionBreve) {
		return descripcionBreve.substring(descripcionBreve.indexOf("<body>") + 6, descripcionBreve.indexOf("</body>"));
	}

	/**
	 * Método que completa como se debe el href de los links generados desde wikipedia
	 * @param descripcionBreve
	 * @return
	 */
	private static String reemplazarLinks(String descripcionBreve) {
		//TODO: definir bien este método
		throw new RuntimeException("Método sin implementar reemplazarLinks(String)");
	}

}
