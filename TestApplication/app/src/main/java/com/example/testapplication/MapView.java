package com.example.testapplication;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class MapView extends View {

    private float lastTouchX;
    private float lastTouchY;
    private float totalTranslateX = 0;
    private float totalTranslateY = 0;

    private Paint paint;

    public MapView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MapView(Context context) {
        super(context);
        init();
    }

    public MapView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.BLACK); // Example color
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);
        // Initialize other properties and your drawing logic
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.translate(totalTranslateX, totalTranslateY);

        // Define the paint for drawing the map grid
        Paint gridPaint = new Paint();
        gridPaint.setColor(Color.GRAY); // Color of the grid lines
        gridPaint.setStrokeWidth(1); // Thickness of the grid lines

        // Define the paint for drawing map elements (e.g., a path)
        Paint pathPaint = new Paint();
        pathPaint.setColor(Color.RED); // Color of the path
        pathPaint.setStrokeWidth(5); // Thickness of the path
        pathPaint.setStyle(Paint.Style.STROKE); // Draw only the outline

        // Wall paint
        Paint wallPaint = new Paint();
        wallPaint.setColor(Color.BLACK); // Color of the walls
        wallPaint.setStrokeWidth(10); // Thickness of the walls
        wallPaint.setStyle(Paint.Style.STROKE); // Draw only the outline

        // Room interior paint
        Paint roomPaint = new Paint();
        roomPaint.setColor(Color.LTGRAY); // Light gray for room interior
        roomPaint.setStyle(Paint.Style.FILL); // Fill the rooms with color

        // Draw a simple grid for the map
        float gridSize = 50; // Size of the grid squares
        for (int i = 0; i < getWidth(); i += gridSize) {
            for (int j = 0; j < getHeight(); j += gridSize) {
                canvas.drawLine(i, 0, i, getHeight(), gridPaint); // Vertical lines
                canvas.drawLine(0, j, getWidth(), j, gridPaint); // Horizontal lines
            }
        }

        // Draw a simple path on the map
        Path path = new Path();
        path.moveTo(100, 100); // Starting point
        path.lineTo(150, 200); // Draw a line to this point
        path.lineTo(200, 100); // Continue the line to this point
        canvas.drawPath(path, pathPaint); // Draw the path on the canvas

        // Define the building structure
        // Room 1
        RectF room1 = new RectF(50, 50, 250, 200); // Define the room's rectangle
        canvas.drawRect(room1, roomPaint); // Draw the room interior
        canvas.drawRect(room1, wallPaint); // Draw the room walls

        // Room 2
        RectF room2 = new RectF(260, 50, 460, 200); // Define another room's rectangle
        canvas.drawRect(room2, roomPaint); // Draw the room interior
        canvas.drawRect(room2, wallPaint); // Draw the room walls

        // Hallway connecting Room 1 and Room 2
        RectF hallway = new RectF(250, 100, 260, 150); // Define the hallway's rectangle
        canvas.drawRect(hallway, roomPaint); // Draw the hallway

        // Additional elements like doors, windows, or furniture can be added similarly
        // using drawRect, drawPath, or other Canvas drawing methods.

        // Optionally, draw more elements like location markers, text, etc.
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // Record the start point
                lastTouchX = event.getX();
                lastTouchY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                // Calculate the distance moved
                float dx = event.getX() - lastTouchX;
                float dy = event.getY() - lastTouchY;

                // Update the total translation
                totalTranslateX += dx;
                totalTranslateY += dy;

                // Request to redraw the view (this will invoke onDraw)
                invalidate();

                // Record the new positions
                lastTouchX = event.getX();
                lastTouchY = event.getY();
                break;
        }
        return true; // Indicate we've handled the touch event
    }

}
