package com.newage.wms.repository;

import org.hibernate.HibernateException;
import org.hibernate.event.spi.PostInsertEvent;
import org.hibernate.event.spi.PostInsertEventListener;
import org.hibernate.persister.entity.EntityPersister;

public class MasterInsertEventListener implements PostInsertEventListener {

    public static final MasterInsertEventListener INSTANCE = new MasterInsertEventListener();

    @Override
    public void onPostInsert ( PostInsertEvent event ) throws HibernateException {
        //this method is called on post insert
    }

    @Override
    public boolean requiresPostCommitHanding ( EntityPersister persister ) {
        return false;
    }

}
