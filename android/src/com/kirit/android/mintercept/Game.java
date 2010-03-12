/*
    Copyright 1995-2010, Kirit Saelensminde.
    http://www.kirit.com/Missile%20intercept

    This file is part of Missile intercept.

    AnimRay is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    AnimRay is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Missile intercept.  If not, see <http://www.gnu.org/licenses/>.
*/


package com.kirit.android.mintercept;

import java.util.Random;

import com.kirit.android.Element;
import com.kirit.android.NumberPanel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;


public class Game extends Element {
	private boolean isover;
	private Player player;
	private Opponent opponent;

	public NumberPanel score, level, missiles;

	private BitmapDrawable gameover;
	private Rect location = new Rect();

	static public Random randomGenerator = new Random();

	public Game(Context context) {
		isover = false;

		score = new NumberPanel(context, 8, R.drawable.score_prolog, R.drawable.score_numbers);
		score.reset(10);

		level = new NumberPanel(context, 3, R.drawable.level_prolog, R.drawable.level_numbers);
		level.reset(1); level.setLeft(score.getWidth());

		missiles = new NumberPanel(context, 4, R.drawable.missiles_prolog, R.drawable.missiles_numbers);
		missiles.reset(0);

		int prolog = Math.max(Math.max(score.getPrologHeight(), missiles.getPrologHeight()), level.getPrologHeight());
		score.setNumberOffset(prolog);
		level.setNumberOffset(prolog);
		missiles.setNumberOffset(prolog);

		player = new Player(context, this);
		opponent = new Opponent(context, this);

		gameover = (BitmapDrawable)context.getResources().getDrawable(R.drawable.gameover); 
	}

	public Player getPlayer() {
		return player;
	}

	/**
	 * When called the game is over
	 */
	public void over() {
		isover = true;
	}
	/**
	 * Allows us to determine if the game is over.
	 */
	public boolean isOver() {
		return isover;
	}

	@Override
	public boolean draw(Canvas c) {
		if ( isover ) {
			location.left = c.getWidth() / 2 - gameover.getMinimumWidth() / 2;
			location.top = c.getHeight() / 2 - gameover.getMinimumHeight();
			location.right = c.getWidth() / 2 + gameover.getMinimumWidth() / 2;
			location.bottom = c.getHeight() / 2;
			gameover.setBounds(location);
			gameover.draw(c);
		}

		score.draw(c);  
		missiles.setLeft(c.getWidth() - missiles.getWidth());
		missiles.draw(c);
		level.setLeft((c.getWidth() - level.getWidth()) / 2);
		level.setTop(c.getHeight() / 3);
		level.draw(c);

		opponent.draw(c);
		player.draw(c);

		return true;
	}
}
