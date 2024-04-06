package com.newage.wms.repository;

import org.hibernate.event.spi.PostDeleteEvent;
import org.hibernate.event.spi.PostDeleteEventListener;
import org.hibernate.persister.entity.EntityPersister;

public class MasterDeleteEventListener implements PostDeleteEventListener {

    public static final MasterDeleteEventListener INSTANCE = new MasterDeleteEventListener();

    @Override
    public void onPostDelete ( PostDeleteEvent event ) {
        //this method is called on post delete
    }

    @Override
    public boolean requiresPostCommitHanding ( EntityPersister persister ) {
        return false;
    }
}