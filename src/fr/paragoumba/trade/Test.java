package fr.paragoumba.trade;

import javafx.util.Pair;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

/**
 * Created by Paragoumba on 09/07/2017.
 */
public class Test {

    public static void main(String[] args) {

        HashMap<Pair<String, Integer>, Pair<Integer, Double>> map = new HashMap<>();

        map.put(new Pair<>("Acacia_Door", 1), new Pair<>(0, 1.0));
        map.put(new Pair<>("Barrier", 4), new Pair<>(1, 10.0));

        System.out.println(map);
    }
}
