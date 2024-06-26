package be.kuleuven.foodrestservice.controllers;

import be.kuleuven.foodrestservice.domain.Meal;
import be.kuleuven.foodrestservice.domain.MealsRepository;
import be.kuleuven.foodrestservice.exceptions.MealNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.Collection;
import java.util.Optional;

@RestController
public class MealsRestRpcStyleController {

    private final MealsRepository mealsRepository;

    @Autowired
    MealsRestRpcStyleController(MealsRepository mealsRepository) {
        this.mealsRepository = mealsRepository;
    }

    @GetMapping("/restrpc/meals/{id}")
    Meal getMealById(@PathVariable String id) {
        Optional<Meal> meal = mealsRepository.findMeal(id);

        return meal.orElseThrow(() -> new MealNotFoundException(id));
    }

    @GetMapping("/restrpc/meals")
    Collection<Meal> getMeals() {
        return mealsRepository.getAllMeal();
    }

    @GetMapping("/restrpc/meals/cheapest")
    Meal getCheapestMeal() {
        Meal cheapestMeal = mealsRepository.getCheapestMeal();
        if (cheapestMeal == null) {
            throw new MealNotFoundException("No meals found.");
        }
        return cheapestMeal;
    }

    @GetMapping("/restrpc/meals/largest")
    Meal getLargestMeal() {
        Meal largestMeal = mealsRepository.getLargestMeal();
        if (largestMeal == null) {
            throw new MealNotFoundException("No meals found.");
        }
        return largestMeal;
    }

    @PostMapping("/restrpc/meals")
    @ResponseStatus(HttpStatus.CREATED)
    Meal addMeal(@RequestBody Meal meal) {
        mealsRepository.addMeal(meal);
        return meal;
    }

    @PutMapping("/restrpc/meals/{id}")
    @ResponseStatus(HttpStatus.OK)
    Meal updateMeal(@PathVariable String id, @RequestBody Meal updatedMeal) {
        mealsRepository.updateMeal(id, updatedMeal);
        return updatedMeal;
    }

    @DeleteMapping("/restrpc/meals/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteMeal(@PathVariable String id) {
        mealsRepository.deleteMeal(id);
    }
}
