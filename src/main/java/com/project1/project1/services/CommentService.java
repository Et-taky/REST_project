package com.project1.project1.services;
import com.project1.project1.dto.CommentCreateDto;
import com.project1.project1.dto.CommentDto;
import com.project1.project1.dto.ListResponseDto;
import com.project1.project1.dto.mappers.CommentMapper;
import com.project1.project1.model.Comment;
import com.project1.project1.model.Post;
import com.project1.project1.model.User;
import com.project1.project1.repository.CommentDao;
import com.project1.project1.repository.PostDao;
import com.project1.project1.repository.UserDao;
import com.project1.project1.specification.CommentSpecification;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.UUID;


@Service
public class CommentService {
    @Autowired
    private CommentDao commentDao;
    @Autowired
    private CommentMapper commentMapper;


    @Autowired
    private PostDao postDao;
    @Autowired
    private UserDao userdao;


    //Get list of comments
    public ListResponseDto<CommentDto> getAllComments(String keyword,LocalDate startDate,LocalDate endDate,Pageable pageable) {
        Specification<Comment> spec= CommentSpecification.search(keyword).and(CommentSpecification.filter(startDate,endDate));
        Page<CommentDto> CommentPage= commentDao.findAll(spec,pageable).map(commentMapper::toDto);

        return new ListResponseDto<>(CommentPage);
    }
    //Get by id
    public CommentDto getCommentById(UUID id) {
         Comment comment=commentDao.findById(id).orElseThrow(()->new IllegalArgumentException("Comment Not Found"));
        return commentMapper.toDto(comment);
    }
    //Get Comment by post
    public ListResponseDto<CommentDto> getCommentsByPost(String keyword,UUID id,LocalDate startDate,LocalDate endDate,Pageable pageable) {
        Specification<Comment> spec= CommentSpecification.byPost(id).and(CommentSpecification.search(keyword).and(CommentSpecification.filter(startDate,endDate)));
        Page<CommentDto> comments=commentDao.findAll(spec,pageable).map(commentMapper::toDto);
        return new ListResponseDto<>(comments);
    }

    //Get Comment by user
    public ListResponseDto<CommentDto> getCommentsByUser(String keyword,UUID id,LocalDate startDate,LocalDate endDate,Pageable pageable) {
        Specification<Comment> spec= CommentSpecification.byUser(id).and(CommentSpecification.search(keyword).and(CommentSpecification.filter(startDate,endDate)));
        Page<CommentDto> comments=commentDao.findAll(spec,pageable).map(commentMapper::toDto);
        return new ListResponseDto<>(comments);
    }

    //create
    public CommentDto createComment(@RequestBody @Valid CommentCreateDto commentDto) {

        User owner=userdao.findById(commentDto.getOwner()).orElseThrow(()->new IllegalArgumentException("No User with the given id found!"));
        Post post=postDao.findById(commentDto.getPost()).orElseThrow(()->new IllegalArgumentException("No Post with the given id found!"));
        Comment c=commentMapper.toEntity(commentDto);
        c.setOwner(owner);
        c.setPost(post);
        return commentMapper.toDto(commentDao.save(c)) ;
    }

    //delete
    public UUID deleteComment(UUID id) {
        if(commentDao.findById(id).isEmpty()){
            throw new IllegalArgumentException("Comment with id: "+id+" does not exist");
        }
        commentDao.deleteById(id);
        return id;
    }
}   