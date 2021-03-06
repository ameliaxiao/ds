/**
 * SpyMessage.java
 * Tianyue Xiao
 * tianyuex@andrew.cmu.edu
 */


import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

/**
 *
 * @author Amelia
 */
public class SpyMessage {

    private Spy curSpy;
    private Document spyDoc;
    /**
     * constructor to get the spy message instance from a string
     * @param message the string containing information about operation and spy
     */
    SpyMessage(String message) {
        spyDoc = getDocument(message);
        spyDoc.getDocumentElement().normalize();
        String name = spyDoc.getElementsByTagName("name").item(0).getTextContent();
        String spyTitle = spyDoc.getElementsByTagName("spyTitle").item(0).getTextContent();
        String location = spyDoc.getElementsByTagName("location").item(0).getTextContent();
        String password = spyDoc.getElementsByTagName("password").item(0).getTextContent();
        curSpy = new Spy(name, spyTitle, location, password);
    }
    /**
     * constructor to get the spy message instance from spy and operation
     * @param curSpy the spy instance 
     * @param operation string representing the operation
     */
    SpyMessage(Spy curSpy) {
        this.curSpy = curSpy;
    }
    /**
     * method to get document from a string
     * @param xmlString xml string
     * @return document built on the string
     */
    private Document getDocument(String xmlString) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        Document spyDoc = null;
        try {
            builder = factory.newDocumentBuilder();
            spyDoc = builder.parse(new InputSource(new StringReader(xmlString)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return spyDoc;
    }

    /**
     * method to get the spy
     * @return 
     */
    public Spy getSpy(){
        return curSpy;
    }
}
