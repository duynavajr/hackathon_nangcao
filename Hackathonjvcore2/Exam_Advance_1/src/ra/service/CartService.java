package ra.service;

import java.util.HashMap;
import java.util.Map;

import ra.model.CartItem;

public class CartService {
    private Map<String, CartItem> cartItems;
    private Map<String, Integer> stock;

    public CartService() {
        this.cartItems = new HashMap<>();
        this.stock = new HashMap<>();
    }

    public void displayAllProducts() {
        System.out.println("Danh sách sản phẩm đang được bán:");
        for (Map.Entry<String, Integer> entry : stock.entrySet()) {
            String productId = entry.getKey();
            int quantity = entry.getValue();
            System.out.println("Mã sản phẩm: " + productId + ", Số lượng còn lại: " + quantity);
        }
    }

    public void addProductToCart(String productId, int quantity) {
        if (!stock.containsKey(productId)) {
            System.out.println("Sản phẩm không tồn tại.");
            return;
        }

        int currentQuantity = stock.get(productId);
        if (currentQuantity < quantity) {
            System.out.println("Sản phẩm không đủ số lượng.");
            return;
        }

        CartItem cartItem;
        if (cartItems.containsKey(productId)) {
            cartItem = cartItems.get(productId);
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        } else {
            double price = getProductPrice(productId);
            cartItem = new CartItem(productId, price, quantity);
            cartItems.put(productId, cartItem);
        }

        stock.put(productId, currentQuantity - quantity);
        System.out.println("Sản phẩm đã được thêm vào giỏ hàng.");
    }

    public void displayCartItems() {
        System.out.println("Danh sách sản phẩm trong giỏ hàng:");
        for (CartItem cartItem : cartItems.values()) {
            System.out.println("Mã sản phẩm: " + cartItem.getCartItemId() +
                    ", Số lượng: " + cartItem.getQuantity() +
                    ", Giá: " + cartItem.getPrice());
        }
    }

    public void updateCartItemQuantity(String cartItemId, int newQuantity) {
        if (!cartItems.containsKey(cartItemId)) {
            System.out.println("Sản phẩm không tồn tại trong giỏ hàng.");
            return;
        }

        CartItem cartItem = cartItems.get(cartItemId);
        String productId = cartItem.getCartItemId();
        int currentQuantity = stock.get(productId);

        if (currentQuantity + cartItem.getQuantity() < newQuantity) {
            System.out.println("Sản phẩm không đủ số lượng.");
            return;
        }

        int quantityDiff = newQuantity - cartItem.getQuantity();
        cartItem.setQuantity(newQuantity);
        stock.put(productId, currentQuantity + cartItem.getQuantity() - newQuantity);
        System.out.println("Số lượng sản phẩm đã được cập nhật.");
    }

    public void removeCartItem(String cartItemId) {
        if (!cartItems.containsKey(cartItemId)) {
            System.out.println("Sản phẩm không tồn tại trong giỏ hàng.");
            return;
        }

        CartItem cartItem = cartItems.remove(cartItemId);
        String productId = cartItem.getCartItemId();
        int currentQuantity = stock.get(productId);
        stock.put(productId, currentQuantity + cartItem.getQuantity());
        System.out.println("Sản phẩm đã được xóa khỏi giỏ hàng.");
    }

    public void removeAllCartItems() {
        for (CartItem cartItem : cartItems.values()) {
            String productId = cartItem.getCartItemId();
            int currentQuantity = stock.get(productId);
            stock.put(productId, currentQuantity + cartItem.getQuantity());
        }

        cartItems.clear();
        System.out.println("Tất cả sản phẩm đã được xóa khỏi giỏ hàng.");
    }

    private double getProductPrice(String productId) {

        Map<String, Double> productPrices = new HashMap<>();
        productPrices.put("P001", 10.0);
        productPrices.put("P002", 15.0);

        if (productPrices.containsKey(productId)) {
            return productPrices.get(productId);
        }

        throw new IllegalArgumentException("Không tìm thấy giá sản phẩm cho mã sản phẩm: " + productId);
    }

}
