package com.project1.project1.services;

import com.project1.project1.dto.ListResponseDto;
import com.project1.project1.dto.UserFullDto;
import com.project1.project1.dto.UserPreviewDto;
import com.project1.project1.dto.mappers.UserMapper;
import com.project1.project1.exception.BodyNotValidException;
import com.project1.project1.exception.ResourceNotFoundException;
import com.project1.project1.model.User;
import com.project1.project1.repository.UserDao;
import com.project1.project1.specification.UserSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    UserMapper userMapper;

    //get List
    public ListResponseDto<UserPreviewDto> getAllUsers(String keyword,LocalDate startDateOfBirth,LocalDate endDateOfBirth,LocalDate startRegisterDate,LocalDate endRegisterDate,String country,String state,String city,String timezone,Pageable pageable) {

        Specification<User> spec= UserSpecification.search(keyword).and(UserSpecification.filter(startDateOfBirth,endDateOfBirth,startRegisterDate,endRegisterDate,country,state,city,timezone));
        Page<UserPreviewDto> userPage = userDao.findAll(spec ,pageable).map(userMapper::toPreviewDto);
        return new ListResponseDto<>(userPage);
    }

    //get by id
    public UserFullDto getUserById(UUID id) {
        return userDao.findById(id).map(userMapper::toFullDto).orElseThrow(()-> new ResourceNotFoundException("No user found!"));
    }

    //create user
    public UserFullDto CreateUser(UserFullDto user) {
        if(user.getFirstName()!=null & user.getLastName()!=null & user.getEmail()!=null){
            User u=userMapper.toEntity(user);
            return userMapper.toFullDto(userDao.save(u));
        }else{
            throw new BodyNotValidException("First name ,Last name and email address are required!");
        }
    }

    //update
    public UserFullDto updateUser(UUID id, UserFullDto userdto) {
        User us=userDao.findById(id).orElseThrow(()-> new ResourceNotFoundException("No user found!"));
        if(userdto.getFirstName()!=null & userdto.getLastName()!=null & userdto.getEmail()!=null){
           if(!userdto.getEmail().equals(us.getEmail())){
               throw new BodyNotValidException("You can not modify the email!");
           }
            userMapper.updateUserFromDto(userdto,us);
            us=userDao.save(us);
        }else {
            throw new BodyNotValidException("First name ,Last name and email address are required!");
        }
        return userMapper.toFullDto(us);
    }

    //delete
    public UUID deleteUser(UUID id) {
       User us= userDao.findById(id).orElseThrow(()->new ResourceNotFoundException("User does not exist!"));
        userDao.deleteById(id);
       return  us.getId();
    }
}


