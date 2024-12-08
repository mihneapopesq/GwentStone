#### Copyright 2024 Popescu Mihnea-Gabriel 321CA

# GwentStone

## Description of the game

GwentStone is a mix of the "Gwent" game from The Witcher 3 and "Hearthstone"
from the World of Warcraft Universe. The game is a card game where two players
face each other in a duel. The goal is to reduce the opponent's hero health points to
zero. Each player has a deck of cards that they can play. In each turn, the respective
player gets to draw the first card from the deck and play it. The cards have different
mana costs and attributes. The players can place as many cards as they want as long as
they have enough mana to place them on the table. Each round the mana increases by one,
reaching a maximum of 10 mana. The player can choose to play the hero's ability or
the cards ability. Every ability is unique. There are 4 minions with abilities and 4 heroes.
The minions which have abilities are: Miraj, TheCursedOne, TheRipper and Disciple. The table is
made out of 4 rows. Player 2 has the first 2 rows and Player 1 the last 2.

- Miraj: "Skyjack" - Swaps his health with an enemies minion health
- TheCursedOne: "Shapeshift" - Swaps an enemies minion health with the attack
- TheRipper: "Weak Knees" - Decreases an enemies minion attack by 2
- Disciple : "God's Plan" - Increases a friendly minion health by 2

The heroes are the following and their ability is applied to a specific row:

- Empress Thorina: "Low Blow" - kills the enemy minion with the highest health  on the affected row
- Lord Royce: "Sub-Zero" - Freezes the cards on the affected row
- King Mudface: "Earth Born" - Increases the health of the friendly minions on the affected
- row by 1
- General Kocioraw: "Blood Thirst" - Increases the attack of the friendly minions on the affected
- row by 1

## Classes and Packages

I partitioned this project in the following way:

- utilities package which has all the classes and packages that this project needs
- commands: holds all the classes that perform actions from the game
- heroes: contains the Hero class which is extended by the 4 heroes the player can play with
- minions contains the Minion class which is extended by the 4 minions with special abilities
- the other classes are being used for the general objects from the game: Card, Hand, Player, Table

## Implementation

The implementation of GwentStone follows an object-oriented approach to provide a clear and
maintainable structure for the game mechanics.

### Game Flow

1. **Initialization**: Players select decks and heroes, and the decks are shuffled by a random seed
2. **Turns**: Players alternate turns, drawing cards and gaining mana each round.
   Mana increases up to a maximum of 10
3. **Card Placement and Abilities**: Players can place cards, use hero abilities, or use
   card abilities during their turn.
4. **Victory Condition**: The game ends when one player's hero health reaches zero

### Key Classes

- **StartGame**: Handles setup, game flow, and managing commands and decks
- **CommandHandler**: Processes player actions, such as placing cards,
  using abilities, and ending turns
- **Player**: Manages the player's deck, mana, hand, and hero
- **Table**: Represents the game board and manages card placements
- **Hero and Minions**: Represent cards with unique abilities that can influence the game

### Future Implementations

- Card Variations : add more minions and heroes to the game
- User Interface : build a GUI for more intuitive gameplay
- AI : implement a simple AI for single player mode
