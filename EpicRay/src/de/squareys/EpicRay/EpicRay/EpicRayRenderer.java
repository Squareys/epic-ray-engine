package de.squareys.EpicRay.EpicRay;

import java.util.ArrayList;

import de.squareys.EpicRay.Bitmap.FastFloatBitmap;
import de.squareys.EpicRay.Bitmap.FastIntBitmap;
import de.squareys.EpicRay.Cursor.FastFloatBitmapCursor;
import de.squareys.EpicRay.Cursor.FastIntBitmapCursor;
import de.squareys.EpicRay.GameLogic.IEntity;
import de.squareys.EpicRay.GameLogic.ITileMap;
import de.squareys.EpicRay.GameLogic.IWorld;
import de.squareys.EpicRay.Rendering.IRenderer;

/**
 * EpicRayRenderer is an implementation of Renderer
 * 
 * EpicRay renders the World with the Raycasting tecnique and Entities as
 * Sprites whose scale is affected by the distance from the camera/player/viewer
 * 
 * @author Squareys
 * 
 */

public final class EpicRayRenderer implements IRenderer<FastIntBitmap> {

	private FastIntBitmap m_bitmap;
	private FastFloatBitmap m_zBuffer;

	private final int m_width;
	private final int m_height;
	private final int m_length;

	private IWorld m_world;
	private IEntity m_camEntity; // camera entity

	private float m_planeX = 0;
	private float m_planeY = 0.66f;

	private final int fov = 66;
	private final float m_planeLength = .66f; // (Math.tan(fov/2)); //must be
										// calculated for fov.

	private boolean m_threaded;
	private final int m_numThreads = 5;

	public EpicRayRenderer(final IWorld world, final IEntity camEntity,
			final int width, final int height) {
		m_width = width;
		m_height = height;
		m_length = width * height;

		m_bitmap = new FastIntBitmap(width, height);
		m_zBuffer = new FastFloatBitmap(width, height);

		m_world = world;
		m_camEntity = camEntity;

		m_threaded = false;
	}

	@Override
	public final void render() {
		ITileMap tileMap = m_world.getTileMap();

		// calculate perpendicular camera plane
		m_planeX = (float) -m_camEntity.getViewDirectionY() * m_planeLength;
		m_planeY = (float) m_camEntity.getViewDirectionX() * m_planeLength;

		// m_bitmap.clear(Color.gray.getRGB());
		m_zBuffer.clear(Float.MAX_VALUE);

		final FastIntBitmapCursor cursor = (FastIntBitmapCursor) m_bitmap
				.getCursor();
		final FastFloatBitmapCursor zCursor = (FastFloatBitmapCursor) m_zBuffer
				.getCursor();

		final float factor = 2.0f / m_width;
		float f2 = -1.0f;
		ArrayList<EpicRayRay> rays = new ArrayList<EpicRayRay>();

		for (int index = 0; index < m_length; f2 += factor, index += m_height) {
			// calculate ray position and direction
			cursor.setAbsolutePosition(index);
			zCursor.setAbsolutePosition(index);

			cursor.setOffset();
			zCursor.setOffset();

			final EpicRayRay ray = new EpicRayRay(m_height,
					(float) m_camEntity.getX(), 
					(float) m_camEntity.getY(),
					(float) m_camEntity.getViewDirectionX() + m_planeX * f2,
					(float) m_camEntity.getViewDirectionY() + m_planeY * f2,
					(FastIntBitmapCursor) cursor.copy(),
					(FastFloatBitmapCursor) zCursor.copy());

			rays.add(ray);
		}
		
		if (m_threaded) {
			renderThreaded(rays, tileMap);
			return;
		} else {
			renderOnThisThread(rays, tileMap);
		}

	}

	private final void renderOnThisThread(final ArrayList<EpicRayRay> rays, final ITileMap tileMap) {
		for (EpicRayRay ray : rays) {
			ray.cast(tileMap);
		}
	}

	private final void renderThreaded(final ArrayList<EpicRayRay> rays, final ITileMap tileMap) {
		int raysPerThread = (int) (rays.size() / m_numThreads);
		int overshoot = rays.size() - raysPerThread * m_numThreads;

		RenderThread[] threads = new RenderThread[m_numThreads];

		int startIndex = 0;
		int endIndex = 0;

		for (int i = 0; i < m_numThreads; ++i) {
			endIndex = startIndex + raysPerThread;

			if (overshoot > 0) {
				endIndex++;
				overshoot--;
			}

			threads[i] = new RenderThread(tileMap, startIndex, endIndex, rays);
			threads[i].start();

			startIndex += endIndex - startIndex;
		}

		for (Thread t : threads) {
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public FastIntBitmap getRenderResult() {
		return m_bitmap;
	}

	public void setCameraEntity(IEntity e) {
		m_camEntity = e;
	}

	class RenderThread extends Thread {
		ITileMap m_map;

		boolean m_running = false;

		int m_xfrom, m_xto;

		ArrayList<EpicRayRay> m_rays;

		public RenderThread(ITileMap map, int from, int to,
				ArrayList<EpicRayRay> rays) {
			super();
			m_map = map;
			m_xfrom = from;
			m_xto = to;

			m_rays = rays;
		}

		@Override
		public synchronized void run() {
			m_running = true;

			for (int i = m_xfrom; i < m_xto; ++i) {
				m_rays.get(i).cast(m_map);
			}

			m_running = false;
		}
	}
}
