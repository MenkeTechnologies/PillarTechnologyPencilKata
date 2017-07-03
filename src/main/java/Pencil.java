import java.util.stream.IntStream;

/**
 * Created by jacobmenke on 7/2/17.
 */

class PointDurability{
    public static final Integer UPPERCASE_CHARCTER_DURABILITY_COST = 2;
    public static final Integer LOWERCASE_CHARCTER_DURABILITY_COST = 1;
    public static final Integer DEFAULT_STARTING_POINT_DURABILITY = 100;
}

public class Pencil {

    private Paper paper;
    private Integer pointDurability;

    public Pencil(Integer pointDurability) {
        this.pointDurability = pointDurability;
    }

    public Pencil() {
        pointDurability = PointDurability.DEFAULT_STARTING_POINT_DURABILITY;
    }

    public Integer getPointDurability() {
        return pointDurability;
    }

    public void setPaperToWriteTo(Paper paper) {
        this.paper = paper;
    }

    public void write(String content){
        paper.addContent(content);
        IntStream textCharactersStream = content.chars().filter(c->c != ' ' && c != '\n');

        Integer uppercaseCharacterCount = (int)textCharactersStream.filter(Character::isUpperCase).count();
        //what about punctuation
        Integer lowercaseCharacterCount = (int)content.chars().filter(Character::isLowerCase).count();

        this.pointDurability -= (uppercaseCharacterCount * PointDurability.UPPERCASE_CHARCTER_DURABILITY_COST
                + lowercaseCharacterCount * PointDurability.LOWERCASE_CHARCTER_DURABILITY_COST);


    }
}
