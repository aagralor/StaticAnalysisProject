package com.example.app.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.example.app.domain.html.BugCollectionHtmlReport;
import com.example.app.domain.html.VulnInstance;

public final class HtmlParser {

	private static final int BUFFER_SIZE = 4096;

	private HtmlParser() {
	}

	public static BugCollectionHtmlReport parseToBugCollection(String filePath)
			throws FileNotFoundException, IOException {

		File fileHtml = new File(filePath);
		String html = XmlParser.inputStreamToString(new FileInputStream(fileHtml));
		Document doc = Jsoup.parse(html);

		Elements warningTableElements = doc.getElementsByAttributeValueStarting("class", "tablerow");

		List<VulnInstance> vulnList = warningTableElements.stream().map(warn -> extractVulnInstance(doc, warn)).filter(vi -> vi != null)
				.collect(Collectors.toList());

		return new BugCollectionHtmlReport(vulnList);
	}

	private static VulnInstance extractVulnInstance(Document doc, Element elem) {

		if (elem.childNodeSize() < 3) {
			return null;
		}
		if (!(elem.child(1).text().equalsIgnoreCase("HIGH") || elem.child(1).text().equalsIgnoreCase("MEDIUM")
				|| elem.child(1).text().equalsIgnoreCase("LOW"))) {
			return null;
		}

		VulnInstance vuln = new VulnInstance();

		vuln.setName(elem.child(0).text());
		vuln.setReference(elem.child(0).child(0).attr("href"));
		vuln.setPriority(elem.child(1).text());
		vuln.setText(elem.child(2).text());
		vuln.setTextHtml(elem.child(2).html());
		vuln.setWarningTextHtml(extractWarningType(doc, elem.child(0).child(0).attr("href")));
		List<String> sourceLineList = Arrays.stream(elem.child(2).text().split(" At "))
				.filter(s -> s.matches(".*:\\[.*\\].*"))
				.map(s -> s.substring(0, s.indexOf("]") + 1))
				.collect(Collectors.toList());
		vuln.setSourceLineList(sourceLineList);

		return vuln;
	}

	private static String extractWarningType(Document doc, String vulnReference) {
		Elements warningTypeElement = doc.getElementsByAttributeValueStarting("name", vulnReference.substring(1));
		Element warningTypeParent = warningTypeElement.parents().first();
		StringBuilder htmlBuilder = new StringBuilder();
		htmlBuilder.append(warningTypeParent.outerHtml());
		boolean lastElement = false;
		Element realElement = warningTypeParent.nextElementSibling();
		while (!lastElement) {
			htmlBuilder.append(realElement.outerHtml());

			realElement = realElement.nextElementSibling();
			if (realElement == null) {
				lastElement = true;
				continue;
			}
			Elements checkElement = realElement.getElementsByAttribute("name");
			lastElement = !checkElement.isEmpty();
		}

		return htmlBuilder.toString();
	}

//	private static String inputStreamToString(InputStream is) throws IOException {
//		StringBuilder sb = new StringBuilder();
//		String line;
//		BufferedReader br = new BufferedReader(new InputStreamReader(is));
//		while ((line = br.readLine()) != null) {
//			sb.append(line);
//		}
//		br.close();
//		return sb.toString();
//	}

