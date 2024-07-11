package mk.ukim.finki.wp.model.exceptions;

public class ShoppingCartNotFoundException extends RuntimeException{
    public ShoppingCartNotFoundException(Long cartId) {
        super(String.format("Shopping cart with id: %d was not found", cartId));
    }
}
