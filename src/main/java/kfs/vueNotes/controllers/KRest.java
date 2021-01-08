package kfs.vueNotes.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
//@RestController
public class KRest {
    // Forwards all routes to FrontEnd except:  '/kAuth', '/kApiNotes/**'
    // Required because of 'mode: history' usage in frontend routing, see README for further details
    @RequestMapping(value = "{_:^(?!kApiNotes|kAuth).*$}")
    public String redirectApi() {
        log.info("URL entered directly into the Browser, so we need to redirect...");
        return "forward:/notes/index.html";
    }

    @RequestMapping({
            "/",
            "/index.html",
            "/about"
    })
    public String index() {
         return "forward:/notes/index.html";
    }
}
