package _designPatterns.step15.command;

public class MakeHamburger extends Command {

	public MakeHamburger(Cook cook) {
		super(cook);
	}

	@Override
	public void execute() {
		this.cook.makeHamburger();
	}

}
