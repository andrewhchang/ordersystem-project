package com.marlo.achang.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.marlo.achang.entities.Product;
import com.marlo.achang.entities.Supplier;
import com.marlo.achang.entities.customerorder.CustomerOrder;
import com.marlo.achang.entities.customerorder.Orderline;
import com.marlo.achang.repositories.ProductRepository;
import com.marlo.achang.repositories.SupplierRepository;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@WebMvcTest
public class ControllerTest {
  @Autowired private MockMvc mockMvc;
  @Autowired @MockBean private ProductRepository productRepository;
  @Autowired @MockBean private SupplierRepository supplierRepository;
  @Autowired private RestTemplate restTemplate;
  private List<Product> products;
  private ObjectWriter writer;
  private CustomerOrder order;
  private Product product;

  @Before
  public void setUp() throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    writer = mapper.writer();
    products = new ArrayList<>();
    product = new Product("Mock Product");
    products.add(product);

    order = new CustomerOrder();
    List<Orderline> orderlineList = new ArrayList<>();
    Orderline testLine = new Orderline("Mock Product", 8);
    orderlineList.add(testLine);
    order.setOrderLines(orderlineList);
  }

  @After
  public void tearDown() throws Exception {
    writer = null;
    products = null;
    product = null;
    order = null;
  }

  @Test
  public void getAllProducts_success() throws Exception {
    Mockito.doReturn(products).when(productRepository).findAll();
    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/products/all")).andReturn();
    String productString = writer.writeValueAsString(products);
    Assert.assertEquals(productString, result.getResponse().getContentAsString());
  }

  @Test
  public void getProductsBySupplier_existingSupplier_success() throws Exception {
    String mockExistingSupplier = "ExistingTestSupplier";
    Mockito.doReturn(products)
        .when(productRepository)
        .findByProductSuppliers_supplierNameIgnoreCase(mockExistingSupplier);
    MvcResult result =
        mockMvc
            .perform(MockMvcRequestBuilders.get("/products/" + mockExistingSupplier))
            .andReturn();
    String productString = writer.writeValueAsString(products);
    Assert.assertEquals(productString, result.getResponse().getContentAsString());
  }

  @Test
  public void validateProduct() throws Exception {
    String orderJson = writer.writeValueAsString(order);
    Mockito.doReturn(product).when(productRepository).findByProductName("Mock Product");
    MvcResult result =
        mockMvc
            .perform(
                MockMvcRequestBuilders.post("/products/validate")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(orderJson))
            .andReturn();
    Assert.assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
  }

  @Test
  public void getSupplier() throws Exception {
    String orderProduct = writer.writeValueAsString(product);
    Mockito.doReturn(new Supplier())
        .when(supplierRepository)
        .findBySupplierProductsContaining(product);
    MvcResult result =
        mockMvc
            .perform(
                MockMvcRequestBuilders.post("/products/getsupplier")
                    .content(product.getProductName()))
            .andReturn();
    Assert.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
  }
}
