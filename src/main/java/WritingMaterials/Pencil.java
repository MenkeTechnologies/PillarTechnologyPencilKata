package WritingMaterials;

import WritingMaterials.Constants.InvalidIndex;
import WritingMaterials.Constants.PencilDefaults;
import WritingMaterials.Constants.PencilSpecialCharacters;
import WritingMaterials.Constants.PointDurabilityCostConstants;

/**
 * Created by jacobmenke on 7/2/17.
 */



public class Pencil {
    private Paper paper;
    private Integer pointDurability;
    private Integer initialPointDurability;
    private Integer length;
    private Integer eraserDurability;
    private Integer initialEraserDurability;


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


    public void setPaperToWriteTo(Paper paper) {
        this.paper = paper;
    }

    /**
     * writes characters to the paper as long as paper object has been set
     * @param content the textual content to write to the paper
     */
    public void write(String content) {

        if (paper != null) {

            for (int i = 0; i < content.length(); i++) {
                Character currentCharacter = content.charAt(i);

                if (Character.isUpperCase(currentCharacter)) {
                    pointDurability -= PointDurabilityCostConstants.UPPERCASE_CHARACTER_DURABILITY_COST;
                }

                if (Character.isLowerCase(currentCharacter)) {
                    pointDurability -= PointDurabilityCostConstants.LOWERCASE_CHARACTER_DURABILITY_COST;
                }
                //should punctuation decrease point durability? Not clear but defaulting to no
                if (pointDurability >= 0) {
                    paper.addCharacter(currentCharacter);
                } else {
                    paper.addCharacter(PencilSpecialCharacters.SPACE);
                }
            }
        } else {
            System.err.println("No paper to write to.");
        }
    }

    /**
     * sharpens the pencil point restoring point durability and reduces the pencil length
     * if the pencil length is greater than zero
     */
    public void sharpen() {

        if (length > 0) {
            length--;
            pointDurability = initialPointDurability;
        }
    }

    /**
     * erases text from the paper object if it exists
     * @param textToErase the text to erase from the paper object
     */
    public Integer erase(String textToErase) {
        if (paper != null) {

            Integer lengthToErase = textToErase.length();
            Integer startingIndexTextToErase = paper.readPage().lastIndexOf(textToErase);
            Integer endingIndexTextToErase = startingIndexTextToErase + lengthToErase - 1;
            //last erased location defaults to starting index of text to erase
            //if eraser runs out during erase mark that location as last erased location
            Integer lastErasedLocation = startingIndexTextToErase;

            StringBuilder uneditedRegionsStringBuilder = new StringBuilder();
            //head end of text unaffected by erasure
            uneditedRegionsStringBuilder.append(paper.readPage().substring(0, startingIndexTextToErase));
            StringBuilder newStringToReplaceErasedString = new StringBuilder();
            String charactersThatCouldNotBeErasedDueToEraserMalfunction = "";

            //loop backwards from ending index of text to erase until starting index of text to erase
            for (int i = endingIndexTextToErase; i >= startingIndexTextToErase; i--) {
                char currentCharacter = paper.readPage().charAt(i);
                if (eraserDurability > 0) {
                    //if eraser is not expended then add space to replacement string
                    newStringToReplaceErasedString.append(PencilSpecialCharacters.SPACE);
                    if (!Character.isWhitespace(currentCharacter)) {
                        eraserDurability--;
                    }
                } else {
                    //if erase is expended mark this location as last erased location
                    //add remaining characters to new replacement String and exit for loop
                    lastErasedLocation = i;
                    charactersThatCouldNotBeErasedDueToEraserMalfunction = paper.readPage().substring(startingIndexTextToErase, i + 1);
                    break;
                }
            }
            //we appended to replacement string in reverse order so we must reverse this string
            uneditedRegionsStringBuilder.append(charactersThatCouldNotBeErasedDueToEraserMalfunction).
                    append(newStringToReplaceErasedString.reverse());
            //add end part of text not affected by erasure
            uneditedRegionsStringBuilder.append(paper.readPage().substring(endingIndexTextToErase + 1));
            paper.setContent(uneditedRegionsStringBuilder.toString());

            return lastErasedLocation;
        } else {
            System.err.println("No paper to write to.");
        }

        return InvalidIndex.InvalidIndex;
    }

    /**
     * inserts a word at a given location (index) in the paper which the pencil is writing to
     * @param erasureLocation the last erased location for a potential edit to be made
     * @param wordToInsert the word to be inserted in the erased location
     */
    public void edit(Integer erasureLocation, String wordToInsert) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(paper.readPage().substring(0, erasureLocation));

        for (int i = 0; i < wordToInsert.length(); i++) {
            Integer eraseLocationPlusOffset = erasureLocation + i;
            if (Character.isWhitespace(paper.readPage().charAt(eraseLocationPlusOffset))) {
                stringBuilder.append(wordToInsert.charAt(i));
            } else {
                stringBuilder.append(PencilSpecialCharacters.OVERLAP_CHARACTER);
            }
        }

        stringBuilder.append(paper.readPage().substring(erasureLocation + wordToInsert.length()));
        paper.setContent(stringBuilder.toString());
    }

    public Integer getLength() {
        return length;
    }

    public Integer getPointDurability() {
        return pointDurability;
    }
}
