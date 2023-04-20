/*
public class MyThreadEsteso extends Thread {
	public MyThreadEsteso(Runnable str) {
		super(str);
	}
	public void run() {
		for (int i = 0; i < 10; i++) {
			System.out.println(i + " " + getName());
			try {
				sleep((long)(Math.random() * 100));
			} catch (InterruptedException e) {}
		}
		System.out.println("FINITO! " + getName());
	}
}

 */