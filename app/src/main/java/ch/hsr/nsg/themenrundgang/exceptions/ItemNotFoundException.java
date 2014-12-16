package ch.hsr.nsg.themenrundgang.exceptions;

/**
 * Created by dominik on 11.12.14.
 */
public class ItemNotFoundException extends Exception {

    public ItemNotFoundException() {}
    public ItemNotFoundException(String message) {
        super(message);
    }
}
