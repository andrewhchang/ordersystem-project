package com.marlo.achang;

import javax.jws.WebMethod;
import javax.jws.WebService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebService
public class SoapServer {
  @WebMethod
  public String receive(String order) {
    log.info("Received {}", order);
    return order;
  }
}
