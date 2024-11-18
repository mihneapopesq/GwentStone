package utilities;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.CardInput;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Represents a card in the game, with its properties and methods
 */
public class Card {

    private final CardInput card;
    private int alreadyAttacked;
    private int isFrozen;


    /**
     * Returns the frozen status of the card
     *
     * @return 1 if the card is frozen, 0 otherwise
     */
    public int getIsFrozen() {
        return isFrozen;
    }

    /**
     * Sets the frozen status of the card
     *
     * @param isFrozen the new frozen status
     */
    public void setIsFrozen(final int isFrozen) {
        this.isFrozen = isFrozen;
    }

    /**
     * Returns whether the card has already attacked or not
     *
     * @return 1 if the card has already attacked, 0 otherwise
     */
    public int getAlreadyAttacked() {
        return alreadyAttacked;
    }

    /**
     * Sets the attacked status of the card
     *
     * @param alreadyAttacked the new attacked status
     */
    public void setAlreadyAttacked(final int alreadyAttacked) {
        this.alreadyAttacked = alreadyAttacked;
    }

    /**
     * Returns the card input associated with this card
     *
     * @return the card input
     */
    public CardInput getCard() {
        return card;
    }

    /**
     * Constructs a card using the specified card input
     *
     * @param card the card input data
     */
    public Card(final CardInput card) {
        this.card = card;
    }

    /**
     * Constructs a card as a copy of another card
     *
     * @param originalCard the card to copy
     */
    public Card(final Card originalCard) {
        this.card = originalCard.getCard();
        this.alreadyAttacked = originalCard.getAlreadyAttacked();
    }

    /**
     * Creates a JSON representation of the card
     *
     * @param inputCard    the card input data
     * @param type         the type of card (hero or others)
     * @param objectMapper the object mapper
     * @return the JSON representation of the card
     */
    public ObjectNode cardNode(final CardInput inputCard,
                               final String type, final ObjectMapper objectMapper) {
        ObjectNode cardNode = objectMapper.createObjectNode();
        cardNode.put("health", inputCard.getHealth());
        if (!type.equals("hero")) {
            cardNode.put("attackDamage", inputCard.getAttackDamage());
        }
        cardNode.put("mana", inputCard.getMana())
                .put("description", inputCard.getDescription())
                .put("name", inputCard.getName())
                .set("colors", getCardColors(inputCard, objectMapper));
        return cardNode;
    }

    /**
     * Returns the colors of the card
     *
     * @param inputCard    the card input data
     * @param objectMapper the object mapper
     * @return the JSON array of card colors
     */
    public ArrayNode getCardColors(final CardInput inputCard, final ObjectMapper objectMapper) {
        ArrayNode colorsNode = objectMapper.createArrayNode();
        for (String color : inputCard.getColors()) {
            colorsNode.add(color);
        }
        return colorsNode;
    }
}
