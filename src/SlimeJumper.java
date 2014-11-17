import javax.management.timer.Timer;

import org.newdawn.slick.AppGameContainer;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.applet.AppletLoader;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class SlimeJumper extends BasicGame {
	public Image Slime1;
	public Image Enemy1;
	public Shape Slime;
	public Shape PlatForm;
	public Shape Coin1;
	public Shape Coin2;
	public Shape Coin3;
	public Shape Item1;
	public Shape Item2;
	public Shape Item3;
	public Shape Type1;
	public Shape Type2;
	float Gravity = .75f;
	float Jump = -25f;
	float speed = 9f;
	float sprint = 14f;
	float velocity = .01f;
	float zg = .01f;
	float x1 = 0;
	float y1 = 0;
	float x2 = 19;
	float y2 = 800;
	int sx2 = 680;
	int sy2 = 100;
	int s = 0;
	int c1 = 0;
	int c2 = 0;
	int c3 = 0;
	int mX = 0;
	int mY = 0;

	public SlimeJumper(String title) {
		super(title);
	}

	public static void main(String args[]) throws SlickException {
		AppGameContainer a = new AppGameContainer(
				new SlimeJumper("SlimeJumper"));
		a.setDisplayMode(800, 800, false);
		a.setVSync(true);
		a.start();
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		DrawDebugLines(g, 20);
		g.setColor(Color.gray);
		g.draw(PlatForm);
		g.setColor(Color.green);
		g.draw(Slime);
		g.setColor(Color.gray);
		g.draw(Item1);
		g.draw(Item2);
		g.draw(Item3);
		g.setColor(Color.red);
		g.draw(Type1);
		g.drawString("Score " + s, 680, 20);
		if (s >= 120) {
			g.setColor(Color.red);
			g.draw(Type2);
		}
		if (s >= 60) {
			g.setColor(Color.yellow);
			g.draw(Coin1);
		}
		if (c1 == 1) {
			g.setColor(Color.red);
			g.drawString("JumpBoost", 680, 40);
		}
		if (s >= 120) {
			g.setColor(Color.yellow);
			g.draw(Coin2);
		}
		if (c2 == 1) {
			g.setColor(Color.red);
			g.drawString("Sprint", 680, 40);
		}
		if (s >= 200) {
			g.setColor(Color.yellow);
			g.draw(Coin3);
		}
		if (c3 == 1) {
			g.setColor(Color.red);
			g.drawString("Gravity", 680, 40);
		}
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		PlatForm = new Rectangle(20, 720, 760, 40);
		Slime = new Rectangle(sx2, sy2, 40, 40);
		Item1 = new Rectangle(20, 100, 0, 1000);
		Item2 = new Rectangle(780, 100, 0, 1000);
		Item3 = new Rectangle(-80, 100, 80, 1000);
		Type1 = new Rectangle(40, 680, 40, 40);
		Type2 = new Rectangle(40, 680, 40, 40);
		Coin1 = new Rectangle(400, 400, 40, 40);
		Coin2 = new Rectangle(300, 450, 40, 40);
		Coin3 = new Rectangle(400, 450, 40, 40);
	}

	@Override
	public void update(GameContainer gc, int g) throws SlickException {
		if(gc.getInput().isKeyPressed(Input.KEY_B)){
			gc.pause();
		}
		if (s < 0 || Slime.intersects(Type1) || Slime.intersects(Type2)
				|| Slime.intersects(Item3)
				|| gc.getInput().isKeyPressed(Input.KEY_T)) {
			gc.exit();
		}
		if (gc.getInput().isKeyPressed(Input.KEY_R)) {
			gc.resume();
		}
		if (gc.getInput().isKeyPressed(Input.KEY_SPACE)) {
			Slime.setY(Slime.getY() + .2f);
			sy2 += .2f;
			if (PlatForm.intersects(Slime)) {
				y1 = Jump;
				Slime.setY(Slime.getY() - .1f);
				sy2 -= .1f;
			}
		}
		y1 += Gravity;
		Slime.setY(Slime.getY() + y1);
		if (PlatForm.intersects(Slime)) {
			Slime.setY(Slime.getY() - y1);
			sy2 = 680;
			y1 = 0;
		}
		if (gc.getInput().isKeyDown(Input.KEY_A)) {
			x1 = -speed;
		} else if (gc.getInput().isKeyDown(Input.KEY_D)) {
			x1 = speed;
		} else {
			x1 = 0;
		}
		Slime.setX(Slime.getX() + x1);
		if (PlatForm.intersects(Slime) || Item1.intersects(Slime)
				|| Item2.intersects(Slime)) {
			Slime.setX(Slime.getX() - x1);
			x1 = 0;
		}
		if (Slime.intersects(Type1) || Slime.intersects(Item3)) {
		}
		if (PlatForm.intersects(Type1) == false) {
			Type1.setLocation(40, 680);
			s += 1;
		}
		if (s >= 60) {
			if (Coin1.intersects(Slime)) {
				c1 = 1;
				Coin1.setLocation(1000, 1000);
			}
		}
		if (s >= 100) {
			if (PlatForm.intersects(Type2)) {
				Type2.setX(Type2.getX() + 10f);
			}
		}
		if (s >= 100) {
			if (Type2.intersects(Item2)) {
				Type2.setLocation(10, 680);
			}
		}
		if (c1 == 1) {
			Jump = -30f;
		}
		if (s > 80) {
			c1 = 0;
		}
		if (c1 == 0) {
			Jump = -25f;
		}
		if (s >= 120) {
			if (Coin2.intersects(Slime)) {
				c2 = 1;
				Coin2.setLocation(2000, 2000);
			}
		}
		if (c2 == 1) {
			if (gc.getInput().isKeyPressed(Input.KEY_W)) {
				Slime.setX(Slime.getX() - 100);
			}
		}
		if (s >= 140) {
			c2 = 0;
			Coin2.setLocation(2000, 2000);
		}
		if (c2 == 0) {
			if (gc.getInput().isKeyPressed(Input.KEY_W)) {
				Slime.setX(Slime.getX() - 0);
			}
		}
		if (s >= 200) {
			if (Coin3.intersects(Slime))
				;
			c3 = 1;
			Coin3.setLocation(3000, 3000);
		}
		if (c3 == 1) {
			Gravity = .5f;
		}
		if (s >= 220) {
			c3 = 0;
		}
		if (s <= 39) {
			if (PlatForm.intersects(Type1)) {
				Type1.setX(Type1.getX() + 9f);
				Gravity = .75f;
			}
		}
		if (s >= 40) {
			if (PlatForm.intersects(Type1)) {
				Type1.setX(Type1.getX() + 9.2f);
				Gravity = .76f;
			}
		}
		if (s >= 80) {
			if (PlatForm.intersects(Type1)) {
				Type1.setX(Type1.getX() + .1f);
				Gravity = .77f;
			}
		}
		if (s >= 120) {
			if (PlatForm.intersects(Type1) && PlatForm.intersects(Type2)) {
				Type1.setX(Type1.getX() + .1f);
				Gravity = .78f;
			}
		}
		if (s >= 160) {
			if (PlatForm.intersects(Type1)) {
				Type1.setX(Type1.getX() + 9.5f);
				Gravity = .79f;
			}
		}
		if (s >= 200) {
			if (PlatForm.intersects(Type1)) {
				Type1.setX(Type1.getX() + 9.6f);
				Gravity = .80f;
			}
		}
		if (s >= 240) {
			if (PlatForm.intersects(Type1)) {
				Type1.setX(Type1.getX() + 9.7f);
				Gravity = .81f;
			}
		}
		if (s >= 260) {
			if (PlatForm.intersects(Type1)) {
				Type1.setX(Type1.getX() + 9.8f);
				Gravity = .82f;
			}
		}
		if (s >= 300) {
			if (PlatForm.intersects(Type1)) {
				Type1.setX(Type1.getX() + 9.9f);
				Gravity = 83f;
			}
		}
		if (s >= 340) {
			if (PlatForm.intersects(Type1)) {
				Type1.setX(Type1.getX() + 10f);
				Gravity = .84f;
			}
		}
		if (s >= 380) {
			if (PlatForm.intersects(Type1)) {
				Type1.setX(Type1.getX() + 10.1f);
				Gravity = .85f;
			}
		}
		if (s >= 420) {
			if (PlatForm.intersects(Type1)) {
				Type1.setX(Type1.getX() + 10.2f);
				Gravity = .86f;
			}
		}
		if (s >= 460) {
			if (PlatForm.intersects(Type1)) {
				Type1.setX(Type1.getX() + 10.3f);
				Gravity = .87f;
			}
		}
		if (s >= 500) {
			if (PlatForm.intersects(Type1)) {
				Type1.setX(Type1.getX() + 10.4f);
				Gravity = .88f;
			}
		}
		if (s >= 540) {
			if (PlatForm.intersects(Type1)) {
				Type1.setX(Type1.getX() + 10.5f);
				Gravity = .89f;
			}
		}
		if (s >= 580) {
			if (PlatForm.intersects(Type1)) {
				Type1.setX(Type1.getX() + 10.6f);
				Gravity = .90f;
			}
		}
		if (s >= 620) {
			if (PlatForm.intersects(Type1)) {
				Type1.setX(Type1.getX() + 10.7f);
				Gravity = .91f;
			}
		}
		if (s >= 660) {
			if (PlatForm.intersects(Type1)) {
				Type1.setX(Type1.getX() + 10.8f);
				Gravity = .92f;
			}
		}
		if (s >= 700) {
			if (PlatForm.intersects(Type1)) {
				Type1.setX(Type1.getX() + 10.9f);
				Gravity = .93f;
			}
		}
		if (s >= 740) {
			if (PlatForm.intersects(Type1)) {
				Type1.setX(Type1.getX() + 11f);
				Gravity = .94f;
			}
		}
		if (s >= 780) {
			if (PlatForm.intersects(Type1)) {
				Type1.setX(Type1.getX() + 11.1f);
				Gravity = .95f;
			}
		}
		if (s >= 820) {
			if (PlatForm.intersects(Type1)) {
				Type1.setX(Type1.getX() + 11.2f);
				Gravity = .96f;
			}
		}
		if (s >= 860) {
			if (PlatForm.intersects(Type1)) {
				Type1.setX(Type1.getX() + 11.3f);
				Gravity = .97f;
			}
		}
			if (s >= 900) {
				if (PlatForm.intersects(Type1)) {
					Type1.setX(Type1.getX() + 11.4f);
					Gravity = .98f;
			}
		}
			if (s >= 940) {
				if (PlatForm.intersects(Type1)) {
					Type1.setX(Type1.getX() + 11.5f);
					Gravity = .99f;
				}
			}
			if (s >= 980) {
				if (PlatForm.intersects(Type1)) {
					Type1.setX(Type1.getX() + 11.6f);
					Gravity = 1.0f;
				}
			}
	}

	public void DrawDebugLines(Graphics g, int size) {
		int resolution = 800;
		g.setColor(Color.green);
		for (int i = 0; i < resolution; i += size) {
			g.setColor(Color.darkGray);
			g.drawLine(i, 0, i, resolution);
			g.drawLine(0, i, resolution, i);
		}
	}
}
/*Coded By Azura*/
