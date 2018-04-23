/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.exam;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.servlet.http.Part;

/**
 *
 * @author l2pc214e
 */
@ManagedBean
public class Image {

    private String s1;

    private int id;
    private String name;
    private Part f1;
    private String path = "C:\\Users\\l2pc214e\\Desktop\\JSFImageApp";
    private List list = new ArrayList();

    public Image(int id, String name, String s1) {

        this.id = id;
        this.name = name;
        this.s1 = s1;
    }

    public Part getF1() {
        return f1;
    }

    public String getS1() {
        return s1;
    }

    public void setS1(String s1) {
        this.s1 = s1;
    }

    public void setF1(Part f1) {
        this.f1 = f1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public void save() {
        try {
            InputStream is = f1.getInputStream();
            s1 = f1.getSubmittedFileName();
            Files.copy(is, new File(path + "\\web\\resources\\images\\", s1).toPath());
            saveToDB("resources/images/" + s1);

        } catch (IOException ex) {
            Logger.getLogger(Image.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void saveToDB(String s) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/userdb", "root", "apcl123456");
            PreparedStatement pstm = conn.prepareStatement("insert into user values(?,?,?)");
            pstm.setInt(1, id);
            pstm.setString(2, name);
            pstm.setString(3, s1);
            int i = pstm.executeUpdate();
            if (i > 0) {
                System.out.println("Save Successfully");
            } else {
                System.out.println("Save Fail");
            }
        } catch (Exception e) {
        }

    }

    public void Show() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/userdb", "root", "apcl123456");
            PreparedStatement pstm = conn.prepareStatement("select * from user");
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                Image im = new Image(rs.getInt(1), rs.getString(2), rs.getString(3));
                list.add(im);
            }
        } catch (Exception e) {
        }

    }

}
