package classes.products;

import java.math.BigDecimal;

public class Product {
    private int id;
    private String name;
    private BigDecimal price;
    private Category category;
    private Boolean isAvailable;
    private String description;


    public Product(int id,String name, BigDecimal price, Category category, Boolean isAvailable, String description) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.isAvailable = isAvailable;
        this.description = description;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Category getCategory() {
        return category;
    }

    public boolean isAvailable() {
        return isAvailable;
    }
    public String getDescription() {
        return description;
    }
    public int getId() {
        return id;
    }
    public String getFullInfo() {
        return String.format(
                "🧾 Szczegóły produktu:\n" +
                        "ID: %d\n" +
                        "Nazwa: %s\n" +
                        "Cena: %.2f zł\n" +
                        "Kategoria: %s\n" +
                        "Dostępny: %s\n" +
                        "Opis: %s",
                id,
                name,
                price,
                category,
                isAvailable ? "TAK" : "NIE",
                description
        );
    }
}
