package com.anjoyo.gamecenter.rank;

public class Rankbean {

	public int id;
	//游戏图片icon
	public String icon;
	//游戏名称
	public String title;
	//游戏大小
	public String filesize;
	//所有打分总和
	public int infopfen;
	//参入打分人数
	public int infopfennum;
	public int onclick;
	public String version;
	public int star;
	//下载地址
	public String flashurl;
	
	
	
	public String getFlashurl() {
		return flashurl;
	}
	public void setFlashurl(String flashurl) {
		this.flashurl = flashurl;
	}
	public int getStar() {
		return star;
	}
	public void setStar(int star) {
		this.star = star;
	}
	public int getOnclick() {
		return onclick;
	}
	public void setOnclick(int onclick) {
		this.onclick = onclick;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getFilesize() {
		return filesize;
	}
	public void setFilesize(String filesize) {
		this.filesize = filesize;
	}
	public int getInfopfen() {
		return infopfen;
	}
	public void setInfopfen(int infopfen) {
		this.infopfen = infopfen;
	}
	public int getInfopfennum() {
		return infopfennum;
	}
	public void setInfopfennum(int infopfennum) {
		this.infopfennum = infopfennum;
	}
	
	
	
}
