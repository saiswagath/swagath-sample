package com.demo.intuit.repository;

import com.demo.intuit.exception.CraftDemoServicesException;
import com.demo.intuit.model.inventory.EcommerceSite;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class JdbcEcommerceRepositoryTest {

    @Mock
    EcommerceRepository ecommerceRepository;

    @Test
    @DisplayName("Should throw exception when invalid store Id is given")
    public void shouldThrowExceptionForInvalidStoreid() {
        JdbcEcommerceRepository ecommerceRepository = new JdbcEcommerceRepository() ;
        assertThatThrownBy(() -> {
            ecommerceRepository.getProductsForStore(new Long(-1));
        }).isInstanceOf(CraftDemoServicesException.class)
                .hasMessage("Invalid storeId given in JdbcEcommerceRepository::getProductsForStore()");
    }


    @Test
    @DisplayName("Should Fetch Ecommerce Store with given Id")
    public void shouldTestFetchingEcommerceStoreById() {
        EcommerceSite ecommerceSite = new EcommerceSite();
        ecommerceSite.setStoreName("Flipkart");

        Mockito.when(ecommerceRepository.getProductsForStore(new Long(4))).thenReturn(ecommerceSite);

        EcommerceSite ecommerceSiteResp = ecommerceRepository.getProductsForStore(new Long(4));
        Assertions.assertThat(ecommerceSiteResp.getStoreName()).isEqualTo(ecommerceSite.getStoreName());
    }

}