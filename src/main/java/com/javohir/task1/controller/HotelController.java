package com.javohir.task1.controller;

import com.javohir.task1.entity.Hotel;
import com.javohir.task1.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/hotel")
public class HotelController {

    @Autowired
    HotelRepository hotelRepository;

    @PostMapping
    public String addOne(@RequestBody Hotel hotel) {
        Hotel hotelNew = new Hotel();
        hotelNew.setName(hotel.getName());
        hotelRepository.save(hotelNew);
        return "Hotel added successfully";
    }

    @GetMapping
    public List<Hotel> getAll() {
        return hotelRepository.findAll();
    }


    @GetMapping(value = "/{id}")
    public Hotel getOne(@PathVariable Integer id) {
        Optional<Hotel> optionalHotel = hotelRepository.findById(id);
        return optionalHotel.orElseGet(Hotel::new);
    }

    @PutMapping(value = "/{id}")
    public String editOne(@RequestBody Hotel hotel, @PathVariable Integer id) {
        Optional<Hotel> optionalHotel = hotelRepository.findById(id);
        if (optionalHotel.isPresent()) {
            Hotel hotelNew = optionalHotel.get();
            hotelNew.setName(hotel.getName());
            hotelRepository.save(hotelNew);
            return "Hotel edited successfully";
        }
        return "Hotel is not found";
    }


    @DeleteMapping(value = "/{id}")
    public String deleteOne(@PathVariable Integer id) {
        Optional<Hotel> optionalHotel = hotelRepository.findById(id);
        if (optionalHotel.isPresent()){
            hotelRepository.deleteById(id);
            return "Hotel deleted successfully!";
        }
        return "Hotel is not found";
    }
}
