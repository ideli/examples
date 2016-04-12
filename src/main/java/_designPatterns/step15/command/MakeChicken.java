package _designPatterns.step15.command;

public class MakeChicken extends Command {

	public MakeChicken(Cook cook) {
		super(cook);
	}
	
	@Override
	public void execute() {
		cook.makeChicken();
	}

}
