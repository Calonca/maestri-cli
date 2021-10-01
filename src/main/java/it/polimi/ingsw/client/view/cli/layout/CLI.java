package it.polimi.ingsw.client.view.cli.layout;

import it.polimi.ingsw.client.view.cli.layout.drawables.Canvas;
import it.polimi.ingsw.client.view.cli.textutil.Characters;
import it.polimi.ingsw.client.view.cli.textutil.Color;

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;

public class CLI {

    private Canvas canvas;
    private int lastInt;

    public CLI() {
        Thread inputThread = new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                lastInput = scanner.nextLine();
                Runnable toRun = afterInput;
                afterInput = this::show;
                inputMessage = "Not asking for input";

                errorMessage = null;
                toRun.run();
            }
        });

        inputThread.start();
    }

    /**
     * Runs the provided runnable if the user inputs a number present in the possible values,
     * the onEnter runnable if the user inputs nothing or displays the
     * error message and asks again for input if the input is not acceptable.
     * @param message Shown to the user to specify the kind of input the function needs
     * @param errorMessage Shown in case the input is not acceptable
     * @param possibleValues The list of the possible accepted values
     * @param onInt The runnable to run when the input number is acceptable
     * @param onEnter The runnable to run when the user input nothing and presses enter
     */
    public void runOnIntListInput(String message, String errorMessage, IntStream possibleValues, Runnable onInt, Runnable onEnter){

        int[] supplier = possibleValues.toArray();
        int max = Arrays.stream(supplier).max().orElse(0);
        int min = Arrays.stream(supplier).min().orElse(0);
        Runnable r2 = ()->{
            if (Arrays.stream(supplier).noneMatch(i->i==getLastInt())) {
                this.errorMessage = errorMessage+", select an active option";
                runOnIntListInput(message,errorMessage, Arrays.stream(supplier),onInt,onEnter);
                show();
            }
            else {
                onInt.run();
            }
        };
        runOnIntInput(message,errorMessage,min,max,r2,onEnter);
    }


    /**
     * @return the last digited input in the cli
     */
    public int getLastInt() {
        return lastInt;
    }
    String inputMessage;
    String errorMessage;
    String lastInput;
    Runnable afterInput;
    boolean isViewMode;
    /**
     * Runs the provided runnable if the user inputs a number, the onEnter runnable if the user inputs nothing or displays the
     * error message and asks again for input if the input is not acceptable.
     * @param message Shown to the user to specify the kind of input the function needs
     * @param errorMessage Shown in case the input is not acceptable
     * @param min The minimum accepted number
     * @param max The maximum accepted number
     * @param r1 The runnable to run when the input number is acceptable
     * @param onEnter The runnable to run when the user input nothing and presses enter
     */
    public void runOnIntInput(String message, String errorMessage, int min, int max, Runnable r1,Runnable onEnter){

        inputMessage = message;

        afterInput = ()->{
            try
            {
                int choice = Integer.parseInt(lastInput);
                if(isViewMode)
                    return;

                if (choice<min||choice>max)
                {
                    this.errorMessage = errorMessage;
                    if (choice<min) {
                        this.errorMessage += ", insert a GREATER number!";
                    }
                    else {
                        this.errorMessage += ", insert a SMALLER number!";
                    }
                    runOnIntInput(message,errorMessage,min,max,r1,onEnter);
                    show();
                }else {
                    lastInt = choice;
                    r1.run();
                }
            }
            catch (NumberFormatException e) {
                if (onEnter != null)
                    onEnter.run();
                else{
                    this.errorMessage = errorMessage + ", insert a NUMBER!";
                    runOnIntInput(message, errorMessage, min, max, r1, null);
                    show();
                }
            }
        };

    }

    public void show(){
        if (canvas!=null){
            putStartDiv();
            System.out.print(canvas);
            putEndDiv();
            if (errorMessage!=null)
                printError(errorMessage+" ");
            System.out.println(Color.colorString(inputMessage,Color.GREEN));
        }
    }

    private void printError(String error){
        System.out.print(Color.colorString(error,Color.RED));
    }

    public void putEndDiv(){
        System.out.println(
                Characters.BOTTOM_LEFT_DIV.getString()+
                        Characters.HOR_DIVIDER.repeated(canvas.getWidth() -2)+
                        Characters.BOTTOM_RIGHT_DIV.getString()
        );
    }

    public void putStartDiv(){
        System.out.println(
                Characters.TOP_LEFT_DIV.getString()+
                        Characters.HOR_DIVIDER.repeated(canvas.getWidth() -2)+
                        Characters.TOP_RIGHT_DIV.getString()
        );
    }


    public Canvas getCanvas() {
        return canvas;
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }
}
