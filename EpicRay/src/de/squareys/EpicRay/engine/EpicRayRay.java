package de.squareys.epicray.engine;

import de.squareys.epicray.bitmap.IBitmap;
import de.squareys.epicray.bitmap.PowerOf2IntMipMap;
import de.squareys.epicray.cursor.CombinedCursor;
import de.squareys.epicray.cursor.FastFloatBitmapCursor;
import de.squareys.epicray.cursor.FastIntBitmapCursor;
import de.squareys.epicray.cursor.ICursor2D;
import de.squareys.epicray.gamelogic.ITile;
import de.squareys.epicray.gamelogic.ITileMap;
import de.squareys.epicray.rendering.IRay;

public class EpicRayRay implements IRay, Runnable {

	protected final float m_x;
	protected final float m_y;

	protected final float m_dirX;
	protected final float m_dirY;

	protected int m_stepX;
	protected int m_stepY;

	private final FastIntBitmapCursor m_dest;
	private final FastFloatBitmapCursor m_zBuf;
	private final CombinedCursor<Integer, Float> m_combined;
	
	private final ITileMap m_tileMap;

	protected final int m_height; // length of m_pixels

	@Deprecated
	protected float m_length; // distance traveled

	protected class RenderVariables {
		float perpWallDist;

		// distance from position to next side
		float sideDistX;
		float sideDistY;

		// the tile position
		int mapX;
		int mapY;

		int side;

		int lineStart;
		int lineEnd;
		int lineHeight;

		int drawStart;
		int drawEnd;

		float wallX;

		public RenderVariables() {
			perpWallDist = 1.0f;

			sideDistX = 0.0f;
			sideDistY = 0.0f;

			mapX = 0;
			mapY = 0;

			side = 0;

			wallX = 0.0f;

			lineStart = lineEnd = lineHeight = 0;
			drawStart = drawEnd = 0;
		}
	}

	protected class VariableStorage {
		boolean cur;

		final RenderVariables a;
		final RenderVariables b;

		public VariableStorage() {
			cur = false;

			a = new RenderVariables();
			b = new RenderVariables();
		}

		final RenderVariables getVariables() {
			return (cur) ? a : b;
		}

		final RenderVariables getNextVariables() {
			return (cur) ? b : a;
		}

		final void exchange() {
			cur = !cur;
		}
	}

	final VariableStorage stor;

	public EpicRayRay(final int height, final float startposX,
			final float startposY, final float dirX, final float dirY,
			final FastIntBitmapCursor dest, FastFloatBitmapCursor zBuffer, final ITileMap tilemap) {
		m_x = startposX;
		m_y = startposY;

		m_dirX = dirX;
		m_dirY = dirY;

		m_height = height;
		m_dest = dest;
		m_zBuf = zBuffer;

		m_combined = new CombinedCursor<Integer, Float>(m_dest, m_zBuf);

		stor = new VariableStorage();

		// distance of side to next side
		final double dirXSq = m_dirX * m_dirX;
		final double dirYSq = m_dirY * m_dirY;

		m_deltaDistX = (float) Math.sqrt(1 + dirYSq / dirXSq);
		m_deltaDistY = (float) Math.sqrt(1 + dirXSq / dirYSq);

		
		RenderVariables next = stor.getNextVariables();
		// the tile position
		next.mapX = (int) m_x;
		next.mapY = (int) m_y;

		// calculate step and initial sideDist
		if (m_dirX < 0) {
			m_stepX = -1;
			next.sideDistX = (m_x - next.mapX) * m_deltaDistX;
		} else {
			m_stepX = 1;
			next.sideDistX = (next.mapX + 1.0f - m_x) * m_deltaDistX;
		}

		if (m_dirY < 0) {
			m_stepY = -1;
			next.sideDistY = (m_y - next.mapY) * m_deltaDistY;
		} else {
			m_stepY = 1;
			next.sideDistY = (next.mapY + 1.0f - m_y) * m_deltaDistY;
		}

		m_addX = (m_stepX == 1) ? 0.0f : 1.0f;
		m_addY = (m_stepY == 1) ? 0.0f : 1.0f;
		
		m_tileMap = tilemap;

	}

