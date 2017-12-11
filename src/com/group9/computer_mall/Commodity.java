package com.group9.computer_mall;

/**
 * 商品实体类
 * 
 * @author Hasee
 */
public class Commodity {
	public int imageId;// 商品图片资源ID
	public String description;// 商品图片描述
	public String price;// 商品价格
	public boolean pay;// 是否购买商品

	public Commodity(int imageId, String description, String price, boolean pay) {
		this.imageId = imageId;
		this.description = description;
		this.price = price;
		this.pay = pay;
	}

	// 获取商品价格
	public int getPrice() {
		return Integer.parseInt(price.substring(1));
	}

	@Override
	public String toString() {
		return "Commodity [imageId=" + imageId + ", description=" + description + ", price=" + price + ", pay=" + pay
				+ "]";
	}

}
