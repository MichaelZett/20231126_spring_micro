package de.zettsystems.netzfilm.rent.adapter;


import de.zettsystems.netzfilm.customer.application.CustomerService;
import de.zettsystems.netzfilm.customer.values.CustomerTo;
import de.zettsystems.netzfilm.movie.application.CopyService;
import de.zettsystems.netzfilm.movie.values.CopyTo;
import de.zettsystems.netzfilm.rent.application.RentService;
import de.zettsystems.netzfilm.rent.values.CustomerTooYoungException;
import de.zettsystems.netzfilm.rent.values.RentTo;
import de.zettsystems.timeutil.TimeUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.util.CustomObjectInputStream;
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
public class RentController {
    private final CopyService copyService;
    private final RentService rentService;
    private final CustomerService customerService;

    @GetMapping("/rent")
    public String showRentForm(Model model, HttpServletRequest request) {
        final List<CustomerTo> customers = customerService.getAllCustomers();
        final List<CopyTo> allRentableCopies = copyService.findAllRentableCopies();
        model.addAttribute("customers", customers);
        model.addAttribute("copies", allRentableCopies);
        model.addAttribute("rent", new RentTo(null, TimeUtil.today(), 1, null));

        return "rent/rent";
    }

    @PostMapping("/addrent")
    public String rent(@Valid @ModelAttribute("rent") RentTo rent, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            final List<CopyTo> allRentableCopies = copyService.findAllRentableCopies();
            model.addAttribute("copies", allRentableCopies);
            final List<CustomerTo> customers = customerService.getAllCustomers();
            model.addAttribute("customers", customers);
            return "rent/rent";
        }
        try {
            rentService.rentAMovie(rent);
            redirectAttributes.addFlashAttribute("message", "Hallo, Du hast '" + copyService.getTitleAndFormat(rent.copyUuid()) + "' geliehen. Danke!");
        } catch (CustomerTooYoungException e) {
            redirectAttributes.addFlashAttribute("error", "Hallo, Leihe hat nicht geklappt, der Kunde ist zu jung.");
        }
        return "redirect:/rent";
    }

}