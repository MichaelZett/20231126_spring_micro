package de.zettsystems.netzfilm.customer.adapter;

import de.zettsystems.netzfilm.customer.application.CustomerService;
import de.zettsystems.netzfilm.customer.values.CustomerCreationTo;
import de.zettsystems.netzfilm.customer.values.CustomerTo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.UUID;

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

    @GetMapping("editcustomer/{uuid}")
    public String showCustomerUpdateForm(@PathVariable("uuid") UUID uuid, Model model) {
        CustomerTo customer = customerService.getCustomer(uuid);
        model.addAttribute("customer", customer);
        return "customers/update";
    }

    @PostMapping("updatecustomer/{uuid}")
    public String updateCustomer(@Valid @ModelAttribute("customer") CustomerTo customer, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "customers/update";
        }
        try {
            customerService.updateCustomer(customer);
            redirectAttributes.addFlashAttribute("message", "Successfully updated " + customer.name() + " " + customer.lastName());
            return "redirect:/customers";
        } catch (ObjectOptimisticLockingFailureException e) {
            redirectAttributes.addFlashAttribute("message", "Wurde zwischenzeitlich aktualisert, versuche es erneut.");
            return "redirect:/editcustomer/" + customer.uuid();
        }
    }

    @GetMapping("deletecustomer/{uuid}")
    public String deleteCustomer(@PathVariable("uuid") UUID uuid, RedirectAttributes redirectAttributes) {
        customerService.deleteCustomer(uuid);
        redirectAttributes.addFlashAttribute("message", "Successfully deleted customer " + uuid);
        return "redirect:/customers";
    }

}