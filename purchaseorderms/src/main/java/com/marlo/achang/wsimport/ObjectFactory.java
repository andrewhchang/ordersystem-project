package com.marlo.achang.wsimport;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

/**
 * This object contains factory methods for each Java content interface and Java element interface
 * generated in the com.marlo.achang.wsimport package.
 *
 * <p>An ObjectFactory allows you to programatically construct new instances of the Java
 * representation for XML content. The Java representation of XML content can consist of schema
 * derived interfaces and classes representing the binding of schema type definitions, element
 * declarations and model groups. Factory methods for each of these are provided in this class.
 */
@XmlRegistry
public class ObjectFactory {

  private static final QName _Receive_QNAME = new QName("http://achang.marlo.com/", "receive");
  private static final QName _ReceiveResponse_QNAME =
      new QName("http://achang.marlo.com/", "receiveResponse");

  /**
   * Create a new ObjectFactory that can be used to create new instances of schema derived classes
   * for package: com.marlo.achang.wsimport
   */
  public ObjectFactory() {}

  /** Create an instance of {@link Receive } */
  public Receive createReceive() {
    return new Receive();
  }

  /** Create an instance of {@link ReceiveResponse } */
  public ReceiveResponse createReceiveResponse() {
    return new ReceiveResponse();
  }

  /**
   * Create an instance of {@link JAXBElement }{@code <}{@link Receive }{@code >}
   *
   * @param value Java instance representing xml element's value.
   * @return the new instance of {@link JAXBElement }{@code <}{@link Receive }{@code >}
   */
  @XmlElementDecl(namespace = "http://achang.marlo.com/", name = "receive")
  public JAXBElement<Receive> createReceive(Receive value) {
    return new JAXBElement<Receive>(_Receive_QNAME, Receive.class, null, value);
  }

  /**
   * Create an instance of {@link JAXBElement }{@code <}{@link ReceiveResponse }{@code >}
   *
   * @param value Java instance representing xml element's value.
   * @return the new instance of {@link JAXBElement }{@code <}{@link ReceiveResponse }{@code >}
   */
  @XmlElementDecl(namespace = "http://achang.marlo.com/", name = "receiveResponse")
  public JAXBElement<ReceiveResponse> createReceiveResponse(ReceiveResponse value) {
    return new JAXBElement<ReceiveResponse>(
        _ReceiveResponse_QNAME, ReceiveResponse.class, null, value);
  }
}
