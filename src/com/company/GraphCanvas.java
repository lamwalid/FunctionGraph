package com.company;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class GraphCanvas extends Canvas {



        // A object of this class can display the graph of a function
        // on the region of the (x,y)-plane given by -5 <= x <= 5 and
        // -5 <= y <= 5.  The graph is drawn very simply, by plotting
        // 301 points and connecting them with line segments.  The canvas
        // is 600-by-600 pixels.  The size could be changed by editing
        // the definition in the constructor.

        Expr func;  // The definition of the function that is to be graphed.
        // If the value is null, no graph is drawn.


        GraphCanvas(Expr firstFunction) {
            super(600,600);
            func = firstFunction;
            draw();  // Draw the canvas at startup.
        }


        public void setFunction(Expr exp) {

            func = exp;
            draw();
        }


        public void clearFunction() {
            // Set the canvas to draw no graph at all.
            func = null;
            draw();
        }


        public void draw() {
            // Fill the canvas with white, then draw a set of axes
            // and the graph of the function.  Or, if func is null,
            // display a message that there is no function to be graphed.
            GraphicsContext g = getGraphicsContext2D();
            g.setFill(Color.WHITE);
            g.fillRect(0,0,getWidth(),getHeight());

            if (func == null) {
                g.setFill( Color.RED );
                g.fillText("No function is available.", 30, 40);
            }
            else {
                g.setFill( Color.PURPLE );
                g.fillText("y = " + func, 5, 15);
                drawAxes(g);
                drawFunction(g);
            }
        }


        void drawAxes(GraphicsContext g) {
            // Draw horizontal and vertical axes in the middle of the
            // canvas.  A 5-pixel border is left at the ends of the axes.
            double width = getWidth();
            double height = getHeight();
            g.setStroke(Color.BLUE);
            g.setLineWidth(2);
            g.strokeLine(5, height/2, width-5, height/2);
            g.strokeLine(width/2, 5, width/2, height-5);
        }


        void drawFunction(GraphicsContext g) {
            // Draw the graph of the function defined by the instance
            // variable func.  Just plot 301 points with lines
            // between them. s

            double x, y;          // A point on the graph.  y is f(x).
            double prevx, prevy;  // The previous point on the graph.

            double dx;  // Difference between the x-values of consecutive
            // points on the graph.

            dx  = 10.0 / 300;

            g.setStroke(Color.RED);
            g.setLineWidth(2.5);

            /* Compute the first point. */

            x = -5;
            y = func.value(x);

            /* Compute each of the other 300 points, and draw a line segment
               between each consecutive pair of points.  Note that if the
               function is undefined at one of the points in a pair, then
               the line segment is not drawn.  */

            for (int i = 1; i <= 300; i++) {

                prevx = x;           // Save the coords of the previous point.
                prevy = y;

                x += dx;            // Get the coords of the next point.

                y = func.value(x);

                if ( (! Double.isNaN(y)) && (! Double.isNaN(prevy)) ) {
                    // Draw a line segment between the two points.
                    putLine(g, prevx, prevy, x, y);
                }

            }  // end for loop

        }  // end drawFunction()


        void putLine(GraphicsContext g, double x1, double y1,
                     double x2, double y2) {
            // Draw a line segment from the point (x1,y1) to (x2,y2).
            // These values must be scaled to convert from coordinates
            // that go from -5 to 5 to the coordinates that are needed
            // for drawing on the canvas, which go from 0 to 600.
            // coordinates of the corresponding pixels.

            if (Math.abs(y1) > 10000 || Math.abs(y2) > 10000) {
                // Only draw lines for reasonable y-values.
                // This should not be necessary, but I'm not sure
                // how GraphicsContext will handle very large values.
                return;
            }

            double a1, b1;   // Pixel coordinates corresponding to (x1,y1).
            double a2, b2;   // Pixel coordinates corresponding to (x2,y2).

            double width = getWidth();     // Width of the canvas (600).
            double height = getHeight();   // Height of the canvas (600).

            a1 = (int)( (x1 + 5) / 10 * width );
            b1 = (int)( (5 - y1) / 10 * height );
            a2 = (int)( (x2 + 5) / 10 * width );
            b2 = (int)( (5 - y2) / 10 * height );

            g.strokeLine(a1,b1,a2,b2);

        }  // end putLine()

     // end class GraphCanvas

}
