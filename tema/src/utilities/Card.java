package utilities;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.CardInput;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ObjectMapper;


public class Card {

    private CardInput card;

    public CardInput getCard() {
        return card;
    }

    public Card(CardInput card) {
        this.card = card;
    }

    public ObjectNode cardNode(CardInput card, String type, ObjectMapper objectMapper) {
        ObjectNode cardNode = objectMapper.createObjectNode();
        cardNode.put("health", card.getHealth());
        if (!type.equals("hero")) {
            cardNode.put("attackDamage", card.getAttackDamage());
        }
        cardNode.put("mana", card.getMana())
                .put("description", card.getDescription())
                .put("name", card.getName())
                .set("colors", getCardColors(card, objectMapper));
        return cardNode;
    }

    public ArrayNode getCardColors(CardInput card, ObjectMapper objectMapper) {
        ArrayNode colorsNode = objectMapper.createArrayNode();
        for (String color : card.getColors()) {
            colorsNode.add(color);
        }
        return colorsNode;
    }
}
