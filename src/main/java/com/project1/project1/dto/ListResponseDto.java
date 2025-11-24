package com.project1.project1.dto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public class ListResponseDto<T> {

     private List<T> data;
     private int total;
     private int page;
     private int limit;

     public ListResponseDto(Page<T> pageResult){
         this.data=pageResult.getContent();
         this.limit=pageResult.getSize();
         this.page=pageResult.getNumber();
         this.total= Math.toIntExact(pageResult.getTotalElements());
     }

     public void  setData(List<T> data){
         this.data=data;
     }
     public List<T> getData(){
         return this.data;
     }
     public void setTotal(int total){
         this.total=total;
     }
     public int getTotal(){
         return this.total;
     }
     public void setPage(int page){
         this.page=page;
     }
     public int getPage(){
         return this.page;
     }
     public void setLimit(int limit){
         this.limit=limit;
     }
     public int getLimit(){
         return this.limit;
     }

}
