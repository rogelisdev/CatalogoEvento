package com.codeup.catalogoDeEventos.infrastructure.config;

import com.codeup.catalogoDeEventos.application.service.EventoService;
import com.codeup.catalogoDeEventos.application.service.LugarService;
import com.codeup.catalogoDeEventos.application.usecase.evento.ActualizarEventoUseCaseImpl;
import com.codeup.catalogoDeEventos.application.usecase.evento.CrearEventoUseCaseImpl;
import com.codeup.catalogoDeEventos.application.usecase.evento.EliminarEventoUseCaseImpl;
import com.codeup.catalogoDeEventos.application.usecase.evento.ObtenerEventoUseCaseImpl;
import com.codeup.catalogoDeEventos.application.usecase.lugar.ActualizarLugarUseCaseImpl;
import com.codeup.catalogoDeEventos.application.usecase.lugar.CrearLugarUseCaseImpl;
import com.codeup.catalogoDeEventos.application.usecase.lugar.EliminarLugarUseCaseImpl;
import com.codeup.catalogoDeEventos.application.usecase.lugar.ObtenerLugarUseCaseImpl;
import com.codeup.catalogoDeEventos.domain.models.Evento;
import com.codeup.catalogoDeEventos.domain.models.Lugar;
import com.codeup.catalogoDeEventos.domain.ports.out.EventoRepositoryPort;
import com.codeup.catalogoDeEventos.domain.ports.out.LugarRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public EventoService serviceEvent(EventoRepositoryPort eventRepositoryPort){
        return new EventoService(
                new CrearEventoUseCaseImpl(eventRepositoryPort),
                new ActualizarEventoUseCaseImpl(eventRepositoryPort),
                new EliminarEventoUseCaseImpl(eventRepositoryPort),
                new ObtenerEventoUseCaseImpl(eventRepositoryPort)
        );
    }

    @Bean
    public LugarService servicePlace(LugarRepositoryPort placeRepositoryPort){
        return new LugarService(
                new CrearLugarUseCaseImpl(placeRepositoryPort),
                new ActualizarLugarUseCaseImpl(placeRepositoryPort),
                new EliminarLugarUseCaseImpl(placeRepositoryPort),
                new ObtenerLugarUseCaseImpl(placeRepositoryPort)
        );
    }
}
