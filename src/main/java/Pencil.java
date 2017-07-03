import java.util.stream.IntStream;

/**
 * Created by jacobmenke on 7/2/17.
 */
class PointDurability {
    public static final Integer UPPERCASE_CHARCTER_DURABILITY_COST = 2;
    public static final Integer LOWERCASE_CHARCTER_DURABILITY_COST = 1;
}

class PencilDefaults {
    public static final Integer DEFAULT_PENCIL_LENGTH = 100;
    public static final Integer DEFAULT_PENCIL_INITIAL_POINT_DURABILITY = 40_000;
}

class SpecialCharacters {
    public static final Character SPACE = ' ';
}

public class Pencil {
    private Paper paper;
    private Integer pointDurability;
    private Integer maximumPointDurability;
    private Integer length;

    public Integer getLength() {
        return length;
    }

    public Pencil(Integer length, Integer pointDurability) {
        this.length = length;
        this.maximumPointDurability = pointDurability;
        this.pointDurability = maximumPointDurability;
    }

    public Pencil(Integer length) {
        this(length, PencilDefaults.DEFAULT_PENCIL_INITIAL_POINT_DURABILITY);
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
     *
     * @param content the textual content to write to the paper
     */
    public void write(String content) {

        if (paper != null) {

            for (int i = 0; i < content.length(); i++) {
                Character currentCharacter = content.charAt(i);
                if (Character.isUpperCase(currentCharacter)) {
                    pointDurability -= PointDurability.UPPERCASE_CHARCTER_DURABILITY_COST;
                }
                //how should punctuation be counted?
                if (Character.isLowerCase(currentCharacter)) {
                    pointDurability -= PointDurability.LOWERCASE_CHARCTER_DURABILITY_COST;
                }

                if (pointDurability >= 0) {
                    paper.addCharacter(currentCharacter);
                } else {
                    paper.addCharacter(SpecialCharacters.SPACE);
                }
            }
        } else {
            System.err.println("No paper to write to.");
        }
    }

    public void sharpen() {
        length--;
        if (length > 0) {
            pointDurability = maximumPointDurability;
        }
    }

    public void erase(String textToErase) {
        if (paper != null) {

            Integer lengthToErase = textToErase.length();
            Integer startindIndex = paper.getContents().lastIndexOf(textToErase);
            Integer endingIndex = startindIndex + lengthToErase;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(paper.getContents().substring(0, startindIndex));
            for (int i = startindIndex; i < endingIndex; i++) {
                stringBuilder.append(SpecialCharacters.SPACE);
            }
            stringBuilder.append(paper.getContents().substring(endingIndex));
            paper.eraseAndSetContents(stringBuilder.toString());
        } else {
            System.err.println("No paper to write to.");
        }
    }
}
