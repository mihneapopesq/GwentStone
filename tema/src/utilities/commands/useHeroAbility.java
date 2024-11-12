package utilities.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;
import utilities.Table;
import utilities.Player;
import utilities.heroes.KingMudface;
import utilities.heroes.LordRoyce;
import utilities.heroes.EmpressThorina;
import utilities.heroes.GeneralKocioraw;
import utilities.Hero;

public class useHeroAbility {
    public void useHeroAbility(ActionsInput action, int playerTurn, Table Table, ObjectMapper objectMapper, ArrayNode output, Player[] player) {
        int affectedRow = action.getAffectedRow();

        if(player[playerTurn].getMana() < player[playerTurn].getHero().getMana()) {
            ObjectNode heroAbilityNode = objectMapper.createObjectNode();
            heroAbilityNode.put("command", "useHeroAbility");
            heroAbilityNode.put("affectedRow", affectedRow);
            heroAbilityNode.put("error", "Not enough mana to use hero's ability.");
            output.add(heroAbilityNode);
            return;
        }

        if(player[playerTurn].getHeroAlreadyAttacked() == 1) {
            ObjectNode heroAbilityNode = objectMapper.createObjectNode();
            heroAbilityNode.put("command", "useHeroAbility");
            heroAbilityNode.put("affectedRow", affectedRow);
            heroAbilityNode.put("error", "Hero has already attacked this turn.");
            output.add(heroAbilityNode);
            return;
        }

        if(player[playerTurn].getHero().getName().equals("Lord Royce") || player[playerTurn].getHero().getName().equals("Empress Thorina")) {
            if((playerTurn == 0 && (affectedRow == 3 || affectedRow == 2)) || (playerTurn == 1 && (affectedRow == 0 || affectedRow == 1))) {
                ObjectNode heroAbilityNode = objectMapper.createObjectNode();
                heroAbilityNode.put("command", "useHeroAbility");
                heroAbilityNode.put("affectedRow", affectedRow);
                heroAbilityNode.put("error", "Selected row does not belong to the enemy.");
                output.add(heroAbilityNode);
                return;
            }
        }
        if(player[playerTurn].getHero().getName().equals("King Mudface") || player[playerTurn].getHero().getName().equals("General Kocioraw")) {
            if((playerTurn == 0 && (affectedRow == 0 || affectedRow == 1)) || (playerTurn == 1 && (affectedRow == 2 || affectedRow == 3))) {
                ObjectNode heroAbilityNode = objectMapper.createObjectNode();
                heroAbilityNode.put("command", "useHeroAbility");
                heroAbilityNode.put("affectedRow", affectedRow);
                heroAbilityNode.put("error", "Selected row does not belong to the current player.");
                output.add(heroAbilityNode);
                return;
            }
        }


        Hero heroInstance = null;
        if (player[playerTurn].getHero().getName().equals("King Mudface")) {
            heroInstance = new KingMudface(player[playerTurn].getHero());
        } else if (player[playerTurn].getHero().getName().equals("Lord Royce")) {
            heroInstance = new LordRoyce(player[playerTurn].getHero());
        } else if (player[playerTurn].getHero().getName().equals("Empress Thorina")) {
            heroInstance = new EmpressThorina(player[playerTurn].getHero());
        } else if (player[playerTurn].getHero().getName().equals("General Kocioraw")) {
            heroInstance = new GeneralKocioraw(player[playerTurn].getHero());
        }

        heroInstance.heroAbility(Table.getTable().get(affectedRow));

        player[playerTurn].setMana(player[playerTurn].getMana() - player[playerTurn].getHero().getMana());
        player[playerTurn].setHeroAlreadyAttacked(1);
    }

}
