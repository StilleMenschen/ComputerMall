package com.group9.computer_mall;

/**
 * ��Ʒʵ����
 * 
 * @author Hasee
 */
public class Commodity {
	public int imageId;// ��ƷͼƬ��ԴID
	public String description;// ��ƷͼƬ����
	public String price;// ��Ʒ�۸�
	public boolean pay;// �Ƿ�����Ʒ

	public Commodity(int imageId, String description, String price, boolean pay) {
		this.imageId = imageId;
		this.description = description;
		this.price = price;
		this.pay = pay;
	}

	// ��ȡ��Ʒ�۸�
	public int getPrice() {
		return Integer.parseInt(price.substring(1));
	}

	@Override
	public String toString() {
		return "Commodity [imageId=" + imageId + ", description=" + description + ", price=" + price + ", pay=" + pay
				+ "]";
	}

}
