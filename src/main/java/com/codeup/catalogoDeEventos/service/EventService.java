    package com.codeup.catalogoDeEventos.service;

    import com.codeup.catalogoDeEventos.domain.Event;
    import com.codeup.catalogoDeEventos.dto.DetailsEventResponse;
    import com.codeup.catalogoDeEventos.dto.EventRequest;
    import java.util.List;
    import java.util.Optional;


    public interface EventService {

        Event create(EventRequest request);
        List<Event> getAll();
        Optional<Event> findById(long id);
        Optional<Event> update(long id, EventRequest request);
        boolean delete(long id);
        Optional<DetailsEventResponse> findDetailsById(long id);

    }
