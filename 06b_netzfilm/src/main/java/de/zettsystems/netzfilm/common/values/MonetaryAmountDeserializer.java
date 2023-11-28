package de.zettsystems.netzfilm.common.values;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.javamoney.moneta.FastMoney;
import org.springframework.boot.jackson.JsonComponent;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import java.io.IOException;
import java.math.BigDecimal;

@JsonComponent
public class MonetaryAmountDeserializer extends JsonDeserializer<MonetaryAmount> {

    @Override
    public MonetaryAmount deserialize(JsonParser jsonParser,
                                      DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        BigDecimal amount = node.get("amount").decimalValue();
        String currency = node.get("currency").asText();
        return FastMoney.of(amount, Monetary.getCurrency(currency));
    }

}