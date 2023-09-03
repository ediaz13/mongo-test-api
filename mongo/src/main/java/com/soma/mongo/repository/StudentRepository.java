package com.soma.mongo.repository;

import com.soma.mongo.model.Student;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StudentRepository
        extends MongoRepository<Student, String> {


}
