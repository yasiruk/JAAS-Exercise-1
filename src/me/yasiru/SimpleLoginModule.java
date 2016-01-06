package me.yasiru;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;
import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;
import java.util.Map;

/**
 * Created by wik2kassa on 1/6/16.
 */
public class SimpleLoginModule implements LoginModule {
    private Subject subject;
    private CallbackHandler callbackHandler;
    private Map sharedState;
    private Map options;
    private boolean success;
    private String username;
    private char[] password;

    @Override
    public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
        this.subject = subject;
        this.callbackHandler = callbackHandler;
        this.sharedState = sharedState;
        this.options = options;
        this.success = false;

    }

    @Override
    public boolean login() throws LoginException {
        Callback[] callbacks = new Callback[2];
        callbacks[0] = new NameCallback("Enter user name: ");
        callbacks[1] = new PasswordCallback("Enter password: ", true);

        try {
            callbackHandler.handle(callbacks);


            username = ((NameCallback)callbacks[0]).getName();
            password = ((PasswordCallback)callbacks[1]).getPassword();

            char[] defPass = {'t','e','s','t'};
            if("user".equals(username) && Arrays.equals(password,defPass)) {
                return true;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedCallbackException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean commit() throws LoginException {
        //putting the username to the shared state for other login modules to use
        sharedState.put("username", username);

        Principal principal = new Principal() {
            @Override
            public String getName() {
                return "Default User";
            }
        };

        subject.getPrincipals().add(principal);
        subject.getPrivateCredentials().add("Private key: 1234");
        subject.getPublicCredentials().add("public key: 5678");

        return true;
    }

    @Override
    public boolean abort() throws LoginException {
        return false;
    }

    @Override
    public boolean logout() throws LoginException {
        return false;
    }
}
