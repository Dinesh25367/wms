package com.newage.wms.repository;

import org.hibernate.event.spi.PostUpdateEvent;
import org.hibernate.event.spi.PostUpdateEventListener;
import org.hibernate.persister.entity.EntityPersister;

public class MasterUpdateEventListener implements PostUpdateEventListener {

    public static final MasterUpdateEventListener INSTANCE = new MasterUpdateEventListener();

    @Override
    public void onPostUpdate ( PostUpdateEvent event ) {
        //this method is called on post update
    }

    @Override
    public boolean requiresPostCommitHanding ( EntityPersister persister ) {
        return false;
    }

}