package com.eat.entity;

import org.springframework.stereotype.Service;

//
@Service
public class Menu {
	int zyid;
	private String zyname;
	private float zyprice;
	private String zyremark;


	public Menu(String zyname, float zyprice, String zyremark) {
		this.zyname = zyname;
		this.zyprice = zyprice;
		this.zyremark = zyremark;

	}

    public Menu() {

    }

    public String getZyname() {
		return zyname;
	}

	public void setZyname(String zyname) {
		this.zyname = zyname;
	}

	public float getZyprice() {
		return zyprice;
	}

	public void setZyprice(float zyprice) {
		this.zyprice = zyprice;
	}

	public String getZyremark() {
		return zyremark;
	}

	public void setZyremark(String zyremark) {
		this.zyremark = zyremark;
	}


	public int getZyid() {
		return zyid;
	}

	public void setZyid(int zyid) {
		this.zyid = zyid;
	}

	@Override
	public String toString() {
		return "Menu{" +
				"zyid=" + zyid +
				", zyname='" + zyname + '\'' +
				", zyprice=" + zyprice +
				", zyremark='" + zyremark + '\'' +
				'}';
	}
}
