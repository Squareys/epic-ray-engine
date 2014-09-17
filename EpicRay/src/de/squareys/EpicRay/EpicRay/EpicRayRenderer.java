package de.squareys.EpicRay.EpicRay;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

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

	private final int fov = 90;
	private final float m_planeLength;

	private boolean m_threaded;
	
	private ExecutorService m_threadPool;

	public EpicRayRenderer(final IWorld world, final IEntity camEntity,
			final int width, final int height) {
		m_width = width;
		m_height = height;
		m_length = width * height;

		m_bitmap = new FastIntBitmap(width, height);
		m_zBuffer = new FastFloatBitmap(width, height);

		m_world = world;
		m_camEntity = camEntity;

		m_threaded = true;
		
		m_planeLength = 0.66f;//Math.abs((float) (Math.tan((double)fov * Math.PI / 50.0)));
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
		final ArrayList<EpicRayRay> rays = new ArrayList<EpicRayRay>();

		for (int index = 0; index < m_length; f2 += factor, index += m_height) {
			// calculate ray position and direction
			cursor.setAbsolutePosition(index);
			zCursor.setAbsolutePosition(index);

			cursor.setOffset();
			zCursor.setOffset();

			final EpicRayRay ray = new EpicRayRay(m_height,
					m_camEntity.getX(), 
					m_camEntity.getY(),
					m_camEntity.getViewDirectionX() + m_planeX * f2,
					m_camEntity.getViewDirectionY() + m_planeY * f2,
					(FastIntBitmapCursor) cursor.copy(),
					(FastFloatBitmapCursor) zCursor.copy(), 
					tileMap);

			rays.add(ray);
		}
		
		if (m_threaded) {
			renderThreaded(rays);
			return;
		} else {
			for (EpicRayRay ray : rays) {
				ray.run();
			}
			return;
		}

	}

	private final void renderThreaded(final ArrayList<EpicRayRay> rays) {
		m_threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()-1);
		
		Future<?> last = null;
		for (EpicRayRay ray : rays) {
			last = m_threadPool.submit(ray);
		}
		
		try {
			m_threadPool.shutdown();
			m_threadPool.awaitTermination(100, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
		}
	}

	@Override
	public FastIntBitmap getRenderResult() {
		return m_bitmap;
	}

	public final void setCameraEntity(IEntity e) {
		m_camEntity = e;
	}
}
