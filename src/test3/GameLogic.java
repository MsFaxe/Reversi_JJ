package test3;

import java.util.*;

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

        humanPlay();
    }

    private void humanPlay() {
        System.out.println("human play");

        for (Field field : listOfActivatedFields) {
            int rI = field.getRowIndex();
            int cI = field.getColumnIndex();


            field.getFieldsLabel().setOnMouseClicked(e -> {
                //check fields below
                if (checkIfFieldExistAndItIsNotNullAndWhatColorHas(rI+1, cI, WHITE_PAWN)) {
                    for (int i = 1; i < 8; i++) {
                        if(checkIfFieldExistAndItIsNotNullAndWhatColorHas(rI+i, cI, BLACK_PAWN)) {
                            clickEvent(field);
                            break;
                        }
                    }
                }

                //check fields under
                else if (checkIfFieldExistAndItIsNotNullAndWhatColorHas(rI-1, cI, WHITE_PAWN)) {
                    for (int i = 1; i < 8; i++) {
                        if(checkIfFieldExistAndItIsNotNullAndWhatColorHas(rI-i, cI, BLACK_PAWN)) {
                            clickEvent(field);
                            break;
                        }
                    }
                }

                //check fields on right
                else if (checkIfFieldExistAndItIsNotNullAndWhatColorHas(rI, cI+1, WHITE_PAWN)) {
                    for (int i = 1; i < 8; i++) {
                        if(checkIfFieldExistAndItIsNotNullAndWhatColorHas(rI, cI+i, BLACK_PAWN)) {
                            clickEvent(field);
                            break;
                        }
                    }
                }

                //check field on left
                else if (checkIfFieldExistAndItIsNotNullAndWhatColorHas(rI, cI-1, WHITE_PAWN)) {
                    for (int i = 1; i < 8; i++) {
                        if(checkIfFieldExistAndItIsNotNullAndWhatColorHas(rI, cI-i, BLACK_PAWN)) {
                            clickEvent(field);
                            break;
                        }
                    }
                }
            });
        }
    }

    private void clickEvent(Field field){
        field.putPawn(BLACK_PAWN);
        activeEmptyFieldsAround(field);
        reverseEnemyPawns(field);
        computerPlay();
    }

    private boolean checkIfFieldExistAndItIsNotNullAndWhatColorHas(int rI, int cI, String pawnColor){
        return primaryField(rI, cI) != null && primaryField(rI, cI).getPawn() != null && primaryField(rI, cI).getPawn().getColorOfPawn().equals(pawnColor);
    }


    //the computer is trying to put a pawn on a random field
    private void computerPlay() {
        System.out.println("computerPlay");

        HashSet<Field> set = listOfActivatedFields;
        List<Field> list = new ArrayList<>(set);

        System.out.println(list.size());
        Random random = new Random();
        Field randomActivateField;

        for (int j = list.size(); j>0 ;j--) {

            System.out.println(list.size());

            randomActivateField = list.get(random.nextInt(j));
            System.out.println(randomActivateField);

            int rI = randomActivateField.getRowIndex(); //rowIndex
            int cI = randomActivateField.getColumnIndex(); //columnIndex

            int start;

            if (checkIfFieldExistAndItIsNotNullAndWhatColorHas(rI + 1, cI, BLACK_PAWN)) {
                start = 1;
                System.out.println("start with " + start);
            } else if (checkIfFieldExistAndItIsNotNullAndWhatColorHas(rI - 1, cI, BLACK_PAWN)) {
                start = 2;
                System.out.println("start with " + start);
            } else if (checkIfFieldExistAndItIsNotNullAndWhatColorHas(rI, cI + 1, BLACK_PAWN)) {
                start = 3;
                System.out.println("start with " + start);
            } else if (checkIfFieldExistAndItIsNotNullAndWhatColorHas(rI, cI - 1, BLACK_PAWN)) {
                start = 4;
                System.out.println("start with " + start);
            } else {
                list.remove(randomActivateField);
                continue;
            }

            int help = 5;

            while (start < 5) {
                switch (start) {
                    case 1:
                        for (int i = 1; i < 8; i++) {
                            if (primaryField(rI + i, cI) != null && primaryField(rI + i, cI).getPawn() != null && primaryField(rI + i, cI).getPawn().getColorOfPawn().equals(WHITE_PAWN)) {
                                randomActivateField.putPawn(WHITE_PAWN);
                                activeEmptyFieldsAround(randomActivateField);
                                reverseEnemyPawns(randomActivateField);

                                humanPlay();
                                help = 5;
                                break;
                            } else {
                                help = 2;
                            }
                        }
                        start = help;
                        if (start == 5) break;
                        System.out.println("out case 1: " + start);

                    case 2:
                        for (int i = 1; i < 8; i++) {
                            if (primaryField(rI - i, cI) != null && primaryField(rI - i, cI).getPawn() != null && primaryField(rI - i, cI).getPawn().getColorOfPawn().equals(WHITE_PAWN)) {
                                randomActivateField.putPawn(WHITE_PAWN);
                                activeEmptyFieldsAround(randomActivateField);
                                reverseEnemyPawns(randomActivateField);

                                humanPlay();
                                help = 5;
                                break;
                            } else {
                                help = 3;

                            }
                        }
                        start = help;
                        if (start == 5) break;
                        System.out.println("out case 2: " + start);

                    case 3:
                        for (int i = 1; i < 8; i++) {
                            if (primaryField(rI, cI + i) != null && primaryField(rI, cI + i).getPawn() != null && primaryField(rI, cI + i).getPawn().getColorOfPawn().equals(WHITE_PAWN)) {
                                randomActivateField.putPawn(WHITE_PAWN);
                                activeEmptyFieldsAround(randomActivateField);
                                reverseEnemyPawns(randomActivateField);

                                humanPlay();
                                help = 5;
                                break;
                            } else {
                                help = 4;
                            }
                        }
                        start = help;
                        if (start == 5) break;
                        System.out.println("out case 3: " + start);

                    case 4:
                        for (int i = 1; i < 8; i++) {
                            if (primaryField(rI, cI - i) != null && primaryField(rI, cI - i).getPawn() != null && primaryField(rI, cI - i).getPawn().getColorOfPawn().equals(WHITE_PAWN)) {
                                randomActivateField.putPawn(WHITE_PAWN);
                                activeEmptyFieldsAround(randomActivateField);
                                reverseEnemyPawns(randomActivateField);

                                humanPlay();
                                help = 5;
                                break;
                            } else {
                                help = 6;
                            }
                        }
                        start = help;
                        if (start == 5) break;
                        System.out.println("out case 4: " + start);
                }
            }
//            humanPlay();
            if (start == 5) {
                break;
            } else {
                list.remove(randomActivateField);
                //continue;
            }
        }
    }

    public void reverseEnemyPawns(Field clickedField) {
        int rI = clickedField.getRowIndex(); //rowIndex
        int cI = clickedField.getColumnIndex(); //columnIndex
        String color = clickedField.getPawn().getColorOfPawn();


        //below
        for (int i = 1; i < 8; i++) {
            if (primaryField(rI + i, cI) == null || primaryField(rI + i, cI).getPawn() == null || primaryField(rI + i, cI).getPawn().getColorOfPawn().equals(color)) {
                break;
            }
            primaryField(rI + i, cI).reversePawn();
        }

        //under
        for (int i = 1; i < 8; i++) {
            if (primaryField(rI - i, cI) == null || primaryField(rI - i, cI).getPawn() == null || primaryField(rI - i, cI).getPawn().getColorOfPawn().equals(color)) {
                break;
            }
            primaryField(rI - i, cI).reversePawn();
        }

        //on right
        for (int i = 1; i < 8; i++) {
            if (primaryField(rI, cI + i) == null || primaryField(rI, cI + i).getPawn() == null || primaryField(rI, cI + i).getPawn().getColorOfPawn().equals(color)) {
                break;
            }
            primaryField(rI, cI + i).reversePawn();
        }

        //on left
        for (int i = 1; i < 8; i++) {
            if (primaryField(rI, cI - i) == null || primaryField(rI, cI - i).getPawn() == null || primaryField(rI, cI - i).getPawn().getColorOfPawn().equals(color)) {
                break;
            }
            primaryField(rI, cI - i).reversePawn();
        }
    }

}