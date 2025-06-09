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

        this.addProduct(new Product(
                4,
                "Smartfon Samsung Galaxy",
                new BigDecimal("1999.00"),
                Category.ELECTRONICS,
                true,
                "Smartfon z ekranem AMOLED i 128GB pamięci"
        ));
        this.addProduct(new Product(
                5,
                "Gra planszowa Monopoly",
                new BigDecimal("129.99"),
                Category.TOYS,
                true,
                "Klasyczna gra planszowa dla całej rodziny"
        ));
        this.addProduct(new Product(
                6,
                "Jeansy męskie Slim Fit",
                new BigDecimal("149.50"),
                Category.CLOTHING,
                true,
                "Wygodne jeansy slim fit w kolorze granatowym"
        ));
        this.addProduct(new Product(
                7,
                "E-book: Clean Code",
                new BigDecimal("59.90"),
                Category.BOOKS,
                false,
                "E-book o pisaniu czystego kodu w Java"
        ));
        this.addProduct(new Product(
                8,
                "Puzzle 1000 elementów",
                new BigDecimal("49.99"),
                Category.TOYS,
                true,
                "Puzzle krajobrazowe 1000 elementów"
        ));
        this.addProduct(new Product(
                9,
                "T-shirt damski z nadrukiem",
                new BigDecimal("39.90"),
                Category.CLOTHING,
                true,
                "Bawełniany T-shirt z kolorowym nadrukiem"
        ));
        this.addProduct(new Product(
                10,
                "Makaron spaghetti",
                new BigDecimal("3.20"),
                Category.FOOD,
                true,
                "Makaron spaghetti 500g z pszenicy durum"
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
