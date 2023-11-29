package de.zettsystems.netzfilm.customer.adapter;


import de.zettsystems.netzfilm.customer.application.CustomerService;
import de.zettsystems.netzfilm.customer.values.CustomerCreationTo;
import de.zettsystems.netzfilm.customer.values.CustomerTo;
import de.zettsystems.timeutil.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = CustomerRestController.class)
class CustomerRestControllerTest {

    MockMvc mvc;

    @MockBean
    CustomerService customerService;
    UUID uuid;

    @BeforeEach
    void setup(WebApplicationContext webApplicationContext) {
        this.mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();
        uuid = UUID.randomUUID();
        CustomerTo bernd = new CustomerTo(uuid, "Bernd", "das Brot", TimeUtil.today(), Boolean.TRUE, 13);

        List<CustomerTo> customers = Arrays.asList(bernd);

        given(customerService.getAllCustomers()).willReturn(customers);
        given(customerService.getCustomer(uuid)).willReturn(bernd);
    }

    @Test
    void shouldFindAllCustomers() throws Exception {

        mvc.perform(get("/api/customers/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].uuid").value(uuid.toString()))
                .andExpect(jsonPath("$.[0].name").value("Bernd"))
                .andExpect(jsonPath("$.[0].lastName").value("das Brot"))
                .andExpect(jsonPath("$.[0].version").value(13))
                .andExpect(jsonPath("$.[0].vip").value(true))
                .andExpect(jsonPath("$.[0].birthdate").value(TimeUtil.today().toString()));

    }

    @Test
    void shouldGetCustomer() throws Exception {
        mvc.perform(get("/api/customers/" + uuid.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uuid").value(uuid.toString()))
                .andExpect(jsonPath("$.name").value("Bernd"))
                .andExpect(jsonPath("$.lastName").value("das Brot"))
                .andExpect(jsonPath("$.version").value(13))
                .andExpect(jsonPath("$.vip").value(true))
                .andExpect(jsonPath("$.birthdate").value(TimeUtil.today().toString()));
    }

    @Test
    void shouldUpdate() throws Exception {
        String body = """
                { "uuid": "%s",\
                  "name": "%s",\
                  "lastName": "%s",\
                  "birthdate": "%s",\
                  "vip": "%s",\
                  "version": "%s"\
                }""".formatted(uuid.toString(), "Bernd", "das Brot", LocalDate.of(2023, 6, 1).toString(), true, 13);
        RequestBuilder request = MockMvcRequestBuilders
                .put("/api/customers/" + uuid.toString())
                .accept("*/*")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(status().isOk());

        ArgumentCaptor<CustomerTo> captor = ArgumentCaptor.forClass(CustomerTo.class);
        verify(customerService).updateCustomer(captor.capture());
        assertThat(captor.getValue().birthdate()).isEqualTo(LocalDate.of(2023, 6, 1));
    }

    @Test
    void shouldDelete() throws Exception {
        mvc.perform(delete("/api/customers/" + uuid.toString()))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldCreate() throws Exception {
        String body = """
                { "name": "%s",\
                  "lastName": "%s",\
                  "birthdate": "%s",\
                  "vip": "%s"\
                }""".formatted("Bernd 2", "das Brot", LocalDate.of(2023, 6, 1).toString(), true);
        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/customers/")
                .accept("*/*")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON);

        final UUID uuid = UUID.randomUUID();
        when(customerService.addCustomer(any(CustomerCreationTo.class))).thenReturn(uuid);

        mvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(header().string("location", containsString("/customers/%s".formatted(uuid.toString()))));

        ArgumentCaptor<CustomerCreationTo> captor = ArgumentCaptor.forClass(CustomerCreationTo.class);
        verify(customerService).addCustomer(captor.capture());
        assertThat(captor.getValue().birthdate()).isEqualTo(LocalDate.of(2023, 6, 1));
    }
}