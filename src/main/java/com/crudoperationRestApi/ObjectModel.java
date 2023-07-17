package com.crudoperationRestApi;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.TypeDef;

@Entity
@TypeDef(name = "StringJsonUserType", typeClass = StringJsonUserType.class)
public class ObjectModel {

    @Id
    @org.hibernate.annotations.Type(type = "StringJsonUserType")
    private Object result;


    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}

