package com.marlo.achang.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.marlo.achang.entities.PurchaseOrder;
import com.marlo.achang.entities.customerorder.CustomerOrder;
import com.marlo.achang.entities.customerorder.Orderline;
import com.marlo.achang.entities.productms.Supplier;
import com.marlo.achang.interfaces.PurchaseOrderRepository;
import com.marlo.achang.wsimport.SoapServer;
import com.marlo.achang.wsimport.SoapServerService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@WebMvcTest
public class ControllerTest {
  @Autowired private MockMvc mockMvc;
  @Autowired @MockBean private PurchaseOrderRepository purchaseOrderRepository;
  @Autowired private RestTemplate restTemplate;
  @Autowired private RestTemplate loadTemplate;
  @Autowired @MockBean private RabbitTemplate rabbitTemplate;
  @Autowired @MockBean private SoapServerService soapClient;
  @Mock SoapServer soapServer;
  private PurchaseOrder purchaseOrder;
  private Supplier supplier;
  private MockRestServiceServer mockServer;
  private MockRestServiceServer loadServer;
  private ObjectWriter writer;
  private CustomerOrder order;
  private Orderline orderline;
  private List<Orderline> orderlineList;
  @Mock private Controller controller;

  @Before
  public void setUp() throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    writer = mapper.writer();
    supplier = new Supplier();
    order = new CustomerOrder();
    orderline = new Orderline("Mock Product", 4);
    orderlineList = new ArrayList<>();
    orderlineList.add(orderline);
    order.setOrderLines(orderlineList);
    purchaseOrder = new PurchaseOrder(orderline);
  }

  @Test
  public void sendPurchaseOrders() throws Exception {
    mockServer = MockRestServiceServer.createServer(restTemplate);
    supplier.setSupplierName("Supplier_A");
    String responseSupplier = writer.writeValueAsString(supplier);
    String orderJson = writer.writeValueAsString(order);
    Mockito.doReturn(purchaseOrder).when(purchaseOrderRepository).save(purchaseOrder);
    Mockito.doReturn(new ResponseEntity(HttpStatus.OK))
        .when(controller)
        .sendToSupplierA(purchaseOrder);
    mockServer
        .expect(MockRestRequestMatchers.requestTo("http://product-service/products/getsupplier"))
        .andExpect(MockRestRequestMatchers.method(HttpMethod.POST))
        .andRespond(
            MockRestResponseCreators.withSuccess(
                responseSupplier, MediaType.APPLICATION_JSON_UTF8));
    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/purchaseorder/distribute")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(orderJson))
        .andExpect(status().isCreated())
        .andReturn();
  }

  @Test
  public void sendToSupplierA() {
    Mockito.doNothing()
        .when(rabbitTemplate)
        .convertAndSend("Pending-Orders", purchaseOrder.toString());
    controller.sendToSupplierA(purchaseOrder);
    Mockito.verify(controller).sendToSupplierA(purchaseOrder);
  }

  @Test
  public void sendToSupplierB() {
    loadServer = MockRestServiceServer.createServer(loadTemplate);
    controller = new Controller(loadTemplate);
    loadServer
        .expect(MockRestRequestMatchers.method(HttpMethod.POST))
        .andRespond(MockRestResponseCreators.withStatus(HttpStatus.CREATED));
    Assert.assertEquals(
        controller.sendToSupplierB(purchaseOrder).getStatusCodeValue(),
        generateResponseEntityObject(HttpStatus.CREATED).getStatusCodeValue());
  }

  @Test
  public void sendToSupplierC() {
    controller = new Controller();
    ReflectionTestUtils.setField(controller, "soapClient", soapClient);
    ReflectionTestUtils.setField(controller, "soapServer", soapServer);
    Mockito.doReturn(soapServer).when(soapClient).getSoapServerPort();
    Mockito.doReturn("TestOrder").when(soapServer).receive(purchaseOrder.toString());
    System.out.println(controller.sendToSupplierC(purchaseOrder));
  }

  private ResponseEntity generateResponseEntityObject(HttpStatus status) {
    return new ResponseEntity(status);
  }
}
