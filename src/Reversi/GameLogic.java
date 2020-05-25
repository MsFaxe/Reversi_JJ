package Reversi;

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static java.lang.Math.abs;

public class GameLogic {

    private String COMPUTER_PAWNS;
    private String HUMAN_PAWNS;

    public void setCOLOR_PAWNS(String HUMAN_PAWNS) {
        this.HUMAN_PAWNS = HUMAN_PAWNS;
        if (HUMAN_PAWNS.equals("white")){
            this.COMPUTER_PAWNS = "black";
        } else{
            this.COMPUTER_PAWNS = "white";
        }
    }

    private static final HashSet<Field> setOfActivatedFields = new HashSet<>();
    private static final HashSet<Field> setOfDeactivatedFields = new HashSet<>();

    public Field primaryField(int rowIndex, int columnIndex){
        return FieldsMap.getField(rowIndex, columnIndex);
    }

    public Field field(int rowIndex, int columnIndex){
        Field field = primaryField(rowIndex, columnIndex);
        activeEmptyFieldsAround(field);

        return field;
    }

    private void activeEmptyFieldsAround(Field activationField){
        int rI = activationField.getRowIndex(); //rowIndex
        int cI = activationField.getColumnIndex(); //columnIndex

        setOfDeactivatedFields.add(activationField);

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (rI > 0 && rI < 7 && cI > 0 && cI < 7) {
                    setOfActivatedFields.add(primaryField(rI + i, cI + j));
                }
            }
        }

        for (Field field: setOfDeactivatedFields){
            setOfActivatedFields.remove(field);
        }
    }

    public void startGame(){
        field(3,3).putPawn("white");
        field(3,4).putPawn("black");
        field(4,3).putPawn("black");
        field(4,4).putPawn("white");

        System.out.println("Komputer gra: " + COMPUTER_PAWNS);
        System.out.println("Gracz gra: " + HUMAN_PAWNS);

        humanPlay();
    }

    private ArrayList<Field> createListOfPawnsAroundEmptyFieldToClick(int rI, int cI, String color){
        // search enemies pawns
        return  (ArrayList<Field>) setOfDeactivatedFields.stream()
                .filter(e -> e.getRowIndex() == rI + 1 || e.getRowIndex() == rI - 1 || e.getRowIndex() == rI)
                .filter(e -> e.getColumnIndex() == cI + 1 || e.getColumnIndex() == cI || e.getColumnIndex() == cI - 1)
                .filter(e -> e.getPawn().getColorOfPawn().equals(color))
                .collect(Collectors.toList());
    }

    private int quantityOfPawns(String color){
        ArrayList <Field> listOfYourPawns = (ArrayList<Field>) setOfDeactivatedFields.stream()
                .filter(e -> e.getPawn().getColorOfPawn().equals(color))
                .collect(Collectors.toList());
        return listOfYourPawns.size();
    }

    private void humanPlay() {

        //necessary to display the current state of the game
        listOfHumanPawns();
        listOfComputerPawns();

        if (setOfDeactivatedFields.size()< 64 && quantityOfPawns(HUMAN_PAWNS) > 0) {
            for (Field field : setOfActivatedFields) {

                int rI = field.getRowIndex();
                int cI = field.getColumnIndex();


                field.getFieldsLabel().setOnMouseClicked(a -> {
                    System.out.println("Człowiek kliknął: " + field);

                    ArrayList<Field> listOfPawnsAroundEmptyFieldToClick = createListOfPawnsAroundEmptyFieldToClick(rI, cI, COMPUTER_PAWNS);

                    for (Field fieldWithPawn : listOfPawnsAroundEmptyFieldToClick) {

                        //calculate vectors to check next fields
                        int rVecCor = rI - fieldWithPawn.getRowIndex(); //row vector coordinate
                        int cVecCor = cI - fieldWithPawn.getColumnIndex(); //column vector coordinate

                        //complete the list of pawns set in the direction of vector movement
                        List<Field> listOfPawnsToReverse = new ArrayList<>();
                        for (Field deactivateField : setOfDeactivatedFields) {
                            for (int i = 1; i < 8; i++) {
                                if (deactivateField.getRowIndex() == (rI - (i * rVecCor)) && deactivateField.getColumnIndex() == cI - (i * cVecCor)) {
                                    listOfPawnsToReverse.add(deactivateField);
                                }
                            }
                        }

                        //complete the list of empty fields set in the direction of vector movement
                        List<Field> listOfPawnsToNotReverse = new ArrayList<>();
                        for (Field activateField : setOfActivatedFields) {
                            for (int i = 1; i < 8; i++) {
                                if (activateField.getRowIndex() == (rI - (i * rVecCor)) && activateField.getColumnIndex() == cI - (i * cVecCor)) {
                                    listOfPawnsToNotReverse.add(activateField);
                                }
                            }
                        }

                        //make the list of human player pawns, to reverse enemy pawn in the same line
                        List<Field> humanPlayerPawns = new ArrayList<>();

                        if (listOfPawnsToReverse.size() > 1) {
                            for (Field pawnToReverse : listOfPawnsToReverse) {
                                if (pawnToReverse.getPawn().getColorOfPawn().equals(HUMAN_PAWNS)) {
                                    humanPlayerPawns.add(pawnToReverse);
                                }
                            }

                            if (humanPlayerPawns.size() > 0) {

                                //distance between player's pawns, necessary to reverse enemy pawns
                                List<Integer> distanceBetweenPawns = new ArrayList<>();

                                for (Field playerPawn : humanPlayerPawns) {
                                    int r = rI - playerPawn.getRowIndex();
                                    int c = cI - playerPawn.getColumnIndex();

                                    int absValue = abs(r + c);
                                    distanceBetweenPawns.add(absValue);
                                }

                                //distance between player's pawn and gap between any pawns
                                //List<Integer> distanceBetweenPlayerPawnAndEmptyField = new ArrayList<>();
                                for (Field activateField: listOfPawnsToNotReverse){
                                    int r = rI - activateField.getRowIndex();
                                    int c = cI - activateField.getColumnIndex();

                                    int absValue = abs(r + c);
                                    distanceBetweenPawns.add(absValue);
                                    //distanceBetweenPlayerPawnAndEmptyField.add(absValue);
                                }

                                int minDistance = Collections.min(distanceBetweenPawns);

//                                List <Field> helplist = new ArrayList<>();
//                                helplist.addAll(humanPlayerPawns);
//                                helplist.addAll(listOfPawnsToNotReverse);
//
//                                helplist = listOfPawnsToReverse.stream()
//                                        .filter(x -> abs((rI - x.getRowIndex()) + (cI - x.getColumnIndex())) == minDistance)
//                                        .collect(Collectors.toList());

                                for (Field playerPawn: humanPlayerPawns){
                                    listOfPawnsToReverse.remove(playerPawn);
                                }

                                //reverse enemy pawns
                                for (Field everyField : listOfPawnsToReverse) {
                                    if (abs((rI - everyField.getRowIndex()) + (cI - everyField.getColumnIndex())) <= minDistance) {
                                        field.putPawn(HUMAN_PAWNS);
                                        activeEmptyFieldsAround(field);
                                        everyField.reversePawn();
                                    }
                                }
                                computerPlay();
                            }
                        }
                    }
                });
            }
        } else {
            EndGame();
        }
    }

    //the computer is trying to put a pawn on a random field
    private void computerPlay() {
        List<Field> list = new ArrayList<>(setOfActivatedFields);

        AtomicInteger sizeListOfActivatedFields = new AtomicInteger(setOfActivatedFields.size());
        int sizeList = list.size();

        Random random = new Random();
        for (int j = list.size(); j > 0; j--) {
            Field field = list.get(random.nextInt(j));

            int rI = field.getRowIndex();
            int cI = field.getColumnIndex();

            if (sizeList <= sizeListOfActivatedFields.get()) {

                System.out.println("Komputer wylosował: " + field);

                // search enemies pawns
                ArrayList<Field> listOfPawnsAroundEmptyFieldToClick = createListOfPawnsAroundEmptyFieldToClick(rI, cI, HUMAN_PAWNS);

                for (Field fieldWithPawn : listOfPawnsAroundEmptyFieldToClick) {
                    //calculate vectors to check next fields
                    int rVecCor = rI - fieldWithPawn.getRowIndex(); //row vector coordinate
                    int cVecCor = cI - fieldWithPawn.getColumnIndex(); //column vector coordinate

                    //complete the list of pawns set in the direction of vector movement
                    List<Field> listOfPawnsToReverse = new ArrayList<>();
                    for (Field deactivateField : setOfDeactivatedFields) {
                        for (int i = 1; i < 8; i++) {
                            if (deactivateField.getRowIndex() == (rI - (i * rVecCor)) && deactivateField.getColumnIndex() == cI - (i * cVecCor)) {
                                listOfPawnsToReverse.add(deactivateField);
                            }
                        }
                    }

                    //make the list of human player pawns, to reverse enemy pawn in the same line
                    List<Field> humanPlayerPawns = new ArrayList<>();

                    if (listOfPawnsToReverse.size() > 1) {
                        for (Field pawnToReverse : listOfPawnsToReverse) {
                            if (pawnToReverse.getPawn().getColorOfPawn().equals(COMPUTER_PAWNS)) {
                                humanPlayerPawns.add(pawnToReverse);
                                field.putPawn(COMPUTER_PAWNS);
                                activeEmptyFieldsAround(field);
                            }
                        }

                        if (humanPlayerPawns.size() > 0) {
                            //distance between player's pawns, necessary to reverse enemy pawns
                            List<Integer> distanceBetweenPawns = new ArrayList<>();
                            for (Field playerPawn : humanPlayerPawns) {
                                int r = rI - playerPawn.getRowIndex();
                                int c = cI - playerPawn.getColumnIndex();

                                int absValue = abs(r + c);
                                distanceBetweenPawns.add(absValue);
                            }

                            int minDistance = Collections.min(distanceBetweenPawns);

                            for (Field playerPawn : humanPlayerPawns) {
                                listOfPawnsToReverse.remove(playerPawn);
                            }

                            //reverse enemy pawns
                            for (Field everyField : listOfPawnsToReverse) {
                                if (abs((rI - everyField.getRowIndex()) + (cI - everyField.getColumnIndex())) <= minDistance) {
                                    everyField.reversePawn();
                                }
                            }
                            humanPlay();
                            sizeListOfActivatedFields.set(0);
                            break;
                        }
                    }
                    list.remove(field);
                }
            }
        }
    }

    private List<Field> listOfHumanPawns(){
        List<Field> listOfHumanPawns = setOfDeactivatedFields.stream().filter(e -> e.getPawn().getColorOfPawn().equals(HUMAN_PAWNS)).collect(Collectors.toList());
        PlayersScore.getHumanScore().setText("human score = " + listOfHumanPawns.size());
        return listOfHumanPawns;
    }

    private List<Field> listOfComputerPawns(){
        List<Field> listOfComputerPawns = setOfDeactivatedFields.stream().filter(e -> e.getPawn().getColorOfPawn().equals(COMPUTER_PAWNS)).collect(Collectors.toList());
        PlayersScore.getComputerScore().setText("computer score = " + listOfComputerPawns.size());
        return listOfComputerPawns;
    }


    private void EndGame() {
        listOfHumanPawns();
        listOfComputerPawns();

        if (listOfHumanPawns().size() == listOfComputerPawns().size()) {
            EndGameScene.endOfGame("Dead-heat", "human score = " + listOfHumanPawns().size() + " computer score = " + listOfComputerPawns().size());
        } else if (listOfHumanPawns().size() > listOfComputerPawns().size()) {
            EndGameScene.endOfGame("You WON!", "human score = " + listOfHumanPawns().size() + " computer score = " + listOfComputerPawns().size());
        } else {
            EndGameScene.endOfGame("You lost ;(", "human score = " + listOfHumanPawns().size() + " computer score = " + listOfComputerPawns().size());
        }

        RecordsBoard recordsBoard = new RecordsBoard();
        try {
            recordsBoard.saveRecords(listOfHumanPawns().size(), listOfComputerPawns().size());
        }catch (Exception e){
            //
        }
    }

    public static void saveGame() throws IOException {
        FileWriter fileWriter = new FileWriter("deactivateFields.txt");
        PrintWriter printWriter = new PrintWriter(fileWriter);
        for (Field record: setOfDeactivatedFields){
            printWriter.print(record);
        }
        printWriter.close();
    }

    public void loadGame() throws FileNotFoundException {
        String file ="deactivateFields.txt";

        Scanner scanner = new Scanner(new File(file));
        scanner.useDelimiter(" ");
        while(scanner.hasNext()) {
            //System.out.println(scanner.next() + scanner.next() + scanner.next());
            field(Integer.parseInt(scanner.next()),Integer.parseInt(scanner.next())).putPawn(scanner.next());
        }
        scanner.close();

        System.out.println(setOfDeactivatedFields.size());
        System.out.println(setOfActivatedFields.size());
        humanPlay();
    }
}