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
    public static final Integer DEFAULT_PENCIL_INITIAL_ERASER_DURABILITY = 40_000;
}

class SpecialCharacters {
    public static final Character SPACE = ' ';
}

public class Pencil {
    private Paper paper;
    private Integer pointDurability;
    private Integer initialPointDurability;
    private Integer length;
    private Integer eraserDurability;
    private Integer initialEraserDurability;

    public Integer getLength() {
        return length;
    }

    public Pencil(Integer length, Integer pointDurability, Integer eraserDurability) {
        this.length = length;
        this.initialPointDurability = pointDurability;
        this.pointDurability = initialPointDurability;
        this.initialEraserDurability = eraserDurability;
        this.eraserDurability = initialEraserDurability;
    }

    public Pencil(Integer length) {
        this(length, PencilDefaults.DEFAULT_PENCIL_INITIAL_POINT_DURABILITY, PencilDefaults.DEFAULT_PENCIL_INITIAL_ERASER_DURABILITY);
    }

    public Pencil(Integer length, Integer eraserDurability) {
        this(length, PencilDefaults.DEFAULT_PENCIL_INITIAL_POINT_DURABILITY, eraserDurability);
    }

    public Integer getPointDurability() {
        return pointDurability;
    }

    public Integer getInitialPointDurability() {
        return initialPointDurability;
    }

    public Integer getEraserDurability() {
        return eraserDurability;
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
            pointDurability = initialPointDurability;
        }
    }

    public void erase(String textToErase) {
        if (paper != null) {

            Integer lengthToErase = textToErase.length();
            Integer startindIndex = paper.getContents().lastIndexOf(textToErase);
            Integer endingIndex = startindIndex + lengthToErase;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(paper.getContents().substring(0, startindIndex));
            for (int i = endingIndex; i > startindIndex; i--) {
                if (eraserDurability > 0) {
                    stringBuilder.append(SpecialCharacters.SPACE);
                    if (!Character.isWhitespace(paper.getContents().charAt(i))) {
                        eraserDurability--;
                    }
                } else {
                    System.err.println("My eraser is worn out. Buy a new pencil!");
                    break;
                }

            }
            stringBuilder.append(paper.getContents().substring(endingIndex));
            paper.eraseAndSetContents(stringBuilder.toString());
        } else {
            System.err.println("No paper to write to.");
        }
    }
}
