package com.hmy.example.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class ApiInfo {
    @Id(autoincrement = true)
    private Long id;
    private long time;
    private String data;

    @Generated(hash = 1356865037)
    public ApiInfo(Long id, long time, String data) {
        this.id = id;
        this.time = time;
        this.data = data;
    }

    @Generated(hash = 1876006400)
    public ApiInfo() {
    }

    public ApiInfo(String data, long timeMillis) {
        this.data = data;
        this.time = timeMillis;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getTime() {
        return this.time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getData() {
        return this.data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", time=" + time +
                ", data='" + data + '\'' +
                '}';
    }
}
