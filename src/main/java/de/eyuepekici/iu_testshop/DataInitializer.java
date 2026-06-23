package de.eyuepekici.iu_testshop;

import de.eyuepekici.iu_testshop.model.Product;
import de.eyuepekici.iu_testshop.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final ProductRepository productRepository;

    public DataInitializer(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) {

        if (productRepository.count() == 0) {

            productRepository.save(
                    new Product(
                            "Laptop",
                            999.99,
                            "Leistungsstarker Business Laptop"));

            productRepository.save(
                    new Product(
                            "Monitor",
                            249.99,
                            "27 Zoll Monitor"));

            productRepository.save(
                    new Product(
                            "Tastatur",
                            49.99,
                            "Mechanische Tastatur"));
        }
    }
}