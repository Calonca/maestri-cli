package it.polimi.ingsw.client.view.cli;

import it.polimi.ingsw.client.view.cli.layout.CLI;
import it.polimi.ingsw.client.view.cli.layout.GridElem;
import it.polimi.ingsw.client.view.cli.layout.Option;
import it.polimi.ingsw.client.view.cli.layout.SizedBox;
import it.polimi.ingsw.client.view.cli.layout.drawables.Canvas;
import it.polimi.ingsw.client.view.cli.layout.drawables.Drawable;
import it.polimi.ingsw.client.view.cli.layout.recursivelist.Column;
import it.polimi.ingsw.client.view.cli.layout.recursivelist.Row;
import it.polimi.ingsw.client.view.cli.textutil.Background;
import it.polimi.ingsw.client.view.cli.textutil.Color;

import java.util.Scanner;

public class Starter {

    private static final CLI cli = new CLI();

    public static void main(String[] args) {
        cli.setCanvas(Canvas.withBorder(80,10));

        initialScreen();

    }

    public static void initialScreen(){
        cli.getCanvas().reset();
        Row r = new Row();
        r.addElem(Option.from("Change\ncli dimension", Starter::changeCanvasDimension));
        Option disabled = Option.from("Disabled option\ntry to select this",()->{});
        disabled.setEnabled(false);
        r.addElem(disabled);
        r.addElem(Option.from("Explore other features",Starter::otherFeatures));
        r.setAlignment(GridElem.Alignment.CANVAS_CENTER_VERTICAL);

        r.addToCanvas(cli.getCanvas(),0,0);

        r.selectInEnabledOption(cli,"Select a number");
        cli.show();

    }

    public static void otherFeatures(){
        cli.getCanvas().reset();

        Column c = new Column();

        //Centering title
        Row title = c.addAndGetRow();
        title.setAlignment(GridElem.Alignment.CANVAS_CENTER_VERTICAL);
        title.addElem(Option.noNumber("View 1"));

        c.addElem(Option.noNumber("This view shows you how to make grids"));

        Row r = c.addAndGetRow();
        r.addElem(Option.noNumber(Drawable.copyShifted(2,0,ball2(Color.BLUE))));

        Row r2 = c.addAndGetRow();

        r2.addElem(new SizedBox(ball2(Color.BLUE).getWidth(),ball2(Color.BLUE).getWidth()));

        r2.addElem(Option.noNumber(Drawable.copyShifted(0,0,ball2(Color.RED))));

        r2.addElem(Option.noNumber(ball1(7,Color.BRIGHT_YELLOW)));

        c.addToCanvas(cli.getCanvas(),1,0);
        c.selectInEnabledOption(cli,"Press enter to return to the previous View",Starter::initialScreen);
        cli.show();
    }

    public static void changeCanvasDimension(){
        int x,y;
        Scanner s = new Scanner(System.in);
        System.out.println("Insert cli width");
        x = s.nextInt();

        System.out.println("Insert cli length");
        y = s.nextInt();
        cli.setCanvas(Canvas.withBorder(x,y));
        initialScreen();
    }

    public static Drawable ball1(int xPos, Color c){
        Drawable dwList = new Drawable();
        dwList.add(xPos,"    █      ",c, Background.DEFAULT);
        dwList.add(xPos," ███████   ",c,Background.DEFAULT);
        dwList.add(xPos,"█████████  ",c,Background.DEFAULT);
        dwList.add(xPos," ███████   ",c,Background.DEFAULT);
        dwList.add(xPos,"    █      ",c,Background.DEFAULT);
        return dwList;
    }

    public static Drawable ball2(Color c){

        Drawable dwList = new Drawable();
        dwList.add(0,"   ██   ",c,Background.DEFAULT);
        dwList.add(0," ██████ ",c,Background.DEFAULT);
        dwList.add(0,"   ██   ",c,Background.DEFAULT);
        return dwList;
    }

}
