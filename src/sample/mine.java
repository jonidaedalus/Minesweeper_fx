package sample;
import java.util.Random;
import java.util.Scanner;
public class mine {
    String inputgrid[][];
    int numofbombs;
    int row;int column;
    int bombs[][];
    int grid[][];
    String grid2[][];


    public mine(int row,int column,int numofbombs){
        this.numofbombs=numofbombs;
        this.row=row;
        this.column=column;
        this.bombs=new int[numofbombs][2];
        this.grid=new int[row][column];
        this.grid2=new String[row][column];
        this.inputgrid=new String[row][column];
    }
    public void createinput(){
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                inputgrid[i][j]="□";
            }
        }
    }
    public void createbombs(){
        Random rand=new Random();
        for(int i=0;i<numofbombs;i++){
            bombs[i][0]=rand.nextInt(9);
            bombs[i][1]=rand.nextInt(9);
        }
    }
    public void creategridint(){
        for(int i=0;i<row;i++){
            for(int j=0;j<column;j++){
                grid[i][j]=0;
            }
        }
        for(int i=0;i<numofbombs;i++){
            grid[bombs[i][0]][bombs[i][1]]=10;
        }
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if(grid[i][j]>9){
                    if(i==0 && j==0){
                        grid[i][j+1]+=1;
                        grid[i+1][j]+=1;
                        grid[i+1][j+1]+=1;
                    }
                    else if(i==0 && j==column-1){
                        grid[i][j-1]+=1;
                        grid[i+1][j-1]+=1;
                        grid[i+1][j]+=1;
                    }
                    else if(i==row-1 && j==0){
                        grid[i-1][j]+=1;
                        grid[i-1][j+1]+=1;
                        grid[i][j+1]+=1;
                    }
                    else if(i==row-1 && j==column-1){
                        grid[i-1][j-1]+=1;
                        grid[i-1][j]+=1;
                        grid[i][j-1]+=1;
                    }
                    else if(i==0){
                        grid[i][j-1]+=1;
                        grid[i][j+1]+=1;
                        grid[i+1][j-1]+=1;
                        grid[i+1][j]+=1;
                        grid[i+1][j+1]+=1;
                    }
                    else if(i==row-1){
                        grid[i-1][j-1]+=1;
                        grid[i-1][j]+=1;
                        grid[i-1][j+1]+=1;
                        grid[i][j-1]+=1;
                        grid[i][j+1]+=1;
                    }
                    else if(j==0){

                        grid[i-1][j]+=1;
                        grid[i-1][j+1]+=1;
                        grid[i][j+1]+=1;
                        grid[i+1][j]+=1;
                        grid[i+1][j+1]+=1;
                    }
                    else if(j==column-1){
                        grid[i-1][j-1]+=1;
                        grid[i-1][j]+=1;
                        grid[i][j-1]+=1;
                        grid[i+1][j-1]+=1;
                        grid[i+1][j]+=1;
                    }
                    else{
                        grid[i-1][j-1]+=1;
                        grid[i-1][j]+=1;
                        grid[i-1][j+1]+=1;
                        grid[i][j-1]+=1;
                        grid[i][j+1]+=1;
                        grid[i+1][j-1]+=1;
                        grid[i+1][j]+=1;
                        grid[i+1][j+1]+=1;
                    }
                }
            }
        }


    }
    public void creategridstr(){
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if(grid[i][j]>9){
                    grid2[i][j]="\uD83D\uDCA3";
                }
                else{
                    grid2[i][j]=""+grid[i][j];
                }
            }
        }
    }

    public void openblocks(int x,int y){

        if(grid[x][y]==0){
            inputgrid[x][y]=grid2[x][y];
            grid[x][y]=20;
            if(x==0 && y==0){
                openblocks(x,y+1);
                openblocks(x+1,y);
                openblocks(x+1,y+1);
            }
            else if(x==0 && y==column-1){
                openblocks(x,y-1);
                openblocks(x+1,y-1);
                openblocks(x+1,y);

            }
            else if(x==row-1 && y==column-1){
                openblocks(x-1,y-1);
                openblocks(x-1,y);
                openblocks(x,y-1);


            }
            else if(x==row-1 && y==0){
                openblocks(x-1,y);
                openblocks(x-1,y+1);
                openblocks(x,y+1);

            }
            else if(x==0){
                openblocks(x,y-1);
                openblocks(x,y+1);
                openblocks(x+1,y-1);
                openblocks(x+1,y);
                openblocks(x+1,y+1);
            }
            else if(x==row-1){
                openblocks(x-1,y-1);
                openblocks(x-1,y);
                openblocks(x-1,y+1);
                openblocks(x,y-1);
                openblocks(x,y+1);
            }
            else if(y==0){
                openblocks(x-1,y);
                openblocks(x-1,y+1);
                openblocks(x,y+1);
                openblocks(x+1,y);
                openblocks(x+1,y+1);
            }
            else if(y==column-1){
                openblocks(x-1,y-1);
                openblocks(x-1,y);
                openblocks(x,y-1);
                openblocks(x+1,y-1);
                openblocks(x+1,y);

            }
            else{
                openblocks(x-1,y-1);
                openblocks(x-1,y);
                openblocks(x-1,y+1);
                openblocks(x,y-1);
                openblocks(x,y+1);
                openblocks(x+1,y-1);
                openblocks(x+1,y);
                openblocks(x+1,y+1);
            }

        }
        else if(grid[x][y]<9){
            inputgrid[x][y]=grid2[x][y];
        }
    }

    public int countblock(){
        int count=0;
        for (int i = 0; i <row; i++) {
            for (int j = 0; j <column; j++) {
                if (inputgrid[i][j].equals("□")) {
                    count += 1;
                }
            }
        }
        return count;

    }
    public static void main( String[] args ) {
        int x,y;
        String more;
        Scanner input = new Scanner(System.in);
        int randbomb;
        Random rand=new Random();
        randbomb=rand.nextInt(48)+2;
        mine test = new mine(9,9,randbomb);
        test.createbombs();
        test.creategridint();
        test.creategridstr();
        test.createinput();


        while(true){
            if(test.countblock()==test.numofbombs){
                for (int i = 0; i < test.row; i++) {
                    for (int j = 0; j <test.column; j++) {
                        System.out.print(test.inputgrid[i][j]+"\t");
                    }
                    System.out.println();
                }
                System.out.println("You win. Congrats! ");
                System.out.println("Would you like to play again? (Y/N)");
                more=input.next();
                if(more.equals("Y")){
                    main(args);
                    return;
                }
                else if(more.equals("N")){
                    break;

                }


            }
            else {
                for (int i = 0; i < test.row; i++) {
                    for (int j = 0; j <test.column; j++) {
                        System.out.print(test.inputgrid[i][j]+"\t");
                    }
                    System.out.println();
                }
                System.out.print("Enter your move (row[1-"+test.row+"] column[1-"+test.column+"]): ");
                while(true) {

                    x = input.nextInt() - 1;
                    y = input.nextInt() - 1;
                    if((x<test.row && x>-1) && (y>-1 && y<test.column)){
                        break;
                    }

                    else{
                        System.out.print("Invalide move, try again: ");
                    }
                }
                if(test.grid2[x][y].equals("\uD83D\uDCA3")){
                    for (int i = 0; i < test.row; i++) {
                        for (int j = 0; j <test.column; j++) {
                            System.out.print(test.grid2[i][j]+"\t");
                        }
                        System.out.println();
                    }

                    System.out.print("Oops! You lose. Would you like to play again? (Y/N): ");
                    more=input.next();
                    if(more.equals("Y")){
                        main(args);
                        return;
                    }
                    else if(more.equals("N")){
                        break;
                    }

                }
                else{
                    test.openblocks(x,y);
                }

            }
        }

    }
}