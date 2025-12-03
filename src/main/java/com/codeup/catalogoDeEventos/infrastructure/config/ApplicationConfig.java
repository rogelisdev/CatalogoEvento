package com.codeup.catalogoDeEventos.infrastructure.config;

import com.codeup.catalogoDeEventos.application.service.EventService;
import com.codeup.catalogoDeEventos.application.service.VenueService;
import com.codeup.catalogoDeEventos.application.usecase.event.UpdateEventUseCaseImpl;
import com.codeup.catalogoDeEventos.application.usecase.event.CreateEventUseCaseImpl;
import com.codeup.catalogoDeEventos.application.usecase.event.DeleteEventUseCaseImpl;
import com.codeup.catalogoDeEventos.application.usecase.event.GetEventUseCaseImpl;
import com.codeup.catalogoDeEventos.application.usecase.venue.UpdateVenueUseCaseImpl;
import com.codeup.catalogoDeEventos.application.usecase.venue.CreateVenueUseCaseImpl;
import com.codeup.catalogoDeEventos.application.usecase.venue.DeleteVenueUseCaseImpl;
import com.codeup.catalogoDeEventos.application.usecase.venue.GetVenueUseCaseImpl;
import com.codeup.catalogoDeEventos.domain.ports.out.EventRepositoryPort;
import com.codeup.catalogoDeEventos.domain.ports.out.VenueRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public EventService serviceEvent(EventRepositoryPort eventRepositoryPort){
        return new EventService(
                new CreateEventUseCaseImpl(eventRepositoryPort),
                new UpdateEventUseCaseImpl(eventRepositoryPort),
                new DeleteEventUseCaseImpl(eventRepositoryPort),
                new GetEventUseCaseImpl(eventRepositoryPort)
        );
    }

    @Bean
    public VenueService servicePlace(VenueRepositoryPort placeRepositoryPort){
        return new VenueService(
                new CreateVenueUseCaseImpl(placeRepositoryPort),
                new UpdateVenueUseCaseImpl(placeRepositoryPort),
                new DeleteVenueUseCaseImpl(placeRepositoryPort),
                new GetVenueUseCaseImpl(placeRepositoryPort)
        );
    }
}
