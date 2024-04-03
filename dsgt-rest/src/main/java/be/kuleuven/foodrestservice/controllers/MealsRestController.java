package be.kuleuven.foodrestservice.controllers;

import be.kuleuven.foodrestservice.domain.Meal;
import be.kuleuven.foodrestservice.domain.Order;
import be.kuleuven.foodrestservice.domain.MealsRepository;
import be.kuleuven.foodrestservice.domain.OrderRepository;
import be.kuleuven.foodrestservice.exceptions.MealNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
public class MealsRestController {

    private final MealsRepository mealsRepository;
    private final OrderRepository orderRepository;

    @Autowired
    MealsRestController(MealsRepository mealsRepository, OrderRepository orderRepository) {
        this.mealsRepository = mealsRepository;
        this.orderRepository = orderRepository;
    }

    @GetMapping("/rest/meals/{id}")
    EntityModel<Meal> getMealById(@PathVariable String id) {
        Meal meal = mealsRepository.findMeal(id).orElseThrow(() -> new MealNotFoundException(id));

        return mealToEntityModel(id, meal);
    }

    @GetMapping("/rest/meals")
    CollectionModel<EntityModel<Meal>> getMeals() {
        Collection<Meal> meals = mealsRepository.getAllMeal();

        List<EntityModel<Meal>> mealEntityModels = new ArrayList<>();
        for (Meal m : meals) {
            EntityModel<Meal> em = mealToEntityModel(m.getId(), m);
            mealEntityModels.add(em);
        }
        return CollectionModel.of(mealEntityModels,
                linkTo(methodOn(MealsRestController.class).getMeals()).withSelfRel());
    }

    private EntityModel<Meal> mealToEntityModel(String id, Meal meal) {
        return EntityModel.of(meal,
                linkTo(methodOn(MealsRestController.class).getMealById(id)).withSelfRel(),
                linkTo(methodOn(MealsRestController.class).getMeals()).withRel("rest/meals"));
    }

    @GetMapping("/rest/meals/cheapest")
    EntityModel<Meal> getCheapestMeal() {
        Meal cheapestMeal = mealsRepository.getCheapestMeal();
        if (cheapestMeal == null) {
            throw new MealNotFoundException("No meals found.");
        }
        return mealToEntityModel(cheapestMeal.getId(), cheapestMeal);
    }

    @GetMapping("/rest/meals/largest")
    EntityModel<Meal> getLargestMeal() {
        Meal largestMeal = mealsRepository.getLargestMeal();
        if (largestMeal == null) {
            throw new MealNotFoundException("No meals found.");
        }
        return mealToEntityModel(largestMeal.getId(), largestMeal);
    }

    @PostMapping("/rest/meals")
    @ResponseStatus(HttpStatus.CREATED)
    EntityModel<Meal> addMeal(@RequestBody Meal meal) {
        mealsRepository.addMeal(meal);
        return EntityModel.of(meal);
    }

    @PutMapping("/rest/meals/{id}")
    @ResponseStatus(HttpStatus.OK)
    EntityModel<Meal> updateMeal(@PathVariable String id, @RequestBody Meal updatedMeal) {
        mealsRepository.updateMeal(id, updatedMeal);
        return EntityModel.of(updatedMeal);
    }

    @DeleteMapping("/rest/meals/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteMeal(@PathVariable String id) {
        mealsRepository.deleteMeal(id);
    }

    @PostMapping("/rest/orders")
    @ResponseStatus(HttpStatus.CREATED)
    public void orderMeal(@RequestBody Map<String, Object> orderData) {
        Order order = new Order();
        order.setAddress((String) orderData.get("address"));
        order.setMealIds((List<String>) orderData.get("mealIds"));

        orderRepository.addOrder(order);
        System.out.println("Received order: " + order.getAddress() + ", Meals: " + order.getMealIds());
    }
}