	@Override
	public final double getX() {
		return m_x;
	}

	@Override
	public final double getY() {
		return m_y;
	}

	@Override
	public final double getDirX() {
		return m_dirX;
	}

	@Override
	public final double getDirY() {
		return m_dirY;
	}

	@Override
	public final double getLength() {
		return m_length;
	}

	@Override
	public final int getPixelHeight() {
		return m_height;
	}

	final float m_deltaDistX;
	final float m_deltaDistY;

	final float m_addX;
	final float m_addY;

	@Override
	public void cast(final ITileMap map) {
		RenderVariables cur = stor.getVariables();
		RenderVariables next = stor.getNextVariables();

		ITile hitTile = null;
		boolean hit = false; // wall hit flag
		boolean outOfWorld = false;

		if (next.sideDistX < next.sideDistY) {
			next.perpWallDist = Math.abs(((float) next.mapX - m_x + m_addX)
					/ m_dirX);
		} else {
			next.perpWallDist = Math.abs(((float) next.mapY - m_y + m_addY)
					/ m_dirY);
		}

		if (next.perpWallDist == 0.0) {
			next.perpWallDist = 0.001f;
		}

		calculateNextLineHeight();
		calculateNextLine();

		// cast the ray
		do {
			stor.exchange();
			cur = stor.getVariables();
			next = stor.getNextVariables();

			// jump to next map square, in x-direction OR in y-direction
			if (cur.sideDistX < cur.sideDistY) {
				next.sideDistX = cur.sideDistX + m_deltaDistX;
				next.sideDistY = cur.sideDistY;
				next.mapX = cur.mapX + m_stepX;
				next.mapY = cur.mapY;
				next.side = 0;

				next.perpWallDist = Math.abs((next.mapX - m_x + m_addX)
						/ m_dirX);
			} else {
				next.sideDistY = cur.sideDistY + m_deltaDistY;
				next.sideDistX = cur.sideDistX;
				next.mapY = cur.mapY + m_stepY;
				next.mapX = cur.mapX;
				next.side = 1;

				next.perpWallDist = Math.abs((next.mapY - m_y + m_addY)
						/ m_dirY);
			}

			if (next.perpWallDist == 0.0) {
				next.perpWallDist = 0.001f;
			}

			hitTile = map.getTileAt(cur.mapX, cur.mapY);

			if (hitTile == null) {
				outOfWorld = true;
				hit = true;
			} else if (hitTile.isOpaque()) {
				hit = true;
			}

			// draw the tile:
			if (!outOfWorld) {
				drawTile(hitTile);
			}
		} while (!hit);
	}

	private final void calculateNextLineHeight() {
		final RenderVariables next = stor.getNextVariables();
		// Calculate height of line to draw on screen
		next.lineHeight = (int) ((float) m_height / next.perpWallDist);

		next.lineHeight -= next.lineHeight & 1; // <=> % 2

	}

	private final void calculateNextLine() {
		final RenderVariables next = stor.getNextVariables();

		// calculate lowest and highest pixel to fill in current stripe
		next.lineStart = (m_height - next.lineHeight) >> 1;
		next.lineEnd = (next.lineHeight + m_height) >> 1;

		next.drawStart = next.lineStart;

		if (next.drawStart < 0) {
			next.drawStart = 0;
		}

		next.drawEnd = next.lineEnd;

		if (next.drawEnd >= m_height) {
			next.drawEnd = m_height - 1;
		} else if (next.drawEnd < 0) {
			next.drawEnd = 0;
		}
	}

