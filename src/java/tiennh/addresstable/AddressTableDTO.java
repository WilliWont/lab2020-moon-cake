/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiennh.addresstable;

import java.io.Serializable;

public class AddressTableDTO implements Serializable {

    private String nameFull;
    private String nameF;
    private String nameL;
    private String addrLine;
    private String ward;
    private String district;
    private String city;
    private String phone;
    private String username;
    private Integer id;

    public String getNameFull() {
        return nameFull;
    }

    public void setNameFull(String nameFull) {
        this.nameFull = nameFull;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNameF() {
        return nameF;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setNameF(String nameF) {
        this.nameF = nameF;
    }

    public String getNameL() {
        return nameL;
    }

    public void setNameL(String nameL) {
        this.nameL = nameL;
    }

    public String getAddrLine() {
        return addrLine;
    }

    public void setAddrLine(String addrLine) {
        this.addrLine = addrLine;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String addressToString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.addrLine);
        sb.append(' ');
        sb.append(this.ward);
        sb.append(' ');
        sb.append(this.district);
        sb.append(' ');
        sb.append(this.city);

        return sb.toString();
    }
}
