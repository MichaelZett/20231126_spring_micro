package de.zettsystems.netzfilm.common.values;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;

import javax.money.MonetaryAmount;
import java.io.IOException;
import java.math.BigDecimal;

@JsonComponent
public class MonetaryAmountSerializer extends JsonSerializer<MonetaryAmount> {

    @Override
    public void serialize(MonetaryAmount money, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("amount", money.getNumber().numberValue(BigDecimal.class));
        jsonGenerator.writeStringField("currency", money.getCurrency().getCurrencyCode());
        jsonGenerator.writeEndObject();
    }


}