package com.company;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }




    /**
     The SimpleGrapher program can draw graphs of functions input by the
     user.  The user enters the definition of the function in a text
     input box.  When the user presses return or clicks the "Enter" button,
     the function is graphed.  (Unless the definition contains an error.
     In that case, an error message is displayed.)

     The graph is drawn on a canvas which represents the region of the
     (x,y)-plane given by  -5 <= x <= 5  and  -5 <= y <= 5.  Any part of
     the graph that lies outside this region is not shown.  The graph
     is drawn by plotting 301 points and joining them with lines.  This
     does not handle discontinuous functions properly.

     An example function is graphed when the program starts.

     This program requires the class Expr, which is defined in by a
     separate file, Expr.java.  That file contains a full description
     of the syntax of legal function definitions, but the program
     understands operators +, -, *, /, and ^ (where ^ represents
     exponentiation), as well as common mathematical functions
     such as sin(x) and ln(x).
     */



        //---------------------------------------------------------------------------------

        private GraphCanvas graph;  // The Canvas that will display the graph.
        // GraphCanvas is a subclass of Canvas that
        // is defined as a static nested class.

        private TextField functionInput;  // A text input box where the user enters
        // the definition of the function.

        private Label message;  // A label for displaying messages to the user,
        // including error messages when the function
        // definition is illegal.

        /**
         * Set up the GUI with a large canvas in the center where the functions
         * are graphed, a label at the top for displaying messages, and
         * an input box for the function below the canvas.  Also adds an Enter
         * button with an ActionEvent handler that graphs the function.  The
         * button is set to be the default button in the window, so that the
         * user can also graph the function by pressing return.
         */
        public void start(Stage stage) {

            /* Create the components and set up event handling.  The
             * canvas is given an initial function to draw, and the
             * textfield is initialized to show the definition of
             * that function. */

            graph = new GraphCanvas( new Expr("sin(x)*3 + cos(5*x)") );

            message = new Label(" Enter a function and click Enter or press return");

            functionInput = new TextField("sin(x)*3 + cos(5*x)");

            Button graphIt = new Button("Enter");
            graphIt.setDefaultButton(true);

            graphIt.setOnAction( evt -> {
                // Get the user's function definition from the box and use it
                // to create a new object of type Expr.  Tell the GraphPanel to
                // graph this function.  If the  definition is illegal, an
                // IllegalArgumentException is  thrown by the Expr constructor.
                // If this happens, the graph is cleared and an error message
                // is displayed in the message label.
                Expr function;  // The user's function.
                try {
                    String def = functionInput.getText();
                    function = new Expr(def);
                    graph.setFunction(function);
                    message.setText(" Enter a function and click Enter or press return.");
                }
                catch (IllegalArgumentException e) {
                    graph.clearFunction();
                    message.setText(e.getMessage());
                }
                functionInput.selectAll();
                functionInput.requestFocus();  // Let's user start typing in input box.
            } );

            /* Create the layout. */

            HBox bottom = new HBox(8, new Label("f(x) ="), functionInput, graphIt);

            BorderPane root = new BorderPane();
            root.setCenter(graph);
            root.setTop(message);
            root.setBottom(bottom);



            root.setStyle("-fx-border-color:gray; -fx-border-width:4px");
            message.setTextFill(Color.RED);  // User red text for the message.
            message.setStyle("-fx-background-color:white; -fx-padding:7px; "
                    + "-fx-border-color:gray; -fx-border-width:0 0 4px 0");
            message.setMaxWidth(10000);  // Required to make the label (and its border)
            // extend the full width of the window.
            bottom.setStyle("-fx-border-color:gray; -fx-border-width:4px 0 0 0; -fx-padding:8px");
            HBox.setHgrow(functionInput, Priority.ALWAYS); // Allows functionInput to grow
            // to fill the available space.

            /* Finish setting up the window and make it visible. */

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("A Simple Function Grapher");
            stage.show();

        }  // end start()






    // end class Main
}
