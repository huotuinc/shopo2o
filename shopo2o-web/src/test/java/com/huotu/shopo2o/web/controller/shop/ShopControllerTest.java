package com.huotu.shopo2o.web.controller.shop;

import com.alibaba.fastjson.JSON;
import com.huotu.shopo2o.service.entity.MallCustomer;
import com.huotu.shopo2o.service.entity.store.Store;
import com.huotu.shopo2o.service.entity.store.SupShopCat;
import com.huotu.shopo2o.service.enums.CustomerTypeEnum;
import com.huotu.shopo2o.web.CommonTestBase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MvcResult;

import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by hxh on 2017-09-26.
 */
public class ShopControllerTest extends CommonTestBase {
    private static String BASE_URL = "/shop";
    private MallCustomer user;
    private String userName;
    private String passWord;
    List<SupShopCat> supShopCats = new ArrayList<>();
    SupShopCat addCat;

    @Before
    public void setInfo() throws Exception {
        userName = UUID.randomUUID().toString();
        passWord = UUID.randomUUID().toString();
        MallCustomer mallCustomer = new MallCustomer();
        mallCustomer.setNickName(UUID.randomUUID().toString());
        mallCustomer.setUsername(userName);
        mallCustomer.setPassword(passwordEncoder.encode(passWord));
        mallCustomer.setCustomerType(CustomerTypeEnum.STORE);
        MallCustomer storeCustomer = mallCustomerRepository.saveAndFlush(mallCustomer);
        Store store = new Store();
        store.setId(mallCustomer.getCustomerId());
        store.setCustomer(mallCustomer);
        store.setDeleted(false);
        store.setDisabled(false);
        storeCustomer.setStore(store);
        user = mallCustomerRepository.saveAndFlush(storeCustomer);
        for (int i = 0; i < 5; i++) {
            SupShopCat shopCat = mockSupShopCat(user);
            supShopCats.add(shopCat);
        }
        addCat = mockSupShopCatWithoutSave(user);
    }

    @Test
    public void testGetCatList() throws Exception {
        MockHttpSession mockHttpSession = loginAs(userName, passWord);
        MvcResult mvcResult = mockMvc.perform(post(BASE_URL + "/getCatList").session(mockHttpSession))
                .andExpect(model().attributeExists("catList"))
                .andExpect(status().isOk())
                .andReturn();
        List<SupShopCat> catList = (List<SupShopCat>) mvcResult.getModelAndView().getModel().get("catList");
        Assert.assertNotNull(catList);
        Assert.assertTrue(catList.size() == supShopCats.size());
        for (int i = 10; i < supShopCats.size(); i++) {
            Assert.assertEquals(catList.get(i).getCatId(), supShopCats.get(i).getCatId());
        }
    }

    @Test
    public void testCatDetail() throws Exception {
        MockHttpSession mockHttpSession = loginAs(userName, passWord);
        mockMvc.perform(post(BASE_URL + "/catDetail/" + supShopCats.get(0).getCatId()).session(mockHttpSession))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void testCatDelete() throws Exception {
        MockHttpSession mockHttpSession = loginAs(userName, passWord);
        mockMvc.perform(post(BASE_URL + "/removeCat/" + supShopCats.get(0).getCatId()).session(mockHttpSession))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void testModifyCat() throws Exception {
        MockHttpSession mockHttpSession = loginAs(userName, passWord);
        MvcResult mvcResult = mockMvc.perform(post(BASE_URL + "/modifyCat/" + supShopCats.get(0).getCatId()).session(mockHttpSession))
                .andExpect(model().attributeExists("catList"))
                .andExpect(model().attributeExists("updateCat"))
                .andExpect(status().isOk())
                .andReturn();
        SupShopCat catList = (SupShopCat) mvcResult.getModelAndView().getModel().get("updateCat");
        Assert.assertTrue(catList.getCatId() == supShopCats.get(0).getCatId());
    }

    @Test
    public void testAddCat() throws Exception {
        MockHttpSession mockHttpSession = loginAs(userName, passWord);
        mockMvc.perform(post(BASE_URL + "/addCat").session(mockHttpSession)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON.toJSONString(addCat)))
                .andExpect(status().isOk())
                .andReturn();
    }
}
