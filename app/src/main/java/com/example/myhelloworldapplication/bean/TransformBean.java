package com.example.myhelloworldapplication.bean;

import java.io.Serializable;
import java.util.List;

public class TransformBean implements Serializable {
    private String created_at;
    private int id;
    private List<Double> field1;


    public void setCreated_at(String created_at){
        this.created_at = created_at;
    }

    public String getCreated_at(){
        return this.created_at;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return this.id;
    }

    public void setField1 (List<Double> field1){
        this.field1 = field1;
    }

    public List<Double> getField1(){
        return this.field1;
    }
}
