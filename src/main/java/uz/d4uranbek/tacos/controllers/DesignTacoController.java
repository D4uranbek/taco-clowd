package uz.d4uranbek.tacos.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.d4uranbek.tacos.domains.Ingredient;
import uz.d4uranbek.tacos.domains.Ingredient.Type;
import uz.d4uranbek.tacos.domains.Taco;
import uz.d4uranbek.tacos.domains.TacoOrder;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author D4uranbek
 * @since 08.06.2022
 */
@Slf4j
@Controller
@RequestMapping( "/design" )
@SessionAttributes( "tacoOrder" )
public class DesignTacoController {

    @ModelAttribute
    public void addIngredientsToModel(Model model) {
        List<Ingredient> ingredients = List.of(
                new Ingredient( "FLTO", "Flour Tortilla", Type.WRAP ),
                new Ingredient( "COTO", "Corn Tortilla", Type.WRAP ),
                new Ingredient( "GRBF", "Ground Beef", Type.PROTEIN ),
                new Ingredient( "CARN", "Carnitas", Type.PROTEIN ),
                new Ingredient( "TMTO", "Diced Tomatoes", Type.VEGGIES ),
                new Ingredient( "LETC", "Lettuce", Type.VEGGIES ),
                new Ingredient( "CHED", "Cheddar", Type.CHEESE ),
                new Ingredient( "JACK", "Monterrey Kack", Type.CHEESE ),
                new Ingredient( "SLSA", "Salsa", Type.SAUCE ),
                new Ingredient( "SRCR", "Sour Cream", Type.SAUCE )
        );

        for ( Type type : Type.values() ) {
            model.addAttribute( type.toString().toLowerCase(),
                    filterByType( ingredients, type ) );
        }
    }

    @ModelAttribute( name = "tacoOrder" )
    public TacoOrder order() {
        return new TacoOrder();
    }

    @ModelAttribute( name = "taco" )
    public Taco taco() {
        return new Taco();
    }

    @GetMapping
    public String showDesignForm() {
        return "design";
    }

    @PostMapping
    public String processTaco(Taco taco, @ModelAttribute TacoOrder tacoOrder) {
        tacoOrder.addTaco( taco );
        log.info( "Processing taco: {}", taco );

        return "redirect:/orders/current";
    }

    private Iterable<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
        return ingredients
                .stream()
                .filter( ingredient -> ingredient.type().equals( type ) )
                .collect( Collectors.toList() );
    }
}
