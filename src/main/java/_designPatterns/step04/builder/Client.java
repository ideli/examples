package _designPatterns.step04.builder;

/*
 * 建造者模式
 * 将一个负责对象的构建与它的表示分离,使得同样的构建过程可以创建不同的表示
 */
public class Client {

	public void test() {
		ProductSmallBuilder hb = new ProductSmallBuilder();
		Director director = new Director(hb);
		director.createProduct();
		Product f1 = hb.getResult();
		f1.show();
		
		ProductBigBuilder cb = new ProductBigBuilder();
		director = new Director(cb);
		director.createProduct();
		Product f2 = cb.getResult();
		f2.show();
	}
}
