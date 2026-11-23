package in.org.moneytrail.exception;

public class TransactionNotFoundException extends RuntimeException {
    public TransactionNotFoundException(Long id){
        super("transaction with id : " + id + " not found!");
        System.out.println("TRANSACTION WITH ID :: " + id + " NOT FOUND !");
    }
}
