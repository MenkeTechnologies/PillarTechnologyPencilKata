import java.util.stream.IntStream;

/**
 * Created by jacobmenke on 7/2/17.
 */

class PointDurability{
    public static final Integer UPPERCASE_CHARCTER_DURABILITY_COST = 2;
    public static final Integer LOWERCASE_CHARCTER_DURABILITY_COST = 1;
    public static final Integer DEFAULT_STARTING_POINT_DURABILITY = 100;
}

class SpecialCharacters{
    public static final Character SPACE = ' ';
}

public class Pencil {

    private Paper paper;
    private Integer pointDurability;
    private Integer maximumPointDurability;

    public Pencil(Integer pointDurability) {
        this.maximumPointDurability = pointDurability;
        this.pointDurability = maximumPointDurability;
    }

    public Pencil() {
        this(PointDurability.DEFAULT_STARTING_POINT_DURABILITY);
    }

    public Integer getPointDurability() {
        return pointDurability;
    }

    public Integer getMaximumPointDurability() {
        return maximumPointDurability;
    }

    public void setPaperToWriteTo(Paper paper) {
        this.paper = paper;
    }

    /**
     * writes characters to the paper as long as paper object has been set
     * @param content the textual content to write to the paper
     */
    public void write(String content){
        if (paper != null) {

            for (int i = 0; i < content.length(); i++) {
                Character currentCharacter = content.charAt(i);
                if (Character.isUpperCase(currentCharacter)){
                    pointDurability -= PointDurability.UPPERCASE_CHARCTER_DURABILITY_COST;
                }
                //how should punctuation be counted?
                if (Character.isLowerCase(currentCharacter)){
                    pointDurability -= PointDurability.LOWERCASE_CHARCTER_DURABILITY_COST;
                }

                if (pointDurability >= 0){
                    paper.addCharacter(currentCharacter);
                } else {
                    paper.addCharacter(SpecialCharacters.SPACE);
                }
            }

        }


    }

    public void sharpen() {
        pointDurability = maximumPointDurability;

    }
}
