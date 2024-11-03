package io.fusionauth.quickstart.springapi.change;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("make-change")
public class MakeChangeController {

    @GetMapping
    public Change get(@RequestParam(required = true) BigDecimal total) {
        BigDecimal[] quotientAndReminder = total
                .multiply(new BigDecimal(100))
                .divideAndRemainder(new BigDecimal(5));
        return toChange(total, quotientAndReminder[0].intValue(), quotientAndReminder[1].intValue());
    }

    private Change toChange(BigDecimal total, int nickels, int pennies) {
        var change = new Change();
        change.setTotal(total);
        change.setNickels(nickels);
        change.setPennies(pennies);
        return change;
    }
}