	/*
	 * Draw a tile.
	 * 
	 * @param tile Tile to draw, not null.
	 */
	private void drawTile(final ITile tile) {
		final RenderVariables cur = stor.getVariables();
		final RenderVariables next = stor.getNextVariables();

		calculateNextLineHeight();
		calculateNextLine();

		/* Calculate WallX */
		if (next.side == 1) {
			next.wallX = m_x + (((float) next.mapY - m_y + m_addY) / m_dirY)
					* m_dirX;
		} else {
			next.wallX = m_y + (((float) next.mapX - m_x + m_addX) / m_dirX)
					* m_dirY;
		}

		next.wallX -= Math.floor(next.wallX);

		final EpicRayRenderingAttributes ra = (EpicRayRenderingAttributes) tile
				.getRenderingAttributes();

		if ((ra.m_textured && ra.m_wallTexture != null)) {
			final IBitmap<Integer> texture = getMipMapTexture(ra.getWallTexture(), cur.lineHeight);
			final ICursor2D<Integer> texCursor = texture.getCursor();
			final float toTexture = (float) texture.getHeight()
					/ (float) cur.lineHeight;
			float texY = 0.0f;

			int texX = (int) (cur.wallX * (float) texture.getWidth());
			// code to flip the texture
			if (cur.side == 0 && m_dirX > 0) {
				texX = texture.getWidth() - texX - 1;
			} else if (cur.side == 1 && m_dirY < 0) {
				texX = texture.getWidth() - texX - 1;
			}

			if (cur.lineStart < 0) {
				texY = toTexture * -cur.lineStart;
			}

			if (texX < 0) {
				texX = 0;
			}
			
			if (texX > texture.getWidth() - 1) {
				texX = texture.getWidth() - 1;
			}
			
			int lastTexY;
			int ty;
			lastTexY = ty = (int) texY;
			texCursor.setPosition(texX, ty);
			
			int color = texCursor.get();
			if (cur.side == 1) {
				// make color darker for y-sides: R, G and B byte each
				// divided through two
				color = (color >> 1) & 8355711;
			}

			m_combined.setPosition(cur.drawStart);
			final int drawLength = cur.drawEnd - cur.drawStart;
			
			// draw the pixels of the stripe as a vertical line
			for (int i = 0; i < drawLength; ++i, m_combined.fwd()) {
				// zBuffer Check
				if (m_zBuf.getNative() < cur.perpWallDist) {
					// perpWallDist is our cur zValue.
					continue;
				}
				
				// Note: Unsafe, but fast ;)
				ty = (int) texY;
				
				if (ty != lastTexY) {
					texCursor.fwd(ty - lastTexY);
					color = texCursor.get();
					
					if (cur.side == 1) {
						// make color darker for y-sides: R, G and B byte
						// each divided through two
						color = (color >> 1) & 8355711;
					}
					
					lastTexY = ty;
				}
				
				texY += toTexture;
				
				m_zBuf.set(cur.perpWallDist);
				m_dest.set(color);
			}
		} else if (ra.m_wallColor != -1) {
			m_combined.setPosition(cur.drawStart);
			final int drawLength = cur.drawEnd - cur.drawStart;
			
			final int color = ra.m_wallColor;

			// draw the pixels of the stripe as a vertical line
			for (int i = 0; i < drawLength; ++i, m_combined.fwd()) {
				// zBuffer Check
				if (m_zBuf.getNative() < cur.perpWallDist) {
					// perpWallDist is our cur zValue.
					continue;
				}

				m_zBuf.set(cur.perpWallDist);
				m_dest.set(color);
			}
		}

		if (tile.isOpaque())
			return; // no floor or ceiling visible.

		final int nInvLineHeight = next.lineStart - cur.drawStart;

		if (nInvLineHeight < 1) {
			return; // floor not visible here.
		}

		final IBitmap<Integer> ceilTexture = getMipMapTexture(ra.m_ceilTexture, cur.lineHeight);;
		final IBitmap<Integer> floorTexture = getMipMapTexture(ra.m_floorTexture, cur.lineHeight);;

		final boolean texCeil = ra.m_textured && (ra.m_ceilTexture != null);
		final boolean texFloor = ra.m_textured && (ra.m_floorTexture != null);

		float startX = 0.0f;
		float startY = 0.0f;

		float endX = 1.0f;
		float endY = 1.0f;

		if (cur.side == 0) {
			if (m_dirY < 0) {
				startX = 1.0f - cur.wallX;
			} else {
				startX = cur.wallX;
			}
		} else {
			if (m_dirX < 0) {
				startY = 1.0f - cur.wallX;
			} else {
				startY = cur.wallX;
			}
		}

		if (next.side == 0) {
			if (m_dirY < 0) {
				endX = 1.0f - next.wallX;
			} else {
				endX = next.wallX;
			}
		} else {
			if (m_dirX < 0) {
				endY = 1.0f - next.wallX;
			} else {
				endY = next.wallX;
			}
		}

		// mirror texture, if ray direction is negative...
		if (m_dirX < 0) {
			endY = 1.0f - endY;
			startY = 1.0f - startY;
		}

		if (m_dirY < 0) {
			endX = 1.0f - endX;
			startX = 1.0f - startX;
		}

		final float diffX = (endX - startX);
		final float diffY = (endY - startY);

		// draw the floor and ceiling
		int floorColor = ra.m_floorColor;
		int ceilColor = ra.m_ceilColor;

		final float invDeltaDist = 1.0f / (next.perpWallDist - cur.perpWallDist);

		
		// Ceiling Cursor
		final FastIntBitmapCursor ceilCursorC = (FastIntBitmapCursor) m_dest
				.copy();
		final FastFloatBitmapCursor ceilCursorZ = (FastFloatBitmapCursor) m_zBuf
				.copy();
		
		ceilCursorC.setPosition(cur.drawStart);
		ceilCursorZ.setPosition(cur.drawStart);

		// Floor Cursor
		final FastIntBitmapCursor floorCursorC = (FastIntBitmapCursor) m_dest
				.copy();
		final FastFloatBitmapCursor floorCursorZ = (FastFloatBitmapCursor) m_zBuf
				.copy();
		
		floorCursorC.setPosition(cur.drawEnd);
		floorCursorZ.setPosition(cur.drawEnd);

		final int ceilTexW = (texCeil) ? ceilTexture.getWidth() - 1 : 0;
		final int ceilTexH = (texCeil) ? ceilTexture.getHeight() - 1 : 0;
		
		final int floorTexW = (texFloor) ? floorTexture.getWidth() - 1 : 0;
		final int floorTexH = (texFloor) ? floorTexture.getHeight() - 1 : 0;
		
		float zValue;

		for (int y = 0; y < nInvLineHeight; ++y, 
				ceilCursorC.fwd(), ceilCursorZ.fwd(), 
				floorCursorC.bck(), floorCursorZ.bck()) {
			zValue = (float) m_height
					/ (float) (m_height - ((cur.drawStart + y) << 1));

			if (texCeil || texFloor) {
				final float theFactor = (zValue - cur.perpWallDist)
						* invDeltaDist;

				final float xFact = theFactor * diffX + startX;
				final float yFact = theFactor * diffY + startY;

				if (texCeil) {
					ceilColor = ceilTexture.getPixel((int) (xFact * ceilTexW),
							(int) (yFact * ceilTexH));
				}

				if (texFloor) {
					floorColor = floorTexture.getPixel(
							(int) (xFact * floorTexW),
							(int) (yFact * floorTexH));
				}

			}

			// zBuffer Check
			if (ceilCursorZ.getNative() > zValue) {
				ceilCursorC.set(ceilColor); // ceiling
			}

			// zBuffer Check
			if (floorCursorZ.getNative() > zValue) {
				floorCursorC.set(floorColor); // floor
			}
		}
	}

	private final IBitmap<Integer> getMipMapTexture(IBitmap<Integer> texture, int lineHeight) {
		if (texture == null) {
			return null;
		}
				
		if (texture instanceof PowerOf2IntMipMap) {
			return ((PowerOf2IntMipMap) texture).getMipImage(
						// calculate mip level
						(int) Math.max(0, 
							((PowerOf2IntMipMap) texture).getNumMips() - getMaxExp(lineHeight)
						)
					);
		}

		return texture;
	}

	private final int[] POWER_OF_2 = { 1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024,
			2048 };

	private final int getMaxExp(final int num) {
		for (int i = POWER_OF_2.length; i >= 0; --i) {
			if (num > POWER_OF_2[i]) {
				return i;
			}
		}
		return 0;
	}

	@Override
	public void run() {
		cast(m_tileMap);
	}
}
