package utilities.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;
import utilities.ConstantsConfig;
import utilities.Table;
import utilities.Player;
import utilities.heroes.KingMudface;
import utilities.heroes.LordRoyce;
import utilities.heroes.EmpressThorina;
import utilities.heroes.GeneralKocioraw;
import utilities.Hero;

/**
 * Command for using hero abilities
 */
public class UseHeroAbility {

    /**
     * Executes the hero's ability based on the action and current game state
     *
     * @param action        the action input containing command details
     * @param playerTurn    the index of the current player
     * @param table         the game table
     * @param objectMapper  the object mapper for JSON serialization
     * @param output        the output array node
     * @param player        the array of players
     */
    public void useHeroAbility(final ActionsInput action, final int playerTurn, final Table table,
                               final ObjectMapper objectMapper, final ArrayNode output,
                               final Player[] player) {
        int affectedRow = action.getAffectedRow();

        if (player[playerTurn].getMana() < player[playerTurn].getHero().getMana()) {
            ObjectNode heroAbilityNode = objectMapper.createObjectNode();
            heroAbilityNode.put("command", "useHeroAbility");
            heroAbilityNode.put("affectedRow", affectedRow);
            heroAbilityNode.put("error", "Not enough mana to use hero's ability.");
            output.add(heroAbilityNode);
            return;
        }

        if (player[playerTurn].getHeroAlreadyAttacked() == 1) {
            ObjectNode heroAbilityNode = objectMapper.createObjectNode();
            heroAbilityNode.put("command", "useHeroAbility");
            heroAbilityNode.put("affectedRow", affectedRow);
            heroAbilityNode.put("error", "Hero has already attacked this turn.");
            output.add(heroAbilityNode);
            return;
        }

        if (player[playerTurn].getHero().getName().equals("Lord Royce")
                || player[playerTurn].getHero().getName().equals("Empress Thorina")) {
            if ((playerTurn == 0 && (affectedRow == ConstantsConfig.PLAYER_ONE_BACK_ROW
                    || affectedRow == 2)) || (playerTurn == 1 && (affectedRow == 0
                    || affectedRow == 1))) {
                ObjectNode heroAbilityNode = objectMapper.createObjectNode();
                heroAbilityNode.put("command", "useHeroAbility");
                heroAbilityNode.put("affectedRow", affectedRow);
                heroAbilityNode.put("error", "Selected row does not belong to the enemy.");
                output.add(heroAbilityNode);
                return;
            }
        }
        if (player[playerTurn].getHero().getName().equals("King Mudface")
                || player[playerTurn].getHero().getName().equals("General Kocioraw")) {
            if ((playerTurn == 0 && (affectedRow == 0 || affectedRow == 1))
                    || (playerTurn == 1 && (affectedRow == 2 || affectedRow
                    == ConstantsConfig.PLAYER_ONE_BACK_ROW))) {
                ObjectNode heroAbilityNode = objectMapper.createObjectNode();
                heroAbilityNode.put("command", "useHeroAbility");
                heroAbilityNode.put("affectedRow", affectedRow);
                heroAbilityNode.put("error", "Selected row does not belong to the current player.");
                output.add(heroAbilityNode);
                return;
            }
        }

        Hero heroInstance;
        if (player[playerTurn].getHero().getName().equals("King Mudface")) {
            heroInstance = new KingMudface(player[playerTurn].getHero());
        } else if (player[playerTurn].getHero().getName().equals("Lord Royce")) {
            heroInstance = new LordRoyce(player[playerTurn].getHero());
        } else if (player[playerTurn].getHero().getName().equals("Empress Thorina")) {
            heroInstance = new EmpressThorina(player[playerTurn].getHero());
        } else { // else it is General Kocioraw
            heroInstance = new GeneralKocioraw(player[playerTurn].getHero());
        }

        heroInstance.heroAbility(table.getTable().get(affectedRow));
        player[playerTurn].setMana(player[playerTurn].getMana() - player[playerTurn]
                .getHero().getMana());
        player[playerTurn].setHeroAlreadyAttacked(1);
    }
}
