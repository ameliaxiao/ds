
package amelia;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.6-1b01 
 * Generated source version: 2.2
 * 
 */
@WebService(name = "CoolService", targetNamespace = "http://Amelia/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface CoolService {


    /**
     * 
     * @param message
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "doSpyOperation", targetNamespace = "http://Amelia/", className = "amelia.DoSpyOperation")
    @ResponseWrapper(localName = "doSpyOperationResponse", targetNamespace = "http://Amelia/", className = "amelia.DoSpyOperationResponse")
    @Action(input = "http://Amelia/CoolService/doSpyOperationRequest", output = "http://Amelia/CoolService/doSpyOperationResponse")
    public String doSpyOperation(
        @WebParam(name = "message", targetNamespace = "")
        String message);

}
