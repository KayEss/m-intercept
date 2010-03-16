/*
    Copyright 1995-2010, Kirit Saelensminde.
    http://www.kirit.com/Missile%20intercept

    This file is part of Missile intercept.

    Missile intercept is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Missile intercept is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Missile intercept.  If not, see <http://www.gnu.org/licenses/>.
*/


package com.kirit.android.mintercept.views;

import com.kirit.android.Element;
import com.kirit.android.mintercept.Explosions;
import com.kirit.android.mintercept.Game;
import com.kirit.android.mintercept.MIntercept;
import com.kirit.android.mintercept.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;


public class Title extends Scene {
	private class Demo extends Element {
		View view;
		BitmapDrawable felspar, instructions, logo;
		int instruction_pos;
		Rect location = new Rect();
		Explosions explosions;

		public Demo(Context context, View v) {
			view = v;
			felspar = (BitmapDrawable)context.getResources().getDrawable(R.drawable.felspar);
			instructions = (BitmapDrawable)context.getResources().getDrawable(R.drawable.instructions);
			instruction_pos = -instructions.getMinimumWidth();
			logo = (BitmapDrawable)context.getResources().getDrawable(R.drawable.logo);
			explosions = new Explosions(35, 35);
		}

		@Override
		public boolean tick() {
			if ( Game.randomGenerator.nextInt(35) == 1 )
				explosions.reset(
					Game.randomGenerator.nextInt(view.getWidth()),
					Game.randomGenerator.nextInt(view.getHeight())
				);
			instruction_pos -= 2;
			if ( instruction_pos <= -instructions.getMinimumWidth() )
				instruction_pos = view.getWidth() + 5;
			return true;
		}

		@Override
		public void draw(Canvas c, Layer layer) {
			if ( layer == Layer.BACKGROUND ) {
				location.left = (view.getWidth() - felspar.getMinimumWidth() ) /2;
				location.right = view.getWidth() - location.left;
				location.top = view.getHeight() - felspar.getMinimumHeight();
				location.bottom = view.getHeight();
				felspar.setBounds(location);
				felspar.draw(c);
				
				location.left = instruction_pos;
				location.right = location.left + instructions.getMinimumWidth();
				location.top = view.getHeight() / 2;
				location.bottom = location.top + instructions.getMinimumHeight();
				instructions.setBounds(location);
				instructions.draw(c);
			} else if ( layer == Layer.CHROME ) {
				location.left = (view.getWidth() - logo.getMinimumWidth() ) /2;
				location.right = view.getWidth() - location.left;
				location.bottom = Math.max(view.getHeight() / 3, logo.getMinimumHeight());
				location.top = location.bottom - logo.getMinimumHeight();
				logo.setBounds(location);
				logo.draw(c);
			}
			explosions.draw(c, layer);
		}
	};
	Demo demo;


	MIntercept mintercept;
	public Title(MIntercept m) {
		super(m);
		mintercept = m;
		demo = new Demo(m, this);
		draw(demo);
	}

	@Override
	public void reset() {
	}


    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
    	if ( keyCode == KeyEvent.KEYCODE_DPAD_UP ) {
    		mintercept.startGame();
    		return true;
    	}
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
    	mintercept.startGame();
    	return true;
    }
}
