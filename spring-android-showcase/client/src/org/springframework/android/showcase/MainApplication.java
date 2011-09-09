package org.springframework.android.showcase;

import org.springframework.security.crypto.encrypt.AndroidEncryptors;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.sqlite.SQLiteConnectionRepository;
import org.springframework.social.connect.sqlite.support.SQLiteConnectionRepositoryHelper;
import org.springframework.social.connect.support.ConnectionFactoryRegistry;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;

import android.app.Application;
import android.database.sqlite.SQLiteOpenHelper;

public class MainApplication extends Application {
    private ConnectionFactoryRegistry connectionFactoryRegistry;
    private SQLiteOpenHelper repositoryHelper;
    private ConnectionRepository connectionRepository;

    // ***************************************
    // Application Methods
    // ***************************************
    @Override
    public void onCreate() {
        // create a new ConnectionFactoryLocator and populate it with Facebook
        // and Twitter ConnectionFactories
        connectionFactoryRegistry = new ConnectionFactoryRegistry();
        connectionFactoryRegistry.addConnectionFactory(new FacebookConnectionFactory(getFacebookAppId(), getFacebookAppSecret()));
        connectionFactoryRegistry.addConnectionFactory(new TwitterConnectionFactory(getTwitterConsumerToken(), getTwitterConsumerTokenSecret()));

        // set up the database and encryption
        repositoryHelper = new SQLiteConnectionRepositoryHelper(this);
        connectionRepository = new SQLiteConnectionRepository(repositoryHelper, connectionFactoryRegistry, AndroidEncryptors.text("password", "5c0744940b5c369b"));
    }

    // ***************************************
    // Private methods
    // ***************************************
    private String getFacebookAppId() {
        return getString(R.string.facebook_app_id);
    }

    private String getFacebookAppSecret() {
        return getString(R.string.facebook_app_secret);
    }

    private String getTwitterConsumerToken() {
        return getString(R.string.twitter_consumer_key);
    }

    private String getTwitterConsumerTokenSecret() {
        return getString(R.string.twitter_consumer_key_secret);
    }

    // ***************************************
    // Public methods
    // ***************************************
    public ConnectionRepository getConnectionRepository() {
        return connectionRepository;
    }

    public FacebookConnectionFactory getFacebookConnectionFactory() {
        return (FacebookConnectionFactory) connectionFactoryRegistry.getConnectionFactory(Facebook.class);
    }

    public TwitterConnectionFactory getTwitterConnectionFactory() {
        return (TwitterConnectionFactory) connectionFactoryRegistry.getConnectionFactory(Twitter.class);
    }

}
