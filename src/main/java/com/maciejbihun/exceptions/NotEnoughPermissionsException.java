package com.maciejbihun.exceptions;

/**
 * @author Maciej Bihun
 * Specify a message that will describe in more
 * detail what kind of permissions a user do not have.
 */
public class NotEnoughPermissionsException extends Exception {

    public NotEnoughPermissionsException(String message){
        super(message);
    }

}
