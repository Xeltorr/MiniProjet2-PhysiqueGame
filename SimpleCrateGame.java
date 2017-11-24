package ch.epfl.cs107.play.game.tutorial;

import ch.epfl.cs107.play.game.Game;

import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.EntityBuilder;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.math.World;
import ch.epfl.cs107.play.window.Window;

public class SimpleCrateGame implements Game {

		// TODO Auto-generated method stub

		private Window window;
		private World world;
		private Entity body;
		private Entity body2;
		private ImageGraphics graphics;
		private ImageGraphics graphics2;
		
		
		public boolean begin(Window window, FileSystem fileSystem) {
			this.window = window;
			world = new World();
			world.setGravity(new Vector(0.0f, -9.81f));
			EntityBuilder entityBuilder = world.createEntityBuilder();
			entityBuilder.setFixed(true);
			entityBuilder.setPosition(new Vector(0.0f, 1.00f));
			body = entityBuilder.build();
			PartBuilder partBuilder = body.createPartBuilder();
			Polygon polygon = new Polygon(
					 new Vector(0.0f, 0.0f),
					 new Vector(1.0f, 0.0f),
					 new Vector(1.0f, 1.0f),
					 new Vector(0.0f, 1.0f));
			partBuilder.setShape(polygon);
			partBuilder.build();
			entityBuilder.setFixed(false);
			entityBuilder.setPosition(new Vector(0.0f, 50.0f));
			body2 = entityBuilder.build();
			partBuilder = body2.createPartBuilder();
			partBuilder.setShape(polygon);
			partBuilder.build();
			graphics = new ImageGraphics("box.4.png", 1.0f, 1.0f);
			graphics.setDepth(0.0f);
			graphics2 = new ImageGraphics("stone.broken.4.png", 1.0f, 1.0f);
			graphics2.setDepth(0.0f);
			graphics.setParent(body);
			graphics2.setParent(body2);
			
			return true;
		}
		public void update(float deltaTime) {
			world.update(deltaTime);
			window.setRelativeTransform(Transform.I.scaled (10.0f)) ;
	    	graphics.draw(window) ;
	    	graphics2.draw(window);
		}
		 public void end() {
		        // Empty on purpose, no cleanup required yet
		    }
	}


