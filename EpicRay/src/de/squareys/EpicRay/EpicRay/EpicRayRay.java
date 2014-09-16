package de.squareys.EpicRay.EpicRay;

import de.squareys.EpicRay.Bitmap.IBitmap;
import de.squareys.EpicRay.Bitmap.PowerOf2IntMipMap;
import de.squareys.EpicRay.Cursor.CombinedCursor;
import de.squareys.EpicRay.Cursor.FastFloatBitmapCursor;
import de.squareys.EpicRay.Cursor.FastIntBitmapCursor;
import de.squareys.EpicRay.Cursor.ICursor2D;
import de.squareys.EpicRay.GameLogic.ITile;
import de.squareys.EpicRay.GameLogic.ITileMap;
import de.squareys.EpicRay.Rendering.IRay;

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
			drawTile(hitTile, outOfWorld);
		} while (!hit);
	}

	private final void calculateNextLineHeight() {
		final RenderVariables next = stor.getNextVariables();
		// Calculate height of line to draw on screen
		next.lineHeight = (int) ((float) m_height / next.perpWallDist);

		next.lineHeight -= next.lineHeight % 2;

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

	private void drawTile(final ITile tile, boolean outOfWorld) {
		if (outOfWorld) {
			return;
		}

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

		if ((ra.m_textured && ra.m_wallTexture != null) || ra.m_wallColor != -1) {
			int texX = 0;
			IBitmap<Integer> texture = null;
			float toTexture = 1.0f;
			float texY = 0.0f;
			int lastTexY = -1;
			int ty;

			ICursor2D<Integer> texCursor = null;

			int color = ra.m_wallColor;

			if (ra.m_textured) {
				texture = getMipMapTexture(ra.getWallTexture(), cur.lineHeight);

				texX = (int) (cur.wallX * (float) texture.getWidth());

				// code to flip the texture
				if (cur.side == 0 && m_dirX > 0) {
					texX = texture.getWidth() - texX - 1;
				} else if (cur.side == 1 && m_dirY < 0) {
					texX = texture.getWidth() - texX - 1;
				}

				toTexture = (float) texture.getHeight()
						/ (float) cur.lineHeight;

				if (cur.lineStart < 0) {
					texY = toTexture * -cur.lineStart;
				}

				if (texX < 0) {
					texX = 0;
				}

				if (texX > texture.getWidth() - 1) {
					texX = texture.getWidth() - 1;
				}

				ty = (int) texY;
				texCursor = texture.getCursor();
				texCursor.setPosition(texX, ty);

				color = texCursor.get();
				if (cur.side == 1) {
					// make color darker for y-sides: R, G and B byte each
					// divided through two with a "shift" and an "and"
					color = (color >> 1) & 8355711;
				}

				lastTexY = ty;
			}

			m_combined.setPosition(cur.drawStart);
			final int drawLength = cur.drawEnd - cur.drawStart;

			// draw the pixels of the stripe as a vertical line
			for (int i = 0; i < drawLength; ++i, m_combined.fwd()) {
				// zBuffer Check
				if (m_zBuf.getNative() < cur.perpWallDist) { // perpWallDist is
																// our cur
																// zValue.
					continue;
				}

				if (ra.m_textured) {
					// Note: Unsafe, but fast ;)
					ty = (int) texY;

					if (ty != lastTexY) {
						texCursor.fwd(ty - lastTexY);
						color = texCursor.get();

						if (cur.side == 1) {
							// make color darker for y-sides: R, G and B byte
							// each
							// divided through two with a "shift" and an "and"
							color = (color >> 1) & 8355711;
						}

						lastTexY = ty;
					}

					texY += toTexture;
				}
				m_zBuf.set(cur.perpWallDist);
				m_dest.set(color);
			}
		}

		if (tile.isOpaque())
			return; // no floor visible.

		final int nInvLineHeight = next.lineStart - cur.drawStart;

		if (nInvLineHeight < 1) {
			return; // floor not visible here.
		}

		IBitmap<Integer> ceilTexture = null;
		IBitmap<Integer> floorTexture = null;

		final boolean texCeil = ra.m_textured && (ra.m_ceilTexture != null);
		final boolean texFloor = ra.m_textured && (ra.m_floorTexture != null);

		if (texCeil) {
			ceilTexture = getMipMapTexture(ra.m_ceilTexture, cur.lineHeight);
		}

		if (texFloor) {
			floorTexture = getMipMapTexture(ra.m_floorTexture, cur.lineHeight);
		}

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

		
		//Ceiling Cursor
		FastIntBitmapCursor ceilCursorC = (FastIntBitmapCursor) m_dest
				.copy();
		FastFloatBitmapCursor ceilCursorZ = (FastFloatBitmapCursor) m_zBuf
				.copy();
		
		ceilCursorC.setPosition(cur.drawStart);
		ceilCursorZ.setPosition(cur.drawStart);

		//Floor Cursor
		FastIntBitmapCursor floorCursorC = (FastIntBitmapCursor) m_dest
				.copy();
		FastFloatBitmapCursor floorCursorZ = (FastFloatBitmapCursor) m_zBuf
				.copy();
		
		floorCursorC.setPosition(cur.drawEnd);
		floorCursorZ.setPosition(cur.drawEnd);

		int ceilTexH = 0, ceilTexW = 0;
		int floorTexH = 0, floorTexW = 0;

		if (texCeil) {
			ceilTexH = ceilTexture.getWidth() - 1;
			ceilTexW = ceilTexture.getHeight() - 1;
		}

		if (texFloor) {
			floorTexW = floorTexture.getWidth() - 1;
			floorTexH = floorTexture.getHeight() - 1;
		}

		for (int y = 0; y < nInvLineHeight; ++y, 
				ceilCursorC.fwd(), ceilCursorZ.fwd(), 
				floorCursorC.bck(), floorCursorZ.bck()) {
			final float zValue = (float) m_height
					/ (float) (m_height - ((cur.drawStart + y) << 1));

			if (texCeil || texFloor) {
				// float zValue =
				// ((float)m_height/2.0*(float)(drawStart+y+1)) -
				// cur.perpWallDist; //amazing hypnotizing results

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
		if (texture instanceof PowerOf2IntMipMap) {
			texture = ((PowerOf2IntMipMap) texture).copy(); // for
															// thread
															// safety
			final int miplevel = (int) Math.max(0,
					((PowerOf2IntMipMap) texture).getNumMips()
							- getMaxExp(lineHeight));

			((PowerOf2IntMipMap) texture).setMipLevel(miplevel);

			return texture;
		}

		return texture;
	}

	private final int[] power2 = { 1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024,
			2048 };

	private int getMaxExp(int num) {
		for (int i = power2.length - 1; i != 0; --i) {
			if (num > power2[i]) {
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
