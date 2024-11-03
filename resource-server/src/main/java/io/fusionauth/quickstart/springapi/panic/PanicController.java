package io.fusionauth.quickstart.springapi.panic;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("panic")
public class PanicController {

    @PostMapping
    @CrossOrigin(origins = "http://localhost:3000")
    public PanicResponse postPanic() {
        return new PanicResponse("We've called the police!");
    }
}
