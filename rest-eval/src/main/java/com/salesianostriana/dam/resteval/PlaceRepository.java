package com.salesianostriana.dam.resteval;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;


import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class PlaceRepository {

    private HashMap<Long, Place> places = new HashMap<>();
    private AtomicLong counter = new AtomicLong(0);

    @PostConstruct
    public void init() {

      add(Place.builder().id(1L).name("Juan").address("Canto").address("a").desc("a").desc("c").build());
      add(Place.builder().id(2L).name("Pepe").address("Santo").address("d").desc("e").desc("f").build());
      add(Place.builder().id(3L).name("Antonio").address("Manto").address("g").desc("h").desc("i").build());
    }


    @PostMapping("/place")
    public Place add(Place place) {
        var id = counter.incrementAndGet();
        place.setId(id);
        places.put(id, place);
        return place;
    }

    @GetMapping("/place/{id}")
    public Optional<Place> get(Long id) {
        return Optional.ofNullable(places.get(id));
    }

    @GetMapping("/place/{id}")
    public List<Place> getAll() {
        return List.copyOf(places.values());
    }

    @PutMapping("/place/{id}")
    public Optional<Place> edit(Long id, Place place) {
        return Optional.ofNullable(places.computeIfPresent(id, (k,v) -> {
            v.setName(place.getName());
            v.setDesc(place.getDesc());
            v.setImage(place.getImage());
            v.setCoords(place.getCoords());
            v.setAddress(place.getAddress());
            return v;
        }));
    }

    @DeleteMapping("/place/{id}/tag/\n" +
            "add/{nuevo_tag}")
    public void delete(Long id) {
        places.remove(id);
    }


}
