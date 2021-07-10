package com.example.myhelloworldapplication.bean;
import java.util.List;
public class ChannelBean {
    private int id;
    private String name;
    private String description;
    private String latitude;
    private String longitude;
    private String field1;
    private String field2;
    private String field3;
    private String field4;
    private String field5;
    private String field6;
    private String field7;
    private String field8;
    private String created_at;
    private String updated_at;
    private String elevation;
    private int last_entry_id;

    public ChannelBean(){}

    public ChannelBean(int id, String name,String description, String latitude, String longitude,
                       String field1, String field2, String field3, String field4, String field5,
                       String field6, String field7, String field8, String created_at, String updated_at, String elevation, int last_entry_id){
        this.id = id;
        this.name =name ;
        this.description =description ;
        this.latitude =latitude ;
        this.longitude =longitude ;
        this.field1 =field1 ;
        this.field2 =field2 ;
        this.field3 =field3 ;
        this.field4 =field4 ;
        this.field5 =field5 ;
        this.field6 =field6 ;
        this.field7 =field7 ;
        this.field8 =field8 ;
        this.created_at =created_at ;
        this.updated_at =updated_at ;
        this.elevation =elevation ;
        this.last_entry_id =last_entry_id ;

    }

    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }

    public void setDescription(String description){
        this.description = description ;
    }
    public String getDescription(){
        return this.description;
    }

    public void setLatitude(String latitude){
        this.latitude =latitude ;
    }
    public String getLatitude(){
        return this.latitude;
    }


    public void setLongitude(String longitude){
        this.longitude =longitude ;
    }
    public String getLongitude(){
        return this.longitude;
    }


    public void setField1(String field1){
        this.field1 = field1;
    }
    public String getField1(){
        return this.field1;
    }


    public void setField2(String field2){
        this.field2 = field2;
    }
    public String getField2(){
        return this.field2;
    }


    public void setField3(String field3){
        this.field3 = field3;
    }
    public String getField3(){
        return this.field3;
    }


    public void setField4(String field4){
        this.field4 = field4;
    }
    public String getField4(){
        return this.field4;
    }


    public void setField5(String field5){
        this.field5 = field5;
    }
    public String getField5(){
        return this.field5;
    }

    public void setField6(String field6){
        this.field6= field6;
    }
    public String getField6(){
        return this.field6;
    }

    public void setField7(String field7){
        this.field7= field7;
    }
    public String getField7(){
        return this.field7;
    }

    public void setField8(String field8){
        this.field8= field8;
    }
    public String getField8(){
        return this.field8;
    }

    public void setCreated_at(String created_at){
        this.created_at= created_at;
    }
    public String getCreated_at(){
        return this.created_at;
    }

    public void setUpdated_at(String updated_at){
        this.updated_at= updated_at;
    }
    public String getUpdated_at(){
        return this.updated_at;
    }

    public void setElevation(String elevation){
        this.elevation= elevation;
    }
    public String get(){
        return this.elevation;
    }



    public void setLast_entry_id(int last_entry_id){
        this.last_entry_id = last_entry_id;
    }
    public int getLast_entry_id(){
        return this.last_entry_id;
    }


}
