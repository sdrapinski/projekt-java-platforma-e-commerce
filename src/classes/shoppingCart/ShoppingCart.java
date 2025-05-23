package classes.shoppingCart;
import classes.products.Product;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
public class ShoppingCart {
    private final Map<Product, Integer> items;

    public ShoppingCart() {
        this.items = new HashMap<>();
    }


    public void addProduct(Product product) {
        if (!product.isAvailable()) {
            System.out.println("❌ Produkt \"" + product.getName() + "\" jest niedostępny i nie może zostać dodany do koszyka.");
            return;
        }

        items.put(product, items.getOrDefault(product, 0) + 1);
        System.out.println("✅ Dodano do koszyka: " + product.getName());
    }

    public void removeProduct(Product product) {
        if (items.containsKey(product)) {
            int count = items.get(product);
            if (count > 1) {
                items.put(product, count - 1);
            } else {
                items.remove(product);
            }
        }
    }
    public void displayCartContents() {
        if (items.isEmpty()) {
            System.out.println("🛒 Koszyk jest pusty.");
        } else {
            System.out.println("🛒 Zawartość koszyka:");
            for (Map.Entry<Product, Integer> entry : items.entrySet()) {
                System.out.println("- " + entry.getKey().getName() + ", " + entry.getValue() + " szt.");
            }
            System.out.println("💰 Łączna cena: " + getTotalPrice() + " zł");
        }
    }
    public boolean isEmpty() {
        return items.isEmpty();
    }

    public Map<Product, Integer> getItems() {
        return items;
    }
    public BigDecimal getTotalPrice() {
        return items.entrySet().stream()
                .map(entry -> entry.getKey().getPrice().multiply(BigDecimal.valueOf(entry.getValue())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
