package com.example.shop.entity;

import com.example.shop.R;

public class Stuff {
    private String id;//id
    private String name;//名称
    private String title;//标题
    private String kind;//分类
    private String price;//价格
    private int pic;//图片

    public Stuff() {
        this.id="-1";
    }

    public Stuff( String name, String title, String kind, String price ) {
        this.id = "id";
        this.name = name;
        this.title = title;
        this.kind = kind;
        this.price = price;
        this.pic = 0;
    }


    public Stuff(String id, String name, String title, String kind, String price) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.kind = kind;
        this.price = price;
        switch (name){
            case "黑笔":
                this.pic= R.drawable.pen;
                break;
            case "本子":
                this.pic=R.drawable.paper;
                break;
            case "保温杯":
                this.pic=R.drawable.cup;
                break;
            case "公园同款垃圾桶":
                this.pic=R.drawable.bucket;
                break;
            case "红轴机械键盘":
                this.pic=R.drawable.keyboard;
                break;
            default:
                this.pic=R.drawable.keyboard;
                break;
        }
    }

    @Override
    public String toString() {
        return "Stuff{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", kind='" + kind + '\'' +
                ", price='" + price + '\'' +
                ", pic=" + pic +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getPic() {
        return pic;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }
}
