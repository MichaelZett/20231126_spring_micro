package de.zettsystems.netzfilm.rent.application;

import de.zettsystems.netzfilm.customer.domain.Customer;
import de.zettsystems.netzfilm.customer.domain.CustomerRepository;
import de.zettsystems.netzfilm.customer.values.NoSuchCustomerException;
import de.zettsystems.netzfilm.movie.domain.Copy;
import de.zettsystems.netzfilm.movie.domain.CopyRepository;
import de.zettsystems.netzfilm.movie.values.CopyType;
import de.zettsystems.netzfilm.movie.values.Fsk;
import de.zettsystems.netzfilm.movie.values.NoSuchCopyException;
import de.zettsystems.netzfilm.rent.domain.Rent;
import de.zettsystems.netzfilm.rent.domain.RentRepository;
import de.zettsystems.netzfilm.rent.values.CustomerTooYoungException;
import de.zettsystems.netzfilm.rent.values.RentFullTo;
import de.zettsystems.netzfilm.rent.values.RentTo;
import de.zettsystems.timeutil.TimeUtil;
import lombok.RequiredArgsConstructor;
import org.javamoney.moneta.FastMoney;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.money.MonetaryAmount;
import java.time.LocalDate;
import java.time.Period;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class RentServiceImpl implements RentService {

    private final CopyRepository copyRepository;
    private final CustomerRepository customerRepository;
    private final RentRepository rentRepository;

    @Override
    @Transactional
    public void rentAMovie(RentTo rentTo) {
        Copy copy = copyRepository.findByUuid(rentTo.copyUuid()).orElseThrow(() -> new NoSuchCopyException(rentTo.copyUuid()));
        Customer customer = customerRepository.findByUuid(rentTo.customerUuid()).orElseThrow(() -> new NoSuchCustomerException(rentTo.customerUuid()));

        if (rentAllowed(customer.getBirthdate(), copy.getMovie().getFsk())) {
            MonetaryAmount amount = calculateAmount(copy.getType(), rentTo.numberOfDays());

            Rent newRent = new Rent(copy, customer, amount, rentTo.startDate(), rentTo.startDate().plusDays(rentTo.numberOfDays()));
            rentRepository.save(newRent);
            copy.lend();
        } else {
            throw new CustomerTooYoungException();
        }
    }

    @Override
    public Collection<RentFullTo> findAllRents() {
        return rentRepository.findAll().stream().map(Rent::toFullTo).toList();
    }

    private static MonetaryAmount calculateAmount(CopyType type, long numberOfDays) {
        if (CopyType.VHS == type) {
            return FastMoney.of(1, "EUR").multiply(numberOfDays);
        } else {
            return FastMoney.of(2, "EUR").multiply(numberOfDays);
        }
    }

    private static boolean rentAllowed(LocalDate birthdate, Fsk fsk) {
        Period period = birthdate.until(TimeUtil.today());
        int age = period.getYears();
        return age >= fsk.age;
    }
}
