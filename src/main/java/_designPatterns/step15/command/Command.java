package _designPatterns.step15.command;

public abstract class Command {

	protected Cook cook;
	
	public Command(Cook cook) {
		this.cook = cook;
	}
	
	public abstract void execute();
}
