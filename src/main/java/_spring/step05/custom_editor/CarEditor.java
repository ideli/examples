package _spring.step05.custom_editor;

import java.beans.PropertyEditorSupport;

public class CarEditor extends PropertyEditorSupport {

	public void setAsText(String text) {
		String[] infos = text.split(",");
		Car car = new Car();
		car.setBrand(infos[0]);
		car.setMaxSpeed(Integer.parseInt(infos[1]));
		car.setPrice(Double.parseDouble(infos[2]));
		setValue(car);
	}
}
