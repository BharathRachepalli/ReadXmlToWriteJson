package Services;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import Dao.SendXmlToDatabase;
import Model.Author;
import Model.Book;

public class FetchXmlData {
	public void getBookData(String xml) {
		List<Book> bookList = new ArrayList<Book>();
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
        DocumentBuilder builder;
        Document doc;
		try {
			builder = factory.newDocumentBuilder();  
			doc = builder.parse( new InputSource( new StringReader( xml ) ) );
			//printing Root Element
			doc.getDocumentElement().normalize();  
//			System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
			
			NodeList nodeList = doc.getElementsByTagName("book");
			for(int i=0;i<nodeList.getLength();i++) {
				Book b = new Book();
				
				Node node = nodeList.item(i);
//				System.out.println("\nNode Name :" + node.getNodeName());
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element bElement = (Element) node;
//					System.out.println("id: "+ bElement.getElementsByTagName("id").item(0).getTextContent());
//					System.out.println("title: "+ bElement.getElementsByTagName("title").item(0).getTextContent());
//					System.out.println("price: "+ bElement.getElementsByTagName("price").item(0).getTextContent());
					
					b.setBookId(Integer.parseInt(bElement.getElementsByTagName("id").item(0).getTextContent()));
					b.setBookTitle(bElement.getElementsByTagName("title").item(0).getTextContent());
					b.setBookPrice(Integer.parseInt(bElement.getElementsByTagName("price").item(0).getTextContent()));
					
					NodeList authors = bElement.getElementsByTagName("authors");
					List<Author> authorList = new ArrayList<Author>();
					for(int j=0;j<authors.getLength();j++) {
						Author a = new Author();
						Node author = authors.item(j);
//						System.out.println("\nNode Name :" + author.getNodeName());
						if(author.getNodeType() == Node.ELEMENT_NODE) {
							Element aElement = (Element) author;
//							System.out.println("id: "+ aElement.getElementsByTagName("id").item(0).getTextContent());
//							System.out.println("id: "+ aElement.getElementsByTagName("name").item(0).getTextContent());
							
							a.setAuthorId(Integer.parseInt(aElement.getElementsByTagName("id").item(0).getTextContent()));
							a.setAuthorName(aElement.getElementsByTagName("name").item(0).getTextContent());
							authorList.add(a);
						}
						
					}
					b.setBookAuthors(authorList);
				}
				
				bookList.add(b);
//				System.out.println("END OF BOOK11111111111111111111111111111111111111111111111111111111111111111");
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		new SendXmlToDatabase().addToDataBase(bookList);
		new FetchJsonFromList().getJson(bookList);
//		for(Book b : bookList) {
//			System.out.println("i");
//			System.out.println(b);
//		}
		

	}
}
