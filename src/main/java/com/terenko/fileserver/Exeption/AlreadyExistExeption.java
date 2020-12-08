package com.terenko.fileserver.Exeption;

public class AlreadyExistExeption extends Exception {
    public AlreadyExistExeption() {
        super("object already exist .Action cannot be performed ");
    }
}
