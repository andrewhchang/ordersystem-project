package com.marlo.achang;

import javax.xml.ws.Endpoint;

public class RunnerC {
  public static void main(String[] args) {
    String bindingURI = "http://localhost:3333/soapService";
    SoapServer soapService = new SoapServer();
    Endpoint.publish(bindingURI, soapService);
  }
}
