package org.h3270.host;

/*
 * Copyright (C) 2003-2008 akquinet tech@spree
 *
 * This file is part of h3270.
 *
 * h3270 is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * h3270 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with h3270; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, 
 * MA 02110-1301 USA
 */

import org.h3270.render.TextRenderer;

/**
 * @author Andre Spiegel spiegel@gnu.org
 * @version $Id$
 */
public class ScreenCharSequence {

  private Screen screen = null;
  private String text = null;
  private int width = 0;
  private int height = 0;
  
  private TextRenderer renderer = new TextRenderer();
  
  public ScreenCharSequence (Screen s) {
    screen = s;
    width  = screen.getWidth();
    height = screen.getHeight();
    text = renderer.render(s);
  }

  public int length() {
    return text.length();
  }

  public char charAt(int index) {
    return text.charAt(index);
  }

  public String subSequence(int start, int end) {
    return text.substring (start, end);
  }

  public String toString() {
    return text;    
  }
  
  public Field getFieldAt(int index) {
    if (index < 0 || index >= length())
      throw new IndexOutOfBoundsException();
    int y = index / (width+1);
    int x = index % (width+1);
    if (x == width)
      return null;
    else
      return screen.getInputFieldAt(x, y);
  }

}
