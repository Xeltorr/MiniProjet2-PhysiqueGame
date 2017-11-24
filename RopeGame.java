package ch.epfl.cs107.play.game.tutorial;

import java.awt.Color;

import ch.epfl.cs107.play.game.Game;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.EntityBuilder;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.RopeConstraintBuilder;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.math.World;
import ch.epfl.cs107.play.window.Window;

public class RopeGame implements Game {

	private Window window;
	private World world;
	private Entity ball;
	private Entity block;
	private ShapeGraphics ballGraphics;
	private ImageGraphics bodyGraphics;
	
	public boolean begin(Window window, FileSystem fileSystem) {
		this.window = window;
		world = new World();
		world.setGravity(new Vector(0.0f, -9.81f));
		
		EntityBuilder entityBuilder = world.createEntityBuilder();
		entityBuilder.setFixed(false);
		entityBuilder.setPosition(new Vector(10.0f, 50.0f));
		ball = entityBuilder.build();
		PartBuilder partBuilder = ball.createPartBuilder();
        Circle circle = new Circle(.60f);
        partBuilder.setShape(circle);
        partBuilder.build();
        
        entityBuilder.setFixed(true);
        entityBuilder.setPosition(new Vector(0.50f, 1.0f));
        block = entityBuilder.build();
        partBuilder = block.createPartBuilder();
        Polygon polygon = new Polygon(
				 new Vector(0.0f, 0.0f),
				 new Vector(1.0f, 0.0f),
				 new Vector(1.0f, 1.0f),
				 new Vector(0.0f, 1.0f));
		partBuilder.setShape(polygon);
		partBuilder.build();
        
		ballGraphics = new ShapeGraphics(circle, Color.blue, Color.RED, .1f, 1.0f, 0);
		ballGraphics.setParent(ball);
		bodyGraphics = new ImageGraphics("box.4.png", 1.0f, 1.0f);
		bodyGraphics.setParent(block);
		RopeConstraintBuilder ropeConstraintBuilder =
				world.createRopeConstraintBuilder () ;
				ropeConstraintBuilder.setFirstEntity(block) ;
				ropeConstraintBuilder.setFirstAnchor(new Vector( 1.0f /2,
				1.0f /2)) ;
				ropeConstraintBuilder.setSecondEntity(ball) ;
				ropeConstraintBuilder.setSecondAnchor(Vector.ZERO) ;
				ropeConstraintBuilder.setMaxLength (3.0f) ;
				ropeConstraintBuilder.setInternalCollision(true) ;
				ropeConstraintBuilder.build () ;
		return true;
		
	}
	public void update(float deltaTime) {
		world.update(deltaTime);
		window.setRelativeTransform(Transform.I.scaled(10.0f));
		ballGraphics.draw(window);
		bodyGraphics.draw(window);
	}
	public void end() {
        // Empty on purpose, no cleanup required yet
    }
}
