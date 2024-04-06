package com.newage.wms.repository;

import org.hibernate.boot.Metadata;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.integrator.spi.Integrator;
import org.hibernate.service.spi.SessionFactoryServiceRegistry;
import org.springframework.stereotype.Component;

@Component
public class MasterEventListenerIntegrator implements Integrator {

    public static final MasterEventListenerIntegrator INSTANCE = new MasterEventListenerIntegrator();

    @Override
    public void integrate(Metadata metadata, SessionFactoryImplementor sessionFactory, SessionFactoryServiceRegistry serviceRegistry) {
        final EventListenerRegistry eventListenerRegistry =
                serviceRegistry.getService(EventListenerRegistry.class);
        eventListenerRegistry.appendListeners(
                EventType.POST_INSERT,
                MasterInsertEventListener.INSTANCE);
        eventListenerRegistry.appendListeners(
                EventType.POST_UPDATE,
                MasterUpdateEventListener.INSTANCE);
        eventListenerRegistry.appendListeners(
                EventType.POST_DELETE,
                MasterDeleteEventListener.INSTANCE);
    }

    @Override
    public void disintegrate(SessionFactoryImplementor sessionFactory, SessionFactoryServiceRegistry serviceRegistry) {
        //this method is used to disintegrate
    }

}
