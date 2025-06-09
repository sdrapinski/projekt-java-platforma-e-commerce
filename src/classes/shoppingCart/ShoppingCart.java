package classes.shoppingCart;
import classes.products.Product;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class ShoppingCart {
    private final Map<Product, Integer> items;
    private Promotions promotions;


    public ShoppingCart() {
        this.items = new HashMap<>();
        this.promotions = Promotions.NONE;
    }


    public void applyPromotions(Promotions promotions) {
        this.promotions = promotions;
        switch (promotions) {
            case NEXTHALFOF -> System.out.println("Dodano promcje drugi za poł ceny");
            case TOOF -> System.out.println("Dodano promcje 10% na caly koszyk");
            case TWOPLUSONE -> System.out.println("Dodano trzeci najtanszy produkt za 1zl");
            case NONE -> System.out.println("Usunieto aktywną promocje");
            default -> System.out.println("Niepoprawny kod promocyjny");
        }
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

    private BigDecimal calculateBasePrice() {
        return items.entrySet().stream()
                .map(entry -> entry.getKey().getPrice().multiply(BigDecimal.valueOf(entry.getValue())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getTotalPrice() {
       switch (promotions) {
           case TOOF -> {
               return calculateBasePrice().multiply(BigDecimal.valueOf(0.9));
           }
           case TWOPLUSONE -> {
             return  calculateTwoPlusOneTotal();
           }
           case NEXTHALFOF -> {
               return  calculateNextHalfOfTotal();
           }

           default -> {
               return calculateBasePrice();
           }

       }
    }

    private BigDecimal calculateNextHalfOfTotal() {
        BigDecimal total = BigDecimal.ZERO;

        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            Product product = entry.getKey();
            int count = entry.getValue();


            int halfPrice = count / 2;
            int fullPrice = count - halfPrice;

            total = total.add(product.getPrice().multiply(BigDecimal.valueOf(halfPrice)).multiply(BigDecimal.valueOf(0.5)));
            total = total.add(product.getPrice().multiply(BigDecimal.valueOf(fullPrice)));
        }
        return total.setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateTwoPlusOneTotal() {
        List<Product> allProducts = new ArrayList<>();
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            allProducts.addAll(Collections.nCopies(entry.getValue(), entry.getKey()));
        }
       int remainder = allProducts.size() / 3;

        allProducts.sort(Comparator.comparing(Product::getPrice));

        BigDecimal total = BigDecimal.ZERO;
        int index = 0;
        for (Product product : allProducts) {
          if(index < remainder) {
            total = total.add(BigDecimal.ONE);
          }else{
              total = total.add(product.getPrice());
          }
          index++;
        }

        return total;
    }
}
