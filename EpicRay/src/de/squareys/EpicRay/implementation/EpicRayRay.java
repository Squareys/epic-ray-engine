package de.squareys.EpicRay.implementation;

import java.awt.Color;

import de.squareys.EpicRay.framework.CombinedCursor;
import de.squareys.EpicRay.framework.ICursor1D;
import de.squareys.EpicRay.framework.IRay;
import de.squareys.EpicRay.framework.ITexture;
import de.squareys.EpicRay.framework.ITile;
import de.squareys.EpicRay.framework.ITileMap;

public class EpicRayRay implements IRay {

	protected float m_x;
	protected float m_y;

	protected float m_dirX;
	protected float m_dirY;

	private ICursor1D<Integer> m_dest;
	private ICursor1D<Float> m_zBuf;
	private CombinedCursor<Integer, Float> m_combined;

	protected int m_height; // length of m_pixels

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

		public float wallX;

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

		RenderVariables a;
		RenderVariables b;

		public VariableStorage() {
			cur = false;

			a = new RenderVariables();
			b = new RenderVariables();
		}

		RenderVariables getVariables() {
			return (cur) ? a : b;
		}

		RenderVariables getNextVariables() {
			return (cur) ? b : a;
		}

		void exchange() {
			cur = !cur;
		}
	}

	VariableStorage stor;

	public EpicRayRay(int height, float startposX, float startposY,
			float dirX, float dirY, ICursor1D<Integer> dest, ICursor1D<Float> zBuffer) {
		m_x = startposX;
		m_y = startposY;

		m_dirX = dirX;
		m_dirY = dirY;

		m_height = height;
		m_dest = dest;
		m_zBuf = zBuffer;
		
		m_combined = new CombinedCursor<Integer, Float>(m_dest, m_zBuf);

		stor = new VariableStorage();
	}

	@Override
	public double getX() {
		return m_x;
	}

	@Override
	public double getY() {
		return m_y;
	}

	@Override
	public double getDirX() {
		return m_dirX;
	}

	@Override
	public double getDirY() {
		return m_dirY;
	}

	@Override
	public double getLength() {
		return m_length;
	}

	@Override
	public int getPixelHeight() {
		return m_height;
	}

	float deltaDistX;
	float deltaDistY;

	@Override
	public void cast(ITileMap map) {
		RenderVariables cur = stor.getVariables();
		RenderVariables next = stor.getNextVariables();

		// the tile position
		next.mapX = (int) m_x;
		next.mapY = (int) m_y;

		// distance of side to next side
		deltaDistX = (float) Math.sqrt(1 + (m_dirY * m_dirY) / (m_dirX * m_dirX));
		deltaDistY = (float) Math.sqrt(1 + (m_dirX * m_dirX) / (m_dirY * m_dirY));

		// the directions signature (-1/+1)
		int stepX;
		int stepY;

		ITile hitTile = null;
		boolean hit = false; // wall hit flag
		boolean outOfWorld = false;

		// calculate step and initial sideDist
		if (m_dirX < 0) {
			stepX = -1;
			next.sideDistX = (m_x - next.mapX) * deltaDistX;
		} else {
			stepX = 1;
			next.sideDistX = (next.mapX + 1.0f - m_x) * deltaDistX;
		}

		if (m_dirY < 0) {
			stepY = -1;
			next.sideDistY = (m_y - next.mapY) * deltaDistY;
		} else {
			stepY = 1;
			next.sideDistY = (next.mapY + 1.0f - m_y) * deltaDistY;
		}

		if (next.sideDistX < next.sideDistY) {
			float add = (stepX == 1) ? 0.0f : 1.0f;
			next.perpWallDist = Math.abs(((float) next.mapX - m_x + add)
					/ m_dirX);
		} else {
			float add = (stepY == 1) ? 0.0f : 1.0f;
			next.perpWallDist = Math.abs(((float) next.mapY - m_y + add)
					/ m_dirY);
		}
		
		// Calculate height of line to draw on screen
		next.lineHeight = 2 * (int) (((float) m_height / cur.perpWallDist) / 2.0);
		// calculate lowest and highest pixel to fill in current stripe
		next.lineStart = (m_height - cur.lineHeight) / 2;
		next.lineEnd = (cur.lineHeight + m_height) / 2;
		
		next.drawStart = next.lineStart;

		if (next.drawStart < 0) {
			next.drawStart = 0;
		}

		next.drawEnd = next.lineEnd;
		
		if (next.drawEnd >= m_height) {
			next.drawEnd = m_height - 1;
		}

		// cast the ray
		do {
			stor.exchange();
			cur = stor.getVariables();
			next = stor.getNextVariables();

			// jump to next map square, OR in x-direction, OR in y-direction
			if (cur.sideDistX < cur.sideDistY) {
				next.sideDistX = cur.sideDistX + deltaDistX;
				next.sideDistY = cur.sideDistY;
				next.mapX = cur.mapX + stepX;
				next.mapY = cur.mapY;
				next.side = 0;
			} else {
				next.sideDistY = cur.sideDistY + deltaDistY;
				next.sideDistX = cur.sideDistX;
				next.mapY = cur.mapY + stepY;
				next.mapX = cur.mapX;
				next.side = 1;
			}

			if (next.side == 0) {
				float add = (stepX == 1) ? 0.0f : 1.0f;
				next.perpWallDist = Math
						.abs(((next.mapX) - m_x + add) / m_dirX);
			} else {
				float add = (stepY == 1) ? 0.0f : 1.0f;
				next.perpWallDist = Math
						.abs(((next.mapY) - m_y + add) / m_dirY);
			}

			hitTile = map.getTileAt(cur.mapX, cur.mapY);

			if (hitTile == null) {
				outOfWorld = true;
				hit = true;
			} else if (hitTile.isOpaque()) {
				hit = true;
			}

			// draw the tile:
			drawTile(hitTile, stepX, stepY, outOfWorld);
		} while (!hit);
	}

	private void drawTile(ITile tile, int stepY, int stepX, boolean outOfWorld) {
		if (outOfWorld) {
			return;
		}

		RenderVariables cur = stor.getVariables();
		RenderVariables next = stor.getNextVariables();

		// Calculate height of line to draw on screen
		next.lineHeight = 2 * (int) (((float) m_height / next.perpWallDist) / 2.0);
		// calculate lowest and highest pixel to fill in current stripe
		next.lineStart = (m_height - next.lineHeight) / 2;
		next.lineEnd = (next.lineHeight + m_height) / 2;
		
		next.drawStart = next.lineStart;

		if (next.drawStart < 0) {
			next.drawStart = 0;
		}

		next.drawEnd = next.lineEnd;
		
		if (next.drawEnd >= m_height) {
			next.drawEnd = m_height - 1;
		}

		/* Calculate WallX */
		if (next.side == 1) {
			float add = (stepX == 1) ? 0.0f : 1.0f;
			next.wallX = m_x + (((float) next.mapY - m_y + add) / m_dirY)
					* m_dirX;
		} else {
			float add = (stepY == 1) ? 0.0f : 1.0f;
			next.wallX = m_y + (((float) next.mapX - m_x + add) / m_dirX)
					* m_dirY;
		}

		next.wallX -= Math.floor(next.wallX);

		EpicRayRenderingAttributes ra;
		ra = (EpicRayRenderingAttributes) tile.getRenderingAttributes();

		if ((ra.m_wallTexture != null && ra.m_textured) || ra.m_wallColor != -1) {
			int texX = 0;
			ITexture texture = null;

			if (ra.m_textured) {
				texture = ra.getWallTexture();
				texX = (int) (cur.wallX * (float) texture.getWidth());

				// code to flip the texture
				if (cur.side == 0 && m_dirX > 0) {
					texX = texture.getWidth() - texX - 1;
				} else if (cur.side == 1 && m_dirY < 0) {
					texX = texture.getWidth() - texX - 1;
				}
			}

			float zValue = cur.perpWallDist; 
			
			//store the initial Value of the pixel
			
			m_combined.setPosition(cur.drawStart);

			// draw the pixels of the stripe as a vertical line
			for (int i = cur.drawStart; i < cur.drawEnd; ++i, m_combined.next()) {
				// zBuffer Check
				if (m_zBuf.get() < zValue) {
					continue;
				}

				int color = -1;

				// if (cur.side == 1) { //for shading
				// color = ra.m_wallColor;
				// } else {
				color = ra.m_wallColor;
				// }

				if (ra.m_textured) {
					int texY = (int) ((float) (((i - cur.lineStart) * texture.getHeight()) / (float) cur.lineHeight));

					if (texX < 0) {
						texX = 0;
					}

					if (texY < 0) {
						texY = 0;
					}

					if (texX > texture.getWidth() - 1) {
						texX = texture.getWidth() - 1;
					}

					if (texY > texture.getHeight() - 1) {
						texY = texture.getHeight() - 1;
					}
					color = texture.getPixel(texX, texY);

					// make color darker for y-sides: R, G and B byte each
					// divided through two with a "shift" and an "and"
					if (cur.side == 1) {
						color = (color >> 1) & 8355711;
					}
				}

				m_combined.set(color, zValue);
			}
		}

		if (tile.isOpaque())
			return; // no floor visible.

		int nInvLineHeight = next.lineStart - cur.drawStart;

		if (nInvLineHeight < 1) {
			return; // floor not visible here.
		}

		float zValue = next.perpWallDist;

		boolean texCeil = ra.m_textured && (ra.m_ceilTexture != null);
		boolean texFloor = ra.m_textured && (ra.m_floorTexture != null);

		float startX = 0.0f;
		float startY = 0.0f;

		float endX = 1.0f;
		float endY = 1.0f;

		if (cur.side == 0) {
			if ( m_dirY < 0) {
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
			if(m_dirY < 0) {
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

		//mirror texture, if ray direction is negative...
		if (m_dirX < 0) {
			endY = 1.0f - endY;
			startY = 1.0f - startY;
		}
		
		if (m_dirY < 0) {
			endX = 1.0f - endX;
			startX = 1.0f - startX;
		}

		// draw the floor and ceiling
		for (int y = 0; y < nInvLineHeight; y++) {
			int floorColor = -1;
			int ceilColor = -1;

			if (texCeil || texFloor) {
				// float theDist =
				// ((float)m_height/2.0*(float)(drawStart+y+1)) -
				// cur.perpWallDist; //amazing hypnotizing results

				float theDist = ((float) m_height / ((float) m_height - 2.0f * (float) (cur.drawStart + y)))
						- cur.perpWallDist;
				float theFactor = theDist
						/ (next.perpWallDist - cur.perpWallDist);

				float xFact = theFactor * (endX - startX) + startX;
				float yFact = theFactor * (endY - startY) + startY;

				

				if (texCeil) {
					int texW = ra.m_ceilTexture.getWidth();
					int texH = ra.m_ceilTexture.getHeight();
					
					int texX = (int) (xFact * (float) texW) % texW;
					int texY = (int) (yFact * (float) texH) % texH;
					
					if (texX >= texW || texY >= texH || texX < 0 || texY < 0) {
						ceilColor = new Color(255, 0, 255).getRGB();
					} else {
						ceilColor = ra.m_ceilTexture.getPixel(texX, texY);
					}
				} else {
					ceilColor = ra.m_ceilColor;
				}

				if (texFloor) {
					int texW = ra.m_floorTexture.getWidth();
					int texH = ra.m_floorTexture.getHeight();
					
					int texX = (int) (xFact * (float) texW) % texW;
					int texY = (int) (yFact * (float) texH) % texH;
					
					if (texX >= texW || texY >= texH || texX < 0 || texY < 0) {
						floorColor = new Color(255, 0, 255).getRGB();
					} else {
						floorColor = ra.m_floorTexture.getPixel(texX, texY);
					}
				} else {
					floorColor = ra.m_floorColor;
				}

			} else {
				floorColor = ra.m_floorColor;
				ceilColor = ra.m_ceilColor;
			}

			m_combined.setPosition(y + cur.drawStart);
			
			// zBuffer Check
			if (m_zBuf.get() >= zValue) {
				m_dest.set(ceilColor); // ceiling
			}
			
			m_combined.setPosition(cur.drawEnd - y);

			// zBuffer Check
			if (m_zBuf.get() >= zValue) {
				m_dest.set(floorColor); // floor
			}
		}
	}
}
