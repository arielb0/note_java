package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.CustomUser;
import com.example.demo.entity.Note;
import java.util.List;


@Repository
public interface NoteRepository extends JpaRepository<Note, Long>{
    
    List<Note> findByUser(CustomUser user);
    List<Note> findByTitleContainingOrBodyContainingAndUser(String title, String body, CustomUser user);

}
