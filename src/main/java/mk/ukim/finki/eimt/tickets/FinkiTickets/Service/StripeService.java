package mk.ukim.finki.eimt.tickets.FinkiTickets.Service;

import com.stripe.Stripe;
import com.stripe.exception.*;
import com.stripe.model.Charge;
import mk.ukim.finki.eimt.tickets.FinkiTickets.Model.ChargeRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CookieValue;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class StripeService {

    @Value("sk_test_F3u5jFhkQkQAXE8SK8mdt5xE00R58799Bb")
    private String secretKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = secretKey;
    }

    public Charge charge(ChargeRequest chargeRequest)
            throws AuthenticationException, InvalidRequestException,
            APIConnectionException, CardException, APIException {


        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", chargeRequest.getAmount());
        chargeParams.put("currency", chargeRequest.getCurrency());
        chargeParams.put("description", chargeRequest.getDescription());
        chargeParams.put("source", chargeRequest.getStripeToken());
        return Charge.create(chargeParams);
    }
}
