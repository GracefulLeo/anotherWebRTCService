package com.gracefull.webRTCService;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gracefull.webRTCService.models.Credentials;
import org.hibernate.Session;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Query;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class CredentialsControler {

    private final CredentialsRepository repository;

    private final SipUserResourceAssembler assembler;

    public CredentialsControler(CredentialsRepository repository, SipUserResourceAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/api/get")
    Resources<Resource<Credentials>> all(){
        List<Resource<Credentials>> sipUsers = repository.findAll().stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());
        return new Resources<>(sipUsers,
                linkTo(methodOn(CredentialsControler.class).all()).withSelfRel());
    }

    @PostMapping("/api/post")
    ResponseEntity<?> newEmployee (@RequestBody Credentials newSipUser) throws URISyntaxException {
        Resource<Credentials> employees = assembler.toResource(repository.save(newSipUser));
        return ResponseEntity
                .created(new URI(employees.getId().expand().getHref()))
                .body(employees);
    }

    @GetMapping("/api/get/{id}")
    ResponseEntity<String> one (@PathVariable Long id){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from Credentials c where c.id = :id")
                .setParameter("id", id);
        Credentials sipUser =  (Credentials) query.getSingleResult();
        Gson gson = new GsonBuilder().create();
        String response = gson.toJson(sipUser);
        sipUser.setIsBusy(true);
        repository.save(sipUser);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/api/put/{id}")
    ResponseEntity<String> two (@PathVariable Long id){
        Credentials sipUser = repository.findById(id).orElseThrow(() -> new SipUserNotFoundException(id));
        assembler.toResource(sipUser);
        sipUser.setIsBusy(false);
        repository.save(sipUser);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping ("/sipuser/{id}")
    void deleteEmployee(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
