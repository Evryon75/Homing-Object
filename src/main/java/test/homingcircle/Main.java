package test.homingcircle;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {

    static boolean right = false;
    static boolean down = false;
    static boolean left = false;
    static boolean up = false;

    @Override
    public void start(Stage stage) {

        Group root = new Group();
        Scene scene = new Scene(root, 500, 500, Color.BLACK);

        Point destination = new Point(10, 100, 100.1, Color.RED, 8);
        Point object = new Point(10, 500, 500, Color.CYAN, 4);

        Label instructions = new Label("Use the arrow keys to move the red point, the animation will start once you hit a key.");
        instructions.setTranslateX(10);
        instructions.setTranslateY(10);
        instructions.setTextFill(Color.WHITE);
        instructions.setFont(Font.font(20));

        root.getChildren().addAll(destination, object, instructions);

        AnimationTimer frame = new AnimationTimer() {

            @Override
            public void handle(long l) {

                //* Destination
                //Movement
                if (destination.getHsp() < destination.getMaxSpeed() && destination.getHsp() > -destination.getMaxSpeed()) {
                    destination.setHsp(destination.getHsp() + (right ? 2 : -(left ? 2 : 0)));
                }
                if (destination.getVsp() < destination.getMaxSpeed() && destination.getVsp() > -destination.getMaxSpeed()) {
                    destination.setVsp(destination.getVsp() + (down ? 2 : -(up ? 2 : 0)));
                }
                //Border collision
                if (destination.getCenterX() + destination.getHsp() < scene.getX() || destination.getCenterX() + destination.getRadius() + destination.getHsp() > scene.getWidth()) {
                    destination.setHsp(0);
                }
                if (destination.getCenterY() + destination.getVsp() < scene.getY() - destination.getRadius() * 2 || destination.getCenterY() + destination.getRadius() + destination.getVsp() > scene.getHeight()) {
                    destination.setVsp(0);
                }
                //Friction
                destination.setHsp(destination.getHsp() - Math.signum(destination.getHsp()) / 2);
                destination.setVsp(destination.getVsp() - Math.signum(destination.getVsp()) / 2);
                //Update
                destination.update();

                //* Object
                //Get the orientations It's supposed to go to
                double orientationX = -Math.signum(object.getCenterX() - destination.getCenterX());
                double orientationY = -Math.signum(object.getCenterY() - destination.getCenterY());
                //Getting the angles from the destination and object
                double aB = Math.abs(object.getCenterX() - destination.getCenterX());
                double bC = Math.abs(object.getCenterY() - destination.getCenterY());
                double cA = Math.sqrt(aB * aB + bC * bC);
                //The ratio of the angles are proportional to 0 : 1
                double verticalAngle = (Math.acos(aB / cA) * 180 / Math.PI) / 90;
                double horizontalAngle = (Math.acos(bC / cA) * 180 / Math.PI) / 90;
                //Updating the speeds
                object.setHsp(horizontalAngle * object.getMaxSpeed() * orientationX);
                object.setVsp(verticalAngle * object.getMaxSpeed() * orientationY);
                //Updating the position
                object.update();
            }
        };

        //Adding key event listeners
        scene.setOnKeyPressed(keyEvent -> {
            switch (keyEvent.getCode()){
                case RIGHT -> right = true;
                case DOWN -> down = true;
                case LEFT -> left = true;
                case UP -> up = true;
            }
            frame.start();
            instructions.setVisible(false);
        });
        scene.setOnKeyReleased(keyEvent -> {
            switch (keyEvent.getCode()){
                case RIGHT -> right = false;
                case DOWN -> down = false;
                case LEFT -> left = false;
                case UP -> up = false;
            }
        });


        stage.setTitle("Homing Object");
        stage.setMaximized(true);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}