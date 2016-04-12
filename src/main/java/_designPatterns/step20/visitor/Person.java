package _designPatterns.step20.visitor;

/*
 * 人(Element 数据结构要求稳定,固定分为Man和Woman)
 */
public abstract class Person {

	public abstract void accept(Action action);
}
