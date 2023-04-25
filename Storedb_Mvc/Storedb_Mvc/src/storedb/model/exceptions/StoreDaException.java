/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package storedb.model.exceptions;

/**
 *Exception to catch data erros
 * @author dax
 */
public class StoreDaException extends Exception {

    private int errorCode;

    public StoreDaException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String toString() {
        return "StoredbExceptions{" + "errorCode=" + errorCode + '}';
    }
    
}
