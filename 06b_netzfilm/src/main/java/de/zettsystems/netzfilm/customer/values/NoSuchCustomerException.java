package de.zettsystems.netzfilm.customer.values;

import java.util.UUID;

public class NoSuchCustomerException extends RuntimeException {
    public NoSuchCustomerException(UUID uuid) {
        super("No customer exists with uuid " + uuid);
    }

    public NoSuchCustomerException(String lastName) {
        super("No customer exists with lastname " + lastName);
    }
}
