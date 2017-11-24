package ch.epfl.cs107.play.game.tutorial;

import java.awt.Color;
import java.awt.event.KeyEvent;

import ch.epfl.cs107.play.game.Game;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.BasicContactListener;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.EntityBuilder;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.math.World;
import ch.epfl.cs107.play.window.Window;

public class ContactGame implements Game {

	private Window window;
	private World world;
	private Entity ball;
	private Entity block;
	private ShapeGraphics ballGraphics;
	private ImageGraphics blockGraphics;
	private BasicContactListener contactListener;
	
	public boolean begin(Window window, FileSystem fileSystem) {
	     this.window = window;
	     world = new World();
	     world.setGravity(new Vector(0.0f, -9.81f));
	     
	     EntityBuilder entityBuilder = world.createEntityBuilder();
		entityBuilder.setFixed(false);
		entityBuilder.setPosition(new Vector(3.0f, 2.0f));
		ball = entityBuilder.build();
		Circle circle = new Circle(0.5f);
		PartBuilder partBuilder = ball.createPartBuilder();
		partBuilder.setShape(circle);
		partBuilder.build();
		
		contactListener = new BasicContactListener () ;
		ball.addContactListener(contactListener) ;
	     EntityBuilder entityBuilder2 = world.createEntityBuilder();

		entityBuilder2.setFixed(true);
		entityBuilder2.setPosition(new Vector(-5.f, -1.f));
		block = entityBuilder2.build();
		Polygon polygon = new Polygon(
				     new Vector(0.0f, 0.f),
				     new Vector(10.f, 0.f),
				     new Vector(10.f, 1.0f),
				     new Vector(0.0f, 1.0f)
				);
		PartBuilder partBuilder2 = block.createPartBuilder();
		partBuilder2.setShape(polygon);
		partBuilder2.build();
		ballGraphics = new ShapeGraphics(circle ,Color.WHITE , Color.BLACK , .1f,
				1, 0) ;
		ballGraphics.setParent(ball);
		blockGraphics = new ImageGraphics("stone.broken.4.png", 10.f, 1.f);
		blockGraphics.setParent(block);
		return true;
	}
	
	public void update(float deltaTime) {
		if (window.getKeyboard ().get(KeyEvent.VK_LEFT).isDown ()) {
			ball.applyAngularForce(05.0f);
		}
		
		else if (window.getKeyboard ().get(KeyEvent.VK_RIGHT).isDown ()) {
			
			ball.applyAngularForce ( -05.0f) ;
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

		int numberOfCollisions = contactListener.getEntities ().size() ;
		if (numberOfCollisions > 0){
			ballGraphics.setFillColor(Color.red) ;
			}
			ballGraphics.draw(window) ;
			blockGraphics.draw(window);
	}
	public void end() {
        // Empty on purpose, no cleanup required yet
    }
	
}
