package _enum.step03;

public enum Soldier {

	Swordman {
		void fight() {
			System.out.println("Cut");
		}
	},
	Horseman {
		void fight() {
			System.out.println("Rush");
		}
	};
	
	abstract void fight();
}
