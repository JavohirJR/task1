package com.javohir.task1.controller;

import com.javohir.task1.entity.Hotel;
import com.javohir.task1.entity.Room;
import com.javohir.task1.payload.RoomDTO;
import com.javohir.task1.repository.HotelRepository;
import com.javohir.task1.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/room")
public class RoomController {

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    HotelRepository hotelRepository;

    @PostMapping
    public String addOne(@RequestBody RoomDTO roomDTO) {
        Optional<Hotel> optionalHotel = hotelRepository.findById(roomDTO.getHotel());
        if (!optionalHotel.isPresent()) return "Hotel is not found!";
        boolean existRoom = roomRepository.existsByNumberAndHotel_Id(roomDTO.getNumber(), roomDTO.getHotel());
        if (existRoom) return "Room is already exist";
        Room roomNew = new Room();
        roomNew.setNumber(roomDTO.getNumber());
        roomNew.setFloor(roomDTO.getFloor());
        roomNew.setSize(roomDTO.getSize());
        roomNew.setHotel(optionalHotel.get());
        roomRepository.save(roomNew);
        return "Room added successfully";
    }

    @PutMapping(value = "/{id}")
    public String editOne(@PathVariable Integer id, @RequestBody RoomDTO roomDTO) {
        Optional<Hotel> optionalHotel = hotelRepository.findById(roomDTO.getHotel());
        if (!optionalHotel.isPresent()) return "Hotel is not found!";
        Optional<Room> optionalRoom = roomRepository.findById(id);
        if (!optionalRoom.isPresent()) return "Room is not found";
        boolean existRoom = roomRepository.existsByNumberAndHotel_Id(roomDTO.getNumber(), roomDTO.getHotel());
        Room roomNew = optionalRoom.get();
        if (existRoom && !roomDTO.getNumber().equals(roomNew.getNumber())) {
            return "Room is already exist";
        }
        roomNew.setNumber(roomDTO.getNumber());
        roomNew.setFloor(roomDTO.getFloor());
        roomNew.setSize(roomDTO.getSize());
        roomNew.setHotel(optionalHotel.get());
        roomRepository.save(roomNew);
        return "Room edited successfully";
    }

    @DeleteMapping(value = "/{id}")
    public String deleteOne(@PathVariable Integer id) {
        Optional<Room> optionalRoom = roomRepository.findById(id);
        if (!optionalRoom.isPresent()) return "Room is not found";
        roomRepository.deleteById(id);
        return "Room deleted successfully";
    }

    @GetMapping
    public List<Room> getAll() {
        return roomRepository.findAll();
    }

    @GetMapping(value = "/byhotel/{id}")
    public Page<Room> getRoomsByHotel(@PathVariable Integer id, @RequestParam int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return roomRepository.findAllByHotel_Id(id, pageable);
    }
}
