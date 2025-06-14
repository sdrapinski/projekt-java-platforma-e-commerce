package core;

import classes.catalogs.Catalog;
import classes.products.Category;
import classes.products.Product;
import classes.shoppingCart.Promotions;
import classes.shoppingCart.ShoppingCart;

import java.util.Scanner;

/*
Szymon Drapiński,
Maciej Adamski,
Jakub Cegłowski,
Dominik Ochej
*/

public class Console {
    private final Catalog catalog;
    private final ShoppingCart shoppingCart;

    public Console() {
        this.catalog = new Catalog();
        this.shoppingCart = new ShoppingCart();
    }

    public void Start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("E-commerce java project");
        showCommands();

        while (true) {
            System.out.print(">> ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("exit")) {
                exitProgram();
                break;
            }

            handleCommand(input);
        }

        scanner.close();
    }

    private void handleCommand(String input) {
        String[] parts = input.split("\\s+");

        switch (parts[0].toLowerCase()) {
            case "list" -> {
                if (parts.length == 1) {
                    listAllProducts();
                } else if (parts.length == 2) {
                    listProductsByCategory(parts[1]);
                } else {
                    System.out.println("❌ Użycie: list lub list <KATEGORIA>");
                }
            }
            case "categories" -> showCategories();
            case "add" -> {
                if (parts.length == 2) {
                    addToCart(parts[1]);
                } else {
                    System.out.println("❌ Użycie: add <ID>");
                }
            }
            case "remove" -> {
                if (parts.length == 2) {
                    removeFromCart(parts[1]);
                } else {
                    System.out.println("❌ Użycie: remove <ID>");
                }
            }
            case "cart" -> shoppingCart.displayCartContents();
            case "apply_promotion" -> {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Wpisz odpowieni kod promocyjny, oto lista dostepnych: (development only)");
                System.out.println("TOOF - 10% taniej");
                System.out.println("TWOPLUSONE - Trzeci najtanszy produkt za 1 zł");
                System.out.println("NEXTHALFOF - Drugi taki sam za 50%");
                System.out.println("NONE - brak promocji");
                String code = scanner.nextLine().trim().toUpperCase();

                try{
                    Promotions promo = Promotions.valueOf(code);
                    shoppingCart.applyPromotions(promo);
                }catch (IllegalArgumentException e){
                    System.out.println("Niepoprawny kod promocyjny");
                }

            }
            case "show" -> {
                if (parts.length == 2) {
                    showProductDetails(parts[1]);
                } else {
                    System.out.println("❌ Użycie: show <ID>");
                }
            }
            default -> System.out.println("❌ Nieznane polecenie: " + input);
        }
    }

    private void showCommands() {
        System.out.println("Dostępne komendy:");
        System.out.println("  categories         - wyświetl wszystkie dostępne kategorie");
        System.out.println("  list               - wyświetl wszystkie produkty");
        System.out.println("  list <KATEGORIA>   - wyświetl dostępne produkty z kategorii (np. BOOKS, FOOD, ELECTRONICS)");
        System.out.println("  show <ID>          - pokaż wszystkie informacje o produkcie");
        System.out.println("  add <ID>           - dodaj produkt do koszyka");
        System.out.println("  remove <ID>        - usuń produkt z koszyka");
        System.out.println("  cart               - pokaż zawartość koszyka");
        System.out.println("  apply_promotion    - dodaj promocje");
        System.out.println("  exit               - zakończ program");
    }

    private void listAllProducts() {
        System.out.println("\n🔤 Wszystkie produkty (nazwa - cena):");
        catalog.getProductNamesAndPrices().forEach(System.out::println);
    }

    private void listProductsByCategory(String categoryInput) {
        try {
            Category category = Category.valueOf(categoryInput.toUpperCase());
            System.out.println("\n📦 Produkty dostępne w kategorii " + category + ":");
            catalog.getAvailableProductsByCategory(category).forEach(System.out::println);
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Nieznana kategoria: " + categoryInput);
            System.out.println("ℹ️ Dostępne kategorie:");
            showCategories();
        }
    }

    private void showProductDetails(String idInput) {
        try {
            int id = Integer.parseInt(idInput);
            Product product = catalog.getProductById(id);
            if (product != null) {
                System.out.println("\n" + product.getFullInfo());
            } else {
                System.out.println("❌ Produkt o ID " + id + " nie istnieje.");
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ Błędny format ID.");
        }
    }

    private void showCategories() {
        System.out.println("\n📚 Dostępne kategorie:");
        for (Category category : Category.values()) {
            System.out.println("- " + category);
        }
    }
    private void addToCart(String idInput) {
        try {
            int id = Integer.parseInt(idInput);
            Product product = catalog.getProductById(id);
            if (product != null) {
                shoppingCart.addProduct(product);
            } else {
                System.out.println("❌ Produkt o ID " + id + " nie istnieje.");
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ Błędny format ID.");
        }
    }
    private void removeFromCart(String idInput) {
        try {
            int id = Integer.parseInt(idInput);
            Product product = catalog.getProductById(id);
            if (product != null) {
                shoppingCart.removeProduct(product);
                System.out.println("🗑️ Usunięto z koszyka: " + product.getName());
            } else {
                System.out.println("❌ Produkt o ID " + id + " nie istnieje.");
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ Błędny format ID.");
        }
    }

    private void exitProgram() {
        System.out.println("👋 Zamykanie programu...");
    }
}
