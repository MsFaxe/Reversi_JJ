package test2;

import javafx.scene.control.Label;

import java.util.*;
import java.util.stream.Collectors;

import static test2.StartGameScene.getPawnColor;

public class GameLogic extends BoardGameScene {
    private boolean action = true;

    public List<Label> unlockLabel() {
//        Map<Key, Label> labelMap = new HashMap<>();
//
//        for (int i = 0; i < 8; i++) { // i - rows
//            for (int j = 0; j < 8; j++) { //j - columns
//                labelMap.put(new Key(i, j), getOneLabel(i, j));
//            }
//        }
//
//        String color = StartGameScene.getPawnColor();

        List<Label> list1 = new ArrayList<>();

        for (int i = 0; i < 8; i++) { // i - rows
            for (int j = 0; j < 8; j++) { //j - columns
                list1.add(getOneLabel(i, j));
            }
        }

//        for (Map.Entry<Key, Label> label : labelMap.entrySet()) {
//            list1.add(label.getValue());
//        }

        List<Label> list2 = list1.stream()
                .filter(e -> (e.getGraphic() != null))
                .collect(Collectors.toList());

        HashSet<Integer> indexesList = new HashSet<>();

        for (Label label : list2) {
            indexesList.add(Integer.parseInt(label.getText()));
            indexesList.add(Integer.parseInt(label.getText()) + 10);
            indexesList.add(Integer.parseInt(label.getText()) - 10);
            indexesList.add(Integer.parseInt(label.getText()) + 1);
            indexesList.add(Integer.parseInt(label.getText()) - 1);

        }

        list2 = list1.stream()
                .filter(e -> indexesList.contains(Integer.parseInt(e.getText())))
                .filter(e -> e.getGraphic() == null)
                .collect(Collectors.toList());

        //System.out.println(list2);
        return list2;
    }


    public void humanPlay() {
        List<Label> list = unlockLabel();

        for (Label label : list) {
            int i = Integer.parseInt(label.getText().substring(0, 1)); //horizontalIndex
            int j = Integer.parseInt(label.getText().substring(1)); //verticalIndex


            label.setOnMouseClicked(e -> {

                if ((i < 7 && getOneLabel(i + 1, j).getGraphic() != null && getOneLabel(i + 1, j).getId() != getPawnColor())
                        || (j > 0 && getOneLabel(i, j - 1).getGraphic() != null && getOneLabel(i, j - 1).getId() != getPawnColor())
                        || (j < 7 && getOneLabel(i, j + 1).getGraphic() != null && getOneLabel(i, j + 1).getId() != getPawnColor())
                        || (i > 0 && getOneLabel(i - 1, j).getGraphic() != null && getOneLabel(i - 1, j).getId() != getPawnColor())) {

                    getOneLabel(i, j).setGraphic(getDot(getPawnColor())); //1st action
                    getOneLabel(i, j).setId(getPawnColor());
                    getOneLabel(i, j).setPickOnBounds(false);

                    changeColorOfPowns(i, j, getPawnColor());

                    computerPlay(); // 3rd action
                }
            });

        }
        //list2.stream().forEach(System.out::println);
    }

    private int round = 0;

    public void computerPlay() {

        System.out.println("good job: round " + round);

        List<Label> list = unlockLabel();
        Random random = new Random();

        Label label = list.get(random.nextInt(list.size()));
        System.out.println(label);

        int i = Integer.parseInt(label.getText().substring(0, 1)); //horizontalIndex
        int j = Integer.parseInt(label.getText().substring(1)); //verticalIndex

        if (getPawnColor() == "white") {

            label.setGraphic(getDot("black"));
            label.setPickOnBounds(false);

            changeColorOfPowns(i, j, "black");

        }else {
            label.setGraphic(getDot("white"));
            label.setPickOnBounds(false);

            changeColorOfPowns(i, j, "white");

        }

        round++;
        humanPlay();
    }

    public boolean possibleToClick(int i, int j){
        if ((i < 7 && getOneLabel(i + 1, j).getGraphic() != null && getOneLabel(i + 1, j).getId() != getPawnColor())
                || (j > 0 && getOneLabel(i, j - 1).getGraphic() != null && getOneLabel(i, j - 1).getId() != getPawnColor())
                || (j < 7 && getOneLabel(i, j + 1).getGraphic() != null && getOneLabel(i, j + 1).getId() != getPawnColor())
                || (i > 0 && getOneLabel(i - 1, j).getGraphic() != null && getOneLabel(i - 1, j).getId() != getPawnColor())){
            return true;
        }else{
            return false;
        }
    }

    public void changeColorOfPowns(int i, int j, String string){
        if (i < 7 && getOneLabel(i + 1, j).getGraphic() != null /*&& getOneLabel(i + 1, j).getId() != getPawnColor()*/) {
            getOneLabel(i + 1, j).setGraphic(getDot(string)); //2nd action
            getOneLabel(i + 1, j).setId(string);
            getOneLabel(i + 1, j).setPickOnBounds(false);
        }

        if (i > 0 && getOneLabel(i - 1, j).getGraphic() != null /*&& getOneLabel(i - 1, j).getId() != getPawnColor()*/) {
            getOneLabel(i - 1, j).setGraphic(getDot(string));
            getOneLabel(i - 1, j).setId(string);
            getOneLabel(i - 1, j).setPickOnBounds(false);
        }

        if (j < 7 && getOneLabel(i, j + 1).getGraphic() != null /*&& getOneLabel(i, j + 1).getId() != getPawnColor()*/) {
            getOneLabel(i, j + 1).setGraphic(getDot(string));
            getOneLabel(i, j + 1).setId(string);
            getOneLabel(i, j + 1).setPickOnBounds(false);
        }

        if (j > 0 && getOneLabel(i, j - 1).getGraphic() != null /*&& getOneLabel(i, j - 1).getId() != getPawnColor()*/) {
            getOneLabel(i, j - 1).setGraphic(getDot(string));
            getOneLabel(i, j - 1).setId(string);
            getOneLabel(i, j - 1).setPickOnBounds(false);
        }
    }
}
