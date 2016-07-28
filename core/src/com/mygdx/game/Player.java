package com.mygdx.game;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

import com.badlogic.gdx.utils.Pool;

public class Player extends AnimatedSprite {

	private int health;
	private int maxHealth; 

	private float moveCooldown;
	private static final float MOVE_COOLDOWN = 0.15f;

	private static final float MOVE_TIME = 0.15f;
	
	private static final int INIT_HEALTH = 100; 
	private static final int DEFAULT_MAX_HEALTH = 100; 

	private boolean hit;

	private Pool<MoveToAction> moveActions;
	private RunnableAction pickItemsUp;

	public Player(int x, int y) {
		animations = new ArrayList<Animation>();

		TextureRegion[][] sprites = TextureRegion.split(new Texture("spriteSheet1.png"), 32, 32);

		TextureRegion[] idleTextures = new TextureRegion[6];
		TextureRegion[] hitTextures = new TextureRegion[2];

		for (int i = 0; i < idleTextures.length; i++) {
			idleTextures[i] = sprites[0][i];
		}

		for (int i = 0; i < hitTextures.length; i++) {
			hitTextures[i] = sprites[5][i];
		}

		Animation idleAnimation;
		Animation hitAnimation;

		idleAnimation = new Animation(0.1f, idleTextures);
		hitAnimation = new Animation(0.15f, hitTextures);

		idleAnimation.setPlayMode(PlayMode.LOOP);
		hitAnimation.setPlayMode(PlayMode.NORMAL);

		animations.add(idleAnimation);
		animations.add(hitAnimation);

		currentAnimation = animations.get(0);

		time = 0;
		moveCooldown = 0;
		health = INIT_HEALTH; 
		maxHealth = DEFAULT_MAX_HEALTH; 

		setX(x);
		setY(y);

		hit = false;

		moveActions = new Pool<MoveToAction>() {

			@Override
			protected MoveToAction newObject() {
				return new MoveToAction();
			}

		};

		pickItemsUp = Actions.run(new Runnable() {
			@Override
			public void run() {
				pickupItems(getXTile(), getYTile());
			}
		});
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		System.out.println("Health: " + health);
		if (hit) {
			batch.draw(currentAnimation.getKeyFrame(time), getX(), getY());
			if (currentAnimation.isAnimationFinished(time)) {
				hit = false;
				currentAnimation = animations.get(0);
			}
		} else {
			batch.draw(currentAnimation.getKeyFrame(time), getX(), getY());
		}
		time += Gdx.graphics.getDeltaTime();
		moveCooldown -= Gdx.graphics.getDeltaTime();
	}

	public void hit() {
		currentAnimation = animations.get(1);
		hit = true;
	}

	// dirX, dirY = -1, 0, 1
	// only one of dirX and dirY should not be 0
	public void move(int dirX, int dirY) {
		Room r = PCGGame.getInstance().getCurrentRoom();

		if (moveCooldown <= 0 && canMoveTo(getXTile() + dirX, getYTile() + dirY)) {
			SequenceAction sequence = new SequenceAction();

			MoveToAction moveAction = moveActions.obtain();
			moveAction.setDuration(MOVE_TIME);
			moveAction.setPosition(getX() + r.getTileWidth() * dirX, getY() + r.getTileHeight() * dirY);

			pickItemsUp = Actions.run(new Runnable() {
				@Override
				public void run() {
					pickupItems(getXTile(), getYTile());
				}
			});

			sequence.addAction(moveAction);
			sequence.addAction(pickItemsUp);

			addAction(sequence);

			moveCooldown = MOVE_COOLDOWN;

		} else if (moveCooldown <= 0) {
			SequenceAction sequence = new SequenceAction();

			MoveToAction moveThere = moveActions.obtain();
			MoveToAction moveBack = moveActions.obtain();
			moveThere.setDuration(MOVE_TIME / 2);
			moveThere.setPosition(getX() + r.getTileWidth() * dirX / 5, getY() + r.getTileHeight() * dirY / 5);
			moveBack.setDuration(MOVE_TIME / 2);
			moveBack.setPosition(getX(0), getY());

			sequence.addAction(moveThere);
			sequence.addAction(moveBack);

			addAction(sequence);
		}
	}

	public void moveLeft() {
		move(-1, 0);
	}

	public void moveRight() {
		move(1, 0);
	}

	public void moveDown() {
		move(0, -1);
	}

	public void moveUp() {
		move(0, 1);
	}

	private boolean canMoveTo(int tileX, int tileY) {
		Room r = PCGGame.getInstance().getCurrentRoom();
		if (tileX < 0 || tileX >= r.getNumXTiles() ||
				tileY < 0 || tileY >= r.getNumYTiles())
			return false; 
		
		return !r.isTileSolid(tileX, tileY);
	}

	public int getXTile() {
		Room r = PCGGame.getInstance().getCurrentRoom();
		return (int) Math.floor((getX() - r.getX() + getWidth() / 2) / r.getTileWidth());
	}

	public int getYTile() {
		Room r = PCGGame.getInstance().getCurrentRoom();
		return (int) Math.floor((getY() - r.getY() + getHeight() / 2) / r.getTileHeight());
	}

	private void pickupItems(int tileX, int tileY) {
		Room r = PCGGame.getInstance().getCurrentRoom();
		List<Item> items = r.getItemsOnTile(tileX, tileY);
		if (items != null) {
			for (Item i : items) {
				health += i.getValue();
			}
			r.removeItemsOnTile(tileX, tileY);
		}

	}

	public void damage(int damage) {
		health -= damage;
	}

	public void heal(int health) {
		if (this.health + health > maxHealth) {
			this.health = maxHealth; 
			return; 
		}
		this.health += health;
	}

	public void setHealth(int health) {
		if (health > maxHealth) {
			this.health = maxHealth;
			return; 
		}
		this.health = health;
	}

	public int getHealth() {
		return health;
	}
}
