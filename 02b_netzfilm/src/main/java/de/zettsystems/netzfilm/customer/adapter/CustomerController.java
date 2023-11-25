package de.zettsystems.netzfilm.customer.adapter;

import de.zettsystems.netzfilm.customer.application.CustomerService;
import de.zettsystems.netzfilm.customer.values.CustomerCreationTo;
import de.zettsystems.netzfilm.customer.values.CustomerTo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping("/customers")
    public String customers(Model model) {
        final List<CustomerTo> customers = customerService.getAllCustomers();
        model.addAttribute("customers", customers);
        model.addAttribute("customer", new CustomerCreationTo(null, null, null, null));
        return "customers/customers";
    }

    @PostMapping("/addcustomer")
    public String addCustomer(@Valid @ModelAttribute("customer") CustomerCreationTo customer, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            final List<CustomerTo> customers = customerService.getAllCustomers();
            model.addAttribute("customers", customers);
            return "customers/customers";
        }
        customerService.addCustomer(customer);
        redirectAttributes.addFlashAttribute("message", "Successfully added " + customer.name() + " " + customer.lastName());
        return "redirect:/customers";
    }
}