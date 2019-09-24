package com.marlo.achang.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.marlo.achang.entities.CustomerOrder;
import com.marlo.achang.interfaces.OrderRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@WebMvcTest
public class ControllerTest {
  @Autowired private MockMvc mockMvc;
  @Autowired @MockBean private OrderRepository orderRepository;
  @Autowired private RestTemplate restTemplate;
  private List<CustomerOrder> orders;
  private CustomerOrder order;
  private MockRestServiceServer mockServer;
  private ObjectWriter writer;

  @Before
  public void setUp() throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    writer = mapper.writer();
    order = new CustomerOrder();
    orders = new ArrayList<>();
    orders.add(new CustomerOrder());
    orders.add(new CustomerOrder());
    mockServer = MockRestServiceServer.createServer(restTemplate);
  }

  @After
  public void tearDown() throws Exception {
    writer = null;
    orders = null;
    order = null;
  }

  @Test
  public void getAllOrders() throws Exception {
    Mockito.doReturn(orders).when(orderRepository).findAll();
    MvcResult result =
        mockMvc.perform(MockMvcRequestBuilders.get("/customerorder/all")).andReturn();
    String orderString = writer.writeValueAsString(orders);
    Assert.assertEquals(orderString, result.getResponse().getContentAsString());
  }

  @Test
  public void submitOrder() throws Exception {
    String orderString = writer.writeValueAsString(order);
    mockServer
        .expect(MockRestRequestMatchers.requestTo("http://product-service/products/validate"))
        .andExpect(MockRestRequestMatchers.method(HttpMethod.POST))
        .andRespond(MockRestResponseCreators.withStatus(HttpStatus.CREATED));
    mockServer
        .expect(
            MockRestRequestMatchers.requestTo(
                "http://purchaseorder-service/purchaseorder/distribute"))
        .andExpect(MockRestRequestMatchers.method(HttpMethod.POST))
        .andRespond(MockRestResponseCreators.withStatus(HttpStatus.CREATED));
    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/customerorder/submit")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(orderString))
        .andExpect(status().isCreated())
        .andReturn();
  }
}