	public static void main(String[] args) throws FileNotFoundException, IOException {
		System.out.println("Hello world");

//		File fileHtml = new File("project/application_report_HTML.htm");
		File fileHtml = new File("project/application_report_non_nested_plain_HTML.htm");
		String html = XmlParser.inputStreamToString(new FileInputStream(fileHtml));

		BugCollectionHtmlReport bugCollection = parseToBugCollection(
				"project/application_report_non_nested_plain_HTML.htm");

		Document doc = Jsoup.parse(html);
//		Element body = doc.body();
//		int nodeCount = body.childNodeSize();

		// Pruebas
//		Elements tables = doc.select("table");
//		Elements warnigTable = doc.getElementsByAttributeValue("class", "warningtable");
//		doc.select("p").forEach(System.out::println);

		// Parseo de elementos de una incidencia
		Elements warningTableElements = doc.getElementsByAttributeValueStarting("class", "tablerow");

		Element firstElement = warningTableElements.get(7);

		String vulnName = firstElement.child(0).text();
		String vulnReference = firstElement.child(0).child(0).attr("href");
		String vulnPriority = firstElement.child(1).text();
		String vulnInstance = firstElement.child(2).text();
		String vulnInstanceHtml = firstElement.child(2).html();

		String[] vulnTextSplit = vulnInstance.split(" At ");
		List<String> fileInstance = Arrays.stream(vulnTextSplit).filter(s -> s.matches(".*:\\[.*\\]$")).collect(Collectors.toList());
		String lineInstance = "";

		// Parseo de contenido de una amenaza
//		Elements warningTypes = doc.getElementsByAttribute("name");
//		List<Integer> warningIndexes = warningTypes.stream().map(wt -> wt.parent().siblingIndex())
//				.collect(Collectors.toList());
//		List<Element> warnignTypesElems = warningIndexes.stream().map(i -> body.child(i)).collect(Collectors.toList());

//		Element elem13 = body.child(13);
//		Element elem17 = body.child(17);
//		Element elem18 = body.child(18);
//		Element elem28 = body.child(28);
//		Element elem43 = body.child(43);
//		Element elem63 = body.child(63);
//		Element elem92 = body.child(92);
//		Element elem108 = body.child(108);
//		Element elem116 = body.child(116);

//		Elements warningTypeElement = doc.getElementsByAttributeValueStarting("name", vulnReference.substring(1));
//		Element warningTypeParent = warningTypeElement.parents().first();
////		int warningTypeIndex = warningTypeParent.siblingIndex();
//		StringBuilder htmlBuilder = new StringBuilder();
//		htmlBuilder.append(warningTypeParent.outerHtml());
//		boolean lastElement = false;
//		Element realElement = warningTypeParent.nextElementSibling();
//		while (!lastElement) {
//			htmlBuilder.append(realElement.outerHtml());
//
//			realElement = realElement.nextElementSibling();
//			Elements checkElement = realElement.getElementsByAttribute("name");
//			lastElement = !checkElement.isEmpty();
//		}
//		;
//
//		String warningTypeHtml = htmlBuilder.toString();

//		Elements checkElement = warningTypeParent.getElementsByAttribute("name");
//		Boolean isElementEmpty = !checkElement.isEmpty();
//		Boolean isElementEmptyWithHasAttr = warningTypeParent.hasAttr("name");

//		Element nextElement = warningTypeParent.nextElementSibling();
//		Element nextElement2 = nextElement.nextElementSibling();
//		Element nextElement3 = nextElement2.nextElementSibling();
//		Element nextElement4 = nextElement3.nextElementSibling();
//		Element nextElement5 = nextElement4.nextElementSibling();
//		Element nextElement6 = nextElement5.nextElementSibling();
//		Element nextElement7 = nextElement6.nextElementSibling();
//		Element nextElement8 = nextElement7.nextElementSibling();
//		Element nextElement9 = nextElement8.nextElementSibling();
//		Element nextElement10 = nextElement9.nextElementSibling();
//		Element nextElement11 = nextElement10.nextElementSibling();
//		Element nextElement12 = nextElement11.nextElementSibling();
//		Element nextElement13 = nextElement12.nextElementSibling();
//		Element nextElement14 = nextElement13.nextElementSibling();
//		Element nextElement15 = nextElement14.nextElementSibling();
//		Element nextElement16 = nextElement15.nextElementSibling();

//		Elements checkElement2 = nextElement15.getElementsByAttribute("name");
//		Boolean isElementEmpty2 = !checkElement2.isEmpty();

//		Elements checkElement3 = nextElement16.getElementsByAttribute("name");
//		Boolean isElementEmpty3 = !checkElement3.isEmpty();

		System.out.println("Bye world");
	}

}
