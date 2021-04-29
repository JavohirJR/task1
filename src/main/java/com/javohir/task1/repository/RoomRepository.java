package com.javohir.task1.repository;

import com.javohir.task1.entity.Hotel;
import com.javohir.task1.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Integer> {
    Page<Room> findAllByHotel_Id(Integer hotel_id, Pageable pageable);
}
