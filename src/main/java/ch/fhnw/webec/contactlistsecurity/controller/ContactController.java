package ch.fhnw.webec.contactlistsecurity.controller;

import ch.fhnw.webec.contactlistsecurity.model.Contact;
import ch.fhnw.webec.contactlistsecurity.service.ContactService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
public class ContactController {

    private final ContactService service;

    @Autowired
    public ContactController(ContactService service) {
        this.service = service;
    }

    @GetMapping
    public ModelAndView getIndex(Authentication authentication, @RequestParam(required = false) Long select, @RequestParam(required = false) String search) {
        final boolean isAdmin = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch("ROLE_ADMIN"::equals);
        final Map<String, Object> model = new HashMap<>();
        model.put("contacts", service.getAllContacts());
        model.put("isAdmin", isAdmin);
        if (select != null) {
            service.findContact(select).ifPresent(
                    contact -> model.put("selected", contact)
            );
        } else if (Strings.isNotBlank(search)) {
            service.findByName(search).stream().findFirst().ifPresent(
                    contact -> model.put("selected", contact)
            );
        }
        return new ModelAndView("index", model);
    }

    @PostMapping
    public ModelAndView addContact(Authentication authentication, @ModelAttribute Contact newContact) {
        final boolean isAdmin = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch("ROLE_ADMIN"::equals);
        final Contact createdContact = service.createContact(newContact);
        final Map<String, Object> model = new HashMap<>();
        model.put("contacts", service.getAllContacts());
        model.put("isAdmin", isAdmin);
        model.put("selected", createdContact);

        return new ModelAndView("index", model);
    }

}
