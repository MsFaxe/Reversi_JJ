package test3;

import java.util.*;
import java.util.concurrent.BlockingDeque;
import java.util.stream.Collectors;

import static java.lang.Math.abs;

public class GameLogic {

    private final String WHITE_PAWN = "white";
    private final String BLACK_PAWN = "black";

    private HashSet<Field> listOfActivatedFields = new HashSet<>();
    private HashSet<Field> listOfDeactivatedFields = new HashSet<>();


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

        listOfDeactivatedFields.add(activationField);

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (rI > 0 && rI < 7 && cI > 0 && cI < 7) {
                    primaryField(rI + i, cI + j).activateTheField();
                    listOfActivatedFields.add(primaryField(rI + i, cI + j));
                }
            }
        }

        for (Field field: listOfDeactivatedFields){
            listOfActivatedFields.remove(field);
        }
    }

    public void startGame(){
        field(3,3).putPawn(WHITE_PAWN);
        field(3,4).putPawn(BLACK_PAWN);
        field(4,3).putPawn(BLACK_PAWN);
        field(4,4).putPawn(WHITE_PAWN);

//        field(3,2).putPawn(WHITE_PAWN);
//        field(4,2).putPawn(WHITE_PAWN);
//        field(5,2).putPawn(WHITE_PAWN);
//        field(5,3).putPawn(WHITE_PAWN);
//        field(5,4).putPawn(BLACK_PAWN);

        humanPlay();
    }

    private void humanPlay() {
        System.out.println("\nhuman play");

        for (Field field : listOfActivatedFields) {

            int rI = field.getRowIndex();
            int cI = field.getColumnIndex();


            field.getFieldsLabel().setOnMouseClicked(a -> {

                System.out.println("Człowiek kliknął: " + field);

                // search enemies pawns
                ArrayList<Field> listOfPawnsAroundEmptyFieldToClick = (ArrayList<Field>) listOfDeactivatedFields.stream()
                        .filter(e -> e.getRowIndex() == rI + 1 || e.getRowIndex() == rI - 1 || e.getRowIndex() == rI)
                        .filter(e -> e.getColumnIndex() == cI + 1 || e.getColumnIndex() == cI || e.getColumnIndex() == cI - 1)
                        .filter(e -> e.getPawn().getColorOfPawn().equals(WHITE_PAWN))
                        .collect(Collectors.toList());

                //System.out.println(listOfPawnsAroundEmptyFieldToClick);


                for (Field fieldWithPawn : listOfPawnsAroundEmptyFieldToClick) {


                    //calculate vectors to check next fields
                    int rVecCor = rI - fieldWithPawn.getRowIndex(); //row vector coordinate
                    int cVecCor = cI - fieldWithPawn.getColumnIndex(); //column vector coordinate

                    //System.out.println(rVecCor + ", " + cVecCor);


                    //complete the list of pawns set in the direction of vector movement
                    List<Field> listOfPawnsToReverse = new ArrayList<>();

                    //System.out.println("List size: " + listOfPawnsToReverse.size());

                    for (Field deactivateField : listOfDeactivatedFields) {
                        for (int i = 1; i < 8; i++) {
                            if (deactivateField.getRowIndex() == (rI - (i * rVecCor)) && deactivateField.getColumnIndex() == cI - (i * cVecCor)) {
                                //System.out.println(deactivateField);
                                listOfPawnsToReverse.add(deactivateField);
                            }
                        }
                    }

                    //System.out.println("List size: " + listOfPawnsToReverse.size());


                    //make the list of human player pawns, to reverse enemy pawn in the same line
                    List<Field> humanPlayerPawns = new ArrayList<>();

                    if (listOfPawnsToReverse.size() > 1) {
                        for (Field pawnToReverse : listOfPawnsToReverse) {
                            if (pawnToReverse.getPawn().getColorOfPawn().equals(BLACK_PAWN)) {
                                //System.out.println(pawnToReverse);
                                humanPlayerPawns.add(pawnToReverse);
                                field.putPawn(BLACK_PAWN);
                                activeEmptyFieldsAround(field);
                            }
                        }

                        //System.out.println(humanPlayerPawns.size());

                        if (humanPlayerPawns.size() > 0) {

                            //distance between player's pawns, necessary to reverse enemy pawns
                            List<Integer> distanceBetweenPawns = new ArrayList<>();

                            for (Field playerPawn : humanPlayerPawns) {
                                int r = rI - playerPawn.getRowIndex();
                                int c = cI - playerPawn.getColumnIndex();

                                int absValue = abs(r + c);
                                distanceBetweenPawns.add(absValue);
                                //System.out.println(absValue);
                            }

                            int minDistance = Collections.min(distanceBetweenPawns);

                            for (Field playerPawn: humanPlayerPawns){
                                listOfPawnsToReverse.remove(playerPawn);
                            }

                            System.out.println("zmienione pola: ");
                            //reverse enemy pawns
                            for (Field everyField : listOfPawnsToReverse) {

                                if (abs((rI - everyField.getRowIndex()) + (cI - everyField.getColumnIndex())) <= minDistance) {
                                    everyField.reversePawn();
                                    System.out.println(everyField);
                                }
                            }
                            computerPlay();
                        }
                    }
                }
            });
        }
    }


    //the computer is trying to put a pawn on a random field
    private void computerPlay() {
        System.out.println("\ncomputerPlay");

        List<Field> lista = new ArrayList<>(listOfActivatedFields);

        int sizeListOfActivatedFields = listOfActivatedFields.size();
        int sizeLista = lista.size();

        Random random = new Random();

        for (int j = lista.size(); j > 0; j--) {

            Field field = lista.get(random.nextInt(j));


            int rI = field.getRowIndex();
            int cI = field.getColumnIndex();

            if (sizeLista <= sizeListOfActivatedFields) {

                System.out.println("Komputer wylosował: " + field);
                // search enemies pawns
                ArrayList<Field> listOfPawnsAroundEmptyFieldToClick = (ArrayList<Field>) listOfDeactivatedFields.stream()
                        .filter(e -> e.getRowIndex() == rI + 1 || e.getRowIndex() == rI - 1 || e.getRowIndex() == rI)
                        .filter(e -> e.getColumnIndex() == cI + 1 || e.getColumnIndex() == cI || e.getColumnIndex() == cI - 1)
                        .filter(e -> e.getPawn().getColorOfPawn().equals(BLACK_PAWN))
                        .collect(Collectors.toList());

                System.out.println(listOfPawnsAroundEmptyFieldToClick);


                for (Field fieldWithPawn : listOfPawnsAroundEmptyFieldToClick) {

                    //calculate vectors to check next fields
                    int rVecCor = rI - fieldWithPawn.getRowIndex(); //row vector coordinate
                    int cVecCor = cI - fieldWithPawn.getColumnIndex(); //column vector coordinate

                    System.out.println(rVecCor + ", " + cVecCor);


                    //complete the list of pawns set in the direction of vector movement
                    List<Field> listOfPawnsToReverse = new ArrayList<>();
                    System.out.println("List size: " + listOfPawnsToReverse.size());

                    for (Field deactivateField : listOfDeactivatedFields) {
                        for (int i = 1; i < 8; i++) {
                            if (deactivateField.getRowIndex() == (rI - (i * rVecCor)) && deactivateField.getColumnIndex() == cI - (i * cVecCor)) {
                                System.out.println(deactivateField);
                                listOfPawnsToReverse.add(deactivateField);
                            }
                        }
                    }

                    System.out.println("List size: " + listOfPawnsToReverse.size());


                    //make the list of human player pawns, to reverse enemy pawn in the same line
                    List<Field> humanPlayerPawns = new ArrayList<>();

                    if (listOfPawnsToReverse.size() > 1) {
                        for (Field pawnToReverse : listOfPawnsToReverse) {
                            if (pawnToReverse.getPawn().getColorOfPawn().equals(WHITE_PAWN)) {
                                System.out.println(pawnToReverse);
                                humanPlayerPawns.add(pawnToReverse);
                                field.putPawn(WHITE_PAWN);
                                activeEmptyFieldsAround(field);
                            }
                        }

                        System.out.println(humanPlayerPawns);

                        if (humanPlayerPawns.size() > 0) {
                            //distance between player's pawns, necessary to reverse enemy pawns
                            List<Integer> distanceBetweenPawns = new ArrayList<>();
                            for (Field playerPawn : humanPlayerPawns) {
                                int r = rI - playerPawn.getRowIndex();
                                int c = cI - playerPawn.getColumnIndex();

                                int absValue = abs(r + c);
                                distanceBetweenPawns.add(absValue);
                                System.out.println(absValue);
                            }

                            int minDistance = Collections.min(distanceBetweenPawns);


                            //listOfPawnsToReverse.remove(humanPlayerPawns);

                            for (Field playerPawn: humanPlayerPawns){
                                listOfPawnsToReverse.remove(playerPawn);
                            }

                            System.out.println("zmienione pola: ");
                            //reverse enemy pawns
                            for (Field everyField : listOfPawnsToReverse) {

                                if (abs((rI - everyField.getRowIndex()) + (cI - everyField.getColumnIndex())) <= minDistance) {
                                    everyField.reversePawn();
                                    System.out.println(everyField);
                                }
                            }
                            humanPlay();
                            sizeListOfActivatedFields = 0;
                            break;
                        }
                    }
                    lista.remove(field);
                    //break;
                }
            }
        }
    }
}