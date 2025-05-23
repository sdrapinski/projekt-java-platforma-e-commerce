package classes.catalogs;

import classes.products.Category;
import classes.products.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Catalog {
    private List<Product> products;

    public Catalog() {
        this.products = new ArrayList<>();
        initializeCatalog();
    }


    private void initializeCatalog() {
        this.addProduct(new Product(
                1,
                "Laptop Lenovo",
                new BigDecimal("2999.99"),
                Category.ELECTRONICS,
                true,
                "Laptop z procesorem Intel i5 i 16GB RAM"
        ));
        this.addProduct(new Product(
                2,
                "Czekolada mleczna",
                new BigDecimal("5.49"),
                Category.FOOD,
                true,
                "Słodka czekolada mleczna 100g"
        ));
        this.addProduct(new Product(
                3,
                "Podstawy Java",
                new BigDecimal("89.90"),
                Category.BOOKS,
                false,
                "Podręcznik programowania w języku Java"
        ));
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public List<Product> getProducts() {
        return products;
    }

    public List<String> getProductNamesAndPrices() {
        return products.stream()
                .sorted((p1, p2) -> p1.getName().compareToIgnoreCase(p2.getName()))
                .map(p -> "ID: %s %s - %s zł".formatted(p.getId(),p.getName(), p.getPrice()))
                .collect(Collectors.toList());
    }

    public List<String> getAvailableProductsByCategory(Category category) {
        return products.stream()
                .filter(p -> p.isAvailable() && p.getCategory() == category)
                .sorted((p1, p2) -> p1.getPrice().compareTo(p2.getPrice()))
                .map(p -> "ID: %s %s - %s zł".formatted(p.getId(),p.getName(), p.getPrice()))
                .collect(Collectors.toList());
    }

    public Product getProductById(int id) {
        return products.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }


}
