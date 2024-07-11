package mk.ukim.finki.wp.model.exceptions;

public class ProductAlreadyInShoppingCartException extends RuntimeException{
    public ProductAlreadyInShoppingCartException(Long productId, String username) {
        super(String.format("Product with id: %d already exists in shopping cart for user with username %s", productId, username));
    }
}
