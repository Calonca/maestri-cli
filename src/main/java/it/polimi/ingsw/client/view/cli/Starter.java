package it.polimi.ingsw.client.view.cli;

import it.polimi.ingsw.client.view.cli.layout.GridElem;
import it.polimi.ingsw.client.view.cli.layout.Option;
import it.polimi.ingsw.client.view.cli.layout.drawables.Canvas;
import it.polimi.ingsw.client.view.cli.layout.drawables.Drawable;
import it.polimi.ingsw.client.view.cli.layout.recursivelist.Row;

import java.util.Scanner;

public class Starter {
    public static void main(String[] args) {
        int x,y;
        Scanner s = new Scanner(System.in);
        System.out.println("Insert cli width");
        x = s.nextInt();

        System.out.println("Insert cli length");
        y = s.nextInt();

        Canvas canvas = Canvas.withBorder(x,y);

        Row r = new Row();
        r.addElem(Option.noNumber(Drawable.copyShifted(1,0,ball())));

        r.addElem(Option.from("Press 0 to\nrun opt1",()->{}));

        //Todo add input and option selection here
        r.addToCanvas(canvas,0,0);


        System.out.println(canvas);

    }

    public static Drawable ball(){
        Drawable d = new Drawable();
        d.add(0,"Test");
        return d;
    }
}
