package com.garcia.giovane.form.impl.repository;

import com.garcia.giovane.form.impl.model.UserModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserModel, Long> {
}
