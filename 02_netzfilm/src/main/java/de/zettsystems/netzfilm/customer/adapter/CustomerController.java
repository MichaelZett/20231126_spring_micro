package de.zettsystems.netzfilm.customer.adapter;

import de.zettsystems.netzfilm.customer.application.CustomerService;
import de.zettsystems.netzfilm.customer.values.CustomerTo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping("/customers")
    public String customers(Model model) {
        final List<CustomerTo> customers = customerService.getAllCustomers();
        model.addAttribute("customers", customers);
        return "customers/customers";
    }

}