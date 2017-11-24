package ch.epfl.cs107.play.game.tutorial;

import java.awt.event.KeyEvent;

import ch.epfl.cs107.play.game.Game;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.EntityBuilder;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.RevoluteConstraintBuilder;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.math.World;
import ch.epfl.cs107.play.window.Window;

public class ScaleGame implements Game {

	private Window window;
	private World world;
	private Entity ball;
	private Entity plank;
	private Entity block;
	private ImageGraphics graphicsBall;
	private ImageGraphics graphicsPlank;
	private ImageGraphics graphicsBlock;
	
	
	public boolean begin(Window window, FileSystem fileSystem) {
		this.window = window;
		
		world = new World();
		world.setGravity(new Vector(0.0f, -9.81f));
		
		EntityBuilder entityBuilder = world.createEntityBuilder();
		entityBuilder.setFixed(true);
		entityBuilder.setPosition(new Vector(-5.0f, -1.0f));
		block = entityBuilder.build();
		PartBuilder partBuilder = block.createPartBuilder();
		Polygon polygon =  new Polygon(
				 new Vector(0.0f, 0.0f),
				 new Vector(10.0f, 0.0f),
				 new Vector(10.0f, 1.0f),
				 new Vector(0.0f, 1.0f));
		partBuilder.setShape(polygon);
		partBuilder.build();
		
		EntityBuilder entityBuilder2 = world.createEntityBuilder();
		entityBuilder2.setFixed(false);
		entityBuilder2.setPosition(new Vector(-2.5f, 0.8f));
		plank = entityBuilder2.build();
		PartBuilder partBuilder2 = plank.createPartBuilder();
		Polygon polygon1 =  new Polygon(
				 new Vector(0.0f, 0.0f),
				 new Vector(5.0f, 0.0f),
				 new Vector(5.0f, 0.20f),
				 new Vector(0.0f, 0.20f));
		partBuilder2.setShape(polygon1);
		partBuilder2.build();
		
		EntityBuilder entityBuilder3 = world.createEntityBuilder();
		entityBuilder3.setFixed(false);
		entityBuilder3.setPosition(new Vector(0.5f, 4.0f));
		ball = entityBuilder3.build();
		PartBuilder partBuilder3 = ball.createPartBuilder();
		Circle circle = new Circle(0.6f);
		partBuilder3.setShape(circle);
		partBuilder3.build();
		
		graphicsBall = new ImageGraphics("explosive.11.png", 1.2f, 1.2f, new Vector(.5f, .5f));
		graphicsBall.setParent(ball);
		graphicsBlock = new ImageGraphics("stone.broken.4.png", 10.0f, 1.0f);
		graphicsBlock.setParent(block);
		graphicsPlank = new ImageGraphics("wood.3.png", 5.0f, 0.2f);
		graphicsPlank.setParent(plank);
		
		RevoluteConstraintBuilder revoluteConstraintBuilder =
				world.createRevoluteConstraintBuilder () ;
				revoluteConstraintBuilder.setFirstEntity(block) ;
				revoluteConstraintBuilder.setFirstAnchor(new Vector(10.f /2,
				(1.0f *7) /4)) ;
				revoluteConstraintBuilder.setSecondEntity(plank) ;
				revoluteConstraintBuilder.setSecondAnchor(new Vector(5.f /2,
				0.2f /2)) ;
				revoluteConstraintBuilder.setInternalCollision(true) ;
				revoluteConstraintBuilder.build () ;
		
		return true;
	}
	
	public void update(float deltaTime) {
		if (window.getKeyboard ().get(KeyEvent.VK_LEFT).isDown ()) {
			ball.applyAngularForce (10.0f);
		}
		
		else if (window.getKeyboard ().get(KeyEvent.VK_RIGHT).isDown ()) {
			ball.applyAngularForce ( -10.0f) ;
			}
		if(window.getKeyboard().get(KeyEvent.VK_SPACE).isPressed()) {
			ball.applyForce(new Vector(.0f, 1000.f), null);
		}
		if (window.getMouse ().getLeftButton ().isPressed ()) 
		{
			ball.applyForce(new Vector(100.f, 1000.f), null);
		}
		if(window.getMouse().getRightButton().isPressed()) {
			ball.applyForce(new Vector(-100.f, 1000.f), null);
		}
		world.update(deltaTime);
		window.setRelativeTransform(Transform.I.scaled(10.0f));
		graphicsBall.draw(window);
		graphicsBlock.draw(window);
		graphicsPlank.draw(window);
		
	}
	public void end() {
        // Empty on purpose, no cleanup required yet
    }
}
