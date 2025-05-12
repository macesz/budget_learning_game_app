package com.codecool.backend.configuration;
import com.codecool.backend.model.entity.Category;
import com.codecool.backend.repository.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration

public class DataInitializer {

    @Bean
    public CommandLineRunner initCategories(CategoryRepository categoryRepository) {
        return args -> {
            // Only populate if empty
            if (categoryRepository.count() == 0) {
                for (Category.CategoryType type : Category.CategoryType.values()) {
                    categoryRepository.save(new Category(type));
                }
                categoryRepository.flush();
            }
        };
    }


}
