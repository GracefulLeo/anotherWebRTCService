package com.gracefull.webRTCService;

import com.gracefull.webRTCService.models.Credentials;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class SipUserResourceAssembler implements ResourceAssembler<Credentials, Resource<Credentials>> {
    @Override
    public Resource<Credentials> toResource(Credentials sipUser) {
        Resource resource =  new Resource<> (sipUser,
                linkTo(methodOn(CredentialsControler.class).one(sipUser.getId())).withSelfRel(),
                linkTo(methodOn(CredentialsControler.class).all()).withRel("employees"));
        return resource;
    }

}
